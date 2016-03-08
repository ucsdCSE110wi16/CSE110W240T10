package sdgkteam10.rent_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;



public class ViewListingActivity extends AppCompatActivity {

    private TextView viewListing_title;
    private TextView viewListing_description;
    private TextView viewListing_priceVal;
    private TextView viewListing_rentVal;
    private TextView viewListing_rentTimeVal;
    private TextView viewListing_categoryVal;
    private TextView viewListing_depositVal;
    private TextView viewListing_negotiableVal;
    private TextView viewListing_buyableVal;
    private TextView viewListing_emailVal;
    private TextView viewListing_phoneVal;
    ViewPager viewPager;
    GallerySwipeAdapter adapter;

    private final ArrayList<Bitmap> bitmapArray = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        getIds();
        adapter = new GallerySwipeAdapter(this);
        viewPager.setAdapter(adapter);
        //adjust padding based on number of images (# images * 325sp)
        /*viewListing_title.setText("My Homie's Wooden Lightsaber");
        viewListing_description.setText("Wow what a great item to use to kill people");
        viewListing_priceVal.setText("$10");
        viewListing_rentVal.setText("1 Century");
        viewListing_categoryVal.setText("Weapons");
        viewListing_depositVal.setText("N/A");*/


        //gets item object passed in from search fragment
        //Intent intent = getIntent();
        //Item item = (Item)intent.getSerializableExtra("item");

        GlobalItem gItem = GlobalItem.getInstance();
        Item item = gItem.getItem();


       /* //convert string array into bitmap images and store in bitmap array
        for (int i = 0; i < item.getImageArray().length; i++) {

            byte[] imageAsBytes = Base64.decode(item.getImageArray()[i], Base64.DEFAULT);
            Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

            bitmapArray.add(bmp);
        }*/

        //example code on setting an image from bitmap array to an ImageView
        //(ImageView) findViewById(R.id.ivImage_CL).setImageBitmap(bitmapArray[0]);

        viewListing_title.setText(item.getItemName());
        viewListing_description.setText(item.getDescription());

        viewListing_priceVal.setText("$"+item.getPrice());

        viewListing_rentVal.setText(item.getMinRentDur());
        viewListing_rentTimeVal.setText(item.getMinDurationSpinner());

        viewListing_categoryVal.setText(item.getCategory());
        viewListing_depositVal.setText(item.getDepositAmount());

    }

    private void getIds()
    {
        viewPager = (ViewPager) findViewById(R.id.view_pager);

        viewListing_title = (TextView) findViewById(R.id.viewListing_title);
        viewListing_description = (TextView) findViewById(R.id.viewListing_description);
        viewListing_priceVal = (TextView) findViewById(R.id.viewListing_priceVal);
        viewListing_rentVal = (TextView) findViewById(R.id.viewListing_rentVal);
        viewListing_rentTimeVal = (TextView) findViewById(R.id.viewListing_rentTimeVal);
        viewListing_categoryVal = (TextView) findViewById(R.id.viewListing_cateogoryVal);
        viewListing_depositVal = (TextView) findViewById(R.id.viewListing_depositVal);
        viewListing_negotiableVal = (TextView) findViewById(R.id.viewListing_negotiableVal);
        viewListing_buyableVal = (TextView) findViewById(R.id.viewListing_buyableVal);
        viewListing_phoneVal = (TextView) findViewById(R.id.viewListing_phoneVal);
        viewListing_emailVal = (TextView) findViewById(R.id.viewListing_emailVal);


        //viewListing_item = (ImageView) findViewById(R.id.viewListing_item);
    }

}