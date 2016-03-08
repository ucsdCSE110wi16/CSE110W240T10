package sdgkteam10.rent_it;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
    ViewPager viewPager;
    GallerySwipeAdapter adapter;

    private Database db;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_listing);

        //initialize database stuff
        Database.setContext(this);
        db = Database.getInstance();

        user = new User();
        user.requestDatabaseData();

        getIds();
        adapter = new GallerySwipeAdapter(this);
        viewPager.setAdapter(adapter);


        GlobalItem gItem = GlobalItem.getInstance();
        Item item = gItem.getItem();


        viewListing_title.setText(item.getItemName());
        viewListing_description.setText(item.getDescription());

        viewListing_priceVal.setText("$"+item.getPrice());

        viewListing_rentVal.setText(item.getMinRentDur());
        viewListing_rentTimeVal.setText(item.getMinDurationSpinner());

        viewListing_categoryVal.setText(item.getCategory());
        viewListing_depositVal.setText(item.getDepositAmount());

        buttonAddFavorite = (Button)findViewById(R.id.button_add_favorite);
        buttonAddFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                GlobalItem gItem = GlobalItem.getInstance();
                user.addFavorite(gItem.getItem());
            }
        });

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

}