package downloadThreads;

import java.io.IOException;
import java.net.SocketTimeoutException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import Utilities.GUIController;
import sk.dhorvath.scifi.SciFi;

import android.os.AsyncTask;
import android.view.View;
import android.webkit.WebView;
import android.widget.ProgressBar;

public class TaleTextThread extends AsyncTask<String, Integer, String> {

	WebView view = null;
	ProgressBar bar = null;
	private Document doc = null;

	public void setView(WebView view) {
		this.view = view;
	}

	public void setProgressBar(ProgressBar b) {
		bar = b;
	}

	@Override
	protected String doInBackground(String... params) {
		String result = SciFi.getTaleText(params[0]);
		if (result == null)
		{
			StringBuilder builder = new StringBuilder();						
			try {
				connect(params[0]);
			} catch (IOException ie) {
				ie.printStackTrace();
			}

			if (doc == null)
				return result;
			Elements lines = doc.select(".snt");

			// Ak nesiel za snt tagom obrazok, zacina sa tu poviedka.
			// Osobitne overujem.

			if (!(lines.first().childNodes().get(0).toString().length() == 1)) {

				builder.append(lines.first().childNodes().get(0).toString());
			}

			for (int j = 2; j < lines.first().children().size(); j++) {
				builder.append("<br clear=/\both\" />"
						+ (lines.first().child(j).nextSibling().toString()));
			}
			doc = null;
			builder.insert(0, "<html><head><meta \"charset=UTF-8\" /></head><body style=\"text-align:justify;background-color:#E6E6E6\">");
			builder.append("</body></html>");
			result = builder.toString();
			SciFi.addTaleText(params[0], result);
		}
		return result;
	}

	private void connect(String path) throws IOException {		
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

	@Override
	protected void onPreExecute() {
		super.onPreExecute();
		bar.setVisibility(View.VISIBLE);

	}

	@Override
	protected void onPostExecute(String result) {
		view.loadDataWithBaseURL(null, result, "text/html", "UTF-8", null);
		bar.setVisibility(View.GONE);
		result = null;
		view = null;
		bar = null;
	}

}
