package com.example.exebit.exebit2k15;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
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
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import ProfilePage_ActionTab.CustomPagerAdapter;
import Schedule.CardAdapter;
import Schedule.ListOfEvents_Event;
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
    //public static SharedPreferences prefs;
    public static int profile_flag;
    public FragmentTransaction fragmentTransaction;
    CircleImageView profileIcon;
    TextView profileIconText;
    public static String userFullName,userProPic,userDateOfBirth,userName,gender,userId,userPassword,userMobile,userCollege,userEmail,userHostel,userHostelRoom;
    String[] actions = new String[] {"All",
            "March 14th",
            "March 15th",
            "Contests","Gaming","Workshops"};
    static int flag_all=0,flag_14=0,flag_15=0,flag_contests=0,flag_gaming=0,flag_workshop=0,flag_talk=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.nav_bar);

        //prefs = getSharedPreferences("Exebit",MODE_PRIVATE);
        Main_Activity.userName = Main_Activity.prefs.getString("userName","");
        Main_Activity.userPassword = Main_Activity.prefs.getString("userPassword","");
            webflag=0;
         if (android.os.Build.VERSION.SDK_INT > 9) {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        //prefs = getSharedPreferences("Exebit",MODE_PRIVATE);

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
/*

        if(Main_Activity.profilePicture == null) {
            profileIcon.setImageResource(R.drawable.profilegrey);
        }
        else profileIcon.setImageBitmap(Main_Activity.profilePicture);
*/
        if(!Main_Activity.prefs.getString("userName","").isEmpty())
            profileIconText.setText(Main_Activity.prefs.getString("userName","").split("@")[0]);
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

    String[] contestsdescs = new String[]{"Do you really want to test the level of your basic programming skills in C? Here is a platform where you can use all devious methods to confuse your opponents and write the most complicated code of your life while cracking your opponent’s code as well.\n" , "If you have good debugging skills, then this is the right time and the right place to prove your mettle. Unleash your debugging skills amongst the top “debuggers” who contend for the prestigious title of “The Debugger”. In this event,our main job is to bug you with lot of bugs and its upto you to debug and get the honors or to get lost. Are you ready for the extravaganza?!\n","Eldorado is an online quizzing event which needs you to answer questions to advance to higher levels. You just need to know how to search stuff on the net and have some enthusiasm.\n","This Exebit, we’re proud to have Flipkart conduct one of the most exciting formats ever introduced - a live quiz event based on Machine Learning and other related aspects.\n","OPC is one of the biggest programming contests of its kind in the country. Be it for an expert in algorithms or for one who just finished writing his \"hello world\" program, we promise loads of fun and challenges in this contest.\n","Puzzles will cover a wide range of topics and will test your understanding in logic, reasoning and mathematics. This time puzzle champ – like last year comes with a different flavour only better!!. We will involve concepts that you learnt in high school Math and Physics.\n" +
            "   \n     \n" +
            "So for all puzzle enthusiasts this is what we at Exebit 2015 guarantee: Hours of uninterrupted intellectual entertainment!    \n ", "Reverse Coding is an event which tests logical thinking and basic programming skills. The idea behind this event is to figure out the \"Black Box\". In other words the participants are given input and output and are asked to do reverse coding, that is to identify the underlined algorithm. The event will test your basic logic skills along with pattern analysis and recognition as you play with the code, identify the patterns and decipher it.\n","Do you like solving new innovative questions too? Do you want to show your skill to give quick and elegant solutions? Then here is your chance to experiment with one of the oldest talents of mankind, logic; but in its latest form. Come participate and win cool exciting prizes.\n","A comprehensive test of General Knowledge with plenty of interesting new facts in an engaging multimedia format. The word wiki is Hawaiian for quick and we want to keep our answers Byte sized for quick processing. To celebrate IIT Madras Quiz Club winning the Inter IIT IIM Quiz Championships for the second year in a row, we follow IITM style Pounce and Bounce format of Quizzing. Expect questions to be tough and cover all areas of human endeavour. As always there will be a computer science flavour to some of the fundaes. Participation is open to all, registration is allowed on the spot. Please bring pens and come witness some of the best human computers (in teams of two) battle it out for the chance to be crowned champions and win fabulous prizes. \n"
    };
    String [] workshopdescs = new String[]{"Details will be updated soon on the website","With increasing complexity of today's ICs, we need a language that helps us design hardware with much ease. Bluespec System Verilog(BSV) is a relatively new language compared to Verilog and VHDL, and provides a much higher level of abstraction. In this workshop you will be taught BSV, and also how to design a simple 5-stage pipelined microprocessor.\n","One should understand, C programming is not just syntax and logic. It involves lots of clarity on debugging , memory and various other performance tweaks. Normally a Deployable project involves handling of huge .C/.H files. And there comes a point where we need run the same code in different architectures/systems. There are many Gcc tools that aid in writing a Good Industrial level C code. After reading above statements one might wonder \"How to write a good C program ?\" This Workshop mainly gives a pathway to, understand C in the right way. \n","This workshop on cryptography will expose people to some new frontiers of theoretical cryptography intertwined with its applications. The idea of the workshop is two-fold. Suvradip will talk on the foundations of provable security while Dipanjan will focus on Cryptanalysis and attacks on some popular cryptosystems.\n","A hands-­on workshop for those enthusiastic about data and pattern mining. Data mining is a vast field of techniques and processes which help uncover patterns in data. One example is the analysis of large quantities of data to do cluster analysis, anomaly detection and association rule mining. We will cover a collection of visualization tools and algorithms for data analysis and predictive modelling.\n","Details will be updated soon on the website","Technology landscape has been changing so rapidly especially over the past 10 years. Isn’t it utterly mind boggling to know that 90% of world’s data has been generated in the last 2 to 3 years? With the advent of ubiquitous computing, internet and social media, there has been a great deluge in the data world.\n" +
            "    \n     \n" +
            "To deal with it, to face it, to harness the real insights is like searching for needle in a haystack. Tools are everywhere but they are useless until we know how to use them. Come, learn and discuss with us about how to scale up with hadoop and branch out with banyan to processing and analyze big data. \n","Machine Learning, a sub­field in AI believes that this can be achieved by designing a machine/program that learns by itself. Algorithms that learn to do a specific task like predicting fraudulent credit card transactions, classifying emails as spam/ham, by looking at examples are Machine Learning Algorithms. \n","Python is fast becoming the next big language. This session aims to bring you up to speed with the language. It focusses on the features and capabilities of Python instead of making it a generic introduction to programming. Some neat tricks, a few advanced concepts and a whole lot of Python awesomeness are all on the platter.\n","During this six-hour long workshop, participants get to learn about the A-B-C’s as well as sines and cosines of Web Development. Case studies of widely prevalent Web Development practices serve as an insight into the real web around us. Kicking off with primary web technologies and markups languages such as HTML and CSS, the workshop ends with the participants capable enough of implementing a fully functional web service, from scratch.\n"};
    String [] gamingdescs = new String[]{"For details, click here"};
    String  techtalkdesc = new String("With enormous amount of data collected every day, Flipkart looks for finding ways of providing improved customer experience and better service. In this endeavor, Data Scientists at Flipkart focus on machine learning, optimization and visualization for solving these problems. The talk focuses on some of the ongoing activities and challenges in the area of Data Science. \n ");

    String[] contestformats = new String[]{"The event consists of 2 phases –    preliminary round     where you will be tested for your basic C programming abilities, and a    finalists’ round   . The final round will be a coding round.\n","There are two rounds which may span for 2 days. The first round itself consists of two sub rounds.     \n" +
            "\n" +

            "   Round 1-A)Five Minute Fix        \n" +
            "Its a problem you\'ve probably seen before and you probably know what it is ! (Atleast the Concept !). There can be a small error that can take down the entire program.1A will have MCQ/1 Word Answers,which are short but requires some logical and basic Programming Skills.     \n" +
            "\n" +
            "   Round 1-B)One-hour to One-day        \n" +
            "These take some investigation, and benefit from some good intuition and sharpness in looking out for errors. You’re mainly following a trail of broken data to its source.1B will have few Logical and Tricky( Not So Direct) subjective questions which may test ur Debugging Skills to the Core!\n" +
            "    \n" +
            "   Round 2) OMG WTF Bugs        \n" +
            "In this round you are more likely to have these thoughts- ”I have no idea what is going on. Dear Program, you’re shaking my faith in all things computational.” The format of this is really interesting,but it will be a secret till you clear the first round ! :) .This will not only test your debugging skills,but also your patience,faith and what not !     \n","Questions will be based on analysis, logic and maths, but can be from anywhere and of any type with no restriction on resources for answering them. It is a team event with a maximum of 3 members per team. Spanning over 3 days, from    6pm on 6th March 2015    to    11:59am on 8th March 2015   , it is certain that your spirits will be kept up throughout the entire event. Come, search, answer and win exciting prizes and goodies.\n","First round\n" +
            "The first round of the quiz is open for all participants. Participants have to form teams of 2 to take part in this round. This round is a written round, wherein the Quiz Master will announce the questions and the participants would answer them and turn their answer sheets back at the end of the round. The top six teams from this round shall pass into the next round. \n" +
            "\n" +
            "Second round\n" +
            "This is an onstage event and will follow the pattern of a live quiz. The quiz will include questions from traditional computer science questions as well as from audio, visual and photo related areas. \n" +
            "\n" +
            "Prizes to be won\n" +
            "1st prize: Digiflip Pro Tablet, Flipkart Gift coupons and lots of goodies.\n" +
            "2nd prize: Bluetooth headphones and lots of goodies.\n" +
            "Mobile Power Bank and other goodies.\n" +
            "Many more goodies waiting for all","The event will be organised on www.hackerrank.com. For all those who are not aware of this environment,\n" +
            "       \n     \n" +

            "    \t  Solution to every problem should be uploaded to the online judge.    \n     \n" +
            "    \t  The judge will run the code against a set of test cases which is not available to the contestant.     \n     \n" +
            "    \t  If the code returns the expected output to those test cases within the specified time limit, the code is judged to be accepted.     \n     \n" +
            "    \t  In case, of wrong answer a \"wrong answer\" message is displayed.     \n     \n" +
            "    \t  In case, the code doesn't return the output within the expected time limit, a \"Time limit exceeded\" message will be displayed.     \n     \n" +
            "    \t  In case the code uses more memory than that is specified, a \"Run Time error\" message will be displayed.      \n     \n" +
            "    \t  If the code doesn't compile properly for the specified language a \"Compile Error\" message will be displayed.     \n     \n" +
            "    \t  The points to every problem is calculated based on the time taken to solve that problem from the beginning of the contest with an additional 20 minutes penalty to every submission which is not accepted. Note that the penalty will be added only when a particular problem is solved.      \n     \n" +
            "    \n" +
            "The individuals are ranked in the increasing order of the time taken.    \n     \n" +
            "\n" +
            "The competition will be conducted on    14th March, 9pm to 12am       \n ","   Round 1: Preliminaries       \n     \n" +
            "Here you will have to enter the details of your team and answer a subjective question paper consisting of written puzzles. The first round will be of 1.5 hour duration. Selection to finals: Based on performance. Top 8 teams will be selected.    \n     \n" +
            "\n" +
            "   Round 2: Finals   \n" +
            "The shortlisted teams from the first round will enter into the finals, where they have to face the Big puzzles. This will include buzzer and a miniQuiz of a lot of short puzzles in a sort of rapid fire fashion     \n","The Event will be conducted in the Institute itself during the festival days. There will be 2 or 3 rounds depending on the number of participants. Not more than 10 participants will be entering the last round. The questions will be designed to test your logical skills and may include various puzzles, ciphers, codes etc.\n","Teams consisting of maximum 2 members are allowed. The event will be conducted in two rounds.\n" +
            "\n" +
            "   Preliminary Round       \n     \n" +
            "The first round will be offline written test for 2hrs. The questions will be about logic, data structures, algorithms. Few teams will be shortlisted for finals.\n" +
            "   \n     \n" +
            "   Finals       \n     \n" +
            "The duration for finals is 3hrs. Here is where the real Triathlon begins. The finals will involve 3 rounds. The first three will be on logic, algorithms and data structures and web designing. The final round is a surprise! Come here to know what it is!\n" +
            "   \n     \n" +
            "Rules for finals will be explained in greater detail during finals.    \n","   Stage One       \n     \n" +
            "Written Prelims, Teams of 2, Questions will be projected on the screen.\n" +
            "   \n     \n" +
            "\n" +
            "   Stage Two       \n     \n" +
            "Top 8 teams make it to the finals. Audio Visual Finals with IIT Madras Bounce and Pounce Format.    \n"};
    String [] workshopformat = new String[]{"   ","1st Session: Introduction to some basic and important features in BSV.    \n     \n" +
            "2nd Session: How to design microprocessors using BSV!    \n    \n","  It will be a hands-on session. \n" +
            " Introduction to writing C in GCC environment.      \n     \n" +
            " Introduction to valgrind     \n     \n" +
            " Writing multiple files, cscopes, ctags etc.     \n     \n" +
            " Debugging using GDB     \n     \n" +
            " GCC Compiler optimisation, GPROF      \n     \n" +
            " Writing portable code     \n ","   Provable Security       \n    \n     \n     \n" +
            " Information Theoretic Security     \n     \n" +
            " Perfectly Secure Encryption and One Time Pad     \n     \n" +
            " Private key generator and Pseudorandomness (PRPs, PRFs)     \n     \n" +
            " One-Way Functions (OWF) and few candidates for OWFs.     \n     \n" +
            " Security Models of Encryption schemes e.g. CPA, CCA-1, CCA-2.     \n     \n" +
            " Security of Signature Schemes e.g. WeUF-CMA, SeUF-CMA etc and Hash Functions and introduction to Random Oracles.    \n     \n" +
            "   Cryptanalysis   ","The workshop focuses on introduction to data mining techniques and followed by a hands-on session with tools such as WEKA.\n","  ","10 am to 12 noon : Interactive Session on Hadoop and Banyan supported with use cases and demo    \n     \n" +
            "3pm to 5pm : Hands on with Hadoop and Banyan    \n     \n" +
            "   Process for the internship opportunity       \n     \n" +
            "Hadoop and Banyan Workshop selection criteria    \n     \n" +
            "    \t Those who answer the questions and ask relevant questions during the session will have higher chances of getting selected.    \n     \n" +
            "    \t The session will also be followed by a puzzle/code challenge. Who ever solves the code [ in any language of their choice ] will be selected.    \n     \n" +
            "   Internship selection criteria:       \n     \n" +
            "    \t Those who perform outstandingly and are independently able to manage the hands-on workshop will be given the internship opportunity\n","In this Workshop, we will introduce key machine learning algorithms, with a lot of emphasize to practice. We will also practically see how to use tools like scikit­learn to design machine learning algorithms for problems in your hand. By the end of this workshop, you will be able to design your own email­spam classifier ! It is not just a spam classifier ! You will also get an intuition about how to approach a real­life problem and how to come up with your own intelligent programs !\n" +
            "   \n     \n" +
            " Practice session will include tutorial on Scikit­learn, a python based ML package. Previous knowledge about python would be helpful, but not necessary.","Workshop Agenda    \n \n" +
            "         \n" +
            " Setting up the toolkit. (Python, IPython Notebook)     \n     \n" +
            " “Hello, World!”     \n     \n" +
            " A short presentation on Python.     \n     \n" +
            " Getting your way around Python. (Writing basic programs)     \n     \n" +
            " More advanced topics arranged by difficulty. \n" +
            "   \n    \n        \n" +
            "   Key points the participants can gain from your workshop       \n     \n" +
            " Learn what Python is capable of.     \n     \n" +
            " Be able to write productive code in Python right from day one.      \n     \n" +
            " Learn some advanced programming concepts.    \n","This workshop is aimed at a beginner in Web Development. No previous knowledge of any of the above mentioned is expected. Nevertheless, prior coding experience would be an added advantage. Coding experience refers to a minimum understanding of what and how programs are written in C, C++, Java or Python.\n"};

    String[] gamingformat = new String[]{"Refer to the PDF file"};
    String techtalkformat = new String("Duration of the Talk\n" +
            "45min-1hour");
    String[] contestsfaq = new String[]{"   Is it team event?        \n     \n" +
            "   \t Though it is not mandatory, it is good to have one partner.\n" +
            "   \n     \n" +
            "   Should we bring our laptops?        \n     \n" +
            "   \t No, you need not.    \n ","   What is the Criteria for participation?       \n     \n" +
            "    \t You should have basic programming skills of undergraduate level.    \n     \n" +
            "\n" +
            "   Programming languages needed?        \n     \n" +
            "    \t C Programming is enough.    \n     \n" +
            "\n" +
            "   Any gadgets needed for the contest like laptops etc?       \n     \n" +
            "    \t Nothing. We will provide everything.    \n     \n" +
            "\n" +
            "   How many members per team?       \n     \n" +
            "    \t Strictly two.    \n     \n" +
            "\n" +
            "   Is it strictly Team Event?       \n     \n" +
            "    \t Yes it is!    \n     \n" +
            "\n" +
            "Should the team members be of same college?    \n     \n" +
            "    \t Not necessarily    \n","   Can there teams with less than 3 members?       \n     \n" +
            "    \t Yes, a team can atmost contain three. The prizes goes to the team irrespective of it's number of members.    \n     \n" +
            "\n" +
            "   How many questions?       \n     \n" +
            "    \t Around 20. It may change.    \n     \n" +
            "\n" +
            "   How does the event end?       \n     \n" +
            "    \t Either when the questions run out or the time runs out, whichever comes first.    \n     \n" +
            "\n" +
            "   How does one win?       \n     \n" +
            "    \t The scoreboard will be maintained. One can see their position at any time. The teams at the top by the end of the event will be declared winners.    \n     \n" +
            "\n" +
            "   What if I get doubt about a question?       \n     \n" +
            "    \t Some discussion thread will be open by the time the event starts. The link will be released in exebit facebook page or/and Eldorado home page.    \n     \n" +
            "\n" +
            "   What if I am stuck in a question?       \n     \n" +
            "    \t If many people are stuck in a question, after a threshold time some hints will be released on those discussion threads.    \n     \n" +
            "\n" +
            "   What if I find two teams collaborating?       \n     \n" +
            "    \t We are    NOT RESPONSIBLE    for vigilance of the activities during the event. The event is intended for a group of 3 and all formalities including prize distribution is done with that premise.    \n","Who should attend?\n" +
            "\n" +
            "Undergrads, post grads, research students and faculty.\n" +
            "Useful in what manner for Undergrads, post grads etc.:\n" +
            "For undergraduates it is sensitizing about the challenges Flipkart offers. For post graduates, inspiration to take up research. For research students, to understand some of the challenges in the real world vis-a-vis their learnings in their academics.","    What is the procedure for registration?        \n     \n" +
            "    \t You have to register on both the Exebit website and on the Hackerrank Contest-page.    \n     \n" +
            "\n" +
            "   Is this a team event?       \n     \n" +
            "    \t No this is an individual event.    \n     \n" +
            "\n" +
            "   Can I do something to prepare for this contest?       \n     \n" +
            "    \t Yes, this falls under the category of events called \"Competitive programming\". Besides the ones mentioned in the resources tab, there are a number of tutorials available on the internet.","   Is this a team event?       \n     \n" +
            "    \t Yes. But you can also participate individually. But the maximum team size is 3    \n ","   What will be the size of the team?       \n     \n" +
            "    \t Maximum team size will be 2.    \n     \n" +
            "\n" +
            "   Do both members need to be from same department or college?       \n     \n" +
            "    \t No any two students can form a team.    \n     \n" +
            "\n" +
            "   Do I need to be experienced programmer?       \n     \n" +
            "    \t Just basic understanding of syntax will be sufficient.    \n     \n" +
            "\n" +
            "   What coding language will be required?        \n     \n" +
            "    \t C or C++.    \n     \n" +
            "\n" +
            "   Do I need to bring laptops?    \n" +
            "    \t No.    \n ","   What will be the size of the team?       \n     \n" +
            "    \t Maximum team size will be 2.    \n     \n" +
            "\n" +
            "   Do both members need to be from same department or college?       \n     \n" +
            "    \t No any two students can form a team.    \n     \n" +
            "\n" +
            "   Do I need to be experienced programmer?       \n     \n" +
            "    \t Just basic understanding of syntax will be sufficient.    \n     \n" +
            "\n" +
            "   What coding language will be required?       \n     \n" +
            "    \t C or Java and HTML.    \n     \n" +
            "\n" +
            "   Do I need to bring laptops?       \n     \n" +
            "    \t Yes, you should.    \n ","   Is it a general quiz?       \n     \n" +
            "    \t  Yes. Questions will test general knowledge. Anything under the sun and beyond is a likely topic.    \n     \n" +
            "\n" +
            "   I have never quizzed before, Can I participate?       \n     \n" +
            "    \t Most definitely. The questions encourage guessing. Form a team and participate.    \n      \n" +
            "\n" +
            "   I don't have a team. Can I participate?       \n     \n" +
            "    \t Lone Wolves are welcome. Perhaps you can meet a partner at the venue and decide to team up.    \n "};
    String[] workshopfaq = new String[]{"   ","   What do you gain from the workshop?       \n     \n" +
            "    \t You learn a new hardware description language, designing a processor and to think hardware.    \n     \n" +
            "\n" +
            "   Is any prior knowledge required?       \n     \n" +
            "    \t Basic knowledge about digital logic is recommended.    \n     \n" +
            "\n" +
            "   Should we bring our laptops?       \n     \n" +
            "    \t No, you need not.","   Who are the target audiences?       \n     \n" +
            "    \t Workshop is for Linux enthusiasts, Beginners in C and anyone eager to know things. We like to introduce people to the next level in learning c and coding.    \n     \n" +
            "\n" +
            "   What do I gain by attending ?        \n     \n" +
            "    \t Mastery over developing Project. You might even start using Linux regularly.    \n     \n" +
            "\n" +
            "   Should we bring our laptops?       \n     \n" +
            "    \t Yes, you should.    \n     \n" +
            "\n" +
            "   Should we have any software installed?        \n     \n" +
            "    \t You need to have Ubuntu 12 or 14 installed in your system or in a virtual box.    \n","   Do we need to bring a laptop?       \n     \n" +
            "    \t No, you need not.    \n     \n" +
            "\n" +
            "   Is it a team workshop?       \n     \n" +
            "    \t No, it is an individual workshop.    \n     \n" +
            "\n" +
            "   Do we need to have any prior knowledge?        \n     \n" +
            "    \t No, but a little knowledge of Cryptography in general will be helpful although not mandatory. Some familiarity with Cryptool will make Cryptanalysis section of the workshop more engaging to the attendee.\n" +
            "   \n     \n" +
            "   What do we gain from this workshop?        \n     \n" +
            "    \t Get familiar with the recent developments and techniques in Cryptography    \n     \n" +
            "    \t Get aware of the recent and well known attacks and also applications of Cryptography     \n","   Do we need to bring a laptop?        \n     \n" +
            "    \t Yes, you should. It should have the WEKA tool installed.    \n     \n" +
            "\n" +
            "    What do you gain from this workshop?        \n     \n" +
            "    \t You will get to learn the applications of data mining techniques in the real world, and familiarity with the WEKA tool.    \n ","    ","   Is there any examination for the first session?       \n     \n" +
            "    \t No","   Is it a hands-on session?       \n     \n" +
            "    \t The first session is theoretical, but the second session is a hands-on session.    \n     \n" +
            "\n" +
            "   Should we bring our laptops?       \n     \n" +
            "    \t Yes, they are essential for the second session.    \n     \n" +
            "\n" +
            "   Are any software needed to be installed?        \n     \n" +
            "    \t Python v3.0 and ScikitLearn are recommended.    \n ","   Is any prior knowledge required?       \n     \n" +
            "    \t Participants are expected to have a basic understanding of variables, loops, functions, recursion etc.    \n     \n" +
            "\n" +
            "   Should we bring our laptops?        \n     \n" +
            "    \t Yes, you should.    \n     \n" +
            "\n" +
            "   Should any software be installed on the laptops?       \n     \n" +
            "    \t Python v3, IPython are recommended, though they can be installed on the venue also.","   \n" +
            "Do I need any prior experience in Web Development?       \n     \n" +
            "    \t Nope. We expect beginners. A bit of experience wouldn't do harm anyway.    \n     \n" +
            "\n" +
            "   What am I going to get from this workshop?       \n     \n" +
            "    \t Don't tell anyone about this. We're going to build Facebook. I promise. If you have a friend doing his MBA, please bring him along. I'll make him build Facebook.    \n     \n" +
            "\n" +
            "   Do I need to install all the above software myself?       \n     \n" +
            "    \t Formally speaking, yes. But well, we're good guys out here. We help you on the venue if you encounter any problems. We even supply the software ready for you, if at all you miss out.    \n "};
    String gamingfaq = new String("   ");
    String techtalkfaq = new String("Who should attend?\n" +
            "\n" +
            "Undergrads, post grads, research students and faculty.\n" +
            "Useful in what manner for Undergrads, post grads etc.:\n" +
            "For undergraduates it is sensitizing about the challenges Flipkart offers. For post graduates, inspiration to take up research. For research students, to understand some of the challenges in the real world vis-a-vis their learnings in their academics.");
    String[] contestscoord = new String[]{" Pratik Piyush Panchal spiderpratikp@gmail.com \n \n" +
            " Deekshit Reddy \n ", "K E Srinivas Desikan kesdesikan@gmail.com \n \n" +
            "Anand Didwania anand.didwania91@gmail.com\n ","Prithvi Raj botcha.prithviraj@gmail.com \n \n" +
            "Gorla Vineeth winningvineeth@gmail.com \n ","    ","A Sunder \n \n" +
            "G Siva Krishna sivakrishna.guntreddi@gmail.com \n ","Shiva Krishna Reddy M      shivakrishnam912@gmail.com      \n     \n" +
            "    Y S S V Kiran     sasikiran1996@gmail.com  ","   Nirav Patel      nirav111nirav@gmail.com      \n     \n" +
            "   Alok Joshi     92alok@gmail.com      \n ","   Rohit Kumar Kesa     rohithkumar162@gmail.com      \n     \n" +
            "    Darwin Reddy     darwinreddy.k@gmail.com      \n ","    Anand Aiyer       anand.r.aiyer@gmail.com   "};
    String[] workshopcoord = new String[]{"     ","Arjun Menon c.arjunmenon@gmail.com    \n     \n" +
            "Neel Gala neelgala@gmail.com \n \n" +
            "Vikas Chauhan vikaschauhan917@gmail.com  \n \n" +
            "Rahul Bodduna rahul.bodduna@gmail.com  \n","Satyanarayanan  sathya281@gmail.com","Suvradip Chakraborty suvradip1111@gmail.com \n \n" +
            "Dipanjan Das  mail.dipanjan.das@gmail.com","Abhik Mondal  abhik.mondal1992@gmail.com \n \n" +
            "Rajan Bhargava  rajanbhargava2002@gmail.com \n  \n" +
            "Sahitya Potluri sahi.493@gmail.com \n","     "," Organised by LatentView Analytics Ltd."," Prasanna Parthasarathi  pp1403@gmail.com "," Karthik koolsatan@gmail.com ","Chaitanya Munukutla  chaitanya.m61292@gmail.com \n"};
    String gamingcoord = new String("Refer to the PDF");

    String techtalkcoord = new String("        ");

    private void functioncall(String compare) {




        if(compare.equals("All")) { flag_all = 1; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=0;

            ScheduleFragment.listofevents.clear();
            for (int j = 0; j < 9; j++) {
                // if(eventschs[j].toLowerCase().contains(str1.toLowerCase()))
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0],contestsdescs[j],contestformats[j],contestsfaq[j],contestscoord[j]));
            }
            for (int j = 0; j < 10; j++) {
                //  if(workschs[j].toLowerCase().contains(str1.toLowerCase()))
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j],ScheduleFragment.workdescs[j],ScheduleFragment.eventcateg[3], workshopdescs[j],workshopformat[j],workshopfaq[j],workshopcoord[j]));
            }
            for (int j = 0; j < 5; j++) {
                //  if(gameschs[0].toLowerCase().contains(str1.toLowerCase()))
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2],gamingdescs[0],gamingformat[0],gamingfaq,gamingcoord));


            }
            ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.techtalktitles,ScheduleFragment.techtalkschs,ScheduleFragment.techtalkdescs,ScheduleFragment.eventcateg[2],techtalkdesc,techtalkformat,techtalkfaq,techtalkcoord));
            // Toast.makeText(getBaseContext(), "Displaying All" + flag_all, Toast.LENGTH_SHORT).show();
        }

        else if(compare.equals("March 14th")){ flag_all = 0; flag_workshop=0;flag_14=1;flag_15=0;flag_contests=0; flag_gaming=0;
            if(flag_14==1){    ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 9; j++) {
                    if(ScheduleFragment.eventschs[j].toLowerCase().contains(str1.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0],contestsdescs[j],contestformats[j],contestsfaq[j],contestscoord[j]));
                }
                for (int j = 0; j < 10; j++) {
                    if(ScheduleFragment.workschs[j].toLowerCase().contains(str1.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3],workshopdescs[j],workshopformat[j],workshopfaq[j],workshopcoord[j]));
                }
                for (int j = 0; j < 5; j++) {
                    if(ScheduleFragment.gameschs[0].toLowerCase().contains(str1.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2],gamingdescs[0],gamingformat[0],gamingfaq,gamingcoord));

                }
                ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.techtalktitles,ScheduleFragment.techtalkschs,ScheduleFragment.techtalkdescs,ScheduleFragment.eventcateg[2],techtalkdesc,techtalkformat,techtalkfaq,techtalkcoord));

            }}
        else if(compare.equals("March 15th")){flag_all = 0; flag_workshop=0;flag_14=0;flag_15=1;flag_contests=0; flag_gaming=0;
            if(flag_15==1) {
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 9; j++) {
                    if (ScheduleFragment.eventschs[j].toLowerCase().contains(str2.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0],contestsdescs[j],contestformats[j],contestsfaq[j],contestscoord[j]));
                }
                for (int j = 0; j < 10; j++) {
                    if (ScheduleFragment.workschs[j].toLowerCase().contains(str2.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3],workshopdescs[j],workshopformat[j],workshopfaq[j],workshopcoord[j]));
                }
                for (int j = 0; j < 5; j++) {
                    if (ScheduleFragment.gameschs[0].toLowerCase().contains(str2.toLowerCase()))
                        ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2],gamingdescs[0],gamingformat[0],gamingfaq,gamingcoord));

                }
            }
        }
        else if(compare.equals("Contests")){ flag_all = 0; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=1; flag_gaming=0;
            if(flag_contests==1){
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 9; j++) {

                    ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.eventtitles[j], ScheduleFragment.eventschs[j], ScheduleFragment.eventdescs[j], ScheduleFragment.eventcateg[0],contestsdescs[j],contestformats[j],contestsfaq[j],contestscoord[j]));
                }

            }}
        else if(compare.equals("Gaming")){flag_all = 0; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=1;
            if(flag_gaming==1){
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 5; j++) {

                    ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.gametitles[j], ScheduleFragment.gameschs[0], ScheduleFragment.gamedescs[0], ScheduleFragment.eventcateg[2],gamingdescs[0],gamingformat[0],gamingfaq,gamingcoord));
                }

            }}
        else if(compare.equals("Workshops")){flag_all = 0; flag_workshop=1;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=0;
            if(flag_workshop==1){
                ScheduleFragment.listofevents.clear();
                for (int j = 0; j < 10; j++) {

                    ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.worktitles[j], ScheduleFragment.workschs[j], ScheduleFragment.workdescs[j], ScheduleFragment.eventcateg[3],workshopdescs[j],workshopformat[j],workshopfaq[j],workshopcoord[j]));
                }
            }}
        else if(compare.equals("Tech Talks")) { flag_all = 0; flag_workshop=0;flag_14=0;flag_15=0;flag_contests=0; flag_gaming=0; flag_talk=0;
            ScheduleFragment.listofevents.clear();
            ScheduleFragment.listofevents.add(new ListOfEvents_Event(ScheduleFragment.techtalktitles, ScheduleFragment.techtalkschs, ScheduleFragment.techtalkdescs, ScheduleFragment.eventcateg[1],techtalkdesc,techtalkformat,techtalkfaq,techtalkcoord));

            //  ScheduleFragment aScheduleFragment = new ScheduleFragment();

        }
        ScheduleFragment.mRecyclerView.getAdapter().notifyDataSetChanged();

    }

    /*private void functioncall(String compare) {




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


    }*/
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

                fragmentTransaction.replace(R.id.frame_container, new HospitalityFragment());
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
                fragmentTransaction.replace(R.id.frame_container,new FaqFragment());
            else
                fragmentTransaction.replace(R.id.frame_container,new SponsorsFragment());
        }
        if(position==4)
        {
            webflag=4;
            fragmentTransaction.replace(R.id.frame_container,new SponsorsFragment());
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
       /* if(position==5){
            webflag=5;
            fragmentTransaction.replace(R.id.frame_container,new SponsorsFragment());
            getSupportActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
            getSupportActionBar().setDefaultDisplayHomeAsUpEnabled(true);
        }
       */ if(position==5){
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
                   // Main_Activity.prefs.edit().putString("userProPic",Main_Activity.encodeTobase64(Main_Activity.profilePicture));

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
            /*else if(f instanceof MyEventsFragment) {

                fragmentTransaction.replace(R.id.frame_container,new MyEventsFragment());

            }
            */else if(f instanceof ScheduleFragment) {
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

    /*public static class DashBoardFragment extends Fragment {
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
          *//*  byte[] post = EncodingUtils.getBytes("name=" + Main_Activity.userName + "&password=" + Main_Activity.userPassword, "base64");
            webView.postUrl("http://exebit.in/backend/_login.php", post);
*//*
            *//*webView.setOnTouchListener(new View.OnTouchListener() {

                public boolean onTouch(View v, MotionEvent event) {
                    WebView.HitTestResult hr = ((WebView) v).getHitTestResult();

                    Log.i("touched", "getExtra = " + hr.getExtra() + "\t\t Type=" + hr.getType());
                    if(hr.getExtra()==null && hr.getType()==0) {
                        Toast.makeText(rootView.getContext(),"time to hide the webview",Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            });*//*


            *//*Thread t1 = new Thread(new Runnable() {
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
*//*

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

            String script = "<script>document.getElementsByName('email')[0].value='" + Main_Activity.userName + "';document.getElementsByName('password')[0].value='" + Main_Activity.userPassword +"';</script>";*//*
            WebSettings webSettings = webView.getSettings();
            webSettings.setJavaScriptEnabled(true);*//*

*//*
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
            threadLogin.start();*//*



            return rootView;
        }
    }*/

    /*public static class ScheduleFragment extends Fragment {

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
*//*
            userName = prefs.getString("userName","");
            userId = prefs.getString("userId","");
            passWord = prefs.getString("passWord", "");

*//*
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

            *//*ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();
            String[] eventtitles,eventschs,eventdescs;
            eventtitles = new String[]{"Inauguration","Gayle Laakmaan Talk","InMobi Tech Talk","Gaming Night","Puzzle Championship","Hackathon"};
            eventschs = new String[]{"Mar 2,6:30 PM, CS 25","Mar 2, 6:30 PM, CS 25","Mar 2, 8 PM,CS 25","Mar 2, 9:30 PM, H/W Lab","Mar 3, 8:30 AM, CS 34","Mar 3, 8:30 AM, CS 24,26,34"};
            eventdescs = new String[]{"Inaugural function of Exebit","A talk by 'The Google Resume' fame Gayle Laakmann","A talk from InMobi Tech","A fierce competition where people battle out their gaming skills","A medley of puzzles in store, unleash the prowess in you and a lot of prize money is up for your grabs","Tackle real-world problems with your app-making skills"};

            ArrayList<String> listofcolors = new ArrayList<>();
            String[] loc;
            loc = new String[]{"#00BCD4","#4CAF50","#E91E63","#FFEB3B","#795548"};
            Random r = new Random();
            int col = r.nextInt();
*//*
            mAdapter = new CardAdapter(listofevents, rootView.getContext());
            mRecyclerView.setAdapter(mAdapter);

           // toolbar = (android.support.v7.widget.Toolbar) rootView.findViewById(R.id.toolbar_actionbar);
           // setSupportActionBar(toolbar);

            return rootView;
        }
    }
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

        public static String[] eventtitles, eventschs, eventdescs, eventcateg;
        public static String[] worktitles, workschs, workdescs;
        public static String[] gametitles, gameschs, gamedescs;



        public static String techtalktitles =  new String("Flipkart Tech Talk");
        public static String techtalkschs = new String("14th March , 9.30 AM -10.30 AM, CS 25");
        public static String techtalkdescs = new  String("The talk focuses on some of the ongoing activities and challenges in the area of Data Science");

        String str1 = "14th";
        String str2 = "15th";
        String str3 = "Contests";
        String str4 = "Gaming";
        String str5 = "Workshops";


        public static ArrayList<ListOfEvents_Event> listofevents = new ArrayList<>();


//        public int randInt(int min, int max) {
//
//            Random rand = new Random();
//
//            int randomNum = rand.nextInt((max - min) + 1) + min;
//
//            return randomNum;
//        }

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
            eventtitles = new String[]{"Code Obfuscation", "Debugging Contest", "El Dorado", "Flipkart Tech Quiz", "Online Programming Contest", "Puzzle Champ", "Reverse Coding", "Triathlon", "Wikibytes"};
            eventschs = new String[]{"14th March,11 AM - 12 PM, CS 24,CS 26", "14th March, 9:30 - 11:00 AM, CS 34,36", "14th March,9 PM to 12 PM ,Online", "14th March,2 PM to 5 PM, M.Tech Lab", "14th March, 9 PM to 12 PM, Online", "14th March, 12:30 PM to 2 PM, CS 34,36", "15th March, 9:30 AM- 11 AM , CS 34,36", "14th March, 2:30 PM - 4:30PM, CS 24,26", "15th March, 9:30 AM - 12:30 PM ,CS 25"};
            eventdescs = new String[]{"Write the most complicated code of your life while cracking your opponent’s code as well", "Our main job is to bug you with lot of bugs and its upto you to debug and get the honors or to get lost", "El Dorado is an online quizzing event which needs you to answer questions to advance to higher levels", "A live quiz event based on Machine Learning and other related aspects.", "One of the biggest programming contests of its kind in the country", " Two rounds of exciting puzzling and prize money is up for the grabs", "Reverse Coding is an event which tests logical thinking and basic programming skills.", "Solve innovative questions using your programming skills", "A CS oriented quiz in an engaging multimedia format"};
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

    public static class ProfilePageFragment extends Fragment {

        public ProfilePageFragment() {
        }

        EditText old_password;
        EditText new_password;

        Button submit;

        WebView webView;

        /*CustomPagerAdapter mCustomPagerAdapter;
        public static ViewPager mViewPager;
        public static MaterialTabHost tabHost;*/

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_dash_board, container, false);

            if(savedInstanceState==null) {
                Log.i("saved instance state", "is null");
            }
            else { Log.i("saved instance state", "is not null");

            }

            webView = (WebView) rootView.findViewById(R.id.webview1);

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
                    "                <input class=\"blueField\" type=\"text\" name=\"email\" value=\""+ Main_Activity.userName+"\" />\n" +
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

            String script = "<script>document.getElementsByName('email')[0].value='" + Main_Activity.userName + "';document.getElementsByName('password')[0].value='" + Main_Activity.userPassword +"';</script>";






            /*old_password = (EditText) rootView.findViewById(R.id.old_password_edit);
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

                    *//*if(position==1) {
                        if (Main_Activity.internetConnection.isConnectingToInternet() == false)
                            Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                        tabHost.setSelectedNavigationItem(0);
                        mViewPager.setCurrentItem(0);
                    }
    *//*

                        getChildFragmentManager().popBackStack();
                        tabHost.setSelectedNavigationItem(position);
                        mViewPager.setCurrentItem(position);

                    if (position == 1 || position == 2)
                        profile_flag = 1;
                    if (position == 0)
                        profile_flag = 0;
                }



            } );

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("My details").setTabListener(this)
            );

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("My Events").setTabListener(this)
            );

            tabHost.addTab(
                    tabHost.newTab()
                            .setText("Change password").setTabListener(this)
            );

            mViewPager.setCurrentItem(0);
*/
return rootView;
        }

        /*@Override
        public void onTabSelected(MaterialTab tab) {
            // when the tab is clicked display the pager swipe content to the tab position
  *//*          if(tab.getPosition()==1)
            {if(Main_Activity.internetConnection.isConnectingToInternet()==false) {
                    Toast.makeText(getActivity().getApplicationContext(), "Please check your internet connection", Toast.LENGTH_SHORT).show();
                    mViewPager.setCurrentItem(0);
                    tabHost.setSelectedNavigationItem(0);

                }
            }
  *//*
                mViewPager.setCurrentItem(tab.getPosition());
                tabHost.setSelectedNavigationItem(tab.getPosition());
            if (tab.getPosition() == 1 || tab.getPosition() == 2)
                profile_flag = 1;
            if (tab.getPosition() == 0)
                profile_flag = 0;
        }

        @Override
        public void onTabReselected(MaterialTab materialTab) {

        }

        @Override
        public void onTabUnselected(MaterialTab materialTab) {

        }
*/
        }

    /*public static class MyEventsFragment extends Fragment{

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
*//*
            userName = prefs.getString("userName","");
            userId = prefs.getString("userId","");
            passWord = prefs.getString("passWord", "");
*//*

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
*/
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

        ImageView titleSponsor,eventSponsor1,eventSponsor2,eventSponsor3,associateSponsor;
        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_sponsors, container, false);

            titleSponsor = (ImageView) rootView.findViewById(R.id.title_sponsor);
            eventSponsor1 = (ImageView) rootView.findViewById(R.id.event_sponsor1);
            eventSponsor2 = (ImageView) rootView.findViewById(R.id.event_sponsor2);
            eventSponsor3 = (ImageView) rootView.findViewById(R.id.event_sponsor3);
            associateSponsor = (ImageView) rootView.findViewById(R.id.associate_sponsor);

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
                    if(Main_Activity.internetConnection.isConnectingToInternet()==true) {
                        Uri uri = Uri.parse("http://www.latentview.com/");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();                }
            });

            eventSponsor3.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {
                    if (Main_Activity.internetConnection.isConnectingToInternet() == true) {
                        Uri uri = Uri.parse("http://www.ericsson.com/in");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    } else
                            Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();                }

            });

            associateSponsor.setOnClickListener(new View.OnClickListener() {

                public void onClick(View v) {

                    if(Main_Activity.internetConnection.isConnectingToInternet()==true)
                    {
                        Uri uri = Uri.parse("http://go.sap.com/index.html");
                        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                        intent.addCategory(Intent.CATEGORY_BROWSABLE);
                        startActivity(intent);
                    }
                    else
                        Toast.makeText(getActivity().getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();
                }
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



