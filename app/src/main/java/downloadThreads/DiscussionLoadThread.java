package downloadThreads;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.jsoup.nodes.Node;
import org.jsoup.select.Elements;

import Utilities.GUIController;
import adapters.CosplayAdapter;
import entities.Cosplay;
import sk.dhorvath.scifi.SciFi;

import entities.Comment;
import android.widget.ArrayAdapter;

public class DiscussionLoadThread extends AbstractThread<Comment> {
	
	ArrayList<Comment> result;
    //private CosplayAdapter adapter;
	public void setList(ArrayList<Comment> c) {
		result = c;
	}

	@Override
	protected ArrayList<Comment> doInBackground(String... params) {		
		try {
			connect(params[0]);			
		} catch (IOException e) {
			e.printStackTrace();
			return result;
		}
		result.clear();
		Elements other = doc.select(".box_b");		
		for (int i = 0; i < other.size() - 2; i++) {			
			String author = other.get(i).select("b").get(0).text();
			List<Node> childs = other.get(i).childNodes();
			String text = childs.get(childs.size() - 1).toString();
			String src = other.get(i).select(".ikona").attr("src");
			// if there is no path to image
			Comment com;
			if (src.length() == 0) {
				com= new Comment(author, text.replace("&quot;", "\"")
						.replace("&apos;", "'"), null);
			} else {
				com= new Comment(author, text.replace("&quot;", "\"")
						.replace("&apos;", "'"), SciFi.URLscifi
						+ src.substring(3));
			}
			result.add(com);
			ImageDownloader id = new ImageDownloader(null, true);
			id.execute(com.getImagePath());
		}
		doc = null;
		return result;
	}

//    public void setAdapter(CosplayAdapter ca){
//        adapter=ca;
//    }

}
