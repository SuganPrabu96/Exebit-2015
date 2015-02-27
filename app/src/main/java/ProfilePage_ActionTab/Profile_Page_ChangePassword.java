package ProfilePage_ActionTab;
/**
 * Created by Suganprabu on 07-02-2015.
 */
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.exebit.exebit2k15.Main_Activity;
import com.example.exebit.exebit2k15.SecondActivity;
import com.example.exebit.exebit2k15.R;

public class Profile_Page_ChangePassword extends Fragment {

    public EditText old_password;
    public EditText new_password;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.profile_page_changepassword, container, false);

        Button submitChangePassword = (Button) rootView.findViewById(R.id.submit_change_password);
        old_password = (EditText) rootView.findViewById(R.id.old_password_edit);
        new_password = (EditText) rootView.findViewById(R.id.new_password_edit);

        submitChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                changepassword();
            }
        });


        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        return rootView;

    }
    public void changepassword()
    {
        if(old_password.getText().toString().equals(Main_Activity.userPassword) && !(old_password.getText().toString().equals("")) && !(old_password.getText().toString().length()<8))
        {
            //SecondActivity.ProfilePageFragment.prefs.edit().putString("passWord",new_password.getText().toString()).commit();
            Main_Activity.userPassword=new_password.getText().toString();
            Toast.makeText(getActivity(), "Password changed", Toast.LENGTH_SHORT).show();
        }
        else if(!old_password.getText().toString().equals(Main_Activity.userPassword))
        {
            AlertDialog.Builder b1 = new AlertDialog.Builder(getActivity());
            b1.setTitle("Incorrect Old Password");
            b1.setTitle("Check your entered old password");
            b1.setNeutralButton("Ok",new DialogInterface.OnClickListener(){
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            b1.create().show();
        }

        else
        {
            AlertDialog.Builder b1 = new AlertDialog.Builder(getActivity());
            b1.setTitle("Invalid New Password");
            b1.setMessage("Please enter a password that is atleast 8 characters long");
            b1.setNeutralButton("Ok",new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog,int which){
                            dialog.cancel();
                        }
                    }
            );
            b1.create().show();
        }
    }

}
