package sdgkteam10.rent_it;

import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.method.KeyListener;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

public class ProfileActivity extends AppCompatActivity {

    //UI elements
    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private TextView userAddress1;
    private TextView userAddress2;
    private TextView userCity;
    private TextView userState;
    private TextView userZip;
    //Edit profile button
    private Spinner stateSpinner;
    private Button editProfile;
    private boolean edit;

    private User user;


    public ProfileActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        //initialize all the UI elements
        userName = (TextView) findViewById(R.id.profile_name);
        userEmail = (TextView) findViewById(R.id.profile_email);
        userPhone = (TextView) findViewById(R.id.profile_phone);
        userAddress1 = (TextView) findViewById(R.id.profile_address1);
        userAddress2 = (TextView) findViewById(R.id.profile_address2);
        userCity = (TextView) findViewById(R.id.profile_city);
        userState = (TextView) findViewById(R.id.profile_state);
        userZip = (TextView) findViewById(R.id.profile_zip);
        //state spinner
        stateSpinner = (Spinner) findViewById(R.id.profile_state_spinner);
        stateSpinner.setVisibility(View.INVISIBLE);
        //editProfile button
        editProfile = (Button) findViewById(R.id.edit_button);

        //make the text view listeners, but make them uneditable (for now)
        final KeyListener userNameKeyListener = userName.getKeyListener();
        final KeyListener userEmailKeyListener = userEmail.getKeyListener();
        final KeyListener userPhoneKeyListener = userPhone.getKeyListener();
        final KeyListener userAddress1KeyListener = userAddress1.getKeyListener();
        final KeyListener userAddress2KeyListener = userAddress2.getKeyListener();
        final KeyListener userCityKeyListener = userCity.getKeyListener();
        final KeyListener userStateKeyListener = userState.getKeyListener();
        final KeyListener userZipKeyListener = userZip.getKeyListener();
        userName.setKeyListener(null);
        userEmail.setKeyListener(null);
        userPhone.setKeyListener(null);
        userAddress1.setKeyListener(null);
        userAddress2.setKeyListener(null);
        userCity.setKeyListener(null);
        userState.setKeyListener(null);
        userZip.setKeyListener(null);
        final ArrayAdapter<CharSequence> stateAdapter = new StateSpinnerAdapter(this);

        Database.setContext(this);
        Database db = Database.getInstance();

        user = new User();
        user.requestDatabaseData();

        //Set boolean value for when button has been clicked
        edit = false;
        //Add listener for button
        editProfile.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                //enable editing
                if (!edit) {
                    edit = true;
                    editProfile.setText(R.string.edit_update);
                    //make the text views editable
                    userName.setKeyListener(userNameKeyListener);
                    userEmail.setKeyListener(userEmailKeyListener);
                    userPhone.setKeyListener(userPhoneKeyListener);
                    userAddress1.setKeyListener(userAddress1KeyListener);
                    userAddress2.setVisibility(View.VISIBLE);
                    userAddress2.setKeyListener(userAddress2KeyListener);
                    userCity.setKeyListener(userCityKeyListener);
                    userState.setKeyListener(userStateKeyListener);
                    userState.setVisibility(View.INVISIBLE);
                    stateSpinner.setVisibility(View.VISIBLE);
                    stateSpinner.setAdapter(stateAdapter);
                    userZip.setKeyListener(userZipKeyListener);
                }
                //disable editing and update the user data
                else {
                    edit = false;
                    editProfile.setText(R.string.edit_profile_ef);
                    //make the text views uneditable
                    userName.setKeyListener(null);
                    userEmail.setKeyListener(null);
                    userPhone.setKeyListener(null);
                    userAddress1.setKeyListener(null);
                    userAddress2.setKeyListener(null);
                    userCity.setKeyListener(null);
                    userState.setKeyListener(null);
                    userState.setVisibility(View.VISIBLE);
                    userState.setText(stateSpinner.getSelectedItem().toString());
                    stateSpinner.setVisibility(View.INVISIBLE);
                    stateSpinner.setAdapter(null);
                    userZip.setKeyListener(null);

                    //update the user data
                    //TODO: validate the data before updating the database
                    user.updateEmail(userEmail.getText().toString());
                    user.updatePhone(userPhone.getText().toString());
                    user.updateAddress(userAddress1.getText().toString());
                    user.updateAddress2(userAddress2.getText().toString());
                    user.updateCity(userCity.getText().toString());
                    user.updateState(userState.getText().toString());
                    user.updateZip(userZip.getText().toString());
                }
            }
        });


        //populate the fields when the user data is all loaded
        db.getRef().child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                populateProfileFields();
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private void populateProfileFields() {
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
        userPhone.setText(user.getPhone());
        userAddress1.setText(user.getAddress());
        userAddress2.setText(user.getAddress2());
        if (user.getAddress2().equals("")) {
            userAddress2.setVisibility(View.GONE);
        }
        userCity.setText(user.getCity());
        userState.setText(user.getState());
        userZip.setText(user.getZip());

        //clear hints so "loading" goes away if the database for that field was empty
        userName.setHint("");
        userEmail.setHint("");
        userPhone.setHint("");
        userAddress1.setHint("");
        userAddress2.setHint("");
        userCity.setHint("");
        userState.setHint("");
        userZip.setHint("");
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }
}