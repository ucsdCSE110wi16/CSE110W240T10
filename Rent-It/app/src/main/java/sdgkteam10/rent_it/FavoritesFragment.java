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
    private ArrayList<Item> favorites;

    //stores the items matching the search
    private ArrayList<Item> results;

    //UI elements
    private ListView listView_F;
    private SearchView searchView_F;

    private TextView emptyView;

    private FirebaseListAdapter<Item> mAdapter;

    //helps to prevent reloading data before searches
    private boolean hasSearched = false;

    //store item keys to prevent duplicates
    Hashtable<Integer,Boolean> uniqueTable
            = new Hashtable<Integer, Boolean>();

    //database reference
    Database db;


    public FavoritesFragment() {
    }

    //returns a reference to this Fragment
    public static FavoritesFragment newInstance() {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initialize database stuff
        Database.setContext(getActivity());
        db = Database.getInstance();

        results = new ArrayList<>();
        favorites = new ArrayList<>();

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

        //make it so search hint text displays when page is loaded
        searchView_F.setIconified(false);
        searchView_F.setFocusable(false);
        searchView_F.clearFocus();

        //get the favorites for the user from the database
        final Query queryRef = db.getRef().child("users").child(db.getLoggedInUser()).child(getString(R.string.firebase_faves)).limitToLast(20);

        //display the items in the query to user
        mAdapter = new FirebaseListAdapter<Item>(getActivity(), Item.class, R.layout.list_item_box, queryRef) {
            @Override
            protected void populateView(View view, Item item, int i) {
                ((TextView)view.findViewById(R.id.itemBoxTitle)).setText(item.getItemName());
                ((TextView)view.findViewById(R.id.itemBoxPrice)).setText("$"+item.getPrice());
                ((TextView)view.findViewById(R.id.itemBoxRate)).setText(item.getPriceRate());


                //converting string saved in fire base to an image
                String tmpImgArray[] = item.getImageArray();
                byte[] imageAsBytes = Base64.decode(tmpImgArray[0], Base64.DEFAULT);
                Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);
                ((ImageView)view.findViewById(R.id.itemBoxImage)).setImageBitmap(bmp);


                /*
                 * check if unique item hash key is already stored, prevent duplicates
                 * if not addd item into arraylist and also add the key into hashtable
                 */
                if(uniqueTable.get(item.getUniqueID()) == null)
                {
                    favorites.add(item);
                    uniqueTable.put(item.getUniqueID(), true);

                    Log.d("search", "inserting " + item.getItemName() + " into hash set and hash set size is " + favorites.size());
                }
                else
                {
                    Log.d("search", "Skipped insertion of duplicate item " + item.getItemName() + " into hash set and hash set size is " + favorites.size());
                }
            }
        };
        listView_F.setAdapter(mAdapter);

        //search functionality
        searchView_F.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //called when the user submits a query
            public boolean onQueryTextSubmit(String query) {
                query = query.toLowerCase();
                hasSearched = true;

                //notify user that searching has begun
                emptyView.setText(R.string.searching_items);

                //empty the previous search results
                results.clear();

                //search the items for matching results
                for (Item item : favorites) {
                    if (item.getItemName().toLowerCase().contains(query) ||
                            item.getCategory().toLowerCase().contains(query) ||
                            item.getDescription().toLowerCase().contains(query) ||
                            item.getZipcode().toLowerCase().contains(query)) {

                        //item matched the query, so add to results
                        results.add(item);
                    }
                }

                //no results found
                if (results.size() == 0) {
                    emptyView.setText(R.string.no_items_found);
                }


                //create a new adapter to display the results of the search
                ArrayAdapter<Item> resultsAdapter = new ArrayAdapter<Item>(
                        getActivity(), R.layout.list_item_box, results) {
                    @Override
                    //fills in the result listView fields
                    public View getView(int position, View convertView, ViewGroup parent) {
                        View view = null;

                        //prevent duplicate views
                        if (convertView == null) {
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            view = inflater.inflate(R.layout.list_item_box, parent, false);
                        } else {
                            view = convertView;
                        }

                        //View view = super.getView(position, convertView, parent);
                        TextView nameText = (TextView) view.findViewById(R.id.itemBoxTitle);
                        TextView priceText = (TextView) view.findViewById(R.id.itemBoxPrice);
                        TextView RateText = (TextView) view.findViewById(R.id.itemBoxRate);
                        ImageView itemImg = (ImageView) view.findViewById(R.id.itemBoxImage);


                        //converting string saved in fire base to an image
                        String tmpImgArray[] = results.get(position).getImageArray();
                        byte[] imageAsBytes = Base64.decode(tmpImgArray[0], Base64.DEFAULT);
                        Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);

                        itemImg.setImageBitmap(bmp);
                        nameText.setText(results.get(position).getItemName());
                        priceText.setText("$" + results.get(position).getPrice());
                        RateText.setText(results.get(position).getPriceRate());

                        return view;
                    }
                };
                listView_F.setAdapter(resultsAdapter);
                listView_F.invalidateViews();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if string is empty, refresh everything and repopulate the original listview
                if (newText.equals("") && hasSearched) {
                    hasSearched = false;
                    emptyView.setText(R.string.loading_items);

                    listView_F.setAdapter(mAdapter);
                    listView_F.invalidateViews();
                }
                return true;
            }
        });

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
                    Log.d("favorites", "Send item object from local array list to View Listing, item is "+ results.get(position).getItemName());

                    Item item = results.get(position);
                    //intent.putExtra("item",item);

                    GlobalItem gItem = GlobalItem.getInstance();
                    gItem.setItem(item);
                }
                else
                {
                    Log.d("favorites", "Send item object from Fire base array list to View Listing, item is " + favorites.get(position).getItemName());

                    Item item = favorites.get(position);
                    //intent.putExtra("item", item);

                    GlobalItem gItem = GlobalItem.getInstance();
                    gItem.setItem(item);
                }

                startActivity(intent);
            }
        });

        return rootView;
    }

    //update the displayed favorites view when returning from viewing an item
    @Override
    public void onResume() {
        super.onResume();
        favorites.clear();
        uniqueTable.clear();
        listView_F.invalidateViews();
        mAdapter.notifyDataSetChanged();
    }
}
