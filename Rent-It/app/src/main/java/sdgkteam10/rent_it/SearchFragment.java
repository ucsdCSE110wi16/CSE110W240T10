package sdgkteam10.rent_it;

import android.app.ListFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.Query;

import java.util.ArrayList;

public class SearchFragment extends Fragment {

    private ArrayList<String> arr = new ArrayList<>(10);

    private String queryString = "mu";


    public SearchFragment() {

    }


    //returns a reference to this Fragment
    public static SearchFragment newInstance() {
        SearchFragment fragment = new SearchFragment();
        return fragment;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_search, container, false);

        //TODO: change temporary testing code to reflect production database
        Firebase.setAndroidContext(getActivity());
        Firebase myFirebaseRef = new Firebase("https://boiling-heat-3337.firebaseio.com/");
        //Query queryRef = myFirebaseRef.child("items").orderByChild("name").equalTo("Mustang");
        // /Query queryRef = ref.orderByKey().startAt("b").endAt("b\uf8ff");
        //Query queryRef = myFirebaseRef.child("items").orderByChild("name").startAt(queryString).endAt(queryString + '\uf8ff').limitToFirst(5);
        Log.d("search", "-----------------about to start new query----------------------");
        //Query queryRef = myFirebaseRef.child("items").orderByChild("name").startAt("m").endAt("m\uf8ff").limitToFirst(5);
        //Query queryRef = myFirebaseRef.orderByChild("name").startAt("m").endAt("m\uf8ff").limitToFirst(5);
        //Query queryRef = myFirebaseRef.child("items").orderByChild("name").startAt("mustang").endAt("m\uf8ff").limitToFirst(5);
        Query queryRef = myFirebaseRef.child("items").orderByChild("name").endAt("mustang").limitToLast(10);


        queryRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot snapshot, String previousChild) {

                Log.d("search", "num of items found is " + snapshot.getChildrenCount());
                Log.d("search", "this was found " + snapshot.getKey());

                arr.add(snapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        ListView listView_S = (ListView) rootView.findViewById(R.id.listView_S);

        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.fragment_search, arr);
        arrayAdapter.notifyDataSetChanged();
        listView_S.setAdapter(arrayAdapter);
        //listView_S.setOnItemClickListener(getActivity());

            /*
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

           TextView text = (TextView) view;
          Toast.makeText(this, text.getText().toString(), Toast.LENGTH_SHORT).show();
         }

        */

        return rootView;

    }}
