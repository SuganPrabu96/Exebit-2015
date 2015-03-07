package com.example.exebit.exebit2k15;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
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

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.GoogleApiClient.ConnectionCallbacks;
import com.google.android.gms.common.api.GoogleApiClient.OnConnectionFailedListener;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.plus.Plus;
import com.google.android.gms.plus.model.people.Person;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import util.ConnectionDetector;


public class Main_Activity extends ActionBarActivity{
    Dialog myDialog;
    public GoogleApiClient mGoogleApiClient;
    public static SharedPreferences prefs = null;
    public static ImageView profilePic = null;
    public static Bitmap profilePicture = null;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private static final int PROFILE_PIC_SIZE = 400;
    private boolean mSignInClicked;
    public static boolean isValidated = true;
    public static ConnectionDetector internetConnection;

    private ConnectionResult mConnectionResult;
    public static String userFullName,userProPic,userDateOfBirth,userName,gender,userId,userPassword,userMobile,userCollege,userEmail,userHostel,userHostelRoom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first);

        internetConnection = new ConnectionDetector(getApplicationContext());
            Window window = Main_Activity.this.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            if(android.os.Build.VERSION.SDK_INT>= Build.VERSION_CODES.LOLLIPOP) {
                window.setStatusBarColor(Main_Activity.this.getResources().getColor(R.color.myStatusBarColor));
            }

            getSupportActionBar().setTitle("Exebit 2015");

        prefs=getSharedPreferences("Exebit",MODE_PRIVATE);
        gender="Male";

        /*userEmail="suganprabu1996@gmail.com";
        userProPic="";
        userFullName="SuganPrabu";
        userCollege="IIT MADRAS";
        *//*userDateOfBirth="06th Nov 1996";
        userName="Sugan";
        userId="EE14B060";
        userPassword="SuganPrabu";
        userMobile="9940246940";
        userCollege="IITM";
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
        prefs.edit().putString("userHostelRoom",userHostelRoom).apply();*/

        userEmail = prefs.getString("userEmail","");


        /*if(userEmail.equals(""))
            {getProfileInformation();
            BitmapDrawable pro_pic = (BitmapDrawable) profilePic.getDrawable();
            profilePicture = pro_pic.getBitmap();
            prefs.edit().putString("userProPic", encodeTobase64(profilePicture)).apply();
        }
        else {
           // profilePicture = decodeBase64(prefs.getString("userProPic", null));
            getProfileInformation();
           *//* BitmapDrawable pro_pic = (BitmapDrawable) profilePic.getDrawable();
            profilePicture = pro_pic.getBitmap();
            Log.d("t",encodeTobase64(profilePicture));*//*
        }
        */if(!prefs.getString("userFullName","").equals(""))
           {
               Intent i = new Intent(Main_Activity.this,SecondActivity.class);
               i.putExtra("Login status",1);
               prefs.edit().putString("userPassword", userPassword).apply();
               prefs.edit().putString("userEmail", userEmail).apply();
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
                if(internetConnection.isConnectingToInternet()==true)
                {Intent intent= new Intent(Main_Activity.this,Register.class);
                startActivity(intent);}
                else
                    Toast.makeText(getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();
            }
        });
        //String error;
        //Log.e("error", "working");
        b2.setOnClickListener(new OnClickListener() {


            public void onClick(View arg0) {

                if(internetConnection.isConnectingToInternet()==true)
                    callLoginDialog();
                else
                    Toast.makeText(getApplicationContext(),"Network error. Check your network connection",Toast.LENGTH_SHORT).show();

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

    public static String encodeTobase64(Bitmap image) {
        try {
            Bitmap immage = image;
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            immage.compress(Bitmap.CompressFormat.PNG, 100, baos);
            byte[] b = baos.toByteArray();
            String imageEncoded = Base64.encodeToString(b, Base64.DEFAULT);
            Log.d("Image Log:", imageEncoded);
            return imageEncoded;
        }
        catch(Exception e) {
            e.getMessage();
            return null;
        }
    }

    public static Bitmap decodeBase64(String input) {
        try{
            byte [] encodeByte=Base64.decode(input,Base64.DEFAULT);
            Bitmap bitmap=BitmapFactory.decodeByteArray(encodeByte, 0, encodeByte.length);
            return bitmap;
        }catch(Exception e){
            e.getMessage();
            return null;
        }
    }

    private void callLoginDialog()
    {
        myDialog.setTitle("");
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

                        /*if (strUserName.equals("sugan") && strPassword.equals("exebit")) {
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
                        }*/

                        if (isValidated) {
                            Intent intent = new Intent(Main_Activity.this, SecondActivity.class);
                            intent.putExtra("Login status", 1);

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

                            /*Thread threadLogin = new Thread( new Runnable() {
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
                                            Log.i("response", responseStr);
                                        } catch (IOException e) {
                                            e.printStackTrace();
                                        }
                                    }
                                }
                            });
*/
/*
                            //TODO : make a database request and populate my events with the user events
                            threadLogin.start();*/
/*
                            HashMap<String, String> data = new HashMap<String, String>();
                            data.put("name", Main_Activity.userName);
                            data.put("password", Main_Activity.userPassword);
                            AsyncHttpPost asyncHttpPost = new AsyncHttpPost(getApplicationContext(),data);
                            asyncHttpPost.execute("http://exebit.in/_login.php");*/
                            new FetchTask().execute(strUserName,strPassword, getApplicationContext());

                        }
                    } else if(strUserName.equals(""))
                        Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_SHORT).show();
                }
        });
    }

   /* protected void onStart() {
        super.onStart();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        if(!mGoogleApiClient.equals(null))
            mGoogleApiClient.connect();
        Log.d("t1","Inside onStart");
    }
*/
   /* protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    *//**
     * Method to resolve any signin errors
     * *//*
    private void resolveSignInError() {
        if (mConnectionResult.hasResolution()) {
            try {
                mIntentInProgress = true;
                mConnectionResult.startResolutionForResult(this, RC_SIGN_IN);
            } catch (IntentSender.SendIntentException e) {
                mIntentInProgress = false;
                mGoogleApiClient.connect();
            }
        }
    }
*/
    @Override
    public void onBackPressed() {
        finish();
    }

  /*  @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        //Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information


    }
*/
   /* private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = null;
                String personPhotoUrl = null;
                String personGooglePlusProfile = null;
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                personName = currentPerson.getDisplayName();
                            personPhotoUrl = currentPerson.getImage().getUrl();
                            personGooglePlusProfile = currentPerson.getUrl();

                Log.d("URL",personPhotoUrl);
                // by default the profile url gives 50x50 px image only
                // we can replace the value with whatever dimension we want by
                // replacing sz=X
                personPhotoUrl = personPhotoUrl.substring(0,
                        personPhotoUrl.length() - 2)
                        + PROFILE_PIC_SIZE;

                new LoadProfileImage(profilePic).execute(personPhotoUrl);

            } else {
                Toast.makeText(getApplicationContext(),
                        "Person information is null", Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }}

    private class LoadProfileImage extends AsyncTask<String, Void, Bitmap> {
        ImageView bmImage;

        public LoadProfileImage(ImageView bmImage) {
            this.bmImage = bmImage;
        }

        protected Bitmap doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return mIcon11;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
        }
    }

*/
/*
    @Override
    public void onConnectionSuspended(int i) {
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {
        if (!connectionResult.hasResolution()) {
            GooglePlayServicesUtil.getErrorDialog(connectionResult.getErrorCode(), this,
                    0).show();
            return;
        }

        if (!mIntentInProgress) {
            // Store the ConnectionResult for later usage
            mConnectionResult = connectionResult;

            if (mSignInClicked) {
                // The user has already clicked 'sign-in' so we attempt to
                // resolve all
                // errors until the user is signed in, or they cancel.
                resolveSignInError();
            }
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int responseCode,
                                    Intent intent) {
        if (requestCode == RC_SIGN_IN) {
            if (responseCode != RESULT_OK) {
                mSignInClicked = false;
            }

            mIntentInProgress = false;

            if (!mGoogleApiClient.isConnecting()) {
                mGoogleApiClient.connect();
            }
        }
    }
*/

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
    class FetchTask extends AsyncTask<Void, String, String> {
        String user,pass;
        ProgressDialog p1;
                String resStr;
        public void execute(String username, String password, Context context) {
            user=username;
            pass=password;
            p1 = new ProgressDialog(Main_Activity.this);
            /*p1.setCancelable(false);
            p1.setTitle("Loading");
            p1.show();*/
            Log.i("fetch","execute");
            Toast.makeText(Main_Activity.this, "Verifying . . .", Toast.LENGTH_SHORT).show();
            onPreExecute();
            //doInBackground();
            //onPostExecute(resStr);
        }
        @Override

        protected String doInBackground(Void ... params) {
            try {/*
                p1.setCancelable(false);
                p1.setTitle("Loading");
                p1.show();*/
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.exebit.in/backend/_login.php");

                Log.i("fetch","do in background");

                // Add your data
                List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                nameValuePairs.add(new BasicNameValuePair("email", user));
                nameValuePairs.add(new BasicNameValuePair("password", pass));
                httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                // Execute HTTP Post Request
                HttpResponse response = httpclient.execute(httppost);

                // parsing data
                resStr = EntityUtils.toString(response.getEntity()).trim();
                Log.i("fetch response",resStr );
                //return resStr;

                /*Log.i("fetch","post execute");
                if(resStr.contains("Seems like you've screwed up somewhere. Try logging in again")) {
                    Toast.makeText(getApplicationContext(), "The email or password you entered is incorrect", Toast.LENGTH_SHORT).show();
                    //p1.hide();
                }
                else {

                    Main_Activity.userName = user;
                    Main_Activity.userPassword = pass;
                    Main_Activity.isValidated = true;
                    Intent intent = new Intent(Main_Activity.this, SecondActivity.class);
                    Main_Activity.userName = this.user;
                    Main_Activity.userPassword = this.pass;
                    intent.putExtra("Login status", 1);
                    startActivity(intent);
                    p1.hide();
                    myDialog.dismiss();
                    myDialog.cancel();

                    finish();
                }*/

                //return  result11;
            } catch (Exception e) {
                e.printStackTrace();
                //return null;
            }

            onPostExecute(resStr);

            return  null;

        }

        @Override
        protected void onPreExecute() {
            Log.i("fetch","pre execute");

            /*p1.setCancelable(false);
            p1.setTitle("Loading");
            p1.show();*/

            doInBackground(null);
        }

        @Override
        protected void onPostExecute(String resStr) {

            Log.i("fetch","post execute");
            if(resStr==null) {
                execute(user,pass,Main_Activity.this);
            }
            else if(resStr.contains("Try logging in again")) {
                Toast.makeText(getApplicationContext(), "The email or password you entered is incorrect", Toast.LENGTH_SHORT).show();
               // p1.hide();
            }
            else {

                Main_Activity.userName = user;
                Main_Activity.userPassword = pass;
                Main_Activity.isValidated = true;
                prefs.edit().putString("userEmail",userName).apply();
                prefs.edit().putString("userName",userName).apply();
                prefs.edit().putString("userPassword",userPassword).apply();
                prefs.edit().putString("userEmail",userName).commit();
                prefs.edit().putString("userName",userName).commit();
                prefs.edit().putString("userPassword",userPassword).commit();
                Intent intent = new Intent(Main_Activity.this, SecondActivity.class);
                Main_Activity.userName = this.user;
                Main_Activity.userPassword = this.pass;
                intent.putExtra("Login status", 1);
                startActivity(intent);
                //p1.dismiss();
                myDialog.dismiss();
                myDialog.cancel();

                finish();
            }

           /*Log.i("fetch","post execute");
           // p1.dismiss();
            if(result.contains("Try logging in again")) {
                Toast.makeText(getApplicationContext(), "Incorrect password", Toast.LENGTH_SHORT).show();
            }
            else {

                Intent intent = new Intent(Main_Activity.this, SecondActivity.class);
                Main_Activity.userName = this.user;
                Main_Activity.userPassword = this.pass;
                intent.putExtra("Login status", 1);
                startActivity(intent);
                myDialog.dismiss();
                myDialog.cancel();
                finish();
            }*/
           /* if (result != null) {
                DocumentBuilderFactory dbf =
                        DocumentBuilderFactory.newInstance();
                DocumentBuilder db = null;
                try {
                    db = dbf.newDocumentBuilder();
                } catch (ParserConfigurationException e) {
                    e.printStackTrace();
                }
                InputSource is = new InputSource();
                is.setCharacterStream(new StringReader(result));
                Document doc;
                try {
                    doc = db.parse(is);
                    //NodeList name=doc.getElementsByTagName("div id=\"secondary-header-content\"");

                    //System.out.print(doc);
                } catch (SAXException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                String check="<div id=\"secondary-header-content\"><div id=\"member-name\"><span>";
                for (String retval: result.split("\n")){
                    if(retval.contains(check)){
                        System.out.println(retval);
                        String[] res=retval.split("<span>");
                        System.out.println(res[1]);
                        String[] res1=res[1].split("</span>");
                        System.out.println(res1[0]);
                    }
                }
                //System.out.print(result);

            } else {
                // error occured
            }*/
        }


    }


}	