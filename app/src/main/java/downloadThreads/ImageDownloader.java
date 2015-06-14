package downloadThreads;

import java.io.InputStream;

import Utilities.GUIController;
import sk.dhorvath.scifi.SciFi;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;

public class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
	private ImageView bmImage;
	private boolean miniature = false;
	private Bitmap mIcon;
    private ProgressBar bar;

    public ImageDownloader(ImageView bmImage, boolean miniature) {
		this.bmImage = bmImage;
		this.miniature = miniature;
	}

	protected Bitmap doInBackground(String... urls) {
		String url = urls[0];
        if(url==null) return null;
		mIcon = SciFi.getBitmapFromMemCache(url);
		if (mIcon != null){
			return mIcon;}
		else {
			try {
				InputStream in = new java.net.URL(url).openStream();
				mIcon = BitmapFactory.decodeStream(in);
			} catch (Exception e) {
				Log.e("Error", e.getMessage());
			}
			// "RECREATE" THE NEW BITMAP
			if (miniature) {
				mIcon = Bitmap.createScaledBitmap(mIcon, 60, 60, true);
			}
//            else {
//                double ratio = mIcon.getWidth()/(double)mIcon.getHeight();
//                mIcon = Bitmap.createScaledBitmap(mIcon, 800, (int)(800/ratio), true);
//            }
			SciFi.addBitmapToCache(url, mIcon);
			return mIcon;
		}
	}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        if (bar!=null) bar.setVisibility(View.VISIBLE);
    }

    protected void onPostExecute(Bitmap result) {
		if (bmImage!=null && result!=null) bmImage.setImageBitmap(result);
        if (bar!=null) bar.setVisibility(View.GONE);
	}

    public void setProgressBar(ProgressBar bar) {
        this.bar= bar;
    }
}