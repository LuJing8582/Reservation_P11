package sg.edu.rp.c346.reservation;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    EditText etName;
    EditText etTelephone;
    EditText etSize;
    EditText etDay;
    EditText etTime;
    CheckBox checkBox;
    Button btReserve;
    Button btReset;
    int TheYear, TheMonth, TheDay, TheHour, TheMin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        etName = findViewById(R.id.editTextName);
        etTelephone = findViewById(R.id.editTextTelephone);
        etSize = findViewById(R.id.editTextSize);
        checkBox = findViewById(R.id.checkBox);
        btReserve = findViewById(R.id.buttonReserve);
        btReset = findViewById(R.id.buttonReset);

        etDay = findViewById(R.id.editTextDate);
        etTime = findViewById(R.id.editTextTime);

        //CHallenge 1 set Date and Time to current when first open
        Calendar now = Calendar.getInstance();
        TheYear = now.get(Calendar.YEAR);
        TheMonth = now.get(Calendar.MONTH);
        TheDay = now.get(Calendar.DAY_OF_MONTH);
        TheHour = now.get(Calendar.HOUR);
        TheMin = now.get(Calendar.MINUTE);
        etDay.setText(TheDay + "/" + (TheMonth+1) + "/" + TheYear);
        etTime.setText(TheHour + ":" + TheMin);

        btReserve.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String isSmoke = "";
                if (checkBox.isChecked()) {
                    isSmoke = "smoking";
                }
                else {
                    isSmoke = "non-smoking";
                }

                /*Toast.makeText(MainActivity.this,
                        "Hi, " + etName.getText().toString() + ", you have booked a "
                                + etSize.getText().toString() + " person(s) "
                                + isSmoke + " table on "
                                + ". Your phone number is "
                                + etTelephone.getText().toString() + ".",
                        Toast.LENGTH_LONG).show();
                        */
                String msg = "New Reservation\n";
                msg += "Name: " + etName.getText().toString() +"\n";
                msg += "Smoking: " + isSmoke + "\n";
                msg += "Size: " + etSize.getText().toString() + "\n";
                msg += "Date: " + etDay.getText().toString() + "\n";
                msg += "Time: " + etTime.getText().toString();

                AlertDialog.Builder myBuilder = new AlertDialog.Builder(MainActivity.this);
                myBuilder.setTitle("Confirm Your Order");
                myBuilder.setMessage(msg);
                myBuilder.setCancelable(false);

                final String finalIsSmoke = isSmoke;
                myBuilder.setPositiveButton("Confirm", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Toast.makeText(MainActivity.this,
                                "Hi, " + etName.getText().toString() + ", you have booked a "
                                        + etSize.getText().toString() + " person(s) "
                                        + finalIsSmoke + " table on "
                                        +etDay.getText().toString() + " at "
                                        +etTime.getText().toString()
                                        + ". Your phone number is "
                                        + etTelephone.getText().toString() + ".",
                                Toast.LENGTH_LONG).show();
                    }
                });

                myBuilder.setNegativeButton("Cancel", null);
                AlertDialog myDialog = myBuilder.create();
                myDialog.show();
            }
        });
        btReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                etName.setText("");
                etTelephone.setText("");
                etSize.setText("");
                checkBox.setChecked(false);

                //Challenge 3: Set to current date and time initially
                Calendar now = Calendar.getInstance();
                TheYear = now.get(Calendar.YEAR);
                TheMonth = now.get(Calendar.MONTH);
                TheDay = now.get(Calendar.DAY_OF_MONTH);
                TheHour = now.get(Calendar.HOUR);
                TheMin = now.get(Calendar.MINUTE);
                etDay.setText(TheDay + "/" + (TheMonth+1) + "/" + TheYear);
                etTime.setText(TheHour + ":" + TheMin);
            }
        });

        etDay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener myDateListener = new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                        TheYear = year;
                        TheMonth = monthOfYear;
                        TheDay = dayOfMonth;

                        etDay.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                    }
                };

                //create date picker dialog
                /*Calendar now = Calendar.getInstance();
                int year = now.get(Calendar.YEAR);
                int month = now.get(Calendar.MONTH);
                int day = now.get(Calendar.DAY_OF_MONTH);*/

                DatePickerDialog myDateDialog = new DatePickerDialog(MainActivity.this,
                        myDateListener, TheYear,TheMonth,TheDay);
                myDateDialog.show();
            }
        });

        etTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //create the listener to set the time
                TimePickerDialog.OnTimeSetListener myTimeListener = new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        TheHour = hourOfDay;
                        TheMin = minute;

                        etTime.setText(hourOfDay + ":" + minute);
                    }
                };

                //create the time picker Dialog
                /*Calendar now = Calendar.getInstance();
                int hour = now.get(Calendar.HOUR);
                int minute = now.get(Calendar.MINUTE);*/
                TimePickerDialog myTimeDialog = new TimePickerDialog(MainActivity.this,
                        myTimeListener, TheHour, TheMin, true);
                myTimeDialog.show();
            }
        });
    }

    //Challenge 4 save with shared preferences
    @Override
    protected void onPause() {
        super.onPause();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor edit = prefs.edit();
        edit.putString("name", etName.getText().toString());
        edit.putString("tel", etTelephone.getText().toString());
        edit.putString("size", etSize.getText().toString());
        edit.putBoolean("smoking", checkBox.isChecked());
        edit.putString("date", etDay.getText().toString());
        edit.putString("time", etTime.getText().toString());
        edit.commit();
    }

    @Override
    protected void onResume() {
        super.onResume();
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        etName.setText(prefs.getString("name", ""));
        etTelephone.setText(prefs.getString("tel", ""));
        etSize.setText(prefs.getString("size", ""));
        checkBox.setChecked(prefs.getBoolean("smoking", false));
        etDay.setText(prefs.getString("date", ""));
        etTime.setText(prefs.getString("time", ""));
    }
}