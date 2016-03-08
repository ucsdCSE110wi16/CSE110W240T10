package sdgkteam10.rent_it;


import android.app.Application;


/*
 * Since we are unable to send large data from one activity to another efficiently,
 * This class creates a global item object that can be used from any activity
 *
 * To Update item:
 * GlobalItem gItem = GlobalItem.getInstance()
 * gItem.setItem(itemObj);
 *
 * To get Item:
 * GlobalItem gItem = GlobalItem.getInstance()
 * Item info = gItem.getItem();
 *
 */
public class GlobalItem extends Application {


    private static GlobalItem instance;

    //global item
    private Item item;

    private GlobalItem(){

    }

    public void setItem(Item it){
        this.item = it;
    }

    public Item getItem(){
        return this.item;
    }

    public static synchronized GlobalItem getInstance(){
        if(instance==null)
        {
            instance = new GlobalItem();
        }
        return instance;
    }



}