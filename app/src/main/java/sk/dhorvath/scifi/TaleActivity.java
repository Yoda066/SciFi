package sk.dhorvath.scifi;

import java.util.ArrayList;

import downloadThreads.TaleTextThread;
import entities.Tale;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TaleActivity extends Activity {

	private WebView view;

	private ArrayList<Tale> tales = null;
	private int id;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.tale_view);
		tales = SciFi.getTales();

		Bundle b = getIntent().getExtras();
		id = b.getInt("selectedTale");
		setTale(id);		
	}

	@Override
	public void onBackPressed() {
		view = null;
		clear();
		this.finish();
	}

	private void clear() {
		tales = null;
	}

	public void runDiscussion(View v) {
		Bundle b = new Bundle();
		b.putInt("selectedTale", id);

		Intent intent = new Intent(this, DiscussionActivity.class);
		intent.putExtras(b);
		startActivity(intent);
		b = null;
		intent = null;
	}

	private void setTale(int i) {
		TextView taleName = (TextView) findViewById(R.id.textTitle);
		TextView taleAuthor = (TextView) findViewById(R.id.textAuthor);

		taleName.setText(tales.get(i).getTitle());
		taleAuthor.setText(tales.get(i).getAuthor());

		TextView rating = (TextView) findViewById(R.id.textRating);
		rating.setText(tales.get(i).getRating());

		TextView discussion = (TextView) findViewById(R.id.TextDiscussion);
		discussion.setText("Diskusia: "
				+ String.valueOf(tales.get(i).getDiscusionCount()));

		// because of text justifying
		((LinearLayout) findViewById(R.id.linear)).removeView(view);
		view = new WebView(this);
		view.setVerticalScrollBarEnabled(true);
		((LinearLayout) findViewById(R.id.linear)).addView(view);
		ProgressBar bar = (ProgressBar) findViewById(R.id.progressTaleText);

		TaleTextThread thread = new TaleTextThread();
		thread.setView(view);
		thread.setProgressBar(bar);
		thread.execute(tales.get(i).getLink());
	}
}
