package com.naizz99.mycalc;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    TextView txtResult;
    String lastInput = "";
    String txtDisplay = "";
    String inputNumber = "";
    ArrayList<String> operators = new ArrayList<>();
    ArrayList<Double> numbers = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtResult = findViewById(R.id.txtResult);

    }

    public void numberClick(View view){
        int number = Integer.parseInt((String)view.getTag());
        txtDisplay += number;

        inputNumber += number;
        txtResult.setText(txtDisplay);
    }

    public void actionHandler(View view){
        numbers.add(Double.parseDouble(inputNumber));
        inputNumber = "";
        String action = (String)view.getTag();
        char operator = '_';
        switch(action){
            case "add":
                operator = '+';
                break;
            case "sub":
                operator = '-';
                break;
            case "mul":
                operator = 'x';
                break;
            case "div":
                operator = '÷';
                break;
            default:
        }

        if((!lastInput.isEmpty()) && (lastInput.equals("add") || (lastInput.equals("sub")) || lastInput.equals("mul") || (lastInput.equals("div")))){
            operators.remove(-1);
            operators.add(action);
            txtDisplay = txtDisplay.substring(0, -1);
            txtDisplay += operator;
            txtResult.setText(txtDisplay);
        }else{
            operators.add(action);
            txtDisplay += operator;
            txtResult.setText(txtDisplay);
        }
    }

    public void calcResult(View view){

        if(inputNumber != ""){
            numbers.add(Double.parseDouble(inputNumber));
            inputNumber = "";
        }

        if(numbers.size() == operators.size()){
            operators.remove(operators.size() - 1);
        }

        double result = 0.0;
        int index = 0;

        for(double i : numbers){
            try{
                if (index == 0){
                    result = i;
                }else if(operators.get(index-1).equals("add")){
                    result += i;
                }else if(operators.get(index-1).equals("sub")){
                    result -= i;
                }else if(operators.get(index-1).equals("mul")){
                    result *= i;
                }else if(operators.get(index-1).equals("div")){
                    result /= i;
                }else{
                    System.out.println("Invalid Operator");
                }
            }catch(IndexOutOfBoundsException ex){
                System.out.println("Result : " + result);
            }catch (Exception ex){
                System.out.println("EX " + ex);
            }
            index++;
        }
        clearCal(view);
        txtResult.setText(String.valueOf(result));
        txtDisplay = String.valueOf(result);
        inputNumber = String.valueOf(result);
    }

    public void calcResult1(View view) {

        if (!inputNumber.isEmpty()) {
            numbers.add(Double.parseDouble(inputNumber));
            inputNumber = "";
        }

        if (numbers.size() == operators.size()) {
            operators.remove(operators.size() - 1);
        }

        ArrayList<Double> tempNumbers = new ArrayList<>(numbers);
        ArrayList<String> tempOperators = new ArrayList<>(operators);

        int index = 0;
        while (index < tempOperators.size()) {
            String operator = tempOperators.get(index);
            if (operator.equals("mul") || operator.equals("div")) {
                double num1 = tempNumbers.get(index);
                double num2 = tempNumbers.get(index + 1);
                double result = 0.0;

                if (operator.equals("mul")) {
                    result = num1 * num2;
                } else if (operator.equals("div")) {
                    result = num1 / num2;
                }

                tempNumbers.set(index, result);
                tempNumbers.remove(index + 1);
                tempOperators.remove(index);
            } else {
                index++;
            }
        }

        double result = tempNumbers.get(0);
        index = 1;

        for (String operator : tempOperators) {
            double operand = tempNumbers.get(index);

            if (operator.equals("add")) {
                result += operand;
            } else if (operator.equals("sub")) {
                result -= operand;
            } else {
                System.out.println("Invalid Operator");
            }
            index++;
        }

        clearCal(view);
        txtResult.setText(String.valueOf(result));
        txtDisplay = String.valueOf(result);
        inputNumber = String.valueOf(result);
    }

    public void clearCal(View view){
        txtResult.setText("");
        operators.clear();
        numbers.clear();

        txtDisplay = "";
        lastInput = "";
        inputNumber = "";
    }
}