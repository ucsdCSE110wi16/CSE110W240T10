package sdgkteam10.rent_it;


class Item {
    private String itemName;
    private String price;
    private String priceRate;
    private String description;
    private String[] imageArray;
    private String category;
    private String itemUserID;

    private String minRentDur;
    private String minDurationSpinner;
    private String depositAmount;
    private String depositReqd;
    //private String depositNotReqd;
    private String priceNeg;
    private String item_Buyout;
    private int uniqueID;


    public Item() {

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


    public String[] getImageArray() {
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

    public String getMinRentDur() {
        return minRentDur;
    }

    public void setMinRentDur(String minRentDur) {
        this.minRentDur = minRentDur;
    }

    public String getMinDurationSpinner() {
        return minDurationSpinner;
    }

    public void setMinDurationSpinner(String minDurationSpinner) {
        this.minDurationSpinner = minDurationSpinner;
    }

    public String getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(String depositAmount) {
        this.depositAmount = depositAmount;
    }

    // this is for the "Yes!" check box
    public String getDepositReqd() {
        return depositReqd;
    }

    public void setDepositReqd(String depositReqd) {
        this.depositReqd = depositReqd;
    }

    /* this is for the "No." check box
    public String getDepositNotReqd() {
        return depositNotReqd;
    }
    public void setDepositNotReqd(String depositNotReqd) {
        this.depositNotReqd = depositNotReqd;
    }*/

    public String getPriceNeg() {
        return priceNeg;
    }

    public void setPriceNeg(String priceNeg) {
        this.priceNeg = priceNeg;
    }

    public String getItemBuyout() {
        return item_Buyout;
    }

    public void setItemBuyout(String item_Buyout) {
        this.item_Buyout = item_Buyout;
    }

    public int getUniqueID() {
        return uniqueID;
    }

    public void setUniqueID(int uniqueID) {
        this.uniqueID = uniqueID;
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