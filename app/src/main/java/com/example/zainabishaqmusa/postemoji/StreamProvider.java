package com.example.zainabishaqmusa.postemoji;

/**
 * Created by developer on 11/3/17.
 */


import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.content.res.AssetFileDescriptor;
import android.content.res.XmlResourceParser;
import android.database.Cursor;
import android.database.MatrixCursor;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.provider.OpenableColumns;
import android.text.TextUtils;
import android.util.Log;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.SoftReference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import org.xmlpull.v1.XmlPullParserException;

/**
 * ContentProvider, based on Google's FileProvider, that can
 * serve up stream content from a variety of data sources,
 * described in the form of StreamStrategy objects.
 */
public class StreamProvider extends ContentProvider {
    private static final String[] COLUMNS= {
            OpenableColumns.DISPLAY_NAME, OpenableColumns.SIZE };
    private static final String[] VALID_DIRS={
            Environment.DIRECTORY_ALARMS,
            Environment.DIRECTORY_DCIM,
            "Documents",
            Environment.DIRECTORY_DOWNLOADS,
            Environment.DIRECTORY_MOVIES,
            Environment.DIRECTORY_MUSIC,
            Environment.DIRECTORY_NOTIFICATIONS,
            Environment.DIRECTORY_PICTURES,
            Environment.DIRECTORY_PODCASTS,
            Environment.DIRECTORY_RINGTONES
    };
    private static final String META_DATA_FILE_PROVIDER_PATHS=
            "com.commonsware.cwac.provider.STREAM_PROVIDER_PATHS";
    private static final String META_DATA_USE_LEGACY_CURSOR_WRAPPER=
            "com.commonsware.cwac.provider.USE_LEGACY_CURSOR_WRAPPER";
    private static final String META_DATA_USE_URI_FOR_DATA_COLUMN=
            "com.commonsware.cwac.provider.USE_URI_FOR_DATA_COLUMN";
    private static final String TAG_FILES_PATH="files-path";
    private static final String TAG_CACHE_PATH="cache-path";
    private static final String TAG_EXTERNAL="external-path";
    private static final String TAG_EXTERNAL_FILES="external-files-path";
    private static final String TAG_EXTERNAL_CACHE_FILES=
            "external-cache-path";
    private static final String TAG_EXTERNAL_PUBLIC_FILES=
            "external-public-path";
    private static final String TAG_RAW="raw-resource";
    private static final String TAG_ASSET="asset";
    private static final String TAG_DIR_PATH="dir-path";
    private static final String ATTR_NAME="name";
    private static final String ATTR_PATH="path";
    private static final String ATTR_READ_ONLY="readOnly";
    private static final String ATTR_DIR="dir";
    private static final String PREF_URI_PREFIX="uriPrefix";

    static {
        Arrays.sort(VALID_DIRS);
    }

    private static ConcurrentHashMap<String, SoftReference<StreamProvider>> INSTANCES=
            new ConcurrentHashMap<String, SoftReference<StreamProvider>>();
    private CompositeStreamStrategy strategy;
    private boolean useLegacyCursorWrapper=false;
    private boolean useUriForDataColumn=false;
    private SharedPreferences prefs;
    private boolean seenExternalFilesPathNoDir=false;
    private boolean seenExternalFilesPathWithDir=false;
    private boolean allReadOnly=false;

    /**
     * Registers a StreamProvider for use with getUriForFile()
     *
     * @param provider a StreamProvider instance
     * @throws PackageManager.NameNotFoundException
     */
    private static void putInstance(StreamProvider provider)
            throws PackageManager.NameNotFoundException {
        PackageManager pm=provider.getContext().getPackageManager();
        PackageInfo pkg=
                pm.getPackageInfo(provider.getContext().getPackageName(),
                        PackageManager.GET_PROVIDERS);

        for (ProviderInfo pi : pkg.providers) {
            if (provider.getClass().getCanonicalName().equals(pi.name)) {
                for (String authority : pi.authority.split(";")) {
                    INSTANCES.put(authority,
                            new SoftReference<StreamProvider>(provider));
                }
            }
        }
    }

    /**
     * As with the FileProvider equivalent, attempts to find a Uri
     * that would serve the associated file
     *
     * @param authority the authority string of the provider
     * @param file the File to be served
     * @return the Uri pointing to that File, or null if the file
     * does not seem to be related to the named provider
     */
    public static Uri getUriForFile(String authority, File file) {
        SoftReference<StreamProvider> ref=INSTANCES.get(authority);
        Uri result=null;

        if (ref!=null) {
            result=ref.get().getUriForFileImpl(authority, file);
        }

        return(result);
    }

    /**
     * Returns the Uri prefix used by this StreamProvider, identified
     * by its authority. This prefix goes after the authority and
     * before the rest of the path.
     *
     * @param authority the authority string of the provider
     * @return the Uri prefix, or null if there is no prefix
     */
    public static String getUriPrefix(String authority) {
        SoftReference<StreamProvider> ref=INSTANCES.get(authority);
        String result=null;

        if (ref!=null) {
            result=ref.get().getUriPrefix();
        }

        return(result);
    }


    /**
     * {@inheritDoc}
     */
    @Override
    public boolean onCreate() {
        try {
            putInstance(this);

            prefs=
                    getContext()
                            .getSharedPreferences(BuildConfig.APPLICATION_ID,
                                    Context.MODE_PRIVATE);

            return(true);
        }
        catch (PackageManager.NameNotFoundException e) {
            Log.e(getClass().getSimpleName(), "Exception caching self", e);
        }

        return(false);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void attachInfo(Context context, ProviderInfo info) {
        super.attachInfo(context, info);

        checkSecurity(info);

        try {
            strategy=new CompositeStreamStrategy();

            String[] authorities=info.authority.split(";");

            for (String authority : authorities) {
                parseStreamStrategy(strategy, context, authority);
            }
        }
        catch (Exception e) {
            throw new IllegalArgumentException("Failed to parse "
                    + META_DATA_FILE_PROVIDER_PATHS + " meta-data", e);
        }
    }

    /**
     * Confirm that our security settings are apropos. In this case,
     * we do not support being exported. If we are, mark the provider
     * as read-only and log a warning to LogCat.
     *
     * @param info
     */
    protected void checkSecurity(ProviderInfo info) {
        if (info.exported) {
            allReadOnly=true;
            Log.w(getClass().getSimpleName(), "Marking exported StreamProvider as read-only");
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Cursor query(Uri uri, String[] projection, String selection,
                        String[] selectionArgs, String sortOrder) {
        Uri normalized=normalize(uri);

        if (projection == null) {
            projection=COLUMNS;
        }

        String[] cols=new String[projection.length];
        Object[] values=new Object[projection.length];
        int i=0;

        for (String col : projection) {
            Object value=getValueForQueryColumn(normalized, col);

            if (value!=null) {
                cols[i]=col;
                values[i++]=value;
            }
        }

        cols=copyOf(cols, i);
        values=copyOf(values, i);

        final MatrixCursor cursor=new MatrixCursor(cols, 1);

        cursor.addRow(values);

        if (!useLegacyCursorWrapper) {
            return(cursor);
        }

        return(new LegacyCompatCursorWrapper(cursor, getType(uri),
                useUriForDataColumn ? uri : null));
    }

    /**
     * Called to get the value to return for a given requested
     * column in a query() call. If it is not some column that
     * you are handling, please chain to the superclass implementation,
     * so StreamProvider can handle the standard ones.
     *
     * @param uri the Uri to the content
     * @param col the name of the column requested in the query()
     * @return the value to return (typically a String, int, or long)
     */
    protected Object getValueForQueryColumn(Uri uri, String col) {
        Object result=null;

        if (OpenableColumns.DISPLAY_NAME.equals(col)) {
            result=strategy.getName(uri);
        }
        else if (OpenableColumns.SIZE.equals(col)) {
            result=strategy.getLength(uri);
        }

        return(result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String getType(Uri uri) {
        String result=strategy.getType(normalize(uri));

        return(result == null ? "application/octet-stream" : result);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        uri=normalize(uri);

        if (strategy.canInsert(uri)) {
            return(strategy.insert(uri, values));
        }

        throw new UnsupportedOperationException("No external inserts");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int update(Uri uri, ContentValues values, String selection,
                      String[] selectionArgs) {
        uri=normalize(uri);

        if (strategy.canUpdate(uri)) {
            return(strategy.update(uri, values, selection, selectionArgs));
        }

        throw new UnsupportedOperationException("No external updates");
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        uri=normalize(uri);

        if (strategy.canDelete(uri)) {
            strategy.delete(uri);
            return(1);
        }

        return(0);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public ParcelFileDescriptor openFile(Uri uri, String mode)
            throws FileNotFoundException {
        return(strategy.openFile(normalize(uri), mode));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public AssetFileDescriptor openAssetFile(Uri uri, String mode)
            throws FileNotFoundException {
        Uri normalized=normalize(uri);

        if (strategy.hasAFD(normalized)) {
            return(strategy.openAssetFile(normalized, mode));
        }

        return(super.openAssetFile(uri, mode));
    }

    private CompositeStreamStrategy parseStreamStrategy(final CompositeStreamStrategy result,
                                                        Context context,
                                                        String authority)
            throws IOException, XmlPullParserException {
        final ProviderInfo info=
                context.getPackageManager()
                        .resolveContentProvider(authority,
                                PackageManager.GET_META_DATA);

        useLegacyCursorWrapper=info.metaData.getBoolean(META_DATA_USE_LEGACY_CURSOR_WRAPPER, true);
        useUriForDataColumn=info.metaData.getBoolean(META_DATA_USE_URI_FOR_DATA_COLUMN, false);

        final XmlResourceParser in=
                info.loadXmlMetaData(context.getPackageManager(),
                        META_DATA_FILE_PROVIDER_PATHS);

        if (in == null) {
            throw new IllegalArgumentException("Missing "
                    + META_DATA_FILE_PROVIDER_PATHS + " meta-data");
        }

        int type;

        while ((type=in.next()) != org.xmlpull.v1.XmlPullParser.END_DOCUMENT) {
            if (type == org.xmlpull.v1.XmlPullParser.START_TAG) {
                final String tag=in.getName();

                if (!"paths".equals(tag)) {
                    final String name=in.getAttributeValue(null, ATTR_NAME);

                    if (TextUtils.isEmpty(name)) {
                        throw new IllegalArgumentException("Name must not be empty");
                    }

                    String path=in.getAttributeValue(null, ATTR_PATH);
                    boolean readOnly=allReadOnly ||
                            Boolean.parseBoolean(in.getAttributeValue(null, ATTR_READ_ONLY));
                    HashMap<String, String> attrs=new HashMap<String, String>();

                    for (int i=0;i<in.getAttributeCount();i++) {
                        attrs.put(in.getAttributeName(i), in.getAttributeValue(i));
                    }

                    StreamStrategy strategy=
                            buildStrategy(context, tag, name, path, readOnly, attrs);

                    if (strategy != null) {
                        result.add(name, strategy);
                    }
                    else {
                        throw new IllegalArgumentException("Could not build strategy for "
                                + tag);
                    }
                }
            }
        }

        return(result);
    }

    /**
     * @return the prefix to use on Uri values from this provider,
     * to help prevent "Surreptitious Sharing" attacks, or null if
     * there should be no such prefix (default behavior: generated
     * UUID for this installed provider)
     */
    protected String getUriPrefix() {
        String prefix=prefs.getString(PREF_URI_PREFIX, null);

        if (prefix==null) {
            prefix=buildUriPrefix();
            prefs.edit().putString(PREF_URI_PREFIX, prefix).apply();
        }

        return(prefix);
    }

    /**
     * @return the prefix to be used by getUriPrefix() for this
     * app installation, or null if there should be no such prefix
     */
    protected String buildUriPrefix() {
        return(UUID.randomUUID().toString());
    }

    /**
     * Given information about a tag in the XML metadata, build
     * a configured StreamStrategy to handle it. Subclasses can
     * override this, handle their own tags, and chain to the
     * superclass for unrecognized tags.
     *
     * @param context a Context, because you might need one
     * @param tag the tag name of the child element of <paths>
     * @param name the value of the name attribute
     * @param path the value of the path attribute, if any
     * @param attrs all attributes
     * @return a StreamStrategy capable of handling this configuration
     * @throws IOException
     */
    protected StreamStrategy buildStrategy(Context context, String tag,
                                           String name, String path,
                                           boolean readOnly,
                                           HashMap<String, String> attrs)
            throws IOException {
        StreamStrategy result;

        if (TAG_RAW.equals(tag)) {
            return(new RawResourceStrategy(context, path));
        }
        else if (TAG_ASSET.equals(tag)) {
            return(new AssetStrategy(context, path));
        }
        else {
            result=buildLocalStrategy(context, tag, name, path, readOnly, attrs);
        }

        return(result);
    }

    private StreamStrategy buildLocalStrategy(Context context,
                                              String tag, String name,
                                              String path, boolean readOnly,
                                              HashMap<String, String> attrs)
            throws IOException {
        File target=null;

        if (TAG_FILES_PATH.equals(tag)) {
            if (TextUtils.isEmpty(path)) {
                throw new
                        SecurityException("Cannot serve files from all of getFilesDir()");
            }

            target=buildPath(context.getFilesDir(), path);
        }
        else if (TAG_DIR_PATH.equals(tag)) {
            if (TextUtils.isEmpty(path)) {
                throw new
                        SecurityException("Cannot serve files from all of getDir()");
            }

            String dir=attrs.get(ATTR_DIR);

            if (TextUtils.isEmpty(dir)) {
                throw new
                        SecurityException("You need to provide the dir attribute, to indicate which directory to serve");
            }

            target=buildPath(context.getDir(dir, Context.MODE_PRIVATE), path);
        }
        else if (TAG_CACHE_PATH.equals(tag)) {
            target=buildPath(context.getCacheDir(), path);
        }
        else if (TAG_EXTERNAL.equals(tag)) {
            target=buildPath(Environment.getExternalStorageDirectory(), path);
        }
        else if (TAG_EXTERNAL_FILES.equals(tag)) {
            String dir=attrs.get(ATTR_DIR);

            if (TextUtils.isEmpty(dir)) {
                dir=null;
            }

            if (dir!=null && Arrays.binarySearch(VALID_DIRS, dir)<0) {
                throw new SecurityException(dir+" is not a valid value, either leave off or choose from: "+TextUtils.join(",", VALID_DIRS));
            }

            if (dir==null) {
                seenExternalFilesPathNoDir=true;
            }
            else {
                seenExternalFilesPathWithDir=true;
            }

            if (seenExternalFilesPathNoDir && seenExternalFilesPathWithDir) {
                throw new IllegalStateException("Cannot have <external-files-path> without dir attribute and another <external-files-path> with a dir attribute");
            }

            target=buildPath(context.getExternalFilesDir(dir), path);
        }
        else if (TAG_EXTERNAL_CACHE_FILES.equals(tag)) {
            target=buildPath(context.getExternalCacheDir(), path);
        }
        else if (TAG_EXTERNAL_PUBLIC_FILES.equals(tag)) {
            String dir=attrs.get(ATTR_DIR);

            if (TextUtils.isEmpty(dir)) {
                throw new
                        SecurityException("You need to provide the dir attribute, to indicate which directory to serve, from a valid Environment value");
            }

            if (Arrays.binarySearch(VALID_DIRS, dir)<0) {
                throw new SecurityException(dir+" is not a valid value, choose from: "+TextUtils.join(",", VALID_DIRS));
            }

            target=buildPath(Environment.getExternalStoragePublicDirectory(dir), path);
        }

        if (target != null) {
            return(new LocalPathStrategy(name, target, readOnly));
        }

        return(null);
    }

    /**
     * @return an instance of CompositeStreamStrategy, or some
     * subclass -- override this if you want custom behavior
     * at resolution time
     */
    protected CompositeStreamStrategy buildCompositeStrategy() {
        return(new CompositeStreamStrategy());
    }

    /**
     * @return the CompositeStreamStrategy created by buildStrategy(),
     * which will be used for handling all incoming Uri requests and
     * operations
     */
    protected CompositeStreamStrategy getRootStrategy() {
        return(strategy);
    }

    private Uri getUriForFileImpl(String authority, File file) {
        Uri.Builder b=new Uri.Builder();

        b.scheme("content").authority(authority);

        String prefix=getUriPrefix();

        if (prefix!=null) {
            b.appendPath(prefix);
        }

        if (strategy.buildUriForFile(b, file)) {
            return(b.build());
        }

        return(null);
    }

    private Uri normalize(Uri input) {
        String prefix=getUriPrefix();

        if (prefix==null) {
            return(input);
        }

        List<String> segments=new ArrayList<String>(input.getPathSegments());

        if (getUriPrefix().equals(segments.get(0))) {
            segments.remove(0);

            return(input
                    .buildUpon()
                    .path(TextUtils.join("/", segments))
                    .build());
        }

        throw new IllegalArgumentException("Unrecognized Uri: "+input.toString());
    }

    public static File buildPath(File base, String... segments) {
        File cur=base;

        for (String segment : segments) {
            if (segment != null) {
                cur=new File(cur, segment);
            }
        }

        return(cur);
    }

    private static String[] copyOf(String[] original, int newLength) {
        final String[] result=new String[newLength];

        System.arraycopy(original, 0, result, 0, newLength);

        return(result);
    }

    private static Object[] copyOf(Object[] original, int newLength) {
        final Object[] result=new Object[newLength];

        System.arraycopy(original, 0, result, 0, newLength);

        return(result);
    }
}