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

import us.fatehi.creditcardnumber.ServiceCode;
import us.fatehi.magnetictrack.bankcard.BankCardMagneticTrack;
import us.fatehi.magnetictrack.bankcard.Track1FormatB;
import us.fatehi.magnetictrack.bankcard.Track2;

import java.util.ServiceConfigurationError;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static to.noc.android.swipeyours.Constants.DEFAULT_SWIPE_DATA;
import static to.noc.android.swipeyours.Constants.SWIPE_DATA_PREF_KEY;

public class SetCardActivity extends Activity {

    /*
     *  Returns true if the passed in track data was successfully parsed, otherwise false.
     *  Track2 data must be supplied.  If track1 data is available, the name field is pulled
     *  from it for display purposes.
     */
    private boolean parseTrackData(String trackData) {

        final BankCardMagneticTrack allTracks = BankCardMagneticTrack.from(trackData);

        Track1FormatB track1Data = allTracks.getTrack1();
        Track2 track2Data = allTracks.getTrack2();

        boolean isValid = track2Data.getPrimaryAccountNumber().isPrimaryAccountNumberValid();

        if (isValid) {
            int warningVisibility = DEFAULT_SWIPE_DATA.equals(trackData) ?
                    View.VISIBLE : View.GONE;
            findViewById(R.id.using_default_card_warning).setVisibility(warningVisibility);

            ServiceCode serviceCode = track2Data.getServiceCode();

            String fullName = track1Data.hasName() ? track1Data.getName().getFullName() : "[none]";
            findViewById(R.id.optional_name_view).setVisibility(
                    track1Data.hasName() ? View.VISIBLE : View.GONE
            );

            String cardNumber = track2Data.getPrimaryAccountNumber().getAccountNumber();
            String cardBrand = track2Data.getPrimaryAccountNumber().getCardBrand().name();
            String expDate = track2Data.getExpirationDate().toString();
            String discretionaryData = track2Data.hasDiscretionaryData() ?
                    track2Data.getDiscretionaryData() : "[none]";

            setTextView(R.id.current_swipe_data, trackData);
            setTextView(R.id.name_on_card, fullName);
            setTextView(R.id.card_number, cardNumber);
            setTextView(R.id.card_brand, cardBrand);
            setTextView(R.id.card_expiration, expDate);
            setTextView(R.id.service_code, serviceCode.getServiceCode());
            setTextView(R.id.service_code1_val, serviceCode.getServiceCode1().getValue() + ":");
            setTextView(R.id.service_code1_descr, serviceCode.getServiceCode1().getDescription());
            setTextView(R.id.service_code2_val, serviceCode.getServiceCode2().getValue() + ":");
            setTextView(R.id.service_code2_descr, serviceCode.getServiceCode2().getDescription());
            setTextView(R.id.service_code3_val, serviceCode.getServiceCode3().getValue() + ":");
            setTextView(R.id.service_code3_descr, serviceCode.getServiceCode3().getDescription());
            setTextView(R.id.discretionary_data, discretionaryData);

        }

        return isValid;
    }

    private void setTextView(int id, String textValue) {
        TextView view = (TextView) findViewById(id);
        view.setText(textValue);
    }


    /*
     *  Triggered by button press
     */
    public void setNewCard(View view) {
        String newSwipeData = ((EditText) findViewById(R.id.swipe_data)).getText().toString().replaceAll("\\s+","");
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
