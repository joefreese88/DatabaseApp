package net.androidbootcamp.databaseapp;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    // declare variables
    Button add_customer;
    Button delete_customer;
    Button update_customer;
    Button view_customers;

    DatabaseHelper myDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDB = new DatabaseHelper(this);    // create new database
        add_customer = (Button) findViewById(R.id.button_addData);
        delete_customer = (Button) findViewById(R.id.button_delete_cus);
        update_customer = (Button) findViewById(R.id.button_update);
        view_customers = (Button) findViewById(R.id.button_view_all);

        // go to the page to add customers
        add_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                 Intent intent = new Intent(MainActivity.this, AddActivity.class);
                 startActivity(intent);
            }
        });

        // go to the page to delete customers
        delete_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               Intent intent = new Intent(MainActivity.this, DeleteActivity.class);
               startActivity(intent);
            }
        });

        // go to the page to update customers
        update_customer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, UpdateActivity.class);
                startActivity(intent);
            }
        });

        // Displays the customer data when the View button is clicked
        view_customers.setOnClickListener(new View.OnClickListener() {
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
                        buffer.append("Car Cost:     $"+ res.getString(4)+"\n\n");
                }

                showMessage("Car Dealer Database", buffer.toString());
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
