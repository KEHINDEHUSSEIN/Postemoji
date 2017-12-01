package com.example.zainabishaqmusa.postemoji;

import android.content.ComponentName;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;


public class Religion extends AppCompatActivity {

    private static final String AUTHORITY= "com.example.zainabishaqmusa.postemoji";
    private static final Uri PROVIDER = Uri.parse("content://" + AUTHORITY);

    private static String ASSET_PATHS;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_religion);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getutill();
    }

    public Bitmap ImageViaAssets(String fileName){
        AssetManager assetmanager = getAssets();
        InputStream is = null;
        try{

            is = assetmanager.open(fileName);
        }catch(IOException e){
            e.printStackTrace();
        }
        Bitmap bitmap = BitmapFactory.decodeStream(is);
        return bitmap;
    }


    public  void  getutill(){

        ImageView a = (ImageView) findViewById(R.id.image1);
        a.setImageBitmap (ImageViaAssets("religion/allahu_alam.png"));
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView b = (ImageView) findViewById(R.id.image2);
        b.setImageBitmap (ImageViaAssets("religion/allahuakbar.png"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView c = (ImageView) findViewById(R.id.image3);
        c.setImageBitmap (ImageViaAssets("religion/ashaduallah_ilaha.png"));
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView d = (ImageView) findViewById(R.id.image4);
        d.setImageBitmap (ImageViaAssets("religion/ashaduallah_ilaha1.png"));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView e = (ImageView) findViewById(R.id.image5);
        e.setImageBitmap (ImageViaAssets("religion/assalam_alaikum.png"));
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView f = (ImageView) findViewById(R.id.image6);
        f.setImageBitmap (ImageViaAssets("religion/astaghfirullah.png"));
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView g = (ImageView) findViewById(R.id.image7);
        g.setImageBitmap (ImageViaAssets("religion/barakkallahu_fik.png"));
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView h = (ImageView) findViewById(R.id.image8);
        h.setImageBitmap (ImageViaAssets("religion/bismillah.png"));
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView i = (ImageView) findViewById(R.id.image9);
        i.setImageBitmap (ImageViaAssets("religion/dua2.png"));
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView j = (ImageView) findViewById(R.id.image10);
        j.setImageBitmap (ImageViaAssets("religion/dua3.png"));
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView k = (ImageView) findViewById(R.id.image11);
        k.setImageBitmap (ImageViaAssets("religion/duareligion.png"));
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView l = (ImageView) findViewById(R.id.image12);
        l.setImageBitmap (ImageViaAssets("religion/haram.png"));
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView m = (ImageView) findViewById(R.id.image13);
        m.setImageBitmap (ImageViaAssets("religion/hasbunallahu_wanimal.png"));
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView n = (ImageView) findViewById(R.id.image14);
        n.setImageBitmap (ImageViaAssets("religion/innalillah.png"));
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView o = (ImageView) findViewById(R.id.image15);
        o.setImageBitmap (ImageViaAssets("religion/innalllahi_wainna.png"));
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView p = (ImageView) findViewById(R.id.image16);
        p.setImageBitmap (ImageViaAssets("religion/inshaallah.png"));
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView s = (ImageView) findViewById(R.id.image17);
        s.setImageBitmap (ImageViaAssets("religion/islam.png"));
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView u = (ImageView) findViewById(R.id.image18);
        u.setImageBitmap (ImageViaAssets("religion/jazzakallahkaryran.png"));
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView v = (ImageView) findViewById(R.id.image19);
        v.setImageBitmap (ImageViaAssets("religion/lahaula_wala.png"));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView x = (ImageView) findViewById(R.id.image20);
        x.setImageBitmap (ImageViaAssets("religion/masallah.png"));
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView t = (ImageView) findViewById(R.id.image21);
        t.setImageBitmap (ImageViaAssets("religion/subhallah.png"));
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView y = (ImageView) findViewById(R.id.image22);
        y.setImageBitmap (ImageViaAssets("religion/yaallah.png"));
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    public void  share (){

        String file = "file:///android_asset/food/apple.png";

        // File filePath = new File(file );
        Uri filePath = Uri.fromFile(new File("assets/food/apple.png"));
        final ComponentName name = new ComponentName("com.whatsapp", "com.whatsapp.ContactPicker");
        Intent oShareIntent = new Intent();
        oShareIntent.setComponent(name);
        //oShareIntent.setType("text/plain");
        //oShareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Website : www.google.com");
        oShareIntent.putExtra(Intent.EXTRA_STREAM, filePath);
        oShareIntent.setType("image/png");
        oShareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        Religion.this.startActivity(oShareIntent);

    }

    private Uri getUri() {
        String path=ASSET_PATHS;

        return(PROVIDER
                .buildUpon()
                .appendPath(StreamProvider.getUriPrefix(AUTHORITY))
                .appendPath(path)
                .build());
    }

    public void sendAsset(View v) {
        String path=ASSET_PATHS;
        Intent share=new Intent(Intent.ACTION_SEND);
        String extension=null;
        int i=path.lastIndexOf('.');

        if (i>0) {
            extension=path.substring(i+1);
        }

        if (extension!=null) {
            share.setType(
                    MimeTypeMap.getSingleton().getMimeTypeFromExtension(
                            extension));
        }

        share.putExtra(Intent.EXTRA_STREAM, getUri());
        share.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

        startActivity(Intent.createChooser(share, "Share Asset"));
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Religion.this, Categories.class));
            finish();
        }
        return false;
    }


}
