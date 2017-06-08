package com.dolapobabs.justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.NumberFormat;
import java.text.StringCharacterIterator;

public class MainActivity extends AppCompatActivity {

    int quantity = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    //This method is called when the plus button is clicked,

    public void increment(View view) {
        if (quantity == 100){
            Toast.makeText(this, "You cannot have less than 1 cup of Coffee", Toast.LENGTH_LONG);
            return;
        }
        quantity = quantity+1;
        displayQuantity(quantity);
    }

    //This method is called when the minus button is clicked,

    public void decrement(View view) {
        if (quantity == 1){
            return;
        }
        quantity = quantity-1;
        displayQuantity(quantity);
    }

    //This method is called when the order button is clicked,
    public void submitOrder(View view) {
        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whippedcream_CheckBox);
        boolean haswhippedcream = whippedCreamCheckBox.isChecked();

        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_CheckBox);
        boolean haschocolate = chocolateCheckBox.isChecked();

        EditText namefield = (EditText)findViewById(R.id.name_field);
        String name =namefield.getText().toString();
        int price = calculatePrice(haswhippedcream, haschocolate);
        String priceMessage  = createOrderSummary(name,price, haswhippedcream, haschocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); //Only email apps should handle this
        intent.putExtra(Intent.EXTRA_TEXT, priceMessage);
        intent.putExtra(Intent.EXTRA_SUBJECT, "Just java Order for" + name);
        if (intent.resolveActivity(getPackageManager()) != null){
            startActivity(intent);
        }

    }
   /* Calculates the price of the order
   @param addwhippedcream id whether or not the user wants whipped cream topping
   @param addchocolate is whether or not the user wants chocolate topping
    */
    private int calculatePrice(boolean addwhippedcream, boolean addchocolate) {
        int basePrice = 5;
        if (addwhippedcream) {
            basePrice = basePrice + 1;
        }

        if (addchocolate){
            basePrice= basePrice + 2;
        }
        return quantity * basePrice;

    }

    private String createOrderSummary(String name, int price, boolean addwhippedcream, boolean addchocolate){
        String priceMessage  = "Name: " +  name ;
        priceMessage += "\nAdd for Whipped Cream?" + addwhippedcream ;
        priceMessage += "\nAdd for Chocolate?" + addchocolate;
        priceMessage += "\nQuantity:"  +  quantity;
        priceMessage += "\nTotal $" + price ;
        priceMessage += "\nThank You!!!";
        return priceMessage;

    }

    //This method displays the given quantity value on the screen
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }



}
