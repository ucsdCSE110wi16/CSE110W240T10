package sdgkteam10.rent_it;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;

public class ProfileActivity extends AppCompatActivity {

    //UI elements
    private TextView userName;
    private TextView userEmail;
    private TextView userPhone;
    private TextView address1;
    private TextView address2;
    private TextView city;
    private TextView state;
    private TextView zip;

    private Firebase myFirebaseRef;

    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        userName = (TextView)findViewById(R.id.profile_name);
        userEmail = (TextView)findViewById(R.id.profile_email);

        Firebase.setAndroidContext(this);
        myFirebaseRef = new Firebase(getResources().getString(R.string.firebase_url));
    }

    protected void onResume() {
        super.onResume();

        //get the currently logged in user
        //TODO: change to user.isLoggedIn()?
        AuthData userData = myFirebaseRef.getAuth();

        //user is logged in
        //TODO: waitForUpdate() causes black screen, but need to wait for database to update
        //TODO:     solution: turn this into an AsyncTask
        if (userData != null)
        {
            user = new User(userData, getApplicationContext());
            //user.waitForUpdate();
            populateProfileFields();
        }
        //user is not logged in
        else
        {
            //TODO: handle not logged in error?
        }
    }

    private void populateProfileFields()
    {
        userName.setText(user.getName());
        userEmail.setText(user.getEmail());
    }
}