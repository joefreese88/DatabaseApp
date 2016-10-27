package net.androidbootcamp.databaseapp;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

public class UpdateActivity extends AppCompatActivity {

    // Declare variables
    DatabaseHelper myDB;
    Button update;
    Button viewData;
    Button returnMain;
    EditText editID;
    EditText editFirstName;
    EditText editLastName;
    EditText editCarMAke;
    EditText editCarCost;
    TextView check;
    String NA = "N/A";
    String cusFirst = "";
    String cusLast = "";
    String carMake = "";
    int carCost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        myDB = new DatabaseHelper(this);    // create database
        update = (Button) findViewById(R.id.button_updateInUpdate);
        viewData = (Button) findViewById(R.id.button_viewInUpdate);
        returnMain = (Button) findViewById(R.id.button_returnFromUpdate);
        editID = (EditText) findViewById(R.id.edit_enterID_txt);
        editFirstName = (EditText) findViewById(R.id.edit_UpdateFirst_txt);
        editLastName = (EditText) findViewById(R.id.edit_UpdateLast_txt);
        editCarMAke = (EditText) findViewById(R.id.edit_UpdateMake_txt);
        editCarCost = (EditText) findViewById(R.id.edit_UpdateCost_txt);
        check = (TextView) findViewById(R.id.textView_viewInUpdate);

        // view data in the customer database when clicked
        viewData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Cursor res = myDB.getAllData();
                if (res.getCount() == 0){
                    // show message
                    showMessage("Error", "Nothing found");
                    return;
                }

                StringBuffer buffer = new StringBuffer();
                while (res.moveToNext()){
                    buffer.append("ID:  "+ res.getString(0)+"\n");
                    buffer.append("First Name:  "+ res.getString(1)+"\n");
                    buffer.append("Last Name:  "+ res.getString(2)+"\n");
                    buffer.append("Car Make:    "+ res.getString(3)+"\n");
                    if (res.getString(4).equals("0"))
                        buffer.append("Car Cost:    N/A"+"\n\n");
                    else
                        buffer.append("Car Cost:    $"+ res.getString(4)+"\n\n");
                }

                showMessage("Car Dealer Database", buffer.toString());
            }
        });

        // update information for customer when clicked
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // check if the edit text widget is blank. If so then it will initialize it to zero
                if (editCarCost.getText().toString().isEmpty()) {
                    carCost = 0;
                }
                else {
                    carCost = Integer.parseInt(editCarCost.getText().toString());
                }

                // if any of the other edit text boxes are empty, they will be initialized to "N/A"
                if (editFirstName.getText().toString().isEmpty()) {
                    cusFirst = NA;
                } else {
                    cusFirst = editFirstName.getText().toString();
                }
                if (editLastName.getText().toString().isEmpty()) {
                    cusLast = NA;
                } else {
                    cusLast = editLastName.getText().toString();
                }
                if (editCarMAke.getText().toString().isEmpty()) {
                    carMake = NA;
                } else {
                    carMake = editCarMAke.getText().toString();
                }


                boolean isUpdated = myDB.updateData(editID.getText().toString(),
                        cusFirst, cusLast, carMake, carCost);

                if (isUpdated == true)
                    Toast.makeText(UpdateActivity.this, "Data Updated", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(UpdateActivity.this, "Data Was Not Updated", Toast.LENGTH_LONG).show();
            }
        });

        // return to main page when clicked
        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(UpdateActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });
    }

    // A function to display a message if nothing is in the database. Otherwise displays the title of the database
    public void showMessage(String title, String Message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true);
        builder.setTitle(title);
        builder.setMessage(Message);
        builder.show();
    }

}
