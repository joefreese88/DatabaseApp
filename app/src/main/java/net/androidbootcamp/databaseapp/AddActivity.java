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
import android.widget.Toast;

public class AddActivity extends AppCompatActivity {

    // declare variables
    DatabaseHelper myDB;
    Button addData;
    Button viewData;
    Button ReturnMain;
    EditText first_name;
    EditText last_name;
    EditText car_make;
    EditText car_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add);

        myDB = new DatabaseHelper(this);    // create database
        addData = (Button) findViewById(R.id.button_addData);
        viewData = (Button) findViewById(R.id.button_viewInADD);
        ReturnMain = (Button) findViewById(R.id.button_returnFromAdd);
        first_name = (EditText) findViewById(R.id.edit_First_txt);
        last_name = (EditText) findViewById(R.id.edit_Last_txt);
        car_make = (EditText) findViewById(R.id.edit_Make_txt);
        car_cost = (EditText) findViewById(R.id.edit_Cost_txt);


        // add a new customer to the database when clicked
        addData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                        String not_Available = "N/A";
                        String firstName = "";
                        String lastName = "";
                        String carMake = "";
                        int carCost;

                        // check if the edit text widget is blank. If so then it will initialize it to zero
                        if (car_cost.getText().toString().isEmpty()) {
                            carCost = 0;
                        }
                        else {
                            carCost = Integer.parseInt(car_cost.getText().toString());
                        }

                        // if any of the other edit text boxes are empty, they will be initialized to "N/A"
                        if (first_name.getText().toString().isEmpty()) {
                            firstName = not_Available;
                        } else {
                            firstName = first_name.getText().toString();
                        }
                        if (last_name.getText().toString().isEmpty()) {
                            lastName = not_Available;
                        } else {
                            lastName = last_name.getText().toString();
                        }
                        if (car_make.getText().toString().isEmpty()) {
                            carMake = not_Available;
                        } else {
                            carMake = car_make.getText().toString();
                        }

                        boolean isInserted = myDB.insertData(firstName, lastName, carMake, carCost);
                        //   Toast.makeText(MainActivity.this, "???????", Toast.LENGTH_LONG).show();
                        if (isInserted == true)
                            Toast.makeText(AddActivity.this, "Data Added", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(AddActivity.this, "Data Was Not Added", Toast.LENGTH_LONG).show();
                    }
        });

        // view the data in the customer database when clicked
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

        // return to the main page when clicked
        ReturnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddActivity.this, MainActivity.class);
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
