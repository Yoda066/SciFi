package sk.dhorvath.scifi;

import java.util.ArrayList;

import adapters.CommentAdapter;
import adapters.CosplayAdapter;
import adapters.TaleAdapter;
import downloadThreads.CosplayLoadThread;
import downloadThreads.DiscussionLoadThread;
import downloadThreads.TalesLoadThread;
import entities.Comment;
import entities.Cosplay;
import entities.Tale;
import android.app.Application;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * @author David Horvath
 */

public class SciFi extends Application {
	
	private static final String URLpoviedky = "http://www.scifi.sk/poviedky/?k=&n=";
	private final static String URLcosplays = "http://www.scifi.sk/spacenews/?k=88&n=";
	public static final String URLscifi = "http://www.scifi.sk/";
	
	private static TaleAdapter adapter;
	
	private static ArrayList<Tale> tales = new ArrayList<Tale>();

	private static CommentAdapter commentAdapter;

	private static ArrayList<Comment> comments = new ArrayList<Comment>();

    private static CosplayAdapter cosplayAdapter;

    private static ArrayList<Cosplay> cosplays = new ArrayList<Cosplay>();

	private static int currentPage = 1;

	private static int currentCosplayPage = 0;

    public static boolean clickedOnPicture = false;
		
	private static LruCache<String, String> taleTextCache = new LruCache<String, String>(1); //1text poviedky kvoli debilnemu webView-u;
	
	private static LruCache<String, Bitmap> mMemoryCache = new LruCache<String, Bitmap>(15); //15 obrazkov bude mat v sebe stale 

    public static TaleAdapter getAdapter(Context c){
        if (adapter == null){
        adapter = new TaleAdapter(c, android.R.layout.simple_list_item_1, tales);}
        return adapter;
    }

	public static void loadCosplays() {
		CosplayLoadThread thread = new CosplayLoadThread(cosplays);
        thread.setAdapter(cosplayAdapter);
		thread.execute(URLcosplays+currentCosplayPage);
	}

	private static void loadTales(int page) {
		TalesLoadThread thread = new TalesLoadThread();
		thread.setAdapter(adapter);		
		tales.clear();
		adapter.clear();
		thread.setList(tales);
		thread.execute(URLpoviedky + "" + Integer.toString(page));
		thread = null;
	}

	private static void loadDiscussion(int id, int page) {
		DiscussionLoadThread thread = new DiscussionLoadThread();
		thread.setAdapter(commentAdapter);
		thread.setList(comments);
		thread.execute(tales.get(id).getDiscusionLink()+"&n="+page);
		thread = null;
	}

	public static void loadFirstPage() {
		currentPage=1;
		loadTales(currentPage);
	}
	
	public static void loadNextPage() {
		currentPage++;
		loadTales(currentPage);
	}

	public static void loadPreviousPage() {
		if (currentPage > 0) {
			currentPage--;
			loadTales(currentPage);
		}
	}
	
	/// returns arrayList of all tales from url
	public static ArrayList<Cosplay> getCosplays(){
		return cosplays;
	}

	public static int getCurrentPage() {
		return currentPage;
	}

	public static ArrayList<Comment> getComments() {
		return comments;
	}

    public static CosplayAdapter getCosplayAdapter(Context c){
        if (cosplayAdapter==null) {cosplayAdapter = new CosplayAdapter(c, R.layout.cosplay_layout, cosplays);}
        return cosplayAdapter;
    }

    public static CommentAdapter getCommentAdapter(Context c){
        if (commentAdapter==null){
            commentAdapter = new CommentAdapter(c, android.R.layout.simple_list_item_1, comments);
        }
        return commentAdapter;
    }

	public static void clearDiscussion(){
		commentAdapter.clear();
		comments.clear();
	}

	public static ArrayList<Tale> getTales() {
		return tales;
	}

	public static ArrayList<Comment> getDiscusion(int id, int page) {
		loadDiscussion(id, page);
		return comments;
	}
	
	public static void addBitmapToCache(String key, Bitmap bitmap){
		if (getBitmapFromMemCache(key) == null) {
	        mMemoryCache.put(key, bitmap);
	    }		
	}

	public static Bitmap getBitmapFromMemCache(String key) {
		return mMemoryCache.get(key);					
	}
	
	public static void addTaleText(String name, String value){
		if (getTaleText(name) == null) {
	         taleTextCache.put(name, value);
	    }		
	}
	
	public static String getTaleText(String name) {
		return taleTextCache.get(name);					
	}

    public static void clearCosplays() {
        mMemoryCache.evictAll();
    }
}