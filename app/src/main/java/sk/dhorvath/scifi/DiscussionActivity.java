package sk.dhorvath.scifi;

import Utilities.GUIController;
import adapters.CommentAdapter;
import android.app.Activity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;

public class DiscussionActivity extends Activity {

	int currentPage = 0;
	int id = 0;
	ListView listView;	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
        GUIController.setProgressBar((ProgressBar) findViewById(R.id.progressMain));
		Bundle b = getIntent().getExtras();
		id = b.getInt("selectedTale");				
		
		listView = (ListView) findViewById(R.id.ListView1);

        CommentAdapter commentAdapter = SciFi.getCommentAdapter(this);

		inflate();
		listView.setAdapter(commentAdapter);
		SciFi.getDiscusion(id, currentPage);
	}

	private void inflate() {
		LayoutInflater inflater = getLayoutInflater();
		ViewGroup header = (ViewGroup) inflater.inflate(R.layout.header,
				listView, false);
		listView.addHeaderView(header, null, false);
		ViewGroup footer = (ViewGroup) inflater.inflate(R.layout.footer,
				listView, false);
		listView.addFooterView(footer, null, false);
	}

	public void previousPage(View v) {
		if (currentPage == 0)
			return;
        SciFi.getDiscusion(id, --currentPage);
		listView.setSelection(10);
	}

	public void nextPage(View v) {
		if (currentPage == SciFi.getTales().get(id).getDiscusionCount() / 10)
			return;
        SciFi.getDiscusion(id, ++currentPage);
		listView.setSelectionAfterHeaderView();
	}

	@Override
	public void onBackPressed() {
        SciFi.clearDiscussion();
		finish();
	}

}
