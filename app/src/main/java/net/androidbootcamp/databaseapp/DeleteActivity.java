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

public class DeleteActivity extends AppCompatActivity {

    // declare variables
    DatabaseHelper myDB;
    Button deleteCustomer;
    Button viewData;
    Button returnMain;
    EditText customer_ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete);

        myDB = new DatabaseHelper(this);    // create database
        deleteCustomer = (Button) findViewById(R.id.button_delete_cus);
        viewData = (Button) findViewById(R.id.button_viewInDelete);
        returnMain = (Button) findViewById(R.id.button_ReturnFromDelete);
        customer_ID = (EditText) findViewById(R.id.edit_ID_txt);

        // view data in database when clicked
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

        // delete customer based on given ID when clicked
        deleteCustomer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Integer deletedRows = myDB.deleteData(customer_ID.getText().toString());
                if (deletedRows > 0)
                    Toast.makeText(DeleteActivity.this, "Data Deleted", Toast.LENGTH_LONG).show();
                else
                    Toast.makeText(DeleteActivity.this, "Try again! Please enter an appropriate ID!", Toast.LENGTH_LONG).show();

            }
        });

        // return to main page when clicked
        returnMain.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DeleteActivity.this, MainActivity.class);
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
