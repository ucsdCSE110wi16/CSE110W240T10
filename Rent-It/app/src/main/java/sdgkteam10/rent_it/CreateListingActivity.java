package sdgkteam10.rent_it;

import android.app.DatePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.view.View;


//To Do: add photos
//To Do: add error message if no drop down items are selected
//To Do: make interactive calendar to choose start and end date
//To Do: upload info to fire base



public class CreateListingActivity extends AppCompatActivity {

    //interactive calendar that lets user choose date
    private DatePickerDialog fromDatePickerDialog;
    private DatePickerDialog toDatePickerDialog;

    private EditText itemNameField_CL;
    private EditText itemPriceField_CL;
    private EditText itemDescriptionField_CL;
    private EditText startDateField_CL;
    private EditText endDateField_CL;
    private Button finishButton_CL;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        //gather ids of widgets
        getIds();

        //when the finish button is pressed call check form
        finishButton_CL.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v){
                checkForm();
            }
        });
    }


    /*
     * gather all widget ids that will be used in this file
     */
    private void getIds(){

        itemNameField_CL = (EditText)findViewById(R.id.itemNameField_CL);
        itemPriceField_CL = (EditText)findViewById(R.id.itemPriceField_CL);
        itemDescriptionField_CL = (EditText)findViewById(R.id.itemDescriptionField_CL);
        startDateField_CL = (EditText)findViewById(R.id.startDateField_CL);
        endDateField_CL = (EditText)findViewById(R.id.endDateField_CL);

        finishButton_CL = (Button)findViewById(R.id.finishButton_CL);
    }


    /*
     * will check to see if all required input fields are completed
     * if they are not error message are displayed
     */
    private void checkForm() {

        //reset error messages
        itemNameField_CL.setError(null);
        itemPriceField_CL.setError(null);
        itemDescriptionField_CL.setError(null);
        startDateField_CL.setError(null);
        endDateField_CL.setError(null);


        //check all fields and set error message
        if( itemNameField_CL.getText().toString().trim().equals(""))
        {
            itemNameField_CL.setError("Item name is required!" );
        }

        if( itemPriceField_CL.getText().toString().trim().equals(""))
        {
            itemPriceField_CL.setError("Item price is required!" );
        }

        if( itemDescriptionField_CL.getText().toString().trim().equals(""))
        {
            itemDescriptionField_CL.setError("Item description is required!" );
        }

        if( startDateField_CL.getText().toString().trim().equals(""))
        {
            startDateField_CL.setError("Start date is required!" );
        }

        if( endDateField_CL.getText().toString().trim().equals(""))
        {
            endDateField_CL.setError("End date is required!" );
        }
    }
}
