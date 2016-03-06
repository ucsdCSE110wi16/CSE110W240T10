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
import android.widget.TableRow;
import android.widget.TextView;

import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class SearchFragment extends Fragment {

    //stores items from Firebase query for faster searching
    private Set<Item> items;

    //stores the items matching the search
    private ArrayList<Item> results;

    //UI elements
    private ListView listView_S;
    private SearchView searchView_S;
    private FirebaseListAdapter<Item> mAdapter;
    private TextView emptyView;

    //helps to prevent reloading data before searches
    private boolean hasSearched = false;

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

        items = new HashSet<Item>();
        results = new ArrayList<Item>();

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
                query = query.toLowerCase();
                hasSearched = true;

                //notify user that searching has begun
                emptyView.setText(R.string.searching_items);

                //empty the previous search results
                results.clear();

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
                        View view = null;

                        //TODO: create custom layout and fill in all fields
                        //prevent duplicate views
                        if (convertView == null) {
                            LayoutInflater inflater = getActivity().getLayoutInflater();
                            view = inflater.inflate(android.R.layout.two_line_list_item, parent, false);
                        }
                        else {
                            view = convertView;
                        }

                        //View view = super.getView(position, convertView, parent);
                        TextView nameText = (TextView) view.findViewById(android.R.id.text1);
                        TextView priceText = (TextView) view.findViewById(android.R.id.text2);

                        nameText.setText(results.get(position).getItemName());
                        priceText.setText(results.get(position).getPrice());
                        return view;
                    }
                };
                listView_S.setAdapter(resultsAdapter);
                listView_S.invalidateViews();

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                //if string is empty, refresh everything and repopulate the original listview
                if (newText.equals("") && hasSearched) {
                    hasSearched = false;
                    items.clear();
                    emptyView.setText(R.string.loading_items);
                    listView_S.setAdapter(mAdapter);
                    listView_S.invalidateViews();
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

        return rootView;
    }
}
