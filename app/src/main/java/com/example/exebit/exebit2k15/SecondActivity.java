package com.example.exebit.exebit2k15;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.StrictMode;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewManager;
import android.view.Window;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EncodingUtils;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ProfilePage_ActionTab.CustomPagerAdapter;
import Schedule_recyclerView.CardAdapter;
import Schedule_recyclerView.ListOfEvents_Event;
import de.hdodenhof.circleimageview.CircleImageView;
import it.neokree.materialtabs.MaterialTab;
import it.neokree.materialtabs.MaterialTabHost;
import it.neokree.materialtabs.MaterialTabListener;
import navigationDrawer.NavDrawerItem;
import navigationDrawer.NavDrawerListAdapter;
import util.data;

public class SecondActivity extends ActionBarActivity {
    private CharSequence mTitle="Exebit";
    private DrawerLayout drawerLayout;
    private ListView drawerList;
    private ActionBarDrawerToggle mDrawerToggle;
    public static FragmentManager SupportFragmentManager;
    int flag,webflag;
    public static SharedPreferences prefs;
    public static int profile_flag;
    public FragmentTransaction fragmentTransaction;
    CircleImageView profileIcon;
    TextView profileIconText;
    public static String userFullName,userProPic,userDateOfBirth,userName,gender,userId,userPassword,userMobile,userCollege,userEmail,userHostel,userHostelRoom;
    String[] actions = new String[] {"All",
            "March 14th",
            "March 15th",
            "Contests","Gaming","Workshops"};
    static int flag_all=0,flag_14=0,flag_15=0,flag_contests=0,flag_gaming=0,flag_workshop=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

            webflag=0;
         if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        prefs = getSharedPreferences("Exebit",MODE_PRIVATE);

        {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getBaseContext(), android.R.layout.simple_spinner_dropdown_item, actions);
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

            // Defining Navigation listener //
            ActionBar.OnNavigationListener navigationListener = new ActionBar.OnNavigationListener() {


                public boolean onNavigationItemSelected(int itemPosition, long itemId) {
                    functioncall(actions[itemPosition]);
                    // Toast.makeText(getBaseContext(), "You selected : " + actions[itemPosition]  , Toast.LENGTH_SHORT).show();
                    return false;
                }
            };

            // Setting dropdown items and item navigation listener for the actionbar /
            getSupportActionBar().setListNavigationCallbacks(adapter, navigationListener);
        }


        profile_flag=0;

        profileIcon = (CircleImageView) findViewById(R.id.profilepic);
        profileIconText = (TextView) findViewById(R.id.profilepic_name);

        if(Main_Activity.profilePicture == null) {
            profileIcon.setImageResource(R.drawable.profilegrey);
        }
        else profileIcon.setImageBitmap(Main_Activity.profilePicture);
        if(Main_Activity.userFullName != null && !Main_Activity.userFullName.isEmpty())
            profileIconText.setText(Main_Activity.userFullName);
        Window window = SecondActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(SecondActivity.this.getResources().getColor(R.color.myStatusBarColor));
        }
        getSupportActionBar().setTitle("Exebit 2015");

        /*getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_launcher);
        getSupportActionBar().setLogo(R.drawable.ic_drawer);
        */SupportFragmentManager = getSupportFragmentManager();

        List<NavDrawerItem> datalist = null;
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, new ScheduleFragment());
        fragmentTransaction.commit();
        Intent i = getIntent();
        flag=i.getExtras().getInt("Login status");
        if(flag==1)
            datalist = data.getNavDrawerItems();
        else if(flag==0)
        {datalist = data.getNavDrawerItemsNotLoggedIn();
            profileIconText.setText("");}
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);

        drawerList.setAdapter(new NavDrawerListAdapter(getApplicationContext(),datalist));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,       /* DrawerLayout object */
                R.string.app_name,  /* "open drawer" description */
                R.string.app_name /* "close drawer" description */
        ) {

            /** Called when a drawer has settled in a completely closed state. */
            public void onDrawerClosed(View view) {
                super.onDrawerClosed(view);
         //       setTitle(mTitle);
            }

            /** Called when a drawer has settled in a completely open state. */
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);

          //      setTitle(mTitle);
            }
        };
        drawerLayout.setDrawerListener(mDrawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);
        mDrawerToggle.syncState();
    }

    String str1 = "14th";
    String str2 = "15th";
    private void functioncall(String compare) {




        if(compare.equals("All")) { flag_all = 1; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=0;

            ScheduleFragment.listofevents.clear();
            for (int j = 0; j < 9; j++) {
                // if(eventschs[j].toLowerCase().contains(str1.toLowerCase()))
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0]));
            }
            for (int j = 0; j < 10; j++) {
                //  if(workschs[j].toLowerCase().contains(str1.toLowerCase()))
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3]));
            }
            for (int j = 0; j < 5; j++) {
                //  if(gameschs[0].toLowerCase().contains(str1.toLowerCase()))
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2]));

            }
            // Toast.makeText(getBaseContext(), "Displaying All" + flag_all, Toast.LENGTH_SHORT).show();
        }

        else if(compare.equals("March 14th")){ flag_all = 0; flag_workshop=0;flag_14=1;flag_15=0;flag_contests=0; flag_gaming=0;
            if(flag_14==1){    ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 9; j++) {
                    if(ScheduleFragment.eventschs[j].toLowerCase().contains(str1.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0]));
                }
                for (int j = 0; j < 10; j++) {
                    if(ScheduleFragment.workschs[j].toLowerCase().contains(str1.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3]));
                }
                for (int j = 0; j < 5; j++) {
                    if(ScheduleFragment.gameschs[0].toLowerCase().contains(str1.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2]));

                }

            }}
        else if(compare.equals("March 15th")){flag_all = 0; flag_workshop=0;flag_14=0;flag_15=1;flag_contests=0; flag_gaming=0;
            if(flag_15==1) {
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 9; j++) {
                    if (ScheduleFragment.eventschs[j].toLowerCase().contains(str2.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0]));
                }
                for (int j = 0; j < 10; j++) {
                    if (ScheduleFragment.workschs[j].toLowerCase().contains(str2.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3]));
                }
                for (int j = 0; j < 5; j++) {
                    if (ScheduleFragment.gameschs[0].toLowerCase().contains(str2.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2]));

                }
            }
        }
        else if(compare.equals("Contests")){ flag_all = 0; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=1; flag_gaming=0;
            if(flag_contests==1){
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 9; j++) {

                    ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0]));
                }

            }}
        else if(compare.equals("Gaming")){flag_all = 0; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=1;
            if(flag_gaming==1){
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 5; j++) {

                    ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2]));
                }

            }}
        else if(compare.equals("Workshops")){flag_all = 0; flag_workshop=1;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=0;
            if(flag_workshop==1){
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 10; j++) {

                    ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3]));
                }
            }}

        //  ScheduleFragment aScheduleFragment = new ScheduleFragment();
        ScheduleFragment.mRecyclerView.getAdapter().notifyDataSetChanged();


    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {
        @Override
        public void onItemClick(AdapterView parent, View view, int position, long id) {
            System.out.println(position);
            selectItem(position);
        }
    }

    private void selectItem(int position) {
        // Create a new fragment and specify the planet to show based on position
        // Insert the fragment by replacing any existing fragment
        final FragmentManager fragmentManager = getSupportFragmentManager();
        final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        final Fragment f = fragmentManager.findFragmentById(R.id.frame_container);
        //if(fragmentTransaction.r))
        if (position != 0) {
            FrameLayout.LayoutParams lp = new FrameLayout.LayoutParams(0,0,0);
            getSupportFragmentManager().findFragmentById(R.id.frame_container)
                    .getView()
                    .setLayoutParams(lp);
        }

        if(position==0) {
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);
            webflag=0;
            fragmentTransaction.replace(R.id.frame_container, new ScheduleFragment());
        }
        if(position==1) {
            webflag=1;
            SupportFragmentManager=getSupportFragmentManager();
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            if(flag==1) {
                fragmentTransaction.replace(R.id.frame_container, new ProfilePageFragment());
                // ProfilePageFragment.mViewPager.setCurrentItem(0);
            }
            else
                fragmentTransaction.replace(R.id.frame_container, new HospitalityFragment());
            //  setTitle("pos1");
           // Toast.makeText(getApplicationContext(),"pos1", Toast.LENGTH_SHORT).show();
        }
        if(position==2){
            if(flag==1)
            {
                fragmentTransaction.replace(R.id.frame_container, new DashBoardFragment());
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            }
            else {
                fragmentTransaction.replace(R.id.frame_container, new FaqFragment());
                getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
                getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);

            }
          //  setTitle("pos2");
           // Toast.makeText(getApplicationContext(),"pos2", Toast.LENGTH_SHORT).show();
        }
        if(position==3){
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            webflag=3;
            if(flag==1)
                fragmentTransaction.replace(R.id.frame_container,new HospitalityFragment());
            else
                fragmentTransaction.replace(R.id.frame_container,new SponsorsFragment());
        }
        if(position==4)
        {
            webflag=4;
            fragmentTransaction.replace(R.id.frame_container,new FaqFragment());
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        if(position==5){
            webflag=5;
            fragmentTransaction.replace(R.id.frame_container,new SponsorsFragment());
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
        if(position==6){
            //final Fragment f = fragmentManager.findFragmentById(R.id.frame_container);
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            builder.setMessage("Are you sure?");

            builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    //    fragmentTransaction.replace(R.id.frame_container,new ProfilePageFragment());

                    dialog.dismiss();
                }
            });

            builder.setPositiveButton("Yes",new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Main_Activity.userFullName="";
                    Main_Activity.userDateOfBirth="";
                    Main_Activity.userName="";
                    Main_Activity.userId="";
                    Main_Activity.gender="";
                    Main_Activity.userPassword="";
                    Main_Activity.userMobile="";
                    Main_Activity.userCollege="";
                    Main_Activity.userEmail="";
                    Main_Activity.userHostel="";
                    Main_Activity.userHostelRoom="";
                    Main_Activity.profilePicture=null;
                    Main_Activity.profilePic=null;

                    Main_Activity.prefs.edit().putString("userFullName",Main_Activity.userFullName).apply();
                    Main_Activity.prefs.edit().putString("userDateOfBirth",Main_Activity.userDateOfBirth).apply();
                    Main_Activity.prefs.edit().putString("userName",Main_Activity.userName).apply();
                    Main_Activity.prefs.edit().putString("userCollege",Main_Activity.userCollege).apply();
                    Main_Activity.prefs.edit().putString("userId",Main_Activity.userId).apply();
                    Main_Activity.prefs.edit().putString("gender",Main_Activity.gender).apply();
                    Main_Activity.prefs.edit().putString("userPassword",Main_Activity.userPassword).apply();
                    Main_Activity.prefs.edit().putString("userMobile",Main_Activity.userMobile).apply();
                    Main_Activity.prefs.edit().putString("userEmail",Main_Activity.userEmail).apply();
                    Main_Activity.prefs.edit().putString("userHostel",Main_Activity.userHostel).apply();
                    Main_Activity.prefs.edit().putString("userHostelRoom",Main_Activity.userHostelRoom).apply();
                    Main_Activity.prefs.edit().putString("userProPic",Main_Activity.encodeTobase64(Main_Activity.profilePicture));

                   /* Thread threadLogout = new Thread( new Runnable() {
                        @Override
                        public void run() {
                            String postReceiverUrl = "http://exebit.in/backend/_logout.php";

                            HttpClient httpClient = new DefaultHttpClient();

                            HttpPost httpPost = new HttpPost(postReceiverUrl);

                            HttpResponse response = null;
                            try {
                                response = httpClient.execute(httpPost);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            HttpEntity resEntity = response.getEntity();

                            if (resEntity != null) {

                                try {
                                    String responseStr = EntityUtils.toString(resEntity).trim();
                                    Log.i("response", responseStr);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }

                        }
                    }
                    );
                    threadLogout.start();
*/
                    Intent i = new Intent(SecondActivity.this,Main_Activity.class);
                    startActivity(i);
                    finish();
                    dialog.dismiss();
                }
            });
            builder.create().show();
            if(f instanceof ProfilePageFragment) {
                fragmentTransaction.replace(R.id.frame_container, new ProfilePageFragment());

            }
            else if(f instanceof MyEventsFragment) {

                fragmentTransaction.replace(R.id.frame_container,new MyEventsFragment());

            }
            else if(f instanceof ScheduleFragment) {
                fragmentTransaction.replace(R.id.frame_container, new ScheduleFragment());

            }
            else if(f instanceof FaqFragment) {

                fragmentTransaction.replace(R.id.frame_container,new FaqFragment());

            }
            else if(f instanceof HospitalityFragment) {

                fragmentTransaction.replace(R.id.frame_container,new HospitalityFragment());

            }
            else if(f instanceof SponsorsFragment) {

                fragmentTransaction.replace(R.id.frame_container,new SponsorsFragment());

            }
        }

        fragmentTransaction.commit();
        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        for(int i=0;i<drawerList.getChildCount();i++)
            drawerList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.NavigationBarUnselectedItem));
        drawerList.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.NavigationBarSelectedItem));
        drawerLayout.closeDrawer(Gravity.START);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle=title;
//        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else if(profile_flag==1) {
            profile_flag=0;
            ProfilePageFragment.tabHost.setSelectedNavigationItem(0);
            ProfilePageFragment.mViewPager.setCurrentItem(0);}

        else if(flag==0) {
            startActivity(new Intent(SecondActivity.this, Main_Activity.class));
            finish();
        }

        else
        {
            finish();
        }
    }

    /*public boolean onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        return true;
    }*/
/*
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            AlertDialog.Builder b1 = new AlertDialog.Builder(SecondActivity.this);
            b1.setTitle("Help");
            b1.create().show();
            return true;
        }

        else if(id == R.id.about_exebit)
        {
            AlertDialog.Builder b1 = new AlertDialog.Builder(SecondActivity.this);
            b1.setTitle("About Exebit");
            b1.setMessage("Exebit is the annual fest of the Computer Science and Engineering Department of IIT Madras");
            b1.setNeutralButton("Ok", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b1.create().show();
            return true;
        }


        return super.onOptionsItemSelected(item);
    }
*/
    /**
     * A placeholder fragment containing a simple view.
     */

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        if (mDrawerToggle.onOptionsItemSelected(item)) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static class DashBoardFragment extends Fragment {
        public DashBoardFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            final View rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);

           final WebView webView = (WebView) rootView.findViewById(R.id.webview1);
            WebSettings webSettings = webView.getSettings();
            webView.setVerticalScrollBarEnabled(false);

//            webView.loadUrl("http://exebit.in/enter.php");
          /*  byte[] post = EncodingUtils.getBytes("name=" + Main_Activity.userName + "&password=" + Main_Activity.userPassword, "base64");
            webView.postUrl("http://exebit.in/backend/_login.php", post);
*/
            /*webView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    WebView.HitTestResult hr = ((WebView) v).getHitTestResult();

                    Log.i("touched", "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                    if(hr.getExtra()==null && hr.getType()==0) {
                        Toast.makeText(rootView.getContext(),"time to hide the webview",Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });*/


            /*Thread t1 = new Thread(new Runnable() {
                @Override
                public void run() {
                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
                    nameValuePairs.add(new BasicNameValuePair("name", "arvindsuresh2009@gmail.com"));
                    nameValuePairs.add(new BasicNameValuePair("password", "helloworld"));

                    HttpClient httpclient = new DefaultHttpClient();
                    HttpPost httppost = new HttpPost("http://exebit.in/backend/_login.php");
                    try {
                        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    HttpResponse response = null;
                    try {
                        response = httpclient.execute(httppost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String data = null;
                    try {
                        data = new BasicResponseHandler().handleResponse(response);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    webView.loadData(data, "text/html", "utf-8");
                }
            });

            t1.start();
*/

            webView.loadDataWithBaseURL("http://exebit.in","\n" +
                    "\n" +
                    "<html>\n" +
                    "<head>\n" +
                    "    <title>Login / Register</title>\n" +
                    "\n" +
                    "    <!-- Style sheets -->\n" +
                    "    <link rel=\"stylesheet\" media=\"(max-width: 1000px)\" href=\"css/enter-mobile.css\" />\n" +
                    "\n" +
                    "    <link href=\"css/enter.css\" media=\"(min-width: 1000px)\" rel=\"stylesheet\" />\n" +
                    "\n" +
                    "    <meta name=\"viewport\" content=\"initial-scale=1.0, width=device-width, height=device-height, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no\" />\n" +
                    "\n" +
                    "\n" +
                    "    <!-- Font files -->\n" +
                    "    \n" +
                    "<!-- Google Roboto -->\n" +
                    "<link href='http://fonts.googleapis.com/css?family=Roboto:400,100,100italic,300,300italic,400italic,500,500italic,700,700italic,900,900italic' rel='stylesheet' type='text/css'>\n" +
                    "\n" +
                    "<!-- Google Roboto Condensed -->\n" +
                    "<link href='http://fonts.googleapis.com/css?family=Roboto+Condensed:300italic,400italic,700italic,400,300,700' rel='stylesheet' type='text/css'>\n" +
                    "\n" +
                    "<!-- Lato -->\n" +
                    "<link href='http://fonts.googleapis.com/css?family=Lato:100,300,400,700,900,100italic,300italic,400italic,700italic,900italic' rel='stylesheet' type='text/css'></head>\n" +
                    "\n" +
                    "<body>\n" +
                    "<header>\n" +
                    "    \n" +
                    "<div id=\"global-header\">\n" +
                    "    <div id=\"global-header-content\">\n" +
                    "        <div id=\"global-header-left\">\n" +
                    "            <span id=\"title-span\"><a href=\"http://www.exebit.in\">Exebit 2015</a></span>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <div id=\"global-header-right\">\n" +
                    "\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</div>\n" +
                    "\n" +
                    "<style>\n" +
                    "\n" +
                    "\n" +
                    "\n" +
                    "    @media (max-width: 1000px) {\n" +
                    "        #global-header {\n" +
                    "            background-color: #FFFFFF;\n" +
                    "            width: 100%;\n" +
                    "            padding: 20px 0 20px 0;\n" +
                    "            border-bottom: 1px solid #90CAF9;\n" +
                    "            overflow: auto;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-header-content {\n" +
                    "            width: 95%;\n" +
                    "            margin: 0 auto;\n" +
                    "            overflow: auto;\n" +
                    "        }\n" +
                    "\n" +
                    "        #title-span {\n" +
                    "            cursor: pointer;\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 300;\n" +
                    "            letter-spacing: 1px;\n" +
                    "            text-transform: uppercase;\n" +
                    "            color: #666;\n" +
                    "            font-size: 1.5em;\n" +
                    "            transition: 400ms color;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-header-left {\n" +
                    "            width: 100%;\n" +
                    "            text-align: center;\n" +
                    "            margin-bottom: 10px;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-header-right {\n" +
                    "            width: 100%;\n" +
                    "            text-align: center;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-menu {\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 400;\n" +
                    "            text-transform: uppercase;\n" +
                    "            color: #AAAAAA;\n" +
                    "            margin-top: 5px;\n" +
                    "            margin-left: 20px;\n" +
                    "            display: inline-block;\n" +
                    "            font-size: 0.8em;\n" +
                    "            cursor: pointer;\n" +
                    "            padding: 5px;\n" +
                    "            transition: 400ms color;\n" +
                    "        }\n" +
                    "\n" +
                    "        .float-out {\n" +
                    "            background-color: #2196F3;\n" +
                    "            color: white;\n" +
                    "            border-radius: 3px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .float-out:hover {\n" +
                    "            background-color: white;\n" +
                    "            border: 1px solid #2196F3;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-menu:first-child {\n" +
                    "            margin-left: 0px !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-menu:hover {\n" +
                    "            color: #2196F3;\n" +
                    "        }\n" +
                    "\n" +
                    "        #title-span:hover {\n" +
                    "            color: #222;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    @media (min-width: 1000px) {\n" +
                    "        #global-header {\n" +
                    "            background-color: #FFFFFF;\n" +
                    "            width: 100%;\n" +
                    "            padding: 20px 0 20px 0;\n" +
                    "            border-bottom: 1px solid #90CAF9;\n" +
                    "            overflow: auto;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-header-content {\n" +
                    "            width: 1000px;\n" +
                    "            margin: 0 auto;\n" +
                    "            overflow: auto;\n" +
                    "        }\n" +
                    "\n" +
                    "        #title-span {\n" +
                    "            cursor: pointer;\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 300;\n" +
                    "            letter-spacing: 1px;\n" +
                    "            text-transform: uppercase;\n" +
                    "            color: #666;\n" +
                    "            font-size: 1.4em;\n" +
                    "            transition: 400ms color;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-header-left {\n" +
                    "            float: left;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-header-right {\n" +
                    "            float: right;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-menu {\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 400;\n" +
                    "            text-transform: uppercase;\n" +
                    "            color: #AAAAAA;\n" +
                    "            margin-left: 20px;\n" +
                    "            display: inline-block;\n" +
                    "            font-size: 0.9em;\n" +
                    "            cursor: pointer;\n" +
                    "            padding: 5px;\n" +
                    "            transition: 400ms color;\n" +
                    "            border: 1px solid transparent;\n" +
                    "        }\n" +
                    "        \n" +
                    "        .global-menu a {\n" +
                    "            text-decoration: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        .float-out {\n" +
                    "            background-color: #2196F3;\n" +
                    "            color: white;\n" +
                    "            border-radius: 3px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .red {\n" +
                    "            background-color: #D0021B !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        .red:hover {\n" +
                    "            color: #D0021B !important;\n" +
                    "            border: 1px solid #D0021B !important;\n" +
                    "            background-color: white !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        .float-out:hover {\n" +
                    "            background-color: white;\n" +
                    "            border: 1px solid #2196F3;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-menu:first-child {\n" +
                    "            margin-left: 0px !important;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-menu:hover {\n" +
                    "            color: #2196F3;\n" +
                    "        }\n" +
                    "\n" +
                    "        #title-span:hover {\n" +
                    "            color: #222;\n" +
                    "        }\n" +
                    "\n" +
                    "\n" +
                    "    }\n" +
                    "\n" +
                    "</style></header>\n" +
                    "\n" +
                    "<div id=\"container\">\n" +
                    "    <div id=\"login-pane\">\n" +
                    "        <div class=\"login-pane-header\">\n" +
                    "            <span>LOGIN</span>\n" +
                    "        </div>\n" +
                    "\n" +
                    "        <form action=\"backend/_login.php\" method=\"post\">\n" +
                    "            <div class=\"box\">\n" +
                    "                <div class=\"box-hint\">\n" +
                    "                    <span>Enter your email</span>\n" +
                    "                </div>\n" +
                    "                <input class=\"blueField\" type=\"text\" name=\"email\" value=\""+Main_Activity.userName+"\" />\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"box\">\n" +
                    "                <div class=\"box-hint\">\n" +
                    "                    <span>Enter your password</span>\n" +
                    "                </div>\n" +
                    "                <input class=\"blueField\" type=\"password\" name=\"password\"value=\""+Main_Activity.userPassword+"\" />\n" +
                    "            </div>\n" +
                    "\n" +
                    "            <div class=\"box\">\n" +
                    "                <input type=\"submit\" value=\"LOGIN\" class=\"blueButton\" />\n" +
                    "            </div>\n" +
                    "        </form>\n" +
                    "\n" +
                    "        <span id=\"forgot-password\"><a href=\"forgot-password.php\">Forgot Password?</a></span>\n" +
                    "\n" +
                    "    </div>\n" +
                    "\n" +
                    "<style>\n" +
                    "    @media (max-width: 1000px) {\n" +
                    "        #global-footer {\n" +
                    "            width: 100%;\n" +
                    "            background-color: #222222;\n" +
                    "            padding: 20px 0 10px 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-footer-content {\n" +
                    "            width: 95%;\n" +
                    "            margin: 0 auto;\n" +
                    "            overflow: auto;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane {\n" +
                    "            width: 95%;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-header {\n" +
                    "            margin-bottom: 10px;\n" +
                    "            padding-bottom: 5px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-header span {\n" +
                    "            color: white;\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 200;\n" +
                    "            letter-spacing: 1px;\n" +
                    "            color: #AAAAAA;\n" +
                    "            font-size: 1.2em;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-items span {\n" +
                    "            color: white;\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 300;\n" +
                    "            color: #666666;\n" +
                    "            display: inline-block;\n" +
                    "            margin-bottom: 10px;\n" +
                    "            cursor: pointer;\n" +
                    "            transition: 400ms color;\n" +
                    "            display: none;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-items span:hover {\n" +
                    "            color: #888888;\n" +
                    "        }\n" +
                    "    }\n" +
                    "\n" +
                    "    @media (min-width: 1000px) {\n" +
                    "        #global-footer {\n" +
                    "            width: 100%;\n" +
                    "            height: 200px;\n" +
                    "            background-color: #222222;\n" +
                    "            padding: 20px 0 20px 0;\n" +
                    "        }\n" +
                    "\n" +
                    "        #global-footer-content {\n" +
                    "            width: 1000px;\n" +
                    "            margin: 0 auto;\n" +
                    "            overflow: auto;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane {\n" +
                    "            float: left;\n" +
                    "            width: 200px;\n" +
                    "            margin-right: 50px;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-header {\n" +
                    "            margin-bottom: 10px;\n" +
                    "            width: 100%;\n" +
                    "            padding-bottom: 5px;\n" +
                    "            border-bottom: 1px solid #333333;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-header span {\n" +
                    "            color: white;\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 200;\n" +
                    "            letter-spacing: 1px;\n" +
                    "            color: #AAAAAA;\n" +
                    "            font-size: 1.2em;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-items span {\n" +
                    "            color: white;\n" +
                    "            font-family: 'Roboto';\n" +
                    "            font-weight: 300;\n" +
                    "            color: #666666;\n" +
                    "            display: inline-block;\n" +
                    "            margin-bottom: 10px;\n" +
                    "            cursor: pointer;\n" +
                    "            transition: 400ms color;\n" +
                    "        }\n" +
                    "\n" +
                    "        .global-footer-pane-items span:hover {\n" +
                    "            color: #888888;\n" +
                    "        }\n" +
                    "    }\n" +
                    "</style></footer>\n" +
                    "</body>\n" +
                    "</html>","text/html","UTF-8",null);

            webView.setWebViewClient(new WebViewClient() {
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                    return false;
                }
            });

            String script = "<script>document.getElementsByName('email')[0].value='" + Main_Activity.userName + "';document.getElementsByName('password')[0].value='" + Main_Activity.userPassword +"';</script>";/*
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);*/

/*
            Thread threadLogin = new Thread( new Runnable() {
                @Override
                public void run() {
                    String postReceiverUrl = "http://exebit.in/backend/_login.php";

                    HttpClient httpClient = new DefaultHttpClient();

                    HttpPost httpPost = new HttpPost(postReceiverUrl);

                    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                    nameValuePairs.add(new BasicNameValuePair("email", Main_Activity.userEmail));
                    nameValuePairs.add(new BasicNameValuePair("password", Main_Activity.userPassword));

                    try {
                        httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                    }

                    HttpResponse response = null;
                    try {
                        response = httpClient.execute(httpPost);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    HttpEntity resEntity = response.getEntity();

                    if (resEntity != null) {

                        try {
                            String responseStr = EntityUtils.toString(resEntity).trim();
                           // Log.i("response", responseStr);
                            try {
                                webView.loadData(EntityUtils.toString(response.getEntity()), "text/html","utf-8");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            threadLogin.start();*/



            return rootView;
        }
    }

    public static class ScheduleFragment extends Fragment {

        public ScheduleFragment() {
        }

        private static RecyclerView mRecyclerView;
        private static CardAdapter mAdapter;

        public static String userName="";
        public static String userId="";
        public static String passWord="";

        public static SharedPreferences prefs;

        public static String[] eventtitles, eventschs, eventdescs, eventcateg;
        public static String[] worktitles, workschs, workdescs;
        public static String[] gametitles, gameschs, gamedescs;
        public static String[] talktitles, talkschs, talkdescs;

        String str1 = "14th";
        String str2 = "15th";
        String str3 = "Contests";
        String str4 = "Gaming";
        String str5 = "Workshops";


        public static ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();


        public int randInt(int min, int max) {

            Random rand = new Random();

            int randomNum = rand.nextInt((max - min) + 1) + min;

            return randomNum;
        }

        private android.support.v7.widget.Toolbar toolbar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_schedule, container, false);
/*
            userName = prefs.getString("userName","");
            userId = prefs.getString("userId","");
            passWord = prefs.getString("passWord", "");

*/
            worktitles = new String[]{"Android", "BlueSpec", "C(Get It Right)", "Cryptography", "Data Mining", "Databases", "Hadoop", "Machine Learning", "Python", "Web"};
            workschs = new String[]{"14th March, 10 AM - 1 PM & 2 PM - 5 PM,CRC 101", "15th March,10 AM - 1 PM,CS 15", "15th March, 10 AM - 1 PM,CRC 101", "15th March,2 PM - 5 PM,CRC 103", "15th March, 2 PM - 5 PM, CRC 101", "14th March, 2 PM -5 PM ,CRC 102", "14th March , 10 AM - 1 PM & 3 PM - 5 PM, CRC 102", "14th March,10 AM - 1 PM & 2 PM - 5 PM, CRC 103", "15th March,10 AM - 1 PM, CRC 103", "15th March,10 AM - 1 PM & 2 PM - 5 PM, CRC 102"};
            workdescs = new String[]{"Details will be updated soon", "In this workshop you will be taught BSV, and also how to design a simple 5-stage pipelined microprocessor", "Learn to code in C in a professional way", "This workshop on cryptography will expose people to some new frontiers of theoretical cryptography intertwined with its applications", "A hands-on workshop for those enthusiastic about data and pattern mining", "Details will be updated soon", "A hands-on workshop with Hadoop and Banyan", "A workshop on Introduction to Machine Learning", "Learn what Python is capable of,how to write productive code and some advanced programming concepts", "Learn about the A-B-C’s as well as sines and cosines of Web Development"};
            gametitles = new String[]{"Counter Strike", "DOTA 2", "FIFA 14", "NFS : MW", "Pocket Tanks"};
            gameschs = new String[]{"14th March, 9 PM to 12 AM, M.Tech Lab"};
            gamedescs = new String[]{"Details will be updated soon"};
            eventtitles = new String[]{"Code Obfuscation", "Debugging Contest", "El Dorado", "Flipkart Hackathon", "Online Programming Contest", "Puzzle Champ", "Reverse Coding", "Triathlon", "Wikibytes"};
            eventschs = new String[]{"14th March,11 AM - 12 PM, CS 24,CS 26", "14th March, 9:30 - 11:00 AM, CS 34,36", "14th March,9 PM to 12 PM ,Online", "14th March,2 PM to 5 PM, M.Tech Lab", "14th March, 9 PM to 12 PM, Online", "14th March, 12:30 PM to 2 PM, CS 34,36", "15th March, 9:30 AM- 11 AM , CS 34,36", "14th March, 2:30 PM - 4:30PM, CS 24,26", "15th March, 9:30 AM - 12:30 PM ,CS 25"};
            eventdescs = new String[]{"Write the most complicated code of your life while cracking your opponent’s code as well", "Our main job is to bug you with lot of bugs and its upto you to debug and get the honors or to get lost", "El Dorado is an online quizzing event which needs you to answer questions to advance to higher levels", "Details will be updated soon", "One of the biggest programming contests of its kind in the country", " Two rounds of exciting puzzling and prize money is up for the grabs", "Reverse Coding is an event which tests logical thinking and basic programming skills.", "Solve innovative questions using your programming skills", "A CS oriented quiz in an engaging multimedia format"};
            eventcateg = new String[]{"Contests", "Tech Talks", "Gaming", "Workshops"};
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            /*ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();
            String[] eventtitles,eventschs,eventdescs;
            eventtitles = new String[]{"Inauguration","Gayle Laakmaan Talk","InMobi Tech Talk","Gaming Night","Puzzle Championship","Hackathon"};
            eventschs = new String[]{"Mar 2,6:30 PM, CS 25","Mar 2, 6:30 PM, CS 25","Mar 2, 8 PM,CS 25","Mar 2, 9:30 PM, H/W Lab","Mar 3, 8:30 AM, CS 34","Mar 3, 8:30 AM, CS 24,26,34"};
            eventdescs = new String[]{"Inaugural function of Exebit","A talk by 'The Google Resume' fame Gayle Laakmann","A talk from InMobi Tech","A fierce competition where people battle out their gaming skills","A medley of puzzles in store, unleash the prowess in you and a lot of prize money is up for your grabs","Tackle real-world problems with your app-making skills"};

            ArrayList<String> listofcolors = new ArrayList<>();
            String[] loc;
            loc = new String[]{"#00BCD4","#4CAF50","#E91E63","#FFEB3B","#795548"};
            Random r = new Random();
            int col = r.nextInt();
*/
            mAdapter = new CardAdapter(listofevents, rootView.getContext());
            mRecyclerView.setAdapter(mAdapter);

           // toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar_actionbar);
           // setSupportActionBar(toolbar);

            return rootView;
        }
    }

    public static class ProfilePageFragment extends Fragment implements MaterialTabListener {

        public ProfilePageFragment() {
        }

        EditText old_password;
        EditText new_password;

        Button submit;

        CustomPagerAdapter mCustomPagerAdapter;
        public static ViewPager mViewPager;
        public static MaterialTabHost tabHost;


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile_page_main_page, container, false);
            old_password = (EditText) rootView.findViewById(R.id.old_password_edit);
            new_password = (EditText) rootView.findViewById(R.id.new_password_edit);
           // submit = (Button) rootView.findViewById(R.id.submit_change_password);

            userPassword = prefs.getString("userPassword","");

            mCustomPagerAdapter = new CustomPagerAdapter(SupportFragmentManager);

            mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
            tabHost = (MaterialTabHost) rootView.findViewById(R.id.materialTabHostText);

            mViewPager.setAdapter(mCustomPagerAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    // when user do a swipe the selected tab change
                    tabHost.setSelectedNavigationItem(position);
                    if(position==1||position==2)
                        profile_flag=1;
                    if(position==0)
                        profile_flag=0;
                }
            });

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("User details").setTabListener(this)
            );

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("Change password").setTabListener(this)
            );

            mViewPager.setCurrentItem(0);

return rootView;
        }

        @Override
        public void onTabSelected(MaterialTab tab) {
            // when the tab is clicked display the pager swipe content to the tab position
            mViewPager.setCurrentItem(tab.getPosition());
            if(tab.getPosition()==1||tab.getPosition()==2)
                profile_flag=1;
            if(tab.getPosition()==0)
                profile_flag=0;
        }

        @Override
        public void onTabReselected(MaterialTab materialTab) {

        }

        @Override
        public void onTabUnselected(MaterialTab materialTab) {

        }

        }

    public static class MyEventsFragment extends Fragment{

        public MyEventsFragment() {
        }

        private static RecyclerView mRecyclerView;
        private static CardAdapter mAdapter;

        public static String userName="";
        public static String userId="";
        public static String passWord="";

        public static String[] eventtitles, eventschs, eventdescs, eventcateg;
        public static String[] worktitles, workschs, workdescs;
        public static String[] gametitles, gameschs, gamedescs;
        public static String[] talktitles, talkschs, talkdescs;

        String str1 = "14th";
        String str2 = "15th";
        String str3 = "Contests";
        String str4 = "Gaming";
        String str5 = "Workshops";

        public static ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();

        public static SharedPreferences prefs;

        private android.support.v7.widget.Toolbar toolbar;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_my_events, container, false);
/*
            userName = prefs.getString("userName","");
            userId = prefs.getString("userId","");
            passWord = prefs.getString("passWord", "");
*/

            worktitles = new String[]{"Android", "BlueSpec", "C(Get It Right)", "Cryptography", "Data Mining", "Databases", "Hadoop", "Machine Learning", "Python", "Web"};
            workschs = new String[]{"14th March, 10 AM - 1 PM & 2 PM - 5 PM,CRC 101", "15th March,10 AM - 1 PM,CS 15", "15th March, 10 AM - 1 PM,CRC 101", "15th March,2 PM - 5 PM,CRC 103", "15th March, 2 PM - 5 PM, CRC 101", "14th March, 2 PM -5 PM ,CRC 102", "14th March , 10 AM - 1 PM & 3 PM - 5 PM, CRC 102", "14th March,10 AM - 1 PM & 2 PM - 5 PM, CRC 103", "15th March,10 AM - 1 PM, CRC 103", "15th March,10 AM - 1 PM & 2 PM - 5 PM, CRC 102"};
            workdescs = new String[]{"Details will be updated soon", "In this workshop you will be taught BSV, and also how to design a simple 5-stage pipelined microprocessor", "Learn to code in C in a professional way", "This workshop on cryptography will expose people to some new frontiers of theoretical cryptography intertwined with its applications", "A hands-on workshop for those enthusiastic about data and pattern mining", "Details will be updated soon", "A hands-on workshop with Hadoop and Banyan", "A workshop on Introduction to Machine Learning", "Learn what Python is capable of,how to write productive code and some advanced programming concepts", "Learn about the A-B-C’s as well as sines and cosines of Web Development"};
            gametitles = new String[]{"Counter Strike", "DOTA 2", "FIFA 14", "NFS : MW", "Pocket Tanks"};
            gameschs = new String[]{"14th March, 9 PM to 12 AM, M.Tech Lab"};
            gamedescs = new String[]{"Details will be updated soon"};
            eventtitles = new String[]{"Code Obfuscation", "Debugging Contest", "El Dorado", "Flipkart Hackathon", "Online Programming Contest", "Puzzle Champ", "Reverse Coding", "Triathlon", "Wikibytes"};
            eventschs = new String[]{"14th March,11 AM - 12 PM, CS 24,CS 26", "14th March, 9:30 - 11:00 AM, CS 34,36", "14th March,9 PM to 12 PM ,Online", "14th March,2 PM to 5 PM, M.Tech Lab", "14th March, 9 PM to 12 PM, Online", "14th March, 12:30 PM to 2 PM, CS 34,36", "15th March, 9:30 AM- 11 AM , CS 34,36", "14th March, 2:30 PM - 4:30PM, CS 24,26", "15th March, 9:30 AM - 12:30 PM ,CS 25"};
            eventdescs = new String[]{"Write the most complicated code of your life while cracking your opponent’s code as well", "Our main job is to bug you with lot of bugs and its upto you to debug and get the honors or to get lost", "El Dorado is an online quizzing event which needs you to answer questions to advance to higher levels", "Details will be updated soon", "One of the biggest programming contests of its kind in the country", " Two rounds of exciting puzzling and prize money is up for the grabs", "Reverse Coding is an event which tests logical thinking and basic programming skills.", "Solve innovative questions using your programming skills", "A CS oriented quiz in an engaging multimedia format"};
            eventcateg = new String[]{"Contests", "Tech Talks", "Gaming", "Workshops"};
            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view_events);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            mAdapter = new CardAdapter(listofevents, rootView.getContext());
            mRecyclerView.setAdapter(mAdapter);

            return rootView;
        }
    }

    public static class FaqFragment extends Fragment {

        public FaqFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_faq, container, false);
            return rootView;
        }
    }

    public static class SponsorsFragment extends Fragment {

        public SponsorsFragment() {
        }

        ImageView titleSponsor,eventSponsor1,eventSponsor2;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sponsors, container, false);

            titleSponsor = (ImageView) rootView.findViewById(R.id.title_sponsor);
            eventSponsor1 = (ImageView) rootView.findViewById(R.id.event_sponsor1);
            eventSponsor2 = (ImageView) rootView.findViewById(R.id.event_sponsor2);

            titleSponsor.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if(Main_Activity.internetConnection.isConnectingToInternet()==true)
                    {
                        Uri uri = Uri.parse("http://www.247-inc.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();
                }
            });

            eventSponsor1.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if(Main_Activity.internetConnection.isConnectingToInternet()==true) {
                        Uri uri = Uri.parse("http://www.flipkart.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();                }
            });

            eventSponsor2.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (Main_Activity.internetConnection.isConnectingToInternet() == true) {
                        Uri uri = Uri.parse("http://www.latentview.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    } else
                            Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();                }

            });
            return rootView;
        }
    }

    public static class HospitalityFragment extends Fragment {

        public HospitalityFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_hospitality, container, false);

            TextView tv= (TextView) rootView.findViewById(R.id.textView6);
            tv.setText("For accomodation details, CLICK HERE.");
            tv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Main_Activity.internetConnection.isConnectingToInternet()) {
                        Uri uri = Uri.parse("http://exebit.in/data/AlternateAccommodation.xlsx");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    } else
                        Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();
                }

            });
            return rootView;
        }
            }
        }



