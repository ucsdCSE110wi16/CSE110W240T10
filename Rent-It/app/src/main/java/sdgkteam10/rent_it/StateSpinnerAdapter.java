package sdgkteam10.rent_it;

import android.content.Context;
import android.widget.ArrayAdapter;

import java.util.ArrayList;

/**
 * Allows for quick addition of the specific Spinner Adapter required for
 * the State Spinner, wherever it may be
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
