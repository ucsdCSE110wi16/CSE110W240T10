package sdgkteam10.rent_it;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class FavoritesFragment extends Fragment {

    public FavoritesFragment() {}

    //returns a reference to this Fragment
    public static FavoritesFragment newInstance()
    {
        return new FavoritesFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //connect the fragment to the tab layout
        View rootView = inflater.inflate(R.layout.fragment_main, container, false);

        //TODO: change this to the proper elements that will be in this tab
        //connect the TextView to that in the layout
        TextView text = (TextView) rootView.findViewById(R.id.textView);
        text.setText("Hello, I'm the favorites tab!");

        return rootView;
    }
}
