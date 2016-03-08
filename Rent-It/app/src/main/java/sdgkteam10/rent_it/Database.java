package sdgkteam10.rent_it;

import android.content.Context;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class Database {
    private final Firebase m_ref;
    private FirebaseError m_createError = null;
    private FirebaseError m_loginError = null;

    private static Context m_context;

    private static Database instance = null;

    private Database() {
        m_ref = new Firebase(m_context.getResources().getString(R.string.firebase_url));
    }

    public static Database getInstance() {
        if (instance == null) {
            instance = new Database();
        }
        return instance;
    }

    //sets the firebase context and grants access to string resources
    //MUST BE CALLED BEFORE GETINSTANCE!!!
    public static void setContext(Context context) {
        Firebase.setAndroidContext(context);
        m_context = context;
    }

    Firebase getRef() { return m_ref; }

    //attempts to create a new user
    public void createUser(String email, String pw, final User user) {
        m_ref.createUser(email, pw, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                //store user data in Firebase
                m_ref.child("users").child(result.get("uid").toString()).setValue(user);
                m_createError = null;
            }

            @Override
            public void onError(FirebaseError firebaseError) {
                m_createError = firebaseError;
            }
        });
    }

    //attempts to login the desired user
    public void requestLogin(String email, String pw, final User user) {
        m_ref.authWithPassword(email, pw, new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d("My Logging", "logging in from User class!");
                getUserData(user);
                m_loginError = null;
            }

            @Override
            public void onAuthenticationError(FirebaseError error) {
                m_loginError = error;
                Log.e("My Logging", "unable to login in User class!");
            }
        });
    }

    //logs out the user and returns a boolean on the success of the logout
    public boolean logoutUser()
    {
        AuthData authData = m_ref.getAuth();

        if (authData != null)
        {
            this.m_ref.unauth();
            return true;
        }
        else
            return false;
    }

    //gets the current logged in user's information
    public void getUserData(final User user) {
        //retrieve data from database
        m_ref.child("users").child(m_ref.getAuth().getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User data = dataSnapshot.getValue(User.class);
                Log.d("My Logging", "getting the data");
                user.setName(data.getName());
                user.setEmail(data.getEmail());
                user.setPw(data.getPw());
                user.setAddress(data.getAddress());
                user.setAddress2(data.getAddress2());
                user.setCity(data.getCity());
                user.setState(data.getState());
                user.setZip(data.getZip());
                user.setPhone(data.getPhone());
                user.setFavorites(
                        (ArrayList<Item>) dataSnapshot
                                .child(m_context.getResources().getString(R.string.firebase_faves))
                                .getValue()
                );
                //user.setFavorites(data.getFavorites());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //unused
            }
        });
    }

    //updates the user data in the database
    public void setUserData(String location, String newVal) {
        m_ref.child("users").child(m_ref.getAuth().getUid()).child(location).setValue(newVal);
    }

    //updates the user's list of favorites
    public void updateFavorites(ArrayList<Item> newFaves) {
        m_ref.child("users")
                .child(m_ref.getAuth().getUid())
                .child(m_context.getResources().getString(R.string.firebase_faves))
                .setValue(newFaves);
    }

    //gets the logged in user id (used in associating listing with a user
    public String getLoggedInUser() {
        return m_ref.getAuth().getUid();
    }

    protected FirebaseError getUserCreateError() {return m_createError;}
    protected FirebaseError getLoginError() {return m_loginError;}
}
