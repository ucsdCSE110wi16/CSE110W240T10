package sdgkteam10.rent_it;

import android.animation.Animator;                  //  http://developer.android.com/reference/android/animation/Animator.html
import android.animation.AnimatorListenerAdapter;   //  http://developer.android.com/reference/android/animation/AnimatorListenerAdapter.html
import android.annotation.TargetApi;                //  http://developer.android.com/reference/android/annotation/TargetApi.html
import android.app.AlertDialog;                     //  http://developer.android.com/reference/android/app/AlertDialog.html
import android.content.Intent;                      //  http://developer.android.com/reference/android/content/Intent.html
import android.content.pm.PackageManager;           //  http://developer.android.com/reference/android/content/pm/PackageManager.html
import android.support.annotation.NonNull;          //  http://tools.android.com/tech-docs/support-annotations
import android.support.design.widget.Snackbar;      //  http://developer.android.com/reference/android/support/design/widget/Snackbar.html
import android.support.v7.app.AppCompatActivity;    //  http://developer.android.com/reference/android/support/v7/app/AppCompatActivity.html
import android.app.LoaderManager.LoaderCallbacks;   //  http://developer.android.com/reference/android/app/LoaderManager.LoaderCallbacks.html

import android.content.CursorLoader;                //  http://developer.android.com/reference/android/content/CursorLoader.html
import android.content.Loader;                      //  http://developer.android.com/reference/android/content/Loader.html
import android.database.Cursor;                     //  http://developer.android.com/reference/android/database/Cursor.html
import android.net.Uri;                             //  http://developer.android.com/reference/android/net/Uri.html
import android.os.AsyncTask;                        //  http://developer.android.com/reference/android/os/AsyncTask.html

import android.os.Build;                            //  http://developer.android.com/reference/android/os/Build.html
import android.os.Bundle;                           //  http://developer.android.com/reference/android/os/Bundle.html
import android.provider.ContactsContract;           //  http://developer.android.com/reference/android/provider/ContactsContract.html
import android.text.TextUtils;                      //  http://developer.android.com/reference/android/text/TextUtils.html
import android.view.KeyEvent;                       //  http://developer.android.com/reference/android/view/KeyEvent.html
import android.view.View;                           //  http://developer.android.com/reference/android/view/View.html
import android.view.View.OnClickListener;           //  http://developer.android.com/reference/android/view/View.OnClickListener.html
import android.view.inputmethod.EditorInfo;         //  http://developer.android.com/reference/android/view/inputmethod/EditorInfo.html
import android.widget.ArrayAdapter;                 //  http://developer.android.com/reference/android/widget/ArrayAdapter.html
import android.widget.AutoCompleteTextView;         //  http://developer.android.com/reference/android/widget/AutoCompleteTextView.html
import android.widget.Button;                       //  http://developer.android.com/reference/android/widget/Button.html
import android.widget.EditText;                     //  http://developer.android.com/reference/android/widget/EditText.html
import android.widget.TextView;                     //  http://developer.android.com/reference/android/widget/TextView.html

import com.firebase.client.AuthData;                //  https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/AuthData.html

//for info on package com.firebase.client:   https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/package-summary.html

import com.firebase.client.Firebase;                        //  https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/Firebase.html
import com.firebase.client.FirebaseError;                   //  https://www.firebase.com/docs/java-api/javadoc/com/firebase/client/FirebaseError.html


import java.util.ArrayList;                                 //  https://docs.oracle.com/javase/7/docs/api/java/util/ArrayList.html
import java.util.List;                                      //  https://docs.oracle.com/javase/8/docs/api/java/util/List.html

import static android.Manifest.permission.READ_CONTACTS;

/**
 * A login screen that offers login via email/password.
 *
 * AppCombatActivity - base class for activities that use the support library action bar features
 */
public class LoginActivity extends AppCompatActivity implements LoaderCallbacks<Cursor> {

    /**
     * Id to identity READ_CONTACTS permission request.
     */
    private static final int REQUEST_READ_CONTACTS = 0;

    /**
     * Keep track of the login task to ensure we can cancel it if requested.
     */
    private UserLoginTask mAuthTask = null;

    // UI references.
    private AutoCompleteTextView mEmailView;
    private EditText mPasswordView;
    private View mProgressView;
    private View mLoginFormView;


    private Database db;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        // Set up the login form.
        mEmailView = (AutoCompleteTextView) findViewById(R.id.email);
        populateAutoComplete();

        mPasswordView = (EditText) findViewById(R.id.password);
        mPasswordView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == R.id.login || id == EditorInfo.IME_NULL) {
                    attemptLogin();
                    return true;
                }
                return false;
            }
        });

        Button mEmailSignInButton = (Button) findViewById(R.id.button_signin);
        mEmailSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                attemptLogin();
            }
        });

        Button buttonRegister = (Button)findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent;
                intent = new Intent(LoginActivity.this, RegistrationActivity.class);
                startActivity(intent);
            }
        });

        mLoginFormView = findViewById(R.id.login_form);
        mProgressView = findViewById(R.id.login_progress);


        Database.setContext(this);
        db = Database.getInstance();
    }

    private void populateAutoComplete() {
        if (!mayRequestContacts()) {
            return;
        }

        getLoaderManager().initLoader(0, null, this);
    }

    private boolean mayRequestContacts() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return true;
        }
        if (checkSelfPermission(READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {
            return true;
        }
        if (shouldShowRequestPermissionRationale(READ_CONTACTS)) {
            Snackbar.make(mEmailView, R.string.permission_rationale, Snackbar.LENGTH_INDEFINITE)
                    .setAction(android.R.string.ok, new OnClickListener() {
                        @Override
                        @TargetApi(Build.VERSION_CODES.M)
                        public void onClick(View v) {
                            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
                        }
                    });
        } else {
            requestPermissions(new String[]{READ_CONTACTS}, REQUEST_READ_CONTACTS);
        }
        return false;
    }

    public void goRegister() {
        Intent intent;
        intent = new Intent(LoginActivity.this, RegistrationActivity.class);
        startActivity(intent);
    }


    /**
     * Callback received when a permissions request has been completed.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_READ_CONTACTS) {
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                populateAutoComplete();
            }
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
    private void attemptLogin() {
        if (mAuthTask != null) {
            return;
        }

        // Reset errors.
        mEmailView.setError(null);
        mPasswordView.setError(null);

        // Store values at the time of the login attempt.
        String email = mEmailView.getText().toString();
        String password = mPasswordView.getText().toString();

        boolean cancel = false;
        View focusView = null;

        // Check for a valid password, if the user entered one.
        if (!TextUtils.isEmpty(password) && !isPasswordValid(password)) {
            mPasswordView.setError(getString(R.string.error_invalid_password));
            focusView = mPasswordView;
            cancel = true;
        }

        // Check for a valid email address.
        if (TextUtils.isEmpty(email)) {
            mEmailView.setError(getString(R.string.error_field_required));
            focusView = mEmailView;
            cancel = true;
        } else if (!isEmailValid(email)) {
            mEmailView.setError(getString(R.string.error_invalid_email));
            focusView = mEmailView;
            cancel = true;
        }

        if (cancel) {
            // There was an error; don't attempt login and focus the first
            // form field with an error.
            focusView.requestFocus();
        } else {
            // Show a progress spinner, and kick off a background task to
            // perform the user login attempt.
            showProgress(true);
            mAuthTask = new UserLoginTask(email, password);
            mAuthTask.execute((Void) null);
        }
    }

    private boolean isEmailValid(String email) {
        return email.contains("@");
    }

    private boolean isPasswordValid(String password) {
        return password.length() > 0;
    }

    /**
     * Shows the progress UI and hides the login form.
     */
    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    private void showProgress(final boolean show) {
        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
        // for very easy animations. If available, use these APIs to fade-in
        // the progress spinner.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
            mLoginFormView.animate().setDuration(shortAnimTime).alpha(
                    show ? 0 : 1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mProgressView.animate().setDuration(shortAnimTime).alpha(
                    show ? 1 : 0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        } else {
            // The ViewPropertyAnimator APIs are not available, so simply show
            // and hide the relevant UI components.
            mProgressView.setVisibility(show ? View.VISIBLE : View.GONE);
            mLoginFormView.setVisibility(show ? View.GONE : View.VISIBLE);
        }
    }

    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(this,
                // Retrieve data rows for the device user's 'profile' contact.
                Uri.withAppendedPath(ContactsContract.Profile.CONTENT_URI,
                        ContactsContract.Contacts.Data.CONTENT_DIRECTORY), ProfileQuery.PROJECTION,

                // Select only email addresses.
                ContactsContract.Contacts.Data.MIMETYPE +
                        " = ?", new String[]{ContactsContract.CommonDataKinds.Email
                .CONTENT_ITEM_TYPE},

                // Show primary email addresses first. Note that there won't be
                // a primary email address if the user hasn't specified one.
                ContactsContract.Contacts.Data.IS_PRIMARY + " DESC");
    }

    @Override
    public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
        List<String> emails = new ArrayList<>();
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            emails.add(cursor.getString(ProfileQuery.ADDRESS));
            cursor.moveToNext();
        }

        addEmailsToAutoComplete(emails);
    }

    @Override
    public void onLoaderReset(Loader<Cursor> cursorLoader) {

    }

    private void addEmailsToAutoComplete(List<String> emailAddressCollection) {
        //Create adapter to tell the AutoCompleteTextView what to show in its dropdown list.
        ArrayAdapter<String> adapter =
                new ArrayAdapter<>(LoginActivity.this,
                        android.R.layout.simple_dropdown_item_1line, emailAddressCollection);

        mEmailView.setAdapter(adapter);
    }

    @Override
    public void onStart() {
        super.onStart();




    }

    @Override
    public void onStop() {
        super.onStop();



    }


    private interface ProfileQuery {
        String[] PROJECTION = {
                ContactsContract.CommonDataKinds.Email.ADDRESS,
                ContactsContract.CommonDataKinds.Email.IS_PRIMARY,
        };

        int ADDRESS = 0;
        int IS_PRIMARY = 1;
    }

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
    public class UserLoginTask extends AsyncTask<Void, Void, Boolean> {

        private final String mEmail;
        private final String mPassword;
        private Firebase.AuthResultHandler mHandler;
        private FirebaseError mLoginError;


        UserLoginTask(String email, String password) {
            mEmail = email;
            mPassword = password;
            mLoginError = null;

            //log out anyone previously logged in
            db.logoutUser();
        }

        /**
         * Performs user authentication in the background.
         */
        @Override
        protected Boolean doInBackground(Void... params) {
            //authenticate user login info with the database
            User user = new User(mEmail, mPassword);

            //workaround for anonymous classes unable to edit local data
            final boolean[] success = new boolean[1];
            success[0] = false;

            //listen for successful login
            db.getRef().addAuthStateListener(new Firebase.AuthStateListener() {
                @Override
                public void onAuthStateChanged(AuthData authData) {
                    if (authData != null) {
                        //successful login
                        success[0] = true;
                    }
                    else {
                        success[0] = false;
                    }
                }
            });

            //wait for login to complete
            while (!success[0] && db.getLoginError() == null);

            //unsuccessful login
            if (db.getLoginError() != null) {
                mLoginError = db.getLoginError();
                return false;
            }

            //successful login
            return true;

        }

        /**
         * Called once doInBackground is complete. Either indicates incorrect user
         * information, or starts the main page's activity.
         */
        @Override
        protected void onPostExecute(final Boolean success) {
            //super.onPostExecute(success);

            showProgress(false);

            if (success) {
                //load the main page
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                mAuthTask = null;
                finish();

            } else {
                //check the error message thrown by Firebase
                if (mLoginError != null)
                {
                    switch (mLoginError.getCode()) {
                        //handle invalid email
                        case FirebaseError.USER_DOES_NOT_EXIST:
                        case FirebaseError.INVALID_EMAIL:
                            mEmailView.setError(getString(R.string.error_invalid_email));
                            mEmailView.requestFocus();
                            break;
                        //handle invalid password
                        case FirebaseError.INVALID_PASSWORD:
                            mPasswordView.setError(getString(R.string.error_incorrect_password));
                            mPasswordView.requestFocus();
                            break;
                        default:
                            new AlertDialog.Builder(LoginActivity.this)
                                    .setTitle("Error")
                                    .setMessage("An error occurred when attempting to login.\n\n" +
                                            "Please try again.")
                                    .setIcon(android.R.drawable.ic_dialog_alert)
                                    .setCancelable(true)
                                    .show();
                            break;
                    }
                }
                else
                {
                    //TODO: only for debugging
                    new AlertDialog.Builder(LoginActivity.this)
                            .setTitle("Error")
                            .setMessage("An error occurred when attempting to login.\n\n" +
                                    "Please try again.")
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setCancelable(true)
                            .show();
                }
                mAuthTask = null;
            }
        }

        @Override
        protected void onCancelled() {
            mAuthTask = null;
            showProgress(false);
        }
    }
}

