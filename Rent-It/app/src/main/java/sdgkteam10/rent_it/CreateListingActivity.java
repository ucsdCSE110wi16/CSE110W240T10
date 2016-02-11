package sdgkteam10.rent_it;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.DatePickerDialog.OnDateSetListener;
import android.os.Bundle;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.DatePicker;
import android.widget.EditText;



//To Do: make interactive calendar to choose start and end date
//To Do: upload info to fire base
//To Do: add photos
//To Do: clean up UI




public class CreateListingActivity extends AppCompatActivity {

    private EditText startdateText;
    private EditText enddateText;

    //interactive calendar that lets user choose date
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

/*
        EditText startdateText = (EditText)findViewById(R.id.startdateText);
        EditText enddateText = (EditText)findViewById(R.id.enddateText);

        startdateText.setOnClickListener( new EditText.OnClickListener(){

            public void onClick(View v){


            }


        });
*/






    }
}
