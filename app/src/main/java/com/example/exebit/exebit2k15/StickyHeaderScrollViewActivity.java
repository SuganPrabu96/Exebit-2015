package com.example.exebit.exebit2k15;
/**
 * Created by srikrishna on 03-03-2015.
 */
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.example.exebit.exebit2k15.Main_Activity;
import com.example.exebit.exebit2k15.R;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollViewCallbacks;
import com.github.ksoichiro.android.observablescrollview.ScrollState;
import com.github.ksoichiro.android.observablescrollview.ScrollUtils;
import com.nineoldandroids.view.ViewHelper;
import com.nineoldandroids.view.ViewPropertyAnimator;

import org.w3c.dom.Text;

import java.util.ArrayList;

public class StickyHeaderScrollViewActivity extends ActionBarActivity implements ObservableScrollViewCallbacks {

    private View mHeaderView;
    private View mToolbarView;
    private ObservableScrollView mScrollView;
    private int mBaseTranslationY;
    //ArrayList<EventsPage_Events> eventspage = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stickyheaderscrollview);

        Intent intent = getIntent();
        String s0 = intent.getStringExtra("schedule");
        String s1 = intent.getStringExtra("title");
        String s2 = intent.getStringExtra("description");
        String s3 = intent.getStringExtra("format");
        String s4 = intent.getStringExtra("faq");
        String s5 = intent.getStringExtra("coord");
        String s6 = intent.getStringExtra("categ");

        TextView t1 = (TextView) findViewById(R.id.description);
        TextView t2 = (TextView) findViewById(R.id.eventformat);
        TextView t3 = (TextView) findViewById(R.id.faqcontents);
        TextView t4 = (TextView) findViewById(R.id.coorddetails);
        TextView t5 = (TextView) findViewById(R.id.dateandtime);
        t1.setText(s2);
        t2.setText(s3);
        t3.setText(s4);
        t4.setText(s5);
        t5.setText(s0);
        setTitle(s1);
        String url = "empty";
        if(s6.equals("Gaming")) { t1.setTextColor(Color.parseColor("#00BFFF"));

            if(s1.equals("DOTA 2")) url = "http://exebit.in/Exebit_Dota.pdf";
            else if(s1.equals("Counter Strike")) url = "http://exebit.in/Exebit_CS.pdf";
            else if(s1.equals("FIFA 14")) url = "http://exebit.in/Exebit_FIFA.pdf";
            else if(s1.equals("NFS : MW")) url = "http://exebit.in/Exebit_NFS.pdf";
            else if(s1.equals("Pocket Tanks")) url = "http://exebit.in/Exebit_PocketTanks.pdf";
            final String finalUrl = url;
            t1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Main_Activity.internetConnection.isConnectingToInternet() == true) {
                        Uri uri = Uri.parse(finalUrl);
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    } else
                        Toast.makeText(getApplicationContext(), "Network error. Check your network connection", Toast.LENGTH_SHORT).show();
                }
            });

        }


        int pos = intent.getIntExtra("pos",-1);

        if(pos!=-1) {
            // Toast.makeText(getApplicationContext(),pos + "", Toast.LENGTH_SHORT).show();
        }

        //setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mHeaderView = findViewById(R.id.header);
        ViewCompat.setElevation(mHeaderView, getResources().getDimension(R.dimen.toolbar_elevation));
        mToolbarView = findViewById(R.id.toolbar);

        mScrollView = (ObservableScrollView) findViewById(R.id.scroll);
        mScrollView.setScrollViewCallbacks(this);
    }

    @Override
    public void onScrollChanged(int scrollY, boolean firstScroll, boolean dragging) {
        if (dragging) {
            int toolbarHeight = mToolbarView.getHeight();
            if (firstScroll) {
                float currentHeaderTranslationY = ViewHelper.getTranslationY(mHeaderView);
                if (-toolbarHeight < currentHeaderTranslationY) {
                    mBaseTranslationY = scrollY;
                }
            }
            float headerTranslationY = ScrollUtils.getFloat(-(scrollY - mBaseTranslationY), -toolbarHeight, 0);
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewHelper.setTranslationY(mHeaderView, headerTranslationY);
        }
    }

    @Override
    public void onDownMotionEvent() {
    }

    @Override
    public void onUpOrCancelMotionEvent(ScrollState scrollState) {
        mBaseTranslationY = 0;

        if (scrollState == ScrollState.DOWN) {
            showToolbar();
        } else if (scrollState == ScrollState.UP) {
            int toolbarHeight = mToolbarView.getHeight();
            int scrollY = mScrollView.getCurrentScrollY();
            if (toolbarHeight <= scrollY) {
                hideToolbar();
            } else {
                showToolbar();
            }
        } else {
            // Even if onScrollChanged occurs without scrollY changing, toolbar should be adjusted
            if (!toolbarIsShown() && !toolbarIsHidden()) {
                // Toolbar is moving but doesn't know which to move:
                // you can change this to hideToolbar()
                showToolbar();
            }
        }
    }

    private boolean toolbarIsShown() {
        return ViewHelper.getTranslationY(mHeaderView) == 0;
    }

    private boolean toolbarIsHidden() {
        return ViewHelper.getTranslationY(mHeaderView) == -mToolbarView.getHeight();
    }

    private void showToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        if (headerTranslationY != 0) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(0).setDuration(200).start();
        }
    }

    private void hideToolbar() {
        float headerTranslationY = ViewHelper.getTranslationY(mHeaderView);
        int toolbarHeight = mToolbarView.getHeight();
        if (headerTranslationY != -toolbarHeight) {
            ViewPropertyAnimator.animate(mHeaderView).cancel();
            ViewPropertyAnimator.animate(mHeaderView).translationY(-toolbarHeight).setDuration(200).start();
        }
    }
}
