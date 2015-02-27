package ProfilePage_ActionTab;

/**
 * Created by Suganprabu on 07-02-2015.
 */
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class CustomPagerAdapter extends FragmentPagerAdapter {

    Context mContext;

    public CustomPagerAdapter(FragmentManager fm) {
        super(fm);

    }

    @Override
    public Fragment getItem(int position) {

        // Create fragment object
        switch (position) {
            case 0: {
                Fragment fragment = new Profile_Page_UserDetails();
                Bundle args = new Bundle();
                args.putInt("page_position", position + 1);
                fragment.setArguments(args);
                return fragment;
            }
            case 1: {
                Fragment fragment = new Fragment2();
                Bundle args = new Bundle();
                args.putInt("page_position", position + 1);
                fragment.setArguments(args);
                return fragment;
            }
            case 2: {
                Fragment fragment = new Profile_Page_ChangePassword();
                Bundle args = new Bundle();
                args.putInt("page_position", position + 1);
                fragment.setArguments(args);
                return fragment;
            }
            default:
                return null;
        }
    }
    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return "Page " + (position + 1);
    }
}