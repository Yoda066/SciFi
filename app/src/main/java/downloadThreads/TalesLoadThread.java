package downloadThreads;

import java.io.IOException;

import java.util.ArrayList;
import org.jsoup.select.Elements;
import sk.dhorvath.scifi.SciFi;
import entities.Tale;
public class TalesLoadThread extends AbstractThread<Tale> {

	private ArrayList<Tale> tales;

	public void setList(ArrayList<Tale> list) {
		this.tales = list;		
	}

	@Override
	protected ArrayList<Tale> doInBackground(String... params) {					
		try {
			connect(params[0]);		
		} catch (IOException e) {
			return null;
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Tu vytahujem len nazvy a linky
		Elements titles = doc.select(".snh").select("a");

		Elements descriptions = doc.select(".snb");

		Elements authors = doc.select(".snf").select("a[href^=../fans]");

		Elements details = doc.select(".snf");

		for (int i = 0; i < titles.size(); i++) {

			Tale t = new Tale();

			t.setTitle(titles.get(i).ownText());

			t.setLink(SciFi.URLscifi + titles.get(i).attr("href").substring(3));

			t.setDescription(descriptions.get(i).ownText());

			t.setAuthor(authors.get(i).ownText());

			try {
				String s = details.get(i).child(0).nextSibling().toString();

				int j = s.indexOf("žáner");
				s = s.substring(j + 7);
				j = s.indexOf('|');

				t.setCathegory(s.substring(0, j - 1));

				j = s.indexOf("Hodnotenie");

				s = s.substring(j + 12);

				j = s.indexOf('|');

				t.setRating(s.substring(0, j - 1));

				j = s.indexOf('(');

				s = s.substring(16, j - 1);

				t.setDiscusionCount(Integer.parseInt(s));
			} catch (Exception e) {
				e.printStackTrace();
			}
			t.setDiscusionLink(SciFi.URLscifi
					+ ""
					+ details.get(i).select("[href]").get(1).attr("href")
							.substring(3));
			tales.add(t);
		}
		return tales;
	}
}
