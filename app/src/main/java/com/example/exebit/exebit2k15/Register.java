package com.example.exebit.exebit2k15;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class Register extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    public static TextView SelectedDateOfBirth;
    //public Spinner spinner;
    public RadioGroup radioGroupId;
    public RadioButton radioGenderButton;
    public String fullName,dateOfBirth,collegeName,phoneNumber,emailId,userName,passWord,gender;
    //public String spinnerItems[] = {"Select your gender","Male","Female"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        final TextView DOB,textViewFullName,textViewDateOfBirth,textViewCollegeName,textViewGender,textViewPhone,textViewEmail,textViewUserName,textViewPassword,textViewCPassword;;
        final EditText fName,cName,pNumber,eId,uName,pWord,confirm_pWord;
        radioGroupId = (RadioGroup) findViewById(R.id.radioGenderGroup);

        textViewFullName = (TextView) findViewById(R.id.textViewFullName);
        textViewDateOfBirth = (TextView) findViewById(R.id.textViewDateOfBirth);
        textViewCollegeName = (TextView) findViewById(R.id.textViewCollegeName);
        textViewGender = (TextView) findViewById(R.id.textViewGender);
        textViewPhone = (TextView) findViewById(R.id.textViewPhone);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);
        textViewUserName = (TextView) findViewById(R.id.textViewUserName);
        textViewPassword = (TextView) findViewById(R.id.textViewPassword);
        textViewCPassword = (TextView) findViewById(R.id.textViewCPassword);

        /*spinner = (Spinner)findViewById(R.id.spinnerGender);
        ArrayAdapter<String>adapter = new ArrayAdapter<String>(Register.this,
                android.R.layout.simple_spinner_item,spinnerItems);

        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);
*/
        fName = (EditText) findViewById(R.id.editTextFullName);
        cName = (EditText) findViewById(R.id.editTextCollegeName);
        pNumber = (EditText) findViewById(R.id.editTextPhone);
        eId = (EditText) findViewById(R.id.editTextEmail);
        uName = (EditText) findViewById(R.id.editTextUserName);
        pWord = (EditText) findViewById(R.id.editTextPassword);
        confirm_pWord = (EditText) findViewById(R.id.editTextCPassword);
        DOB = (TextView) findViewById(R.id.textViewSelectedDateOfBirth);
        Button b= (Button) findViewById(R.id.buttonSubmit);
        gender="";

        SelectedDateOfBirth = (TextView) findViewById(R.id.textViewSelectedDateOfBirth);

        b.setOnClickListener(new OnClickListener() {

            public void onClick(View arg0) {

                fullName = fName.getText().toString();
                dateOfBirth = DOB.getText().toString();
                collegeName = cName.getText().toString();
                phoneNumber = pNumber.getText().toString();
                emailId = eId.getText().toString();
                userName = uName.getText().toString();
                passWord = pWord.getText().toString();
                radioGenderButton= (RadioButton) findViewById(radioGroupId.getCheckedRadioButtonId());
                try {
                    gender = (String) radioGenderButton.getText().toString();
                }
                catch(Exception e)
                {
                    e.printStackTrace();
                }
                Log.d("t", fullName);

                if (fullName.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your full name", Toast.LENGTH_SHORT).show();
                    //textViewFullName;
                } else if (dateOfBirth.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please choose your date of birth", Toast.LENGTH_SHORT).show();
                } else if (collegeName.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your college name", Toast.LENGTH_SHORT).show();
                    //cName.setBackgroundColor(getResources().getColor(R.color.myNavigationBarProPicBackgroundColor));
                } else if (gender.equals(""))
                    Toast.makeText(getApplicationContext(), "Please select your gender", Toast.LENGTH_SHORT).show();

                else if (phoneNumber.equals("")) {
                    Toast.makeText(getApplicationContext(), "Please enter your phone number", Toast.LENGTH_SHORT).show();
                    //  pNumber.setBackgroundColor(getResources().getColor(R.color.myNavigationBarProPicBackgroundColor));
                }

                else if(phoneNumber.length()!=10)
                    Toast.makeText(getApplicationContext(),"Please enter a valid phone number",Toast.LENGTH_SHORT).show();

                else if (emailId.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter your email id", Toast.LENGTH_SHORT).show();

                else if(!emailId.contains("@"))
                    Toast.makeText(getApplicationContext(),"Please enter a valid email id",Toast.LENGTH_SHORT).show();

                else if (userName.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter your desired user name", Toast.LENGTH_SHORT).show();

                else if (passWord.equals(""))
                    Toast.makeText(getApplicationContext(), "Please enter your desired password", Toast.LENGTH_SHORT).show();

                else if (passWord.length() < 8)
                    Toast.makeText(getApplicationContext(), "Password must be of minimum 8 characters", Toast.LENGTH_SHORT).show();

                else {
                    if (passWord.equals(confirm_pWord.getText().toString())) {
                        // TODO pass these values to the database
                        Main_Activity.userFullName = fullName;
                        Main_Activity.userDateOfBirth = dateOfBirth;
                        Main_Activity.userName = userName;
                        Main_Activity.userId = "";
                        Main_Activity.userPassword = passWord;
                        Main_Activity.userMobile = phoneNumber;
                        Main_Activity.userCollege = collegeName;
                        Main_Activity.userEmail = emailId;
                        Main_Activity.userHostel = "";
                        Main_Activity.userHostelRoom = "";
                        Main_Activity.gender=gender;
                        // TODO allot appropriate hostel and userID

                        Main_Activity.prefs.edit().putString("userFullName", Main_Activity.userFullName).apply();
                        Main_Activity.prefs.edit().putString("userDateOfBirth", Main_Activity.userDateOfBirth).apply();
                        Main_Activity.prefs.edit().putString("userName", Main_Activity.userName).apply();
                        Main_Activity.prefs.edit().putString("userCollege", Main_Activity.userCollege).apply();
                        Main_Activity.prefs.edit().putString("userId", Main_Activity.userId).apply();
                        Main_Activity.prefs.edit().putString("userPassword", Main_Activity.userPassword).apply();
                        Main_Activity.prefs.edit().putString("userMobile", Main_Activity.userMobile).apply();
                        Main_Activity.prefs.edit().putString("userEmail", Main_Activity.userEmail).apply();
                        Main_Activity.prefs.edit().putString("userHostel", Main_Activity.userHostel).apply();
                        Main_Activity.prefs.edit().putString("userHostelRoom", Main_Activity.userHostelRoom).apply();
                        Main_Activity.prefs.edit().putString("gender",Main_Activity.gender).apply();

                        Intent intent = new Intent(Register.this, SecondActivity.class);
                        intent.putExtra("Login status", 1);
                        startActivity(intent);
                        finish();
                    } else
                        Toast.makeText(getApplicationContext(), "Passwords don't match", Toast.LENGTH_SHORT).show();
                }

               /* Thread threadLogin = new Thread( new Runnable() {
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
                });*/

                Thread threadRegistration = new Thread( new Runnable() {
                    @Override
                    public void run() {
                        String postReceiverUrl = "http://exebit.in/backend/_register.php";

                        HttpClient httpClient = new DefaultHttpClient();

                        HttpPost httpPost = new HttpPost(postReceiverUrl);

                        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
                        nameValuePairs.add(new BasicNameValuePair("fname", Main_Activity.userFullName));
                        nameValuePairs.add(new BasicNameValuePair("lname", ""));
                        nameValuePairs.add(new BasicNameValuePair("insti", Main_Activity.userCollege));
                        nameValuePairs.add(new BasicNameValuePair("gender", Main_Activity.gender));
                        nameValuePairs.add(new BasicNameValuePair("city", ""));
                        nameValuePairs.add(new BasicNameValuePair("instiRoll", ""));
                        nameValuePairs.add(new BasicNameValuePair("email", Main_Activity.userEmail));
                        nameValuePairs.add(new BasicNameValuePair("mobile", Main_Activity.userMobile));
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
                }
                );
                threadRegistration.start();
               // threadLogin.start();
            }

        });
    }
    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position) {
            case 0:
                gender="";
                break;
            case 1:
                gender="Male";
                Main_Activity.gender="Male";
                break;
            case 2:
                gender="Female";
                Main_Activity.gender="Female";
                break;

        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {

            final Calendar c = Calendar.getInstance();
            int year = 1994;
            int month = 0;
            int day = 1;

            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int month, int day) {
            // Do something with the date chosen by the user
            String[] months = new String[]{"Jan","Feb","Mar","Apr","May","Jun","Jul","Aug","Sep","Oct","Nov","Dec"};

            String[] sub = new String[] {"st","nd","rd","th","th","th","th","th","th","th","th","th","th","th","th","th","th","th",
            "th","th","st","nd","rd","th","th","th","th","th","th","th","st"};
            if(day%10==0)
                SelectedDateOfBirth.setText(day+"th"+" "+months[month]+" "+year);
            else
                SelectedDateOfBirth.setText(day+sub[day%10-1]+" "+months[month]+" "+year);
        }


    }

};
