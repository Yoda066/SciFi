package sk.dhorvath.scifi;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.widget.AdapterViewFlipper;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import Utilities.GUIController;

/**
 * Created by student on 12. 2. 2015.
 */
public class CosplayActivity extends Activity {
    private AdapterViewFlipper adapterViewFlipper;
    private float lastX, lastY;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cosplay_layout);
        adapterViewFlipper = (AdapterViewFlipper) findViewById(R.id.adapterViewFlipper);
        adapterViewFlipper.setAdapter(SciFi.getCosplayAdapter(this));
        SciFi.loadCosplays();
    }

    public boolean onTouchEvent(MotionEvent touchevent) {
        switch (touchevent.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastX = touchevent.getX();
                lastY = touchevent.getY();

                break;
            case MotionEvent.ACTION_UP:
                if (SciFi.clickedOnPicture)

                {//if i didnt move, I clicked.
                    if (lastX - touchevent.getX() == 0 && lastY - touchevent.getY() == 0)
                        startImageView();
                }
                float currentX = touchevent.getX();
                if (lastX < currentX) {
                    // Smer dolava
                    if ((adapterViewFlipper.getDisplayedChild())!=0){
                        adapterViewFlipper.setInAnimation(this, R.animator.right_in);
                        adapterViewFlipper.setOutAnimation(this, R.animator.left_out);
                    adapterViewFlipper.showPrevious();
                }}
                if (lastX > currentX) {
                    // Smer doprava
                    if ((adapterViewFlipper.getDisplayedChild()+1)<SciFi.getCosplays().size()){
                        adapterViewFlipper.setInAnimation(this, R.animator.left_in);
                        adapterViewFlipper.setOutAnimation(this, R.animator.right_out);
                    adapterViewFlipper.showNext();
                }}
                SciFi.clickedOnPicture = false;
                break;
        }
        return true;
    }

    private void startImageView() {
        Intent intent = new Intent(this, Obrazok.class);
        Bundle b = new Bundle();
        //poslem aktivite obrazok rovno url obrazka aby si ho priamo vytiahol z cache
        b.putString("CosplayURL", SciFi.getCosplays().get(adapterViewFlipper.getDisplayedChild()).getURL());

        intent.putExtras(b);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {
        SciFi.clearCosplays();
        finish();
    }
}
