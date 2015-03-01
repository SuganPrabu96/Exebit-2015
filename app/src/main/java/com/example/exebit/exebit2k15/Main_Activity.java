package com.example.exebit.exebit2k15;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
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

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.Calendar;

public class Main_Activity extends ActionBarActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    Dialog myDialog;
    public GoogleApiClient mGoogleApiClient;
    public static SharedPreferences prefs = null;
    public static ImageView profilePic = null;
    public static Bitmap profilePicture = null;
    private static final int RC_SIGN_IN = 0;
    private boolean mIntentInProgress;
    private static final int PROFILE_PIC_SIZE = 400;
    private boolean mSignInClicked;

    private ConnectionResult mConnectionResult;
    public static String userFullName,userProPic,userDateOfBirth,userName,gender,userId,userPassword,userMobile,userCollege,userEmail,userHostel,userHostelRoom;

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
        userEmail="suganprabu1996@gmail.com";
        userProPic="";
        /*userFullName="SuganPrabu";
        userDateOfBirth="06th Nov 1996";
        userName="Sugan";
        userId="";
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
        prefs.edit().putString("userHostelRoom",userHostelRoom).apply();
        */

        if(userEmail.equals("")&&userProPic.equals(""))
            {getUserProPic();
             BitmapDrawable pro_pic = (BitmapDrawable) profilePic.getDrawable();
             profilePicture = pro_pic.getBitmap();
             prefs.edit().putString("userProPic", encodeTobase64(profilePicture)).apply();
            }
        else profilePicture = decodeBase64(prefs.getString("userProPic", null));
        if(!prefs.getString("userId","").equals(""))
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
                            myDialog.dismiss();
                            myDialog.cancel();
                            finish();
                        }
                    } else if(strUserName.equals(""))
                        Toast.makeText(getApplicationContext(), "Enter your username", Toast.LENGTH_SHORT).show();
                else Toast.makeText(getApplicationContext(),"Enter your password",Toast.LENGTH_SHORT).show();
                }
        });
    }

    public void getUserProPic(){
        getProfileInformation();
    }

    protected void onStart() {
        super.onStart();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this).addApi(Plus.API, null)
                .addScope(Plus.SCOPE_PLUS_LOGIN).build();
        mGoogleApiClient.connect();
    }

    protected void onStop() {
        super.onStop();
        if (mGoogleApiClient.isConnected()) {
            mGoogleApiClient.disconnect();
        }
    }

    /**
     * Method to resolve any signin errors
     * */
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

    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mSignInClicked = false;
        Toast.makeText(this, "User is connected!", Toast.LENGTH_LONG).show();

        // Get user's information


    }

    private void getProfileInformation() {
        try {
            if (Plus.PeopleApi.getCurrentPerson(mGoogleApiClient) != null) {
                Person currentPerson = Plus.PeopleApi
                        .getCurrentPerson(mGoogleApiClient);
                String personName = null;
                String personPhotoUrl = null;
                String personGooglePlusProfile = null;
                String email = Plus.AccountApi.getAccountName(mGoogleApiClient);
                        if(email.equals(userEmail)) {
                            personName = currentPerson.getDisplayName();
                            personPhotoUrl = currentPerson.getImage().getUrl();
                            personGooglePlusProfile = currentPerson.getUrl();
                          }


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


	