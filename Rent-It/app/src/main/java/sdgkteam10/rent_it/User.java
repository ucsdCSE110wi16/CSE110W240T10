package sdgkteam10.rent_it;

import android.content.Context;
import android.content.res.Resources;
import android.support.v4.content.ContextCompat;
import android.util.Log;

import com.firebase.client.AuthData;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

//TODO: add isLoggedIn() function


/**
 * Created by Jeremy on 2/15/2016.
 * Updated by David Ruble
 */
public class User {
    private String name;
    private String email;
    private String pw;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;

    private Context appContext; //for accessing strings in strings.xml

    //Firebase fields
    private String m_uid;
    private Firebase m_ref;
    private AuthData userData;
    private FirebaseError m_createError = null;
    private FirebaseError m_loginError = null;

    private boolean finished = false;

    //default ctor (required by Firebase)
    User(){}

    //new user -- for use with registering new users
    User(String name, String email, String pw, String a1, String a2,
         String city, String state, String zip, String phone, Context context){
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.address = a1;
        this.address2 = a2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.appContext = context;
        this.m_ref = new Firebase(this.appContext.getString(R.string.firebase_url));
        m_ref.keepSynced(true);

        //create the account
        m_ref.createUser(email, pw, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                m_uid = result.get("uid").toString();
                //store user data in Firebase
                m_ref.child("users").child(result.get("uid").toString()).setValue(User.this);
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                m_createError = firebaseError;
            }
        });
    }

    //"getter" ctor that pulls an existing user from the database
    User(AuthData data, Context context)
    {
        this.appContext = context;
        this.m_ref = new Firebase(this.appContext.getString(R.string.firebase_url));
        m_ref.keepSynced(true);
        this.userData = data;
        this.m_uid = data.getUid();

        Log.d("My Logging", "in getter ctor");

        //retrieve data from database -- TODO: not being called :(
        m_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d("My Logging", "getting the data");
                User data = dataSnapshot.child("users").child(getUid()).getValue(User.class);
                setName(data.getName());
                setEmail(data.getEmail());
                Log.d("My Logging", "data email = " + getEmail() + "user email = " + data.getEmail());
                setPw(data.getPw());
                setAddress(data.getAddress());
                setAddress2(data.getAddress2());
                setCity(data.getCity());
                setState(data.getState());
                setZip(data.getZip());
                setPhone(data.getPhone());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //unused
            }
        });
    }

    //login ctor
    User(String email, String password, Context context)
    {
        this.email = email;
        this.pw = password;

        this.appContext = context;
        this.m_ref = new Firebase(this.appContext.getString(R.string.firebase_url));
        m_ref.keepSynced(true);

        //login and then wait until authenticated
        this.login();
        while (!finished);

        //update class' fields with data from database
        m_ref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                User data = dataSnapshot.child("users").child(getUid()).getValue(User.class);
                setName(data.getName());
                setEmail(data.getEmail());
                setPw(data.getPw());
                setAddress(data.getAddress());
                setAddress2(data.getAddress2());
                setCity(data.getCity());
                setState(data.getState());
                setZip(data.getZip());
                setPhone(data.getPhone());
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                //unused
            }
        });

        //retrieve data from database
        this.name = (String) this.userData.getProviderData().get("name");
        this.email = (String) this.userData.getProviderData().get("email");
        this.pw = (String) this.userData.getProviderData().get("pw");
        this.address = (String) this.userData.getProviderData().get("address");
        this.address2 = (String) this.userData.getProviderData().get("address2");
        this.city = (String) this.userData.getProviderData().get("city");
        this.state = (String) this.userData.getProviderData().get("state");
        this.zip = (String) this.userData.getProviderData().get("zip");
        this.phone = (String) this.userData.getProviderData().get("phone");
    }

    public void login()
    {
        m_ref.authWithPassword(this.getEmail(), this.getPw(), new Firebase.AuthResultHandler() {
            @Override
            public void onAuthenticated(AuthData authData) {
                Log.d("My Logging", "logging in from User class!");
                userData = authData;
                m_uid = userData.getUid();
                finished = true;
            }

            @Override
            public void onAuthenticationError(FirebaseError error) {
                m_loginError = error;
                Log.e("My Logging", "unable to login in User class!");
            }
        });
    }

    public void logout()
    {
        if (this.userData != null)
        {
            this.m_ref.unauth();

            //TODO -- other logging out thingalings
        }
    }

    public FirebaseError getUserCreateError() {return this.m_createError;}
    public FirebaseError getLoginError() {return this.m_loginError;}

    //setters
    //TODO: make the setters change the actual value in the database
    public void setName(String f) {this.name = f;}
    public void setEmail(String e) {this.email = e;}
    public void setPw(String pass) {this.pw = pass;}
    public void setAddress(String a) {this.address = a;}
    public void setAddress2(String a2) {this.address2 = a2;}
    public void setCity(String city) {this.city = city;}
    public void setState(String state) {this.state = state;}
    public void setZip(String zip) {this.zip = zip;}
    public void setPhone(String phone) {this.phone = phone;}

    //getters
    public String getName() {return this.name;}
    public String getEmail() {return this.email;}
    public String getPw() {return this.pw;}
    public String getAddress() {return this.address;}
    public String getAddress2() {return this.address2;}
    public String getCity() {return this.city;}
    public String getState() {return this.state;}
    public String getZip() {return this.zip;}
    public String getPhone() {return this.phone;}
    public String getUid() {return this.m_uid;}
}
