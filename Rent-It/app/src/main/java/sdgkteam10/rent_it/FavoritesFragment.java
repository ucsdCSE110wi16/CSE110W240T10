package sdgkteam10.rent_it;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Base64;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.Hashtable;

public class FavoritesFragment extends Fragment {

    //stores items from Firebase query for faster searching
    //private Set<Item> items;
    private ArrayList<Item> itemz;

    //stores the items matching the search
    private ArrayList<Item> resultz;

    //UI elements
    private ListView listView_F;
    private SearchView searchView_F;

    private TextView emptyView;

    //helps to prevent reloading data before searches
    private boolean hasSearched = false;

    //store item keys to prevent duplicates
    Hashtable<Integer,Boolean> uniqueTable
            = new Hashtable<Integer, Boolean>();

    //database reference
    Database db;


    public FavoritesFragment() {
    }

    public ArrayList<Item> getItems(){return this.itemz;}

    //returns a reference to this Fragment
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        //items = new HashSet<Item>();
        itemz = User.favoriteItems;
        resultz = new ArrayList<Item>();


        //initialize UI elements
        View rootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        searchView_F = (SearchView) rootView.findViewById(R.id.searchView_F);
        listView_F = (ListView) rootView.findViewById(R.id.listView_F);
        listView_F.setTextFilterEnabled(true);
        listView_F.setFriction(ViewConfiguration.getScrollFriction() * 20);


        //set default text of list view (when no results are present)
        emptyView = new TextView(getActivity());
        emptyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
        emptyView.setText(R.string.loading_items);
        emptyView.setTextSize(20);
        emptyView.setVisibility(View.GONE);
        emptyView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ((ViewGroup)listView_F.getParent()).addView(emptyView);
        listView_F.setEmptyView(emptyView);


        //view listing activity functionality
        listView_F.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {


                Intent intent = new Intent(getActivity(), ViewListingActivity.class);

                /*
                 * pass the entire item object to view listing activity depending on
                 * whether the item was clicked from fire base results or local results.
                 */
                if(hasSearched)
                {
                    Log.d("search", "Send item object from local array list to View Listing, item is "+ resultz.get(position).getItemName());

                    Item item = resultz.get(position);
                    //intent.putExtra("item",item);

                    GlobalItem gItem = GlobalItem.getInstance();
                    gItem.setItem(item);
                }
                else
                {
                    Log.d("search", "Send item object from Fire base array list to View Listing, item is "+ itemz.get(position).getItemName());

                    Item item = itemz.get(position);
                    //intent.putExtra("item", item);

                    GlobalItem gItem = GlobalItem.getInstance();
                    gItem.setItem(item);
                }

                startActivity(intent);
            }
        });

        return rootView;
    }
}
