package sdgkteam10.rent_it;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.view.View;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.firebase.client.Firebase;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

//ToDo: keep images even after phone has been rotated
//Todo: deselect a text field
//ToDo: deposit Required
//ToDo: Make money field only 2 decimal length

public class CreateListingActivity extends AppCompatActivity {
    private static final int IMAGE_CAMERA = 1;
    private static final int IMAGE_PICK = 2;
    private View parentLayout;
    private EditText itemNameField_CL;
    private EditText itemPriceField_CL;
    private EditText itemDescriptionField_CL;
    private EditText minRentDuration_CL;
    private EditText amountOfDeposit_CL;
    private Button finishButton_CL;
    private Button addPhotosButton_CL;

    private Spinner itemPriceSpinner_CL;
    private Spinner categorySpinner_CL;
    private Spinner minRentSpinner_CL;

    private final ArrayList<Bitmap> bitmapArray = new ArrayList<>();
    private ImageView ivImage_CL;

    private String userID;
    private Database db;

    private CheckBox depositYes;
    private CheckBox depositNo;
    private CheckBox priceNegtble;
    private CheckBox itemBuyout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_listing);

        //initialize database stuff
        Database.setContext(this);
        db = Database.getInstance();
        userID = db.getLoggedInUser();

        //gather ids of widgets
        getIds();

        //using eric's spinner method to change text color of drop down box
        createStateSpinner();

        //when the Add Photos Button is pressed call selectImages method
        addPhotosButton_CL.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                selectImages();
            }
        });

        //when the finish button is pressed call check form method
        finishButton_CL.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                checkForm();
            }
        });

        // when the deposit is required (Yes! check box)
        depositYes.setOnClickListener(new CheckBox.OnClickListener() {
            public void onClick(View v) {
                if(depositNo.isChecked()){
                    depositNo.toggle();
                }
                depositNo.setError(null);
            }
        });

        // when the deposit is not required (No. check box)
        depositNo.setOnClickListener(new CheckBox.OnClickListener() {
            public void onClick(View v) {
                if(depositYes.isChecked()){
                    depositYes.toggle();
                }
                depositNo.setError(null);
            }
        });
    }


    /*
     * gather all widget ids that will be used in this file
     */
    private void getIds() {

        itemNameField_CL = (EditText) findViewById(R.id.itemNameField_CL);
        itemPriceField_CL = (EditText) findViewById(R.id.itemPriceField_CL);
        itemDescriptionField_CL = (EditText) findViewById(R.id.itemDescriptionField_CL);
        minRentDuration_CL = (EditText) findViewById(R.id.minRentDuration_CL);
        amountOfDeposit_CL = (EditText) findViewById(R.id.amountOfDeposit_CL);

        addPhotosButton_CL = (Button) findViewById(R.id.addPhotosButton_CL);
        finishButton_CL = (Button) findViewById(R.id.finishButton_CL);

        itemPriceSpinner_CL = (Spinner) findViewById(R.id.itemPriceSpinner_CL);
        categorySpinner_CL = (Spinner) findViewById(R.id.categorySpinner_CL);
        minRentSpinner_CL = (Spinner) findViewById(R.id.minRentSpinner_CL);

        ivImage_CL = (ImageView) findViewById(R.id.ivImage_CL);
        parentLayout = findViewById(R.id.ScrollView01);

        depositYes = (CheckBox) findViewById(R.id.depositYes);
        depositNo = (CheckBox) findViewById(R.id.depositNo);
        priceNegtble = (CheckBox) findViewById(R.id.priceNegtble);
        itemBuyout = (CheckBox) findViewById(R.id.itemBuyout);

    }


    /*
     * when add photos button is pressed display a dialog box asking
     * the user to select an image from the camera or gallery.
     * call the respective intent accordingly
     */
    private void selectImages() {

        final CharSequence[] items = {"From Camera", "From Gallery", "Exit"};

        //create a dialog box and show choices in items array
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateListingActivity.this);
        builder.setTitle(R.string.selectImageLoc);

        //define what happens when a choice is selected
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int index) {

                //if from camera is selected open the camera
                if (items[index].equals("From Camera")) {
                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(intent, IMAGE_CAMERA);
                }

                //if from gallery is selected open the gallery
                else if (items[index].equals("From Gallery")) {
                    Intent intent = new Intent(Intent.ACTION_PICK,
                            MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                    //can only select multiple images with api 18+
                    // intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);

                    intent.setType("image/*");
                    startActivityForResult(intent, IMAGE_PICK);
                }

                //if exit is selected close the dialog box
                else if (items[index].equals("Exit")) {
                    dialog.dismiss();
                }
            }
        });

        builder.show();
    }


    /*
     * Handles data returned from camera and gallery intent
     * saves images into bitmap array list
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        //check if operation was successful before proceeding
        if (resultCode == Activity.RESULT_OK) {
            //handle data from camera
            if (requestCode == IMAGE_CAMERA) {
                //getting data from camera and cast into image
                Bitmap bmp = (Bitmap) data.getExtras().get("data");

                //add into bitmap array list
                bitmapArray.add(bmp);

                //set preview image
                ivImage_CL.setImageBitmap(bmp);
            }

            //handle data from gallery
            else if (requestCode == IMAGE_PICK) {
                Uri selectedImageUri = data.getData();
                String[] projection = {MediaStore.MediaColumns.DATA};

                CursorLoader cursorLoader = new CursorLoader(this, selectedImageUri, projection, null, null,
                        null);
                Cursor cursor = cursorLoader.loadInBackground();

                int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
                cursor.moveToFirst();

                String selectedImagePath = cursor.getString(column_index);

                Bitmap bm;
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;

                BitmapFactory.decodeFile(selectedImagePath, options);
                final int REQUIRED_SIZE = 200;
                int scale = 1;

                while (options.outWidth / scale / 2 >= REQUIRED_SIZE
                        && options.outHeight / scale / 2 >= REQUIRED_SIZE)
                    scale *= 2;

                options.inSampleSize = scale;
                options.inJustDecodeBounds = false;

                bm = BitmapFactory.decodeFile(selectedImagePath, options);

                bitmapArray.add(bm);
                ivImage_CL.setImageBitmap(bm);
            }
        }
    }


    /*
     * will check to see if all input fields are completed
     * if they are not error messages are displayed accordingly
     * if all is good, call uploadInfo() to update fire base
     */
    private void checkForm() {
        boolean moveOn = true;

        //reset error messages
        itemNameField_CL.setError(null);
        itemPriceField_CL.setError(null);
        itemDescriptionField_CL.setError(null);
        minRentDuration_CL.setError(null);
        amountOfDeposit_CL.setError(null);
        depositYes.setError(null);

        //check all fields and set error message
        if (itemNameField_CL.getText().toString().trim().equals("")) {
            itemNameField_CL.setError("Item name is required!");
            moveOn = false;
        }

        if (itemPriceField_CL.getText().toString().trim().equals("")) {
            itemPriceField_CL.setError("Item price is required!");
            moveOn = false;
        }

        if (itemDescriptionField_CL.getText().toString().trim().equals("")) {
            itemDescriptionField_CL.setError("Item description is required!");
            moveOn = false;
        }

        if (minRentDuration_CL.getText().toString().trim().equals("")) {
            minRentDuration_CL.setError("Minimum rent duration is required!");
            moveOn = false;
        }

        // in the case Yes! was selected but no deposit amount is entered
        if ((depositYes.isChecked()) && (amountOfDeposit_CL.getText().toString().trim().equals(""))) {
            amountOfDeposit_CL.setError("Amount of the deposit is required since 'Yes!' was selected.");
            moveOn = false;
        }

        // in the case No was selected and deposit amount is entered
        if ((depositNo.isChecked()) && !(amountOfDeposit_CL.getText().toString().trim().equals(""))) {
            amountOfDeposit_CL.setError("Amount of the deposit is entered even though 'No.' was selected.");
            moveOn = false;
        }

        // checking if Yes or No check boxes are clicked, if non is clicked then return false
        if ((!depositYes.isChecked()) &&  (!depositNo.isChecked())) {
            depositNo.setError("Please specify if the deposit is required or not.");
            moveOn = false;
        }

        if (itemPriceSpinner_CL.getSelectedItem().toString().trim().equals("Select")) {
            ((TextView) itemPriceSpinner_CL.getSelectedView()).setError("");
            moveOn = false;
        }

        if (categorySpinner_CL.getSelectedItem().toString().trim().equals("Select")) {
            ((TextView) categorySpinner_CL.getSelectedView()).setError("");
            moveOn = false;
        }

        if (minRentSpinner_CL.getSelectedItem().toString().trim().equals("Select")) {
            ((TextView) minRentSpinner_CL.getSelectedView()).setError("");
            moveOn = false;
        }

        if (bitmapArray.isEmpty()) {
            Snackbar snackbar = Snackbar
                    .make(parentLayout, "Error: Please add some Photos", Snackbar.LENGTH_LONG);
            snackbar.show();
            //EmmoveOn = false;
        }

        //if all fields are set than upload to fire base
        if (moveOn) {
            uploadInfo();
        }
    }


    /*
     * convert bitmap array to base 64 string
     * gather all info from text fields and drop down boxes
     * upload all info to fire base
     */
    private void uploadInfo() {
        bitmapArray.trimToSize();
        String[] stringImgArray = new String[bitmapArray.size()];

        //convert bitmap images to base 64 string
        for (int i = 0; i < bitmapArray.size(); i++) {
            Bitmap bmp = bitmapArray.get(i);

            //stream to write compressed data
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            byte[] byteArray = stream.toByteArray();
            stringImgArray[i] = Base64.encodeToString(byteArray, Base64.DEFAULT);
        }

        Item item = new Item();
        item.setItemName(itemNameField_CL.getText().toString().trim().toLowerCase());
        item.setPrice(itemPriceField_CL.getText().toString().trim());
        item.setPriceRate(itemPriceSpinner_CL.getSelectedItem().toString().trim());
        item.setDescription(itemDescriptionField_CL.getText().toString().trim());
        item.setImageArray(stringImgArray);
        item.setCategory(categorySpinner_CL.getSelectedItem().toString().trim());
        item.setItemUserID(userID);

        Log.d("createlisting", "the item name to be posted is " + item.getItemName());
        //Log.d("createlisting", "the first element in string is " + stringImgArray[0]);


        //Firebase locPath = ref.child("users").child(userID).child("items");
        Firebase locPath = db.getRef().child("items");
        //locPath.child(item.getItemName()).setValue(item);
        locPath.push().setValue(item);


        //dialog box: item was uploaded
        AlertDialog.Builder builder = new AlertDialog.Builder(CreateListingActivity.this);
        builder.setTitle("Your Item has been Posted!");
        builder.setCancelable(false);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int id) {
                //done with this activity
                finish();
            }

        });

        builder.show();
    }


    /*
     * Modified Eric's Spinner method from registration
     * Changes the text color of a drop down box
     */
    private void createStateSpinner() {

        ArrayAdapter<CharSequence> priceAdapter;
        ArrayAdapter<CharSequence> categoryAdapter;
        ArrayAdapter<CharSequence> minRent;

        //adds adapter to specified spinner
        priceAdapter = ArrayAdapter.createFromResource(this, R.array.rentLength_array,
                R.layout.special_spinner_cl);
        categoryAdapter = ArrayAdapter.createFromResource(this, R.array.itemCategories_array,
                R.layout.special_spinner_cl);
        minRent = ArrayAdapter.createFromResource(this, R.array.minRentDur_array,
                R.layout.special_spinner_cl);

        //changes the layout
        priceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        minRent.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        itemPriceSpinner_CL.setAdapter(priceAdapter);
        categorySpinner_CL.setAdapter(categoryAdapter);
        minRentSpinner_CL.setAdapter(minRent);

        itemPriceSpinner_CL.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }
}



/* junk

   //sample encode to bitmap
    Bitmap bmp = (Bitmap) data.getExtras().get("data");
    ByteArrayOutputStream stream = new ByteArrayOutputStream();
    bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
    bmp.recycle();
    byte[] byteArray = stream.toByteArray();
    String imageFile = Base64.encodeToString(byteArray, Base64.DEFAULT);


   //sample decode bitmap
   byte[] imageAsBytes = Base64.decode(imageFile, Base64.DEFAULT);
   Bitmap bmp = BitmapFactory.decodeByteArray(imageAsBytes, 0, imageAsBytes.length);


   //sample array list
   ArrayList<Bitmap> bitmapArray = new ArrayList<Bitmap>();
   bitmapArray.add(myBitMap); // Add a bitmap
   bitmapArray.get(0); // Get first bitmap

   //broken add image from gallery
                 Uri selectedImage = data.getData();
                String[] filePathColumn = {MediaStore.Images.Media.DATA};

                Cursor cursor = getContentResolver().query(selectedImage, filePathColumn,null,null,null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String filePath = cursor.getString(columnIndex);
                cursor.close();

                Bitmap bmp = BitmapFactory.decodeFile(filePath);



    //item parameter fields
    itemNameField_CL.getText().toString().trim()
        itemPriceField_CL.getText().toString().trim()
        itemDescriptionField_CL.getText().toString().trim()

        itemPriceSpinner_CL.getSelectedItem().toString().trim()
        categorySpinner_CL.getSelectedItem().toString().trim()

*/