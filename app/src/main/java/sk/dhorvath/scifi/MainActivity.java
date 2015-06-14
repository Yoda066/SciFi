package sk.dhorvath.scifi;

import Utilities.CheckNetworkConnection;
import Utilities.GUIController;
import adapters.TaleAdapter;

import android.app.ActionBar;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.app.ActionBar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class MainActivity extends Activity {

	ListView listView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
        GUIController.setContext(this);
		super.onCreate(savedInstanceState);
	    checkConnection();
        //TODO pridat menu
	}

	private void checkConnection() {
		if (CheckNetworkConnection.isConnectionAvailable(this)) {
            inicialize();
			if (SciFi.getCurrentPage() <= 1) {
                SciFi.loadFirstPage();
			}
			showCosplay();
		} else
			setContentView(R.layout.no_internet);
	}

    private void inicialize(){
        setContentView(R.layout.activity_main);
        GUIController.setProgressBar((ProgressBar) findViewById(R.id.progressMain));

        listView = (ListView) findViewById(R.id.ListView1);

        TaleAdapter taleAdapter = SciFi.getAdapter(this);
        inflate();
        listView.setAdapter(taleAdapter);
        listView.setOnItemClickListener(new OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1,
                                    int position, long arg3) {
                showTale(position);
            }
        });

        taleAdapter.notifyDataSetChanged();
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

	public void runDiscussion(View v) {
		ListView listView = (ListView) findViewById(R.id.ListView1);
		showDiscussion(listView.getPositionForView(v) - 1);
	}

	// code below stops reloading when orientation is changed
	@Override
	public void onConfigurationChanged(Configuration newConfig) {
		super.onConfigurationChanged(newConfig);
	}
	
	public void checkConnection(View v) {
		checkConnection();
	}

	public void previousPage(View v) {
        SciFi.loadPreviousPage();
	}

	public void nextPage(View v) {
        SciFi.loadNextPage();
	}

	private void showTale(int i) {
		Bundle b = new Bundle();
		b.putInt("selectedTale", i - 1);
		Intent intent = new Intent(this, TaleActivity.class);
		intent.putExtras(b);
		startActivity(intent);
    }


	private void showDiscussion(int i) {
		Bundle b = new Bundle();
		b.putInt("selectedTale", i);

		Intent intent = new Intent(this, DiscussionActivity.class);
		intent.putExtras(b);
		startActivity(intent);
	}

	private void showCosplay() {
		//Intent intent = new Intent(this, CosplayActivity.class);
        Intent intent = new Intent(this, CosplayActivity.class);
		startActivity(intent);
	}

	@Override
        public boolean onCreateOptionsMenu(Menu menu) {
            MenuInflater inflater = getMenuInflater();
            inflater.inflate(R.menu.activity_main, menu);
            return super.onCreateOptionsMenu(menu);
        }


}
