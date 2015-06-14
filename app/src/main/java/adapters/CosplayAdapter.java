package adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;

import Utilities.GUIController;
import downloadThreads.ImageDownloader;
import entities.Cosplay;
import sk.dhorvath.scifi.R;
import sk.dhorvath.scifi.SciFi;

/**
 * Created by Master Yoda on 14. 2. 2015.
 */
public class CosplayAdapter extends ArrayAdapter<Cosplay> {

    private final Context context;
    private final ArrayList<Cosplay> cosplays;
    private Bitmap b = null;

    static class ViewHolder {
        public TextView title;
        public TextView description;
        public ImageView picture;
        ProgressBar bar;
    }

    public CosplayAdapter(Context context, int textViewResourceId,
                          ArrayList<Cosplay> objects) {
        super(context, R.layout.cosplay_page_layout, objects);
        this.context = context;
        cosplays = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View rowView = convertView;
        if (rowView == null) {
            LayoutInflater inflater = (LayoutInflater) context
                    .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            rowView = inflater.inflate(R.layout.cosplay_page_layout, null);

            ViewHolder viewHolder = new ViewHolder();
            viewHolder.title = (TextView) rowView
                    .findViewById(R.id.cosplayTitle);
            viewHolder.bar = (ProgressBar) rowView.findViewById(R.id.cosplayProgressBar);
            ImageView iv =(ImageView) rowView.findViewById(R.id.cosplayImage);
            viewHolder.picture = iv;
            viewHolder.description = (TextView) rowView.findViewById(R.id.cosplayDescription);
            rowView.setTag(viewHolder);

            iv.setOnTouchListener(otl);
        }

        ViewHolder holder = (ViewHolder) rowView.getTag();
        holder.title.setText(cosplays.get(position).getTitle());
        holder.description.setText(cosplays.get(position).getDescription());

        // loading of image ;)
        //musim spustit vlakno, kvoli tonu progresbaru. Teda vlakno overuje ci naslo v cachi obrazok
                ImageDownloader id = new ImageDownloader(holder.picture, false);
                id.setProgressBar(holder.bar);
                id.execute(cosplays.get(position).getURL());

        return rowView;
    }

    //this listener only indicates if click was initiated on imageView. Event itself is handled in actually in CosplayActivity
      private View.OnTouchListener otl = new View.OnTouchListener() {
          @Override
          public boolean onTouch(View v, MotionEvent event) {
              if (event.getAction()==MotionEvent.ACTION_DOWN || event.getAction()==MotionEvent.ACTION_UP){
                  SciFi.clickedOnPicture = true;
              }
              return false;
          }
      };
}
