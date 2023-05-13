package com.example.project2;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CheckoutActivity extends AppCompatActivity {

    private EditText cardNumberField;
    private EditText expiryDateField;
    private EditText cvvField;
    private Button payButton;

    Button returnButton;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        cardNumberField = findViewById(R.id.cardNumberField);
        expiryDateField = findViewById(R.id.expiryDateField);
        cvvField = findViewById(R.id.cvvField);
        payButton = findViewById(R.id.payment);

        payButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view){
                if(validateInput()){
                    processPayment();
                    Intent intent = new Intent(CheckoutActivity.this, OrderActivity.class);
                    startActivity(intent);
                }else{
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast toast = Toast.makeText(CheckoutActivity.this, "Unable to complete payment", Toast.LENGTH_SHORT);
                            toast.show();
                        }
                    });
                }
            }
        });

        returnButton = findViewById(R.id.return_button);


        returnButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private boolean validateInput() {
        String cardNumber = cardNumberField.getText().toString();
        String expiryDate = expiryDateField.getText().toString();
        String cvv = cvvField.getText().toString();

        if (cardNumber.isEmpty() || cardNumber.length() != 16) {
            Toast.makeText(this, "Please enter a valid 16 digit card number.", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (expiryDate.isEmpty() || !expiryDate.matches("(0[1-9]|1[0-2])/\\d{2}")) {
            Toast.makeText(this, "Please enter a valid expiry date (MM/YY).", Toast.LENGTH_SHORT).show();
            return false;
        }

        if (cvv.isEmpty() || cvv.length() != 3) {
            Toast.makeText(this, "Please enter a valid 3 digit CVV.", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private void processPayment() {
        if (validateInput()) {
            Toast.makeText(this, "Payment successful!", Toast.LENGTH_SHORT).show();
            // Proceed with next steps
        } else {
            Toast.makeText(this, "Payment failed. Please check your information and try again.", Toast.LENGTH_SHORT).show();
        }
    }

}
