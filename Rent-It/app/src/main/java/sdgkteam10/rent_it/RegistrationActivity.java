package sdgkteam10.rent_it;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.MenuItem;
import android.support.v4.app.NavUtils;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.firebase.client.AuthData;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;


import static android.graphics.Color.parseColor;


//TODO: add Firebase authorization, persistence, user account creation
//TODO: add event listeners to buttons, storing data from datafiezlds

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class RegistrationActivity extends AppCompatActivity {

    //UI elements
    private EditText fillName;
    private EditText fillEmail;
    private EditText fillPW1;
    private EditText fillPW2;
    private EditText fillAddress;
    private EditText fillAddress2;
    private EditText fillCity;
    private Spinner  fillState;
    private EditText fillZip;
    private EditText fillPhone;

    //user to be created
    private User user = null;

    //database reference
    private Database db;

    /**
     * Whether or not the system UI should be auto-hidden after
     * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
     */
    private static final boolean AUTO_HIDE = true;

    /**
     * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
     * user interaction before hiding the system UI.
     */
    private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

    /**
     * Some older devices needs a small delay between UI widget updates
     * and a change of the status and navigation bar.
     */
    private static final int UI_ANIMATION_DELAY = 300;
    private final Handler mHideHandler = new Handler();
    private View mContentView;
    private View mControlsView;
    private boolean mVisible;



    private void addRedAsterisk(EditText t) {
        CharSequence text = t.getHint();
        CharSequence redAsterisk = "*";
        SpannableStringBuilder b = new SpannableStringBuilder();

        b.append(redAsterisk);
        b.append(text);
        int start = 0;
        int end = 1;

        b.setSpan(new ForegroundColorSpan(parseColor("#e1a7a7")), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        t.setHint(b);
    }

    private void createStateSpinner() {

        //ArrayAdapter<CharSequence> stateAdapter;
        Spinner stateSpinner = (Spinner)findViewById(R.id.fill_state);
        //stateAdapter = ArrayAdapter.createFromResource(this, R.array.states_array,
        //        R.layout.special_spinner_item);
        //stateAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        //stateAdapter = new StateSpinnerAdapter(this);

        stateSpinner.setAdapter(new StateSpinnerAdapter(this));
        stateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private final Runnable mHidePart2Runnable = new Runnable() {
        @SuppressLint("InlinedApi")
        @Override
        public void run() {
            // Delayed removal of status and navigation bar

            // Note that some of these constants are new as of API 16 (Jelly Bean)
            // and API 19 (KitKat). It is safe to use them, as they are inlined
            // at compile-time and do nothing on earlier devices.
          //  mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
          //          | View.SYSTEM_UI_FLAG_FULLSCREEN
          //          | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
          //          | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
          //          | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
          //          | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        }
    };

    private final Runnable mShowPart2Runnable = new Runnable() {
        @Override
        public void run() {
            // Delayed display of UI elements
            ActionBar actionBar = getSupportActionBar();
            if (actionBar != null) {
                actionBar.show();
            }
            mControlsView.setVisibility(View.VISIBLE);
        }
    };

    private final Runnable mHideRunnable = new Runnable() {
        @Override
        public void run() {
            hide();
        }
    };
    /**
     * Touch listener to use for in-layout UI controls to delay hiding the
     * system UI. This is to prevent the jarring behavior of controls going away
     * while interacting with activity UI.
     */
    private final View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            if (AUTO_HIDE) {
                delayedHide(AUTO_HIDE_DELAY_MILLIS);
            }
            return false;
        }
    };


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Database.setContext(this);
        db = Database.getInstance();

        setContentView(R.layout.activity_registration);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        mVisible = false;
        //mControlsView = findViewById(R.id.fullscreen_content_controls);
       // mContentView = findViewById(R.id.fullscreen_content);

        db.logoutUser(); //log out any currently logged in user
        db.getRef().addAuthStateListener(new Firebase.AuthStateListener() {
            @Override
            public void onAuthStateChanged(AuthData authData) {
                //registration successful -> a new user logged in
                if (authData != null)
                {
                    Log.d("My Logging", "successful login!");

                    //load the main page
                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                }
            }
        });

        //initialize the UI elements
        Button button_send = (Button)findViewById(R.id.submit_button);
        fillName = (EditText)findViewById(R.id.fill_name);
        fillName.setHintTextColor(parseColor("#A8CEEB"));
        addRedAsterisk(fillName);
        fillEmail = (EditText)findViewById(R.id.fill_email);
        fillEmail.setHintTextColor(parseColor("#A8CEEB"));
        addRedAsterisk(fillEmail);
        fillPW1 = (EditText)findViewById(R.id.fill_pw1);
        fillPW1.setHintTextColor(parseColor("#A8CEEB"));
        addRedAsterisk(fillPW1);
        fillPW2 = (EditText)findViewById(R.id.fill_pw2);
        fillPW2.setHintTextColor(parseColor("#A8CEEB"));
        addRedAsterisk(fillPW2);
        fillAddress = (EditText)findViewById(R.id.fill_address1);
        fillAddress.setHintTextColor(parseColor("#A8CEEB"));
        fillAddress2 = (EditText)findViewById(R.id.fill_address2);
        fillAddress2.setHintTextColor(parseColor("#A8CEEB"));
        fillCity = (EditText)findViewById(R.id.fill_city);
        fillCity.setHintTextColor(parseColor("#A8CEEB"));
        fillState = (Spinner) findViewById(R.id.fill_state);
        fillZip = (EditText)findViewById(R.id.fill_zip);
        fillZip.setHintTextColor(parseColor("#A8CEEB"));
        addRedAsterisk(fillZip);
        fillPhone = (EditText)findViewById(R.id.fill_phone);
        fillPhone.setHintTextColor(parseColor("#A8CEEB"));

        fillName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillName.setText("");
            }
        });
        fillEmail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillEmail.setText("");
            }
        });
        fillPW1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillPW1.setText("");
            }
        });
        fillPW2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillPW2.setText("");
            }
        });
        fillAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillAddress.setText("");
            }
        });
        fillAddress2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillAddress2.setText("");
            }
        });
        fillCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillCity.setText("");
            }
        });
        fillZip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillZip.setText("");
            }
        });
        fillPhone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fillPhone.setText("");
            }
        });

        //TODO: add a loading spinner
        button_send.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               boolean validated = validateInput();

               if (validated) {
                   //create a new user from input
                   user = new User(
                           fillName.getText().toString(),
                           fillEmail.getText().toString(),
                           fillPW1.getText().toString(),
                           fillAddress.getText().toString(),
                           fillAddress2.getText().toString(),
                           fillCity.getText().toString(),
                           fillState.getSelectedItem().toString(),
                           fillZip.getText().toString(),
                           fillPhone.getText().toString()
                   );

                   //attempt to login once the user has been created
                   db.getRef().child("users").addChildEventListener(new ChildEventListener() {
                       @Override
                       public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                           db.requestLogin(user.getEmail(), user.getPw(), user);
                       }

                       //TODO: add error handling for creating user
                       @Override
                       public void onCancelled(FirebaseError firebaseError) {
                           Log.e("My Logging", "creation fail: " + firebaseError.getMessage());
                       }

                       //the following are required and ignored
                       @Override
                       public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                       }

                       @Override
                       public void onChildRemoved(DataSnapshot dataSnapshot) {
                       }

                       @Override
                       public void onChildMoved(DataSnapshot dataSnapshot, String s) {
                       }
                   });

                   //make sure the user successfully logged in
                   db.getRef().addAuthStateListener(new Firebase.AuthStateListener() {
                       @Override
                       public void onAuthStateChanged(AuthData authData) {
                           //successful login
                           if (authData != null) {
                               //load the main page
                               Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                               intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                               startActivity(intent);
                               finish();
                           }
                           //something went wrong in login
                           else {
                               //TODO: handle login errors
                               FirebaseError error = db.getLoginError();
                               if (error != null)
                                   Log.e("My Logging", "login fail: " + error.getMessage());
                               else
                                   Log.e("My Logging", "login fail");
                           }
                       }
                   });

               } else {
                   //TODO: handle improper input
                   Log.e("My Logging", "bad input");
               }
           }
       }
        );


        createStateSpinner();

    }

    //TODO -- validate the user fields
    private boolean validateInput()
    {
        if (!fillPW1.getText().toString().equals(fillPW2.getText().toString()))
        {
            //TODO: error -- passwords don't match!!
            return false;
        }

        //TODO: other user input validation and proper handling

        //input is good
        return true;
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);

        // Trigger the initial hide() shortly after the activity has been
        // created, to briefly hint to the user that UI controls
        // are available.
        delayedHide(100);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button.
            NavUtils.navigateUpFromSameTask(this);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void toggle() {
        if (mVisible) {
            hide();
        } else {
            show();
        }
    }

    private void hide() {
        // Hide UI first
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.hide();
        }
        //mControlsView.setVisibility(View.GONE);
        mVisible = false;

        // Schedule a runnable to remove the status and navigation bar after a delay
        mHideHandler.removeCallbacks(mShowPart2Runnable);
        mHideHandler.postDelayed(mHidePart2Runnable, UI_ANIMATION_DELAY);
    }

    @SuppressLint("InlinedApi")
    private void show() {
        // Show the system bar
        mContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION);
        mVisible = true;

        // Schedule a runnable to display UI elements after a delay
        mHideHandler.removeCallbacks(mHidePart2Runnable);
        mHideHandler.postDelayed(mShowPart2Runnable, UI_ANIMATION_DELAY);
    }

    /**
     * Schedules a call to hide() in [delay] milliseconds, canceling any
     * previously scheduled calls.
     */
    private void delayedHide(int delayMillis) {
        mHideHandler.removeCallbacks(mHideRunnable);
        mHideHandler.postDelayed(mHideRunnable, delayMillis);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }
}
