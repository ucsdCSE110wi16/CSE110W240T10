package sdgkteam10.rent_it;

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
    private String m_uid;

    //default ctor (required by Firebase)
    User(){}

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

        Database.getInstance().createUser(this.getEmail(), this.getPw(), this);
    }

    //login ctor
    public User(String email, String password)
    {
        this.email = email;
        this.pw = password;

        Database.getInstance().requestLogin(email, password, this);
    }

    public void requestDatabaseData() {
        Database.getInstance().getUserData(this);
    }

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
   // public void setUid(String uid) {this.m_uid = uid;}

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
   // public String getUid() {return this.m_uid;}
}
