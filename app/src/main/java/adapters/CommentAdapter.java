package adapters;

import java.util.ArrayList;

import sk.dhorvath.scifi.R;
import sk.dhorvath.scifi.SciFi;
import downloadThreads.ImageDownloader;

import entities.Comment;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CommentAdapter extends ArrayAdapter<Comment> {

	private final Context context;
	private final ArrayList<Comment> comments;
	private Bitmap b = null;

	static class ViewHolder {
        public TextView author;
        public TextView body;
        public ImageView icon;
    }

	public CommentAdapter(Context context, int textViewResourceId,
			ArrayList<Comment> objects) {
		super(context, R.layout.row_layout, objects);
		this.context = context;
		comments = objects;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View rowView = convertView;
		if (rowView == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			rowView = inflater.inflate(R.layout.discussion_layout, null);

			ViewHolder viewHolder = new ViewHolder();
			viewHolder.author = (TextView) rowView
					.findViewById(R.id.CommentAuthor);
			viewHolder.icon = (ImageView) rowView.findViewById(R.id.imageView1);
			viewHolder.body = (TextView) rowView.findViewById(R.id.CommentText);
			rowView.setTag(viewHolder);
		}

		ViewHolder holder = (ViewHolder) rowView.getTag();
		holder.author.setText(comments.get(position).getAuthor());
		holder.body.setText(comments.get(position).getText());

		// loading of image ;)
		if (comments.get(position).getImagePath() != null) {

			b = SciFi.getBitmapFromMemCache(comments.get(position)
					.getImagePath());
			if (b != null) {
				holder.icon.setImageBitmap(b);
			} else {
				ImageDownloader id = new ImageDownloader(holder.icon, true);
				id.execute(comments.get(position).getImagePath());
				id = null;
			}
		}
		return rowView;
	}
}
