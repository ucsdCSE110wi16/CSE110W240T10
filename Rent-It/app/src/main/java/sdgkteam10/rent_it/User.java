package sdgkteam10.rent_it;

import com.firebase.client.AuthData;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.Map;

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
    private int zip;
    private String phone;

    //Firebase fields
    private String uid;
    private Firebase ref;
    private AuthData userData;

    //default ctor (required by Firebase)
    User(){}

    //new user -- for use with registering new users
    User(String name, String email, String pw, String a1, String a2,
         String city, String state, int zip, String phone, Firebase ref){
        this.name = name;
        this.email = email;
        this.pw = pw;
        this.address = a1;
        this.address2 = a2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.ref = ref;

        //create the account
        ref.createUser(email, pw, new Firebase.ValueResultHandler<Map<String, Object>>() {
            @Override
            public void onSuccess(Map<String, Object> result) {
                uid = result.get("uid").toString();
                login();
            }
            @Override
            public void onError(FirebaseError firebaseError) {
                //TODO -- error handling
            }
        });
    }

    //"getter" ctor that pulls an existing user from the database
    User(Firebase ref, AuthData data)
    {
        this.ref = ref;
        this.userData = data;
        this.uid = data.getUid();

        //retrieve data from database
        this.name = (String) this.userData.getProviderData().get("name");
        this.email = (String) this.userData.getProviderData().get("email");
        this.pw = (String) this.userData.getProviderData().get("pw");
        this.address = (String) this.userData.getProviderData().get("address");
        this.address2 = (String) this.userData.getProviderData().get("address2");
        this.city = (String) this.userData.getProviderData().get("city");
        this.state = (String) this.userData.getProviderData().get("state");
        this.zip = (int) this.userData.getProviderData().get("zip");
        this.phone = (String) this.userData.getProviderData().get("phone");
    }

    public void login()
    {

    }

    public void logout()
    {
        if (this.userData != null)
        {
            this.ref.unauth();

            //TODO -- other logging out thingalings
        }
    }

    //setters
    public void setName(String f){this.name = f;}
    public void setEmail(String e){this.email = e;}
    public void setPw(String pass){this.pw = pass;}
    public void setAddress(String a){this.address = a;}
    public void setAddress2(String a2){this.address2 = a2;}
    public void setCity(String city) {this.city = city;}
    public void setState(String state) {this.state = state;}
    public void setZip(int zip) {this.zip = zip;}
    public void setPhone(String phone) {this.phone = phone;}

    //getters
    public String getName(){return this.name;}
    public String getEmail(){return this.email;}
    public String getPw(){return this.pw;}
    public String getAddress(){ return this.address;}
    public String getAddress2(){return this.address2;}
    public String getCity() {return this.city;}
    public String getState() {return this.state;}
    public int setZip() {return this.zip;}
    public String setPhone() {return this.phone;}
}
