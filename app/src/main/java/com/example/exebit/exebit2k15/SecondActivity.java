package com.example.exebit.exebit2k15;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import ProfilePage_ActionTab.CustomPagerAdapter;
import Schedule_recyclerView.CardAdapter;
import Schedule_recyclerView.ListOfEvents_Event;
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
    int flag;
    ImageView profileIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

        profileIcon = (ImageView) findViewById(R.id.profilepic);

        // TODO check if the email ID of the user has an associated profile picture
       /* if(Main_Activity.gender.equals("Male")) profileIcon.setImageResource(R.drawable.profilemale2);
        else if(Main_Activity.gender.equals("Female")) profileIcon.setImageResource(R.drawable.profilefemale2);
*/
        Window window = SecondActivity.this.getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.setStatusBarColor(SecondActivity.this.getResources().getColor(R.color.myStatusBarColor));
        getSupportActionBar().setTitle("Exebit 2015");


        getSupportActionBar().setTitle("Exebit 2015");

        SupportFragmentManager = getSupportFragmentManager();

        List<NavDrawerItem> datalist = null; /*= new LinkedList<>(Arrays.asList(data.navtitles));*/
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.frame_container, new ScheduleFragment());
        fragmentTransaction.commit();
        Intent i = getIntent();
        flag=i.getExtras().getInt("Login status");
        if(flag==1)
            datalist = data.getNavDrawerItems();
        else if(flag==0)
            datalist = data.getNavDrawerItemsNotLoggedIn();
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawerList = (ListView) findViewById(R.id.list_slidermenu);
        // Set the adapter for the list view
        drawerList.setAdapter(new NavDrawerListAdapter(getApplicationContext(),datalist));
        // Set the list's click listener
        drawerList.setOnItemClickListener(new DrawerItemClickListener());
        mDrawerToggle = new ActionBarDrawerToggle(
                this,                  /* host Activity */
                drawerLayout,         /* DrawerLayout object */
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
       /* getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setIcon(R.drawable.ic_drawer);*/
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        if(position==0) {
            fragmentTransaction.replace(R.id.frame_container, new ScheduleFragment());

           // setTitle("pos0");
           // Toast.makeText(getApplicationContext(),"pos0", Toast.LENGTH_SHORT).show();
        }
        if(position==1) {
            SupportFragmentManager=getSupportFragmentManager();
            if(flag==1)
                fragmentTransaction.replace(R.id.frame_container, new ProfilePageFragment());
            else
                fragmentTransaction.replace(R.id.frame_container, new FaqFragment());
            //  setTitle("pos1");
           // Toast.makeText(getApplicationContext(),"pos1", Toast.LENGTH_SHORT).show();
        }
        if(position==2){
            fragmentTransaction.replace(R.id.frame_container, new MyEventsFragment());
          //  setTitle("pos2");
           // Toast.makeText(getApplicationContext(),"pos2", Toast.LENGTH_SHORT).show();
        }
        if(position==3){
            fragmentTransaction.replace(R.id.frame_container,new FaqFragment());
        }
        if(position==4){

            AlertDialog.Builder builder = new AlertDialog.Builder(SecondActivity.this);
            builder.setMessage("Are you sure?");

            builder.setNegativeButton("No",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
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

                    Intent i = new Intent(SecondActivity.this,Main_Activity.class);
                    startActivity(i);
                    finish();
                    dialog.cancel();
                }
            });
            builder.create().show();

        }
        fragmentTransaction.commit();
        // Highlight the selected item, update the title, and close the drawer
        drawerList.setItemChecked(position, true);
        for(int i=0;i<drawerList.getChildCount();i++)
            drawerList.getChildAt(i).setBackgroundColor(getResources().getColor(R.color.NavigationBarUnselectedItem));
        drawerList.getChildAt(position).setBackgroundColor(getResources().getColor(R.color.NavigationBarSelectedItem));
        drawerLayout.closeDrawer(drawerList);
    }

    @Override
    public void setTitle(CharSequence title) {
        mTitle=title;
//        getSupportActionBar().setTitle(title);
    }

    @Override
    public void onBackPressed(){
        if(drawerLayout.isDrawerOpen(Gravity.LEFT))
            drawerLayout.closeDrawer(Gravity.LEFT);
        else
        {
            finish();
        }
    }

   /* @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

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
    public static class ScheduleFragment extends Fragment {

        public ScheduleFragment() {
        }

        private static RecyclerView mRecyclerView;
        private static CardAdapter mAdapter;

        public static String userName="";
        public static String userId="";
        public static String passWord="";

        public static SharedPreferences prefs;


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

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();
            String[] eventtitles,eventschs,eventdescs;
            eventtitles = new String[]{"Inauguration","Gayle Laakmaan Talk","InMobi Tech Talk","Gaming Night","Puzzle Championship","Hackathon"};
            eventschs = new String[]{"Mar 2,6:30 PM, CS 25","Mar 2, 6:30 PM, CS 25","Mar 2, 8 PM,CS 25","Mar 2, 9:30 PM, H/W Lab","Mar 3, 8:30 AM, CS 34","Mar 3, 8:30 AM, CS 24,26,34"};
            eventdescs = new String[]{"Inaugural function of Exebit","A talk by 'The Google Resume' fame Gayle Laakmann","A talk from InMobi Tech","A fierce competition where people battle out their gaming skills","A medley of puzzles in store, unleash the prowess in you and a lot of prize money is up for your grabs","Tackle real-world problems with your app-making skills"};

            ArrayList<String> listofcolors = new ArrayList<>();
            String[] loc;
            loc = new String[]{"#00BCD4","#4CAF50","#E91E63","#FFEB3B","#795548"};
            Random r = new Random();
            int col = r.nextInt();

            for(int j=0; j < 6; j++) {
                listofevents.add(new ListOfEvents_Event(eventtitles[j], eventschs[j], eventdescs[j]));
            }

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

        CustomPagerAdapter mCustomPagerAdapter;
        ViewPager mViewPager;
        MaterialTabHost tabHost;

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_profile_page_main_page, container, false);
            /*prefs = getSharedPreferences("Exebit", MODE_PRIVATE);
            prefs.edit().putString("userName", "");
            prefs.edit().putString("userId","");
            prefs.edit().putString("userPassword","");
            userName = prefs.getString("userName","");
            userId = prefs.getString("userId","");
            passWord = prefs.getString("passWord","");
            */old_password = (EditText) rootView.findViewById(R.id.old_password_edit);
            new_password = (EditText) rootView.findViewById(R.id.new_password_edit);

            mCustomPagerAdapter = new CustomPagerAdapter(SupportFragmentManager);

            mViewPager = (ViewPager) rootView.findViewById(R.id.pager);
            tabHost = (MaterialTabHost) rootView.findViewById(R.id.materialTabHostText);

            mViewPager.setAdapter(mCustomPagerAdapter);
            mViewPager.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
                @Override
                public void onPageSelected(int position) {
                    // when user do a swipe the selected tab change
                    tabHost.setSelectedNavigationItem(position);
                }
            });

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("User details").setTabListener(this)
            );

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("Tab").setTabListener(this)
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

            mRecyclerView = (RecyclerView) rootView.findViewById(R.id.my_recycler_view);
            mRecyclerView.setHasFixedSize(true);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(rootView.getContext()));
            mRecyclerView.setItemAnimator(new DefaultItemAnimator());

            ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();
            String[] eventtitles,eventschs,eventdescs;
            eventtitles = new String[]{"Inauguration","Gayle Laakmaan Talk","InMobi Tech Talk","Gaming Night","Puzzle Championship","Hackathon"};
            eventschs = new String[]{"Mar 2,6:30 PM, CS 25","Mar 2, 6:30 PM, CS 25","Mar 2, 8 PM,CS 25","Mar 2, 9:30 PM, H/W Lab","Mar 3, 8:30 AM, CS 34","Mar 3, 8:30 AM, CS 24,26,34"};
            eventdescs = new String[]{"Inaugural function of Exebit","A talk by 'The Google Resume' fame Gayle Laakmann","A talk from InMobi Tech","A fierce competition where people battle out their gaming skills","A medley of puzzles in store, unleash the prowess in you and a lot of prize money is up for your grabs","Tackle real-world problems with your app-making skills"};

            ArrayList<String> listofcolors = new ArrayList<>();
            String[] loc;
            loc = new String[]{"#00BCD4","#4CAF50","#E91E63","#FFEB3B","#795548"};
            Random r = new Random();
            int col = r.nextInt();

            for(int j=0; j < 6; j++) {
                listofevents.add(new ListOfEvents_Event(eventtitles[j], eventschs[j], eventdescs[j]));
            }

            mAdapter = new CardAdapter(listofevents, rootView.getContext());
            mRecyclerView.setAdapter(mAdapter);

            // toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar_actionbar);
            // setSupportActionBar(toolbar);

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
    }

