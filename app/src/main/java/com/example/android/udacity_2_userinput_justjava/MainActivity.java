package com.example.android.udacity_2_userinput_justjava;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import java.text.NumberFormat;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final int COFFEE_PRICE = 5;

    private Button btnOrder, btnDec, btnInc;
    private TextView quantityView;
    private int quantity;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnOrder = (Button) findViewById(R.id.order_button);
        btnDec = (Button) findViewById(R.id.dec_button);
        btnInc = (Button) findViewById(R.id.inc_button);
        btnOrder.setOnClickListener(this);
        btnDec.setOnClickListener(this);
        btnInc.setOnClickListener(this);
        quantityView = (TextView) findViewById(R.id.quantity_text);
        quantity = Integer.parseInt(quantityView.getText().toString());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.order_button:
                submitOrder();
                break;
            case R.id.dec_button:
                if (quantity > 0) quantity--;
                quantityView.setText(String.valueOf(quantity));
                break;
            case R.id.inc_button:
                if (quantity < 100) quantity++;
                quantityView.setText(String.valueOf(quantity));
                break;
        }
    }


    public void submitOrder() {
        EditText nameField = (EditText) findViewById(R.id.name_editText);
        String name = nameField.getText().toString();

        CheckBox whippedCreamCheckBox = (CheckBox) findViewById(R.id.whipped_cream_checkbox);
        boolean hasWhippedCream = whippedCreamCheckBox.isChecked();
        CheckBox chocolateCheckBox = (CheckBox) findViewById(R.id.chocolate_checkbox);
        boolean hasChocolate = chocolateCheckBox.isChecked();

        int price = calculatePrice(hasWhippedCream, hasChocolate);

        String message = createOrderSummary(name, price, hasWhippedCream, hasChocolate);

        Intent intent = new Intent(Intent.ACTION_SENDTO);
        intent.setData(Uri.parse("mailto:"));
        intent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.order_summary_email_subject, name));
        intent.putExtra(Intent.EXTRA_TEXT, message);

        startActivity(intent);

    }

    private int calculatePrice(boolean whippedCream, boolean chocolate) {
        int price = COFFEE_PRICE;

        if (whippedCream) {
            price++;
        }
        if (chocolate) {
            price+= 2;
        }

        return price * quantity;
    }

    private String createOrderSummary(String name, int price, boolean addWhippedCream, boolean addChocolate) {
        String priceMessage = getString(R.string.order_summary_name, name);
        priceMessage += "\n" + getString(R.string.order_summary_whipped_cream, addWhippedCream);
        priceMessage += "\n" + getString(R.string.order_summary_chocolate, addChocolate);
        priceMessage += "\n" + getString(R.string.order_summary_quantity, quantity);
        priceMessage += "\n" + getString(R.string.order_summary_price, NumberFormat.getCurrencyInstance().format(price));
        priceMessage += "\n" + getString(R.string.thank_you);
        return priceMessage;
    }
}