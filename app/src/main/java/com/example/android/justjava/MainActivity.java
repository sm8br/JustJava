package com.example.android.justjava;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * This app displays an order form to order coffee.
 */
public class MainActivity extends AppCompatActivity {

    int quantity = 2;
//    boolean hasWhippedCream;
//    boolean hasChocolate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void increment(View view) {
        if (quantity < 100) {
            quantity = quantity + 1;
            displayQuantity(quantity);
        } else {
            //toast message "You cannot order more then 100 coffees"
            Toast.makeText(this, "You cannot order more then 100 coffees", Toast.LENGTH_SHORT).show();
        }
    }

    public void decrement(View view) {
        if (quantity > 1) {
            quantity = quantity - 1;
            displayQuantity(quantity);
        } else {
            //return the toast message "You cannot order less then 1 coffee"
            Toast.makeText(this, "You cannot order less then 1 coffee", Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * This method is called when the order button is clicked.
     */
    public void submitOrder(View view) {

        boolean hasWhippedCream = whippedCreamChecked();
        boolean hasChocolate = chocolateChecked();
        int price = calculatePrice(hasWhippedCream, hasChocolate);
        String inputName = nameInput();

//        displayMessage(createOrderSummary(price, hasWhippedCream, hasChocolate, inputName));
        String subject = "JustJava order for " + inputName;
        composeEmail(subject,createOrderSummary(price, hasWhippedCream, hasChocolate, inputName));

    }

    public void composeEmail(String subject, String body) {
        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:")); // only email apps should handle this
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        intent.putExtra(Intent.EXTRA_TEXT, body);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }


    /**
     * Calculates the price of the order.
     *
     * @return the total price
     */
    private int calculatePrice(boolean hasWhippedCream, boolean hasChocolate) {
        // int price = quantity * pricePerCup;
        int toppings = 0;
        if (hasWhippedCream) {
            toppings = toppings + 1;
        }
        if (hasChocolate) {
            toppings = toppings + 2;
        }
        return quantity * (5 + toppings);
    }

    /**
     * Checks if the checkbox Whipped Cream is checked
     *
     * @return the value of the checkbox
     */
    private boolean whippedCreamChecked() {
        CheckBox whipped_cream_checkbox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        return whipped_cream_checkbox.isChecked();
    }

    private boolean chocolateChecked() {
        CheckBox chocolate_checkbox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        return chocolate_checkbox.isChecked();
    }

    /**
     * Fetches the text from the EditText View
     *
     * @return the name of the client
     */
    private String nameInput() {
        EditText inputName = (EditText) findViewById(R.id.inputName);
        return inputName.getText().toString();
    }

    /**
     * Concatenates the whole order message.
     *
     * @return the concatenated message
     */
    private String createOrderSummary(int price, boolean hasWhippedCream, boolean hasChocolate, String inputName) {


        String name = inputName;
        String message = "Name: " + name + "\n";
        message += "Add whipped cream? " + hasWhippedCream + "\n";
        message += "Add chocolate? " + hasChocolate + "\n";
        message += "Quantity: " + quantity + "\n";
        message += "Total: $" + price + "\n";
        message += "Thank you!";
        return message;
    }

    /**
     * This method displays the given quantity value on the screen.
     */
    private void displayQuantity(int number) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantity_text_view);
        quantityTextView.setText("" + number);
    }


    /**
     * This method displays the given text on the screen.
     */
    private void displayMessage(String message) {
        TextView orderSummaryTextView = (TextView) findViewById(R.id.order_summary_text_view);
        orderSummaryTextView.setText(message);
    }
}