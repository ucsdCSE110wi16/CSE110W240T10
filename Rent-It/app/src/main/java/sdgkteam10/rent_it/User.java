package sdgkteam10.rent_it;

import com.shaded.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.ArrayList;

//TODO: store items as references to places in the database, not full items themselves?
@JsonIgnoreProperties({"favoriteItems"})
class User {
    private String name;
    private String email;
    private String pw;
    private String address;
    private String address2;
    private String city;
    private String state;
    private String zip;
    private String phone;
    private ArrayList<Item> favoriteItems;

    //default constructor
    User(){
        this.favoriteItems = new ArrayList<>();
    }

    //new user -- for use with registering new users
    public User(String name, String email, String pw, String a1, String a2,
         String city, String state, String zip, String phone){

        this.name = name;
        this.email = email;
        this.pw = pw;
        this.address = a1;
        this.address2 = a2;
        this.city = city;
        this.state = state;
        this.zip = zip;
        this.phone = phone;
        this.favoriteItems = new ArrayList<>();

        Database.getInstance().createUser(this.getEmail(), this.getPw(), this);
    }

    //login constructor
    public User(String email, String password) {
        this.email = email;
        this.pw = password;
        this.favoriteItems = new ArrayList<>();
        Database.getInstance().requestLogin(email, password, this);
    }

    public void  requestDatabaseData() {
        Database.getInstance().getUserData(this);
    }

    //setters for initializing user info
    public void setName(String n) {this.name = n;}
    public void setEmail(String e) {this.email = e;}
    public void setPw(String pass) {this.pw = pass;}
    public void setAddress(String a) {this.address = a;}
    public void setAddress2(String a2) {this.address2 = a2;}
    public void setCity(String city) {this.city = city;}
    public void setState(String state) {this.state = state;}
    public void setZip(String zip) {this.zip = zip;}
    public void setPhone(String phone) {this.phone = phone;}
    public void setFavorites(ArrayList<Item> faves) {this.favoriteItems = faves;}

    //setters for updating the user info in the database
    public void updateName(String n) {
        this.name = n;
        Database.getInstance().setUserData("name", n);
    }
    public void updateEmail(String e) {
        this.email = e;
        Database.getInstance().setUserData("email", e);
    }
    public void updatePw(String pass) {
        this.pw = pass;
        Database.getInstance().setUserData("pw", pass);
    }
    public void updateAddress(String a) {
        this.address = a;
        Database.getInstance().setUserData("address", a);
    }
    public void updateAddress2(String a2) {
        this.address2 = a2;
        Database.getInstance().setUserData("address2", a2);
    }
    public void updateCity(String city) {
        this.city = city;
        Database.getInstance().setUserData("city", city);
    }
    public void updateState(String state) {
        this.state = state;
        Database.getInstance().setUserData("state", state);
    }
    public void updateZip(String zip) {
        this.zip = zip;
        Database.getInstance().setUserData("zip", zip);
    }
    public void updatePhone(String phone) {
        this.phone = phone;
        Database.getInstance().setUserData("phone", phone);
    }

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
    public ArrayList<Item> getFavorites() {
        if (this.favoriteItems == null) {
            this.favoriteItems = new ArrayList<>();
        }
        return this.favoriteItems;
    }

    //adds a new favorite item for the user
    public void addFavorite(Item item) {
        //no favorites added yet
        if (this.favoriteItems == null) {
            this.favoriteItems = new ArrayList<>();
        }
        this.favoriteItems.add(item);
        Database.getInstance().updateFavorites(this.favoriteItems);
    }

    //removes s user favorite
    public void removeFavorite(Item item) {
        //no favorites -- can't remove
        if (this.favoriteItems == null) {
            this.favoriteItems = new ArrayList<>();
        }
        //remove item (if it exists)
        else if (this.favoriteItems.contains(item)) {
            this.favoriteItems.remove(item);
        }
        Database.getInstance().updateFavorites(this.favoriteItems);
    }
}
