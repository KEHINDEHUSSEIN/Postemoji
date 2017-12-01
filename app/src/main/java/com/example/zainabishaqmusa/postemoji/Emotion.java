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
import android.widget.ImageView;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

public class Emotion extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_emotion);
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
        a.setImageBitmap (ImageViaAssets("emotions/angry.png"));
        a.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView b = (ImageView) findViewById(R.id.image2);
        b.setImageBitmap (ImageViaAssets("emotions/beatupsmile.png"));
        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView c = (ImageView) findViewById(R.id.image3);
        c.setImageBitmap (ImageViaAssets("emotions/blackout2.png"));
        c.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView d = (ImageView) findViewById(R.id.image4);
        d.setImageBitmap (ImageViaAssets("emotions/bluelaugh.png"));
        d.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView e = (ImageView) findViewById(R.id.image5);
        e.setImageBitmap (ImageViaAssets("emotions/bluesquint.png"));
        e.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView f = (ImageView) findViewById(R.id.image6);
        f.setImageBitmap (ImageViaAssets("emotions/boo.png"));
        f.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView g = (ImageView) findViewById(R.id.image7);
        g.setImageBitmap (ImageViaAssets("emotions/cold.png"));
        g.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView h = (ImageView) findViewById(R.id.image8);
        h.setImageBitmap (ImageViaAssets("emotions/confident.png"));
        h.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView i = (ImageView) findViewById(R.id.image9);
        i.setImageBitmap (ImageViaAssets("emotions/cryblue.png"));
        i.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView j = (ImageView) findViewById(R.id.image10);
        j.setImageBitmap (ImageViaAssets("emotions/depressed.png"));
        j.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView k = (ImageView) findViewById(R.id.image11);
        k.setImageBitmap (ImageViaAssets("emotions/dissapointed.png"));
        k.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView l = (ImageView) findViewById(R.id.image12);
        l.setImageBitmap (ImageViaAssets("emotions/eeeew.png"));
        l.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView m = (ImageView) findViewById(R.id.image13);
        m.setImageBitmap (ImageViaAssets("emotions/emotions.png"));
        m.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView n = (ImageView) findViewById(R.id.image14);
        n.setImageBitmap (ImageViaAssets("emotions/glasses_bigsmile.png"));
        n.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView o = (ImageView) findViewById(R.id.image15);
        o.setImageBitmap (ImageViaAssets("emotions/glasses_frown.png"));
        o.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView p = (ImageView) findViewById(R.id.image16);
        p.setImageBitmap (ImageViaAssets("emotions/great.png"));
        p.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView s = (ImageView) findViewById(R.id.image17);
        s.setImageBitmap (ImageViaAssets("emotions/grin.png"));
        s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView u = (ImageView) findViewById(R.id.image18);
        u.setImageBitmap (ImageViaAssets("emotions/hehehehe.png"));
        u.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView v = (ImageView) findViewById(R.id.image19);
        v.setImageBitmap (ImageViaAssets("emotions/hot.png"));
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView x = (ImageView) findViewById(R.id.image20);
        x.setImageBitmap (ImageViaAssets("emotions/hungry.png"));
        x.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView t = (ImageView) findViewById(R.id.image21);
        t.setImageBitmap (ImageViaAssets("emotions/i_feel_blessed.png"));
        t.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView y = (ImageView) findViewById(R.id.image22);
        y.setImageBitmap (ImageViaAssets("emotions/lier.png"));
        y.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView q = (ImageView) findViewById(R.id.image23);
        q.setImageBitmap (ImageViaAssets("emotions/lonely.png"));
        q.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView w = (ImageView) findViewById(R.id.image24);
        w.setImageBitmap (ImageViaAssets("emotions/mad.png"));
        w.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView r = (ImageView) findViewById(R.id.image25);
        r.setImageBitmap (ImageViaAssets("emotions/shy_smile.png"));
        r.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView z = (ImageView) findViewById(R.id.image26);
        z.setImageBitmap (ImageViaAssets("emotions/sick.png"));
        z.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView a1 = (ImageView) findViewById(R.id.image27);
        a1.setImageBitmap (ImageViaAssets("emotions/silly_face.png"));
        a1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView a2 = (ImageView) findViewById(R.id.image28);
        a2.setImageBitmap (ImageViaAssets("emotions/sleepy.png"));
        a2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView a3 = (ImageView) findViewById(R.id.image29);
        a3.setImageBitmap (ImageViaAssets("emotions/toothless_smile.png"));
        a3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });

        ImageView a4 = (ImageView) findViewById(R.id.image30);
        a4.setImageBitmap (ImageViaAssets("emotions/uncomfortone.png"));
        a4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                share();
            }
        });
    }

    public void  share (){
         /* String file = "food/apple.png";
                Uri theUri = Uri.parse("content://com.example.zainabishaqmusa.postemoji/" + file );
                Intent theIntent = new Intent(Intent.ACTION_SEND);
                theIntent.setType("image/*");
                theIntent.putExtra(Intent.EXTRA_STREAM,theUri);
                theIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"Subject for message");
                theIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Body for message");
                startActivity(theIntent);*/



        //Drawable m = a.getDrawable();
        //Bitmap bitmap =  ((BitmapDrawable) m).getBitmap();
        //String path = MediaStore.Images.Media.insertImage(getContentResolver(), bitmap, "food/apple.png", null);
        //Uri uri = Uri.parse(path);

        //String file = "food/apple.png";
        //Uri uri = Uri.parse("content://com.example.zainabishaqmusa.postemoji/" + file );
                /*Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_STREAM, R.drawable.apple);
                shareIntent.setType("image/png");
                startActivity(Intent.createChooser(shareIntent, " "));*/

        String file = "file:///android_asset/food/apple.png";
        //Uri theUri = Uri.parse("content://com.example.zainabishaqmusa.postemoji/" + file );
        // Intent theIntent = new Intent(Intent.ACTION_SEND);
        //theIntent.setType("image/*");
        //theIntent.putExtra(Intent.EXTRA_STREAM,theUri);
        //theIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        //theIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,"");
        //theIntent.putExtra(android.content.Intent.EXTRA_TEXT, "");
        //startActivity(theIntent);

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
        Emotion.this.startActivity(oShareIntent);

    }



    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            startActivity(new Intent(Emotion.this, Categories.class));
            finish();
        }
        return false;
    }

}
