package sk.dhorvath.scifi;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Base64;
import android.webkit.WebView;

import java.io.ByteArrayOutputStream;

import Utilities.TouchImageView;

public class Obrazok extends Activity {

    @Override
	protected void onCreate(Bundle savedInstanceState) {		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.obrazok);

        Bundle b = getIntent().getExtras();
        String url = b.getString("CosplayURL");

        Bitmap bitmap = SciFi.getBitmapFromMemCache(url);
        TouchImageView tiv = (TouchImageView) findViewById(R.id.TouchImageView);
        tiv.setImageBitmap(bitmap);
	}
	
}
