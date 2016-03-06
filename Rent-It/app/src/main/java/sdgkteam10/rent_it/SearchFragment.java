package sdgkteam10.rent_it;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    //stores items from Firebase query for faster searching
    private final ArrayList<Item> items = new ArrayList<>(10);

    //UI elements
    private ListView listView_S;
    private SearchView searchView_S;
    private FirebaseListAdapter<Item> mAdapter;

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


        //Query queryRef = ref.child("items").orderByChild("name").endAt("mustang").limitToLast(10);
        Query queryRef = db.getRef().child("items").limitToLast(20);

        //display the items in the query
        //TODO: create custom layout
        mAdapter = new FirebaseListAdapter<Item>(getActivity(), Item.class, android.R.layout.two_line_list_item, queryRef) {
            @Override
            protected void populateView(View view, Item item, int i) {
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
                //TODO: implement search functionality

                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                return false;
            }
        });

        //TODO: when an item is clicked on, go to the view listing activity
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
