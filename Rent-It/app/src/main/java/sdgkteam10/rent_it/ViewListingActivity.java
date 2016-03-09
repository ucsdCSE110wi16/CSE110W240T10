package sdgkteam10.rent_it;

import android.graphics.Color;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.style.QuoteSpan;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.client.ValueEventListener;

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
    private Button buttonAddFavorite;
    private Button buttonRent;
    private ViewPager viewPager;
    private GallerySwipeAdapter adapter;

    private Database db;
    //private ChildEventListener favesListener;
    private ValueEventListener favesListener;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        //initialize database stuff
        Database.setContext(getApplicationContext());
        db = Database.getInstance();

        user = new User();
        user.requestDatabaseData();

        getIds();
        adapter = new GallerySwipeAdapter(this);
        viewPager.setAdapter(adapter);

        GlobalItem gItem = GlobalItem.getInstance();
        final Item item = gItem.getItem();

        viewListing_title.setText(item.getItemName());
        viewListing_description.setText(item.getDescription());

        viewListing_priceVal.setText("$"+item.getPrice());

        viewListing_rentVal.setText(item.getMinRentDur());
        viewListing_rentTimeVal.setText(item.getMinDurationSpinner());

        viewListing_categoryVal.setText(item.getCategory());
        viewListing_depositVal.setText(item.getDepositAmount());

        buttonAddFavorite = (Button)findViewById(R.id.button_add_favorite);
        buttonRent = (Button)findViewById(R.id.button_rent);

        //button to add/remove the item in the favorites
        buttonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //add to the favorites if it isn't in the favorites
                if (buttonAddFavorite.getText().toString().equals(getString(R.string.add_to_faves))) {
                    user.addFavorite(item);
                    buttonAddFavorite.setText(R.string.remove_from_faves);
                }
                //remove from favorites if it is in the favorites
                else {
                    user.removeFavorite(item);
                    buttonAddFavorite.setText(R.string.add_to_faves);
                }
            }
        });

        //button to "rent" tbe item
        //Eventual goal: add paypal/money-handling logic
        buttonRent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //temporary: display message that item has been "rented"
                Toast.makeText(getApplicationContext(),
                        getString(R.string.rentit_toast),
                        Toast.LENGTH_SHORT)
                        .show();
                buttonRent.setEnabled(false);
            }
        });

        //gets the status of the item as a favorite or not
        favesListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                setButtonText(item);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        };

        //update the add button text when the item's favorites status changes
        db.getRef().child("users").child(db.getLoggedInUser()).addListenerForSingleValueEvent(favesListener);
    }

    private void setButtonText(Item item) {
        if (user.getFavorites().contains(item)) {
            buttonAddFavorite.setText(R.string.remove_from_faves);
        }
        else {
            buttonAddFavorite.setText(R.string.add_to_faves);
        }
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
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        //db.getRef().child("users").child(db.getLoggedInUser()).removeEventListener(favesListener);
        finish();
    }

}