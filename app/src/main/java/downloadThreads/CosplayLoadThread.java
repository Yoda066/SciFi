package downloadThreads;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.ArrayList;

import org.jsoup.nodes.Entities;
import org.jsoup.select.Elements;

import sk.dhorvath.scifi.SciFi;
import entities.Cosplay;

public class CosplayLoadThread extends
		AbstractThread<Cosplay> {

	private ArrayList<String> cosplayLinks = new ArrayList<String>();
	private ArrayList<Cosplay> cosplays;

	public CosplayLoadThread(ArrayList<Cosplay> cosplays) {
		this.cosplays = cosplays;
	}

	@Override
	protected ArrayList<Cosplay> doInBackground(String... params) {
			try {
				connect(params[0]);
				loadCosplayLinks();				
				for(int i = 0; i<cosplayLinks.size(); i++){
                    Cosplay c = getCosplay(i);
                    if (c!=null) {
					    cosplays.add(c);
                        }
                    }

			} catch (IOException e) {
				e.printStackTrace();
				return cosplays;
			}
		return cosplays;
	}	

	private void loadCosplayLinks() {
		Elements el = doc.select(".snh").select("a");
		for (int i = 2; i < el.size(); i++) {
			cosplayLinks.add("http://www.scifi.sk"
					+ el.get(i).attr("href").substring(2));
		}
	}

	private Cosplay getCosplay(int desiredCosplay) {				

		try {
			connect(cosplayLinks.get(desiredCosplay));
			doc.outputSettings().escapeMode(Entities.EscapeMode.xhtml);
		} catch (UnknownHostException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		Cosplay cosplay = new Cosplay();			

        try {
            Elements el = doc.select(".snt").select("div");

            cosplay.setTitle(el.last().ownText());

            el = doc.select(".text_img");

            cosplay.setURL(SciFi.URLscifi + el.get(0).attr("src").substring(6));

            el = doc.select("div").select("a[href^=../../diskusie/?t]");

            cosplay.setURLdiskusia(SciFi.URLscifi + ""
                    + el.first().attr("href").substring(6));

            el = doc.select(".snn");

            cosplay.setDescription(el.first().ownText());
        } catch (Exception e){
            e.printStackTrace();
            return null;
        }
		return cosplay;
	}

}
