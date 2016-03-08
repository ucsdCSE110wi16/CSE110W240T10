package sdgkteam10.rent_it;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Created by tyrsson on 3/7/16.
 */
public class StateSpinnerAdapter extends ArrayAdapter<CharSequence> {

    // Context and sequence of states
    private Context context;
    private ArrayList<CharSequence> states;

    public StateSpinnerAdapter(Context context) {
        super(context, R.layout.special_spinner_item,
                context.getResources().getStringArray(R.array.states_array));
        this.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

}
 /*
 private void createStateSpinner() {

        ArrayAdapter<CharSequence> stateAdapter;
        Spinner stateSpinner = (Spinner)findViewById(R.id.fill_state);
        stateAdapter = ArrayAdapter.createFromResource(this, R.array.states_array,
                R.layout.special_spinner_item);
        stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        stateSpinner.setAdapter(stateAdapter);
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
  */