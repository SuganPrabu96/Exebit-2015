package util;

import navigationDrawer.NavDrawerItem;

import com.example.exebit.exebit2k15.Main_Activity;
import com.example.exebit.exebit2k15.R;

import java.util.Arrays;
import java.util.List;

/**
 * Created by vigneshm on 27/01/15.
 */
public class data {
    public static NavDrawerItem[] navtitles={new NavDrawerItem("Schedule", R.drawable.ic_launcher),
            new NavDrawerItem("View Profile",R.drawable.ic_launcher),
            new NavDrawerItem("My events",R.drawable.ic_launcher),
            new NavDrawerItem("FAQ",R.drawable.ic_launcher),
            new NavDrawerItem("Logout",R.drawable.ic_launcher)
    };

    public static NavDrawerItem[] navtitles_notloggedin={new NavDrawerItem("Schedule", R.drawable.ic_launcher),
            new NavDrawerItem("FAQ",R.drawable.ic_launcher)};

    public static List<NavDrawerItem> getNavDrawerItems(){
        return Arrays.asList(navtitles);
    }

    public static List<NavDrawerItem> getNavDrawerItemsNotLoggedIn(){
        return Arrays.asList(navtitles_notloggedin);
    }
}
