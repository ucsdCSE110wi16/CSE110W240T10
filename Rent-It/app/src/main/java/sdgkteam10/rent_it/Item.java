package sdgkteam10.rent_it;


class Item {
    private String itemName;
    private String price;
    private String priceRate;
    private String description;
    private String[] imageArray;
    private String category;
    private String itemUserID;

    public Item(){

    }

    public String getItemName() {
        return itemName;
    }
    public void setItemName(String itemName) {
        this.itemName = itemName;
    }


    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }


    public String getPriceRate() {
        return priceRate;
    }
    public void setPriceRate(String priceRate) {
        this.priceRate = priceRate;
    }


    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }


    public  String[] getImageArray() {
        return imageArray;
    }
    public void setImageArray(String[] imageArray) {
        this.imageArray = imageArray;
    }


    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }


    public String getItemUserID() {
        return itemUserID;
    }
    public void setItemUserID(String itemUserID) {
        this.itemUserID = itemUserID;
    }
}
/*

public String get() {
        return ;
    }
public void set(String ) {
        this. = ;
    }


* */