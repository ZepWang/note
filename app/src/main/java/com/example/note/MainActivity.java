package com.example.note;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;

public class MainActivity extends AppCompatActivity {

    NoteModel DBMD;
    TextView balance;
    EditText Date, Item, Price;
    Button Add, Sub;
    TextView history;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        DBMD = new NoteModel(this);
        balance = (TextView) findViewById(R.id.balance);
        Date = (EditText) findViewById(R.id.Date);
        Price = (EditText) findViewById(R.id.Price);
        Item = (EditText) findViewById(R.id.Item);
        Add = (Button) findViewById(R.id.Add);
        Sub = (Button) findViewById(R.id.Sub);
        history = (TextView) findViewById(R.id.history);
        addHistory();
        retHistory();
    }

    public void addHistory(){
        Add.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = Double.parseDouble(Price.getText().toString());
                        boolean result = DBMD.createHistory(Item.getText().toString(), Date.getText().toString(), price);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        retHistory();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");

                        MainActivity.this.Item.setText("");
                    }
                }
        );

        Sub.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        double price = -1 * Double.parseDouble(Price.getText().toString());
                        boolean result = DBMD.createHistory(Item.getText().toString(), Date.getText().toString(), price);
                        if (result)
                            Toast.makeText(MainActivity.this, "Successfully Created", Toast.LENGTH_LONG).show();
                        else
                            Toast.makeText(MainActivity.this, "Failed to Create", Toast.LENGTH_LONG).show();
                        retHistory();
                        MainActivity.this.Date.setText("");
                        MainActivity.this.Price.setText("");

                        MainActivity.this.Item.setText("");
                    }
                }
        );
    }

    public void retHistory(){
        Cursor result = DBMD.pullData();
        StringBuffer str = new StringBuffer();
        Double balance = 0.0;

        while(result.moveToNext()){
            String priceString = result.getString(3);
            double price = Double.parseDouble(result.getString(3));
            balance += price;

            if (price < 0) {
                str.append("Spent $");
                priceString = priceString.substring(1);




            }
            else {
                str.append("Added $");
            }
            str.append(priceString + " on " + result.getString(2)
                    + " for " + result.getString(1) + "\n");
        }
        MainActivity.this.balance.setText("Current Balance: $" + Double.toString(balance));
        MainActivity.this.history.setText(str);
    }
}