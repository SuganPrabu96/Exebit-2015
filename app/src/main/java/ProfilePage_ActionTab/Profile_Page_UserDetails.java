package ProfilePage_ActionTab;
/**
 * Created by Suganprabu on 07-02-2015.
 */
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.exebit.exebit2k15.Main_Activity;
import com.example.exebit.exebit2k15.R;

public class Profile_Page_UserDetails extends Fragment {

    TextView name,gender,college,mobile,email,hostel,room;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.profile_page_user_details, container, false);

        name = (TextView) rootView.findViewById(R.id.profilepage_name);
        gender = (TextView) rootView.findViewById(R.id.profilepage_gender);
        college = (TextView) rootView.findViewById(R.id.profilepage_college);
        mobile = (TextView) rootView.findViewById(R.id.profilepage_mobile);
        email = (TextView) rootView.findViewById(R.id.profilepage_email);
        hostel = (TextView) rootView.findViewById(R.id.profilepage_hostel);
        room = (TextView) rootView.findViewById(R.id.profilepage_roomno);

        name.setText(Main_Activity.prefs.getString("userFullName",""));
        gender.setText(Main_Activity.prefs.getString("gender",""));
        college.setText(Main_Activity.prefs.getString("userCollege",""));
        mobile.setText(Main_Activity.prefs.getString("userMobile",""));
        email.setText(Main_Activity.prefs.getString("userEmail",""));
        hostel.setText(Main_Activity.prefs.getString("userHostel",""));
        room.setText(Main_Activity.prefs.getString("userHostelRoom",""));
        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        return rootView;

    }}
