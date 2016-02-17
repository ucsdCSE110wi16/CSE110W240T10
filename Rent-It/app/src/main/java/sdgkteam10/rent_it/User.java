package sdgkteam10.rent_it;

/**
 * Created by Jeremy on 2/15/2016.
 */
public class User {

    private String name;
    private String email;
    private String pw1;
    private String pw2;
    private String address1;
    private String address2;
    private String city;
    private String zipcode;
    private String phone;

    User(){}
    User(String fn, String ln, String em, String p1, String p2, String a1, String a2, String c, String z, String p){
        this.setName(fn);
        this.setEmail(em);
        this.setPw1(p1);
        this.setPw2(p2);
        this.setAddress1(a1);
        this.setAddress2(a2);
        this.setCity(c);
        this.setZipcode(z);
        this.setPhone(p);
    }

    public void setName(String f){this.name = f;}
    public void setEmail(String e){this.email = e;}
    public void setPw1(String p1){this.pw1 = p1;}
    public void setPw2(String p2){this.pw2 = p2;}
    public void setAddress1(String a1){this.address1 = a1;}
    public void setAddress2(String a2){this.address2 = a2;}
    public void setCity(String c){this.city = c;}
    public void setZipcode(String z){this.zipcode = z;}
    public void setPhone(String p){this.phone = p;}

    public String getName(){return this.name;}
    public String getEmail(){return this.email;}
    public String getPw1(){return this.pw1;}
    public String getPw2(){return this.pw2;}
    public String getAddress1(){return this.address1;}
    public String getAddress2(){return this.address2;}
    public String getCity(){return this.city;}
    public String getZipcode(){return this.zipcode;}
    public String getPhone(){return this.phone;}

}
