package Utilities;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.graphics.Point;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.Toast;

public class GUIController extends Application {

	private static ListView list;
    private static Context context;
    private static ProgressBar bar;

	public static void setListView(ListView view){
		list=view;
	}

    public static void setContext(Context c){
        context=c;
    }

    public static void setProgressBar(ProgressBar c){
       bar = c;
    }
	
	public static void disableListView(){
		if(list!=null)
		list.setEnabled(false);
	}
	
	public static void enableListView(){
		if(list!=null)
		list.setEnabled(true);
	}

    public static void makeToast(String g){
        Toast.makeText(context, g, Toast.LENGTH_SHORT).show();
    }

    public static void stopLoad(){
        bar.setVisibility(View.GONE);
    }
    public static void startLoad(){
        bar.setVisibility(View.VISIBLE);
    }

//    @SuppressLint("NewApi")
//    public static void screenSize(){
//        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
//        Display display = wm.getDefaultDisplay();
//
//        Point size = new Point();
//        display.getSize(size);
//        int width = size.x;
//        int height = size.y;
//        makeToast(width+" "+height);
//    }
}
