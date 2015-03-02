package util;

import navigationDrawer.NavDrawerItem;

import com.example.exebit.exebit2k15.Main_Activity;
import com.example.exebit.exebit2k15.R;

import java.util.Arrays;
import java.util.List;

public class data {
    public static NavDrawerItem[] navtitles={
            new NavDrawerItem("Schedule", R.drawable.schedule),
            new NavDrawerItem("My Profile",R.drawable.ic_launcher),
            new NavDrawerItem("My events",R.drawable.registered),
            new NavDrawerItem("Hospitality",R.drawable.ic_launcher),
            new NavDrawerItem("FAQ",R.drawable.faq),
            new NavDrawerItem("Sponsors",R.drawable.sponsor),
            new NavDrawerItem("Logout",R.drawable.logout)
    };

    public static NavDrawerItem[] navtitles_notloggedin={
            new NavDrawerItem("Schedule", R.drawable.schedule),
            new NavDrawerItem("Hospitality",R.drawable.ic_launcher),
            new NavDrawerItem("FAQ",R.drawable.faq),
            new NavDrawerItem("Sponsors",R.drawable.sponsor)};

    public static List<NavDrawerItem> getNavDrawerItems(){
        return Arrays.asList(navtitles);
    }

    public static List<NavDrawerItem> getNavDrawerItemsNotLoggedIn(){
        return Arrays.asList(navtitles_notloggedin);
    }
}
