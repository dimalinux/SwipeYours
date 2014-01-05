package to.noc.android.swipeyours;

public class Constants {

    //
    //  We include a prepaid Visa debit card with no balance so the app has a card
    //  configured until the user switches to their own card:
    //
    public static final String DEFAULT_SWIPE_DATA = "%B4046460664629718^000NETSPEND^161012100000181000000?;4046460664629718=16101210000018100000?";

    //
    //  Key used to store the user's Swipe data in the app's shared preferences
    //
    public static final String SWIPE_DATA_PREF_KEY = "SWIPE_DATA";
}
