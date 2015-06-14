package downloadThreads;

import java.io.IOException;
import java.net.SocketTimeoutException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import android.os.AsyncTask;
import android.widget.ArrayAdapter;

import Utilities.GUIController;

public abstract class AbstractThread<T> extends
		AsyncTask<String, Integer, ArrayList<T>> {

	Document doc = null;
	ArrayAdapter<T> adapter;

	protected void connect(String path) throws IOException {
		try {
			doc = Jsoup.connect(path).get();
			doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
		} catch (SocketTimeoutException e) {
			publishProgress(1);
			connect(path);
		}
	}

	@Override
	protected void onProgressUpdate(Integer... integers) {
		if (integers[0] == 1) {
            GUIController.makeToast("Chyba pripojenia, pripájam sa znova");
		}
	}
	
	public void setAdapter( ArrayAdapter<T> ad) {
		adapter=ad;
	}

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        GUIController.startLoad();
    }

    @Override
	protected void onPostExecute(ArrayList<T> result) {
		if (adapter != null) {
			adapter.notifyDataSetChanged();
		}
		GUIController.stopLoad();
	}

}
