package com.example.mycalc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Button;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    TextView mResult;
    TextView mOperation;
    EditText mNumber;
    String mLastOperation = "=";
    Double mOperand = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mResult =(TextView) findViewById(R.id.result);
        mOperation = (TextView) findViewById(R.id.operation);
        mNumber = (EditText) findViewById(R.id.number);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState)
    {
        outState.putString("OPERATION", mLastOperation);
        if(mOperand !=null)
            outState.putDouble("OPERAND", mOperand);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState)
    {
        super.onRestoreInstanceState(savedInstanceState);
        mLastOperation = savedInstanceState.getString("OPERATION");
        mOperand = savedInstanceState.getDouble("OPERAND");
        mResult.setText(mOperand.toString());
        mOperation.setText(mLastOperation);
    }

    public void onNumberClick(View view)
    {

        Button button = (Button)view;
        mNumber.append(button.getText());

        if(mLastOperation.equals("=") && mOperand !=null){
            mOperand = null;
        }
    }

    public void onOperationClick(View view)
    {
        Button button = (Button)view;
        String number = this.mNumber.getText().toString();
        String op = button.getText().toString();
        if (number.length() > 0)
        {
            number = number.replace(',', '.');
            try
            {
                performOperation(Double.valueOf(number), op);
            }   catch (NumberFormatException ex)
            {
                this.mNumber.setText("");
            }

            mLastOperation = op;
            mOperation.setText(mLastOperation);
        }
    }

    private void performOperation(Double number, String operation)
    {
        if(mOperand == null)
        {
            mOperand = number;
        }
        else
        {
            if(mLastOperation.equals("="))
            {
                mLastOperation = operation;
            }
            switch (mLastOperation)
            {
                case "=":
                    mOperand = number;
                    break;
                case "/":
                    if (number == 0)
                    {
                        mOperand = 0.0;
                    }
                    else
                    {
                        mOperand /= number;
                    }
                    break;
                case "*":
                    mOperand *= number;
                    break;
                case "+":
                    mOperand += number;
                    break;
                case "-":
                    mOperand -= number;
                    break;
            }
        }
        mResult.setText(mOperand.toString().replace('.', ','));
        this.mNumber.setText("");
    }
}