package com.example.laura.distinctnumbersapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.app.AlertDialog;

import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    private static boolean isDistinct(int iteration, int[] array) {
        boolean isDistinct = true;

        for (int j = iteration + 1; j < array.length; j++) {
            if (array[iteration] == array[j]) {
                isDistinct = false;
            }
        }

        return isDistinct;
    }

    private static int countDistinctNumbersInArray(int[] array) {
        int counter = 0;

        // Loop through numbers and increase counter if they are unique
        for (int i = 0; i < array.length; i++) {
            if (isDistinct(i, array)) {
                counter++;
            }
        }

        return counter;
    }

    private static Integer[] createDistinctNumbersArray(int[] array) {
        Integer[] distinctNumbersArray = new Integer[array.length];

        // Loop through numbers and check if they are distinct
        // If distinct, save number at position i to new array
        // If not distinct, new array value at position i defaults to null
        for (int i = 0; i < array.length; i++) {
            if (isDistinct(i, array)) {
                distinctNumbersArray[i] = array[i];
            }
        }

        return distinctNumbersArray;
    }

    private static String getDistinctNumbers(int[] numbersArray) {
        String result = "";

        for (Integer num : createDistinctNumbersArray(numbersArray)) {

            // Null indicates duplicate (non-distinct) number and is ignored
            if (num != null)
                result += num + " ";
        }

        return result;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button showDistinctBtn = (Button) findViewById(R.id.showDistinctBtn);
        final EditText numbersInput = (EditText) findViewById(R.id.numbersInput);
        final TextView distinctNumbers = (TextView) findViewById(R.id.distinctNumbers);

        showDistinctBtn.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {

                // Get string from user input field
                String numbersString = numbersInput.getText().toString();

                if (numbersString.length() > 0) {
                    String[] array = numbersString.split(" ");

                    if (array.length > 0 && array.length <= 10) {

                        int[] numArray = new int[array.length];

                        // Parse and display results only if string is numeric with spaces

                        for (int i = 0; i < array.length; i++) {
                            if (array[i].matches("-?[0-9 ]+")) {
                                numArray[i] = Integer.parseInt(array[i]);
                            }
                            // If not numeric, display error dialog and reset input field and result text
                            else {
                                AlertDialog.Builder dlgNumericAlert = new AlertDialog.Builder(MainActivity.this);

                                dlgNumericAlert.setMessage("You input something other than numbers");
                                dlgNumericAlert.setTitle("Error ...");
                                dlgNumericAlert.setPositiveButton("RETRY", null);
                                dlgNumericAlert.setCancelable(false);
                                dlgNumericAlert.create().show();

                                numbersInput.setText("");
                                distinctNumbers.setText("");

                                break;
                            }
                        }

                        Arrays.sort(numArray);

                        distinctNumbers.setText(countDistinctNumbersInArray(numArray) + " distinct numbers." + "\n\nThe distinct numbers are: " + "\n" + getDistinctNumbers(numArray));
                    } else if (array.length > 10) {
                        AlertDialog.Builder dlgAlert = new AlertDialog.Builder(MainActivity.this);

                        dlgAlert.setMessage("You input more than 10 numbers!");
                        dlgAlert.setTitle("Error ...");
                        dlgAlert.setPositiveButton("RETRY", null);
                        dlgAlert.setCancelable(false);
                        dlgAlert.create().show();

                        numbersInput.setText("");
                        distinctNumbers.setText("");
                    }
                }

                else {
                    AlertDialog.Builder dlgNoInputAlert = new AlertDialog.Builder(MainActivity.this);

                    dlgNoInputAlert.setMessage("You didn\'t input any numbers!");
                    dlgNoInputAlert.setTitle("Error ...");
                    dlgNoInputAlert.setPositiveButton("RETRY", null);
                    dlgNoInputAlert.setCancelable(false);
                    dlgNoInputAlert.create().show();

                    numbersInput.setText("");
                    distinctNumbers.setText("");
                }


            }
        });

    }
}
