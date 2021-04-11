package com.example.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TextView result;
    TextView operation;
    EditText number;
    String lastOperation = "=";
    Double operand = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result =(TextView) findViewById(R.id.result);
        operation = (TextView) findViewById(R.id.operation);
        number = (EditText) findViewById(R.id.number);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString("OPERATION", lastOperation);
        if(operand!=null)
            outState.putDouble("OPERAND", operand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        lastOperation = savedInstanceState.getString("OPERATION");
        operand= savedInstanceState.getDouble("OPERAND");
        result.setText(operand.toString());
        operation.setText(lastOperation);
    }

    public void onNumberClick(View view)
    {

        Button button = (Button)view;
        number.append(button.getText());

        if(lastOperation.equals("=") && operand!=null){
            operand = null;
        }
    }

    public void onOperationClick(View view)
    {
        Button button = (Button)view;
        String number = this.number.getText().toString();
        String op = button.getText().toString();
        if (number.length() > 0)
        {
            number = number.replace(',', '.');
            try
            {
                performOperation(Double.valueOf(number), op);
            }   catch (NumberFormatException ex)
            {
                this.number.setText("");
            }

            lastOperation = op;
            operation.setText(lastOperation);
        }
    }

    private void performOperation(Double number, String operation)
    {
        if(operand == null)
        {
            operand = number;
        }
        else
        {
            if(lastOperation.equals("="))
            {
                lastOperation = operation;
            }
            switch (lastOperation)
            {
                case "=":
                    operand = number;
                    break;
                case "/":
                    if (number == 0)
                    {
                        operand = 0.0;
                    }
                    else
                    {
                        operand /= number;
                    }
                    break;
                case "*":
                    operand *= number;
                    break;
                case "+":
                    operand += number;
                    break;
                case "-":
                    operand -= number;
                    break;
            }
        }
        result.setText(operand.toString().replace('.', ','));
        this.number.setText("");
    }
}