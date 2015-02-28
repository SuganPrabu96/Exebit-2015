package com.example.exebit.exebit2k15;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;
import android.annotation.TargetApi;
import android.app.Dialog;
import android.os.Build;
import android.util.Log;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import java.util.Calendar;

public class Main_Activity extends ActionBarActivity {
    Dialog myDialog;
    public static SharedPreferences prefs = null;
    public static String userFullName,userDateOfBirth,userName,gender,userId,userPassword,userMobile,userCollege,userEmail,userHostel,userHostelRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

            Window window = Main_Activity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.setStatusBarColor(Main_Activity.this.getResources().getColor(R.color.myStatusBarColor));
            getSupportActionBar().setTitle("Exebit 2015");

        prefs=getSharedPreferences("Exebit",MODE_PRIVATE);
        gender="Male";
        /*userFullName="SuganPrabu";
        userDateOfBirth="06th Nov 1996";
        userName="Sugan";
        userId="";
        userPassword="SuganPrabu";
        userMobile="9940246940";
        userCollege="IITM";
        userEmail="suganprabu1996@gmail.com";
        userHostel="Pampa";
        userHostelRoom="651";
        prefs.edit().putString("userFullName",userFullName).apply();
        prefs.edit().putString("userDateOfBirth",userDateOfBirth).apply();
        prefs.edit().putString("userName",userName).apply();
        prefs.edit().putString("userCollege",userCollege).apply();
        prefs.edit().putString("userId",userId).apply();
        prefs.edit().putString("userPassword",userPassword).apply();
        prefs.edit().putString("userMobile",userMobile).apply();
        prefs.edit().putString("userEmail",userEmail).apply();
        prefs.edit().putString("userHostel",userHostel).apply();
        prefs.edit().putString("userHostelRoom",userHostelRoom).apply();
        */if(!prefs.getString("userId","").equals(""))
           {
               Intent i = new Intent(Main_Activity.this,SecondActivity.class);
               i.putExtra("Login status",1);
               startActivity(i);
               finish();
           }
        final ImageView iv=(ImageView) findViewById(R.id.imageView2);
        final Button b1= (Button)findViewById(R.id.button1);
        final Button b2= (Button) findViewById(R.id.button2);
        final Button b3= (Button) findViewById(R.id.button3);
        iv.setAlpha(0f);
        b1.setAlpha(0f);
        b2.setAlpha(0f);
        b3.setAlpha(0f);
        RelativeLayout rv= (RelativeLayout) findViewById(R.id.matrix);
        //rv.setY(-400);
        TranslateAnimation tAnimation = new TranslateAnimation(0, 0, -1000,0);
        tAnimation.setDuration(1000);
        tAnimation.setRepeatCount(0);
        tAnimation.setInterpolator(new AccelerateDecelerateInterpolator());
        tAnimation.setFillAfter(true);
        tAnimation.setAnimationListener(new AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
            @Override
            public void onAnimationEnd(Animation animation) {
                iv.animate().alpha(1f).setDuration(1000).start();
                b1.animate().alpha(1f).setDuration(1000).start();
                b2.animate().alpha(1f).setDuration(1000).start();
                b3.animate().alpha(1f).setDuration(1000).start();
            }
        });

        rv.startAnimation(tAnimation);



        myDialog=new Dialog(this);
        b1.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {
                Intent intent= new Intent(Main_Activity.this,Register.class);
                startActivity(intent);
            }
        });
        //String error;
        //Log.e("error", "working");
        b2.setOnClickListener(new OnClickListener() {


            public void onClick(View arg0) {

                callLoginDialog();

            }
        });
        b3.setOnClickListener(new OnClickListener() {

            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(Main_Activity.this,SecondActivity.class);
                intent.putExtra("Login status",0);
                startActivity(intent);
                finish();
            }
        });
    }
    private void callLoginDialog()
    {

        myDialog.setContentView(R.layout.login);
        myDialog.setCancelable(true);
        Button login = (Button) myDialog.findViewById(R.id.button1);

        final EditText name = (EditText) myDialog.findViewById(R.id.editText1);
        final EditText password = (EditText) myDialog.findViewById(R.id.editText2);

        myDialog.show();
        Log.e("err", "working");
        login.setOnClickListener(new OnClickListener()
        {

            @Override
            public void onClick(View v)
            {
                String strUserName = name.getText().toString();
                String strPassword = password.getText().toString();
                userFullName = "Sugan";
                gender="Male";

                 //TODO : from the database get all the user details namely username,password,college,gender,userID,Mobile,Email,Hostel,HostelRoom

                    if (!(strUserName.equals("") || strPassword.equals(""))) {
                        //TODO : make a database request and give a result
                        boolean isValidated;
                        if (strUserName.equals("sugan") && strPassword.equals("don")) {
                            isValidated = true;
                            prefs.edit().putString("userFullName",userFullName).apply();
                            prefs.edit().putString("userName", strUserName).apply();
                            prefs.edit().putString("userCollege", userCollege).apply();
                            prefs.edit().putString("userId", userId).apply();
                            prefs.edit().putString("gender",gender).apply();
                            prefs.edit().putString("userPassword", strPassword).apply();
                            prefs.edit().putString("userMobile", userMobile).apply();
                            prefs.edit().putString("userEmail", userEmail).apply();
                            prefs.edit().putString("userHostel", userHostel).apply();
                            prefs.edit().putString("userHostelRoom", userHostelRoom).apply();
                        } else {
                            isValidated = false;
                            Toast.makeText(getApplicationContext(), "The username or password you entered is incorrect", Toast.LENGTH_SHORT).show();
                        }

                        if (isValidated) {
                            Intent intent = new Intent(Main_Activity.this, SecondActivity.class);
                            intent.putExtra("Login status", 1);

                            //TODO : make a database request and populate my events with the user events

                            startActivity(intent);
                            finish();
                        }
                    } else if(strUserName.equals(""))
                        Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_SHORT).show();
                }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
    }

    /*@Override
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

        if (id == R.id.help) {
            AlertDialog.Builder b1 = new AlertDialog.Builder(Main_Activity.this);
            b1.setTitle("Help");
            b1.create().show();
            return true;
        }

        else if(id == R.id.about_exebit)
        {
            AlertDialog.Builder b1 = new AlertDialog.Builder(Main_Activity.this);
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

}


	