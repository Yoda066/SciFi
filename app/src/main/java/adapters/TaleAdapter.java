package adapters;

import java.util.ArrayList;

import sk.dhorvath.scifi.R;

import entities.Tale;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class TaleAdapter extends ArrayAdapter<Tale> {

	private final Context context;
	private final ArrayList<Tale> tales;

	
	static class ViewHolder {
	    public TextView textTitle;	 
	    public TextView textAuthor;
	    public TextView textRating;
	    public TextView textDiscussions;
	    public TextView textDi;
	  }

	
	public TaleAdapter(Context context, int textViewResourceId,
			ArrayList<Tale> objects) {
		super(context, R.layout.row_layout, objects);
		this.context = context;
		tales = objects; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) { 
		View rowView = convertView;
		if (rowView==null){
			LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);			
			rowView = inflater.inflate(R.layout.row_layout, null);
			ViewHolder viewHolder = new ViewHolder();
			viewHolder.textAuthor=(TextView) rowView.findViewById(R.id.textAuthor);
			viewHolder.textTitle = (TextView) rowView.findViewById(R.id.textTitle);
			viewHolder.textRating = (TextView) rowView.findViewById(R.id.textRating);
			viewHolder.textDiscussions = (TextView) rowView.findViewById(R.id.TextDiscussions);
			viewHolder.textDi = (TextView) rowView.findViewById(R.id.textView1);
			rowView.setTag(viewHolder);
		}
					
		
		ViewHolder holder = (ViewHolder) rowView.getTag();		
	    holder.textTitle.setText(tales.get(position).getTitle());	    	    
	    holder.textAuthor.setText(tales.get(position).getAuthor());
	    holder.textRating.setText("Hodnotenie: "+tales.get(position).getRating());
	    holder.textDiscussions.setText("Diskusia: "+String.valueOf(tales.get(position).getDiscusionCount()));	    
	    holder.textDi.setText(String.valueOf(tales.get(position).getDescription()));
	    
		return rowView;
	}
}

