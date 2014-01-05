package to.noc.android.swipeyours;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static to.noc.android.swipeyours.Constants.DEFAULT_SWIPE_DATA;
import static to.noc.android.swipeyours.Constants.SWIPE_DATA_PREF_KEY;

public class SetCardActivity extends Activity {

    /*
     *  Returns true if the passed in track data was successfully parsed, otherwise false.
     */
    private boolean parseTrackData(String trackData) {
        Pattern pattern = Pattern.compile("^\\s*(?:%B(\\d+)\\^([^^]+)\\^\\d+)?\\?;(\\d+)=(\\d\\d)(\\d\\d)\\d+\\?\\s*$");
        Matcher match = pattern.matcher(trackData);

        boolean isValid = match.matches();

        if (isValid) {

            String fullName = match.group(2);
            String cardNumber = match.group(3);
            String expDate = match.group(5) + "/" + match.group(4);

            int warningVisibility = DEFAULT_SWIPE_DATA.equals(trackData) ?
                    View.VISIBLE : View.GONE;
            findViewById(R.id.using_default_card_warning).setVisibility(warningVisibility);

            TextView currentSwipeDataTextView = (TextView) findViewById(R.id.current_swipe_data);
            currentSwipeDataTextView.setText(trackData);

            TextView currentNameOnCard = (TextView) findViewById(R.id.name_on_card);
            currentNameOnCard.setText(fullName);

            TextView cardNumberTextView = (TextView) findViewById(R.id.card_number);
            cardNumberTextView.setText(cardNumber);

            TextView cardExpirationTextView = (TextView) findViewById(R.id.card_expiration);
            cardExpirationTextView.setText(expDate);
        }

        return isValid;
    }


    /*
     *  Triggered by button press
     */
    public void setNewCard(View view) {
        String newSwipeData = ((EditText) findViewById(R.id.swipe_data)).getText().toString();
        boolean newDataIsValid = parseTrackData(newSwipeData);

        String toastMessage;
        int toastDuration;

        if (newDataIsValid) {
            toastMessage = "New Card Set";
            toastDuration = Toast.LENGTH_SHORT;
            storeNewSwipeData(newSwipeData);
        } else {
            toastMessage = "Invalid swipe data";
            toastDuration = Toast.LENGTH_LONG;
        }

        Toast toast = Toast.makeText(getApplicationContext(), toastMessage, toastDuration);
        toast.setGravity(Gravity.CENTER, 0, 0);
        toast.show();
    }


    /*
     *  Get any previously saved swipe data (or default swipe data) from the preference storage
     *  of the app.
     */
    private String getSavedSwipeData() {
        SharedPreferences prefs =  PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
        return prefs.getString(SWIPE_DATA_PREF_KEY, DEFAULT_SWIPE_DATA);
    }

    /*
     *  Save new magnetic stripe data to the shared preference storage of our app.
     */
    private void storeNewSwipeData(String newSwipeData) {
        SharedPreferences.Editor prefEditor =
                PreferenceManager.getDefaultSharedPreferences(getApplicationContext()).edit();
        prefEditor.putString(SWIPE_DATA_PREF_KEY, newSwipeData);
        prefEditor.commit();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_card);
        parseTrackData(getSavedSwipeData());
    }

}
