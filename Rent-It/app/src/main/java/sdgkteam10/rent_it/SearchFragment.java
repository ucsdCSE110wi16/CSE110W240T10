package sdgkteam10.rent_it;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.regex.Pattern;

public class SearchFragment extends Fragment {

    //stores items from Firebase query for faster searching
    private final ArrayList<Item> items = new ArrayList<>(10);

    //UI elements
    private ListView listView_S;
    private SearchView searchView_S;
    private FirebaseListAdapter<Item> mAdapter;
    private TextView emptyView;

    //database reference
    Database db;

    public SearchFragment() {}

    //returns a reference to this Fragment
    public static SearchFragment newInstance() {
        return new SearchFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //initialize database stuff
        Database.setContext(getActivity());
        db = Database.getInstance();

        //initialize UI elements
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);
        searchView_S = (SearchView) rootView.findViewById(R.id.searchView_S);
        listView_S = (ListView) rootView.findViewById(R.id.listView_S);
        listView_S.setTextFilterEnabled(true);

        //set default text of list view (when no results are present)
        emptyView = new TextView(getActivity());
        emptyView.setLayoutParams(new TableRow.LayoutParams(TableRow.LayoutParams.FILL_PARENT, TableRow.LayoutParams.FILL_PARENT));
        emptyView.setText(R.string.loading_items);
        emptyView.setTextSize(20);
        emptyView.setVisibility(View.GONE);
        emptyView.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL);
        ((ViewGroup)listView_S.getParent()).addView(emptyView);
        listView_S.setEmptyView(emptyView);

        //Query queryRef = ref.child("items").orderByChild("name").endAt("mustang").limitToLast(10);
        final Query queryRef = db.getRef().child(getActivity().getString(R.string.firebase_items)).limitToLast(20);

        //display the items in the query
        mAdapter = new FirebaseListAdapter<Item>(getActivity(), Item.class, android.R.layout.two_line_list_item, queryRef) {
            @Override
            protected void populateView(View view, Item item, int i) {
                //TODO: create custom layout and fill in all fields
                ((TextView)view.findViewById(android.R.id.text1)).setText(item.getItemName());
                ((TextView)view.findViewById(android.R.id.text2)).setText(item.getPrice());
                //add the item to the list of searchable items
                items.add(item);
            }
        };
        listView_S.setAdapter(mAdapter);

        //search functionality
        searchView_S.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            //called when the user submits a query
            public boolean onQueryTextSubmit(String query) {
                //notify user that searching has begun
                emptyView.setText(R.string.searching_items);

                //iterate through items ArrayList and check different values of each item
                //remove items from the listview that do not match the criteria
                query = query.toLowerCase();

                //stores the items matching the search
                final ArrayList<Item> results = new ArrayList<Item>(0);

                //search the items for matching results
                for (Item item: items) {
                    if (item.getItemName().toLowerCase().contains(query) ||
                        item.getCategory().toLowerCase().contains(query) ||
                        item.getDescription().toLowerCase().contains(query)) {

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
                        getActivity(), android.R.layout.two_line_list_item, android.R.id.text1, results) {
                    @Override
                    //fills in the result listView fields
                    public View getView(int position, View convertView, ViewGroup parent) {
                        //TODO: create custom layout and fill in all fields
                        View view = super.getView(position, convertView, parent);
                        TextView nameText = (TextView) view.findViewById(android.R.id.text1);
                        TextView priceText = (TextView) view.findViewById(android.R.id.text2);

                        nameText.setText(results.get(position).getItemName());
                        priceText.setText(results.get(position).getPrice());
                        return view;
                    }
                };
                listView_S.setAdapter(resultsAdapter);

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if string is empty, repopulate the original listview
                if (newText.equals("")) {
                    listView_S.setAdapter(mAdapter);
                    mAdapter.notifyDataSetChanged();
                    listView_S.invalidateViews();
                    emptyView.setText(R.string.loading_items);
                }
                return true;
            }
        });

        //TODO: when an item is clicked on, go to the view listing activity
        //view listing activity functionality
        listView_S.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

            /*
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           TextView text = (TextView) view;
          Toast.makeText(this, text.getText().toString(), Toast.LENGTH_SHORT).show();
         }

        */

        return rootView;

    }}
