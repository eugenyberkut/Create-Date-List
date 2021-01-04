package com.example.createdatelist;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import android.text.format.DateUtils;

import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.view.View.OnClickListener;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String LOG_TAG = "LOG";
    EditText editText;
    Button myButton1;
    Button myButton2;
    Button myButton3;

    CheckBox checkButton0;
    CheckBox checkButton1;
    CheckBox checkButton2;
    CheckBox checkButton3;
    CheckBox checkButton4;
    CheckBox checkButton5;
    CheckBox checkButton6;
    Calendar dateAndTime = Calendar.getInstance();

    ArrayList<String> listDates;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editText = findViewById(R.id.editText);
        myButton1 = (Button) findViewById(R.id.myButton1);
        myButton2 = (Button) findViewById(R.id.myButton2);
        myButton3 = (Button) findViewById(R.id.myButton3);
        checkButton0 = findViewById(R.id.checkButton0);
        checkButton1 = findViewById(R.id.checkButton1);
        checkButton2 = findViewById(R.id.checkButton2);
        checkButton3 = findViewById(R.id.checkButton3);
        checkButton4 = findViewById(R.id.checkButton4);
        checkButton5 = findViewById(R.id.checkButton5);
        checkButton6 = findViewById(R.id.checkButton6);
        setInitialDateTime1();
        setInitialDateTime2();
        OnClickListener oMyButton = new OnClickListener() {
            @Override
            public void onClick(View v) {
                //editText.setText("Вы использовали 1-й способ");
                setDate(v, d1);

            }
        };

        myButton1.setOnClickListener(oMyButton);

        myButton2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        //editText.setText("Вы использовали 2-й способ");
        setDate(v, d2);
    }


    public void clickMyBtn(View view) {
        //editText.setText("Вы использовали 3-й способ");
        listDates = generateList();
        System.out.println(listDates);
        if (!listDates.isEmpty()) {
            Intent myIntent = new Intent(view.getContext(), ShowListActivity.class);
            myIntent.putExtra("mylist", listDates);
            startActivityForResult(myIntent, 0);
        }
//        String fileName = "group"+ editText.getText()+".csv";
//        try {
//            // отрываем поток для записи
//            BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(
//                    openFileOutput(fileName, MODE_PRIVATE)));
//            // пишем данные
//            for (String listDate : listDates) {
//
//                bw.write(listDate+";");
//            }
//            // закрываем поток
//
//            bw.close();
//            Log.d(LOG_TAG, "Файл записан");
//
//
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
    }


    private ArrayList<String> generateList() {
        Set<Integer> daysOfWeek = createSetOfWeek();
        ArrayList<String> listDates = new ArrayList<>();
        String[] split1 = myButton1.getText().toString().split("/");
        String[] split2 = myButton2.getText().toString().split("/");


        // календарь на текущую дату
        Calendar c = new GregorianCalendar();
        Calendar c3 = new GregorianCalendar(2021, 3, 8);
        c3.add(Calendar.DAY_OF_YEAR, 1);

        // календарь на 21.12.2014
        Calendar c1 = new GregorianCalendar(Integer.parseInt(split1[2]), Integer.parseInt(split1[1]) - 1, Integer.parseInt(split1[0]));
        Calendar c2 = new GregorianCalendar(Integer.parseInt(split2[2]), Integer.parseInt(split2[1]) - 1, Integer.parseInt(split2[0]));
        if(daysOfWeek.isEmpty()) Toast.makeText(this, "Выберите дни недели занятий", Toast.LENGTH_SHORT).show();
        else
        if (c1.compareTo(c2) > 0)// выводим сообщение
            Toast.makeText(this, "Дата окончания занятий должна быть позже даты начала", Toast.LENGTH_SHORT).show();
        else
        if (c1.compareTo(c2) == 0)// выводим сообщение
            Toast.makeText(this, "Указаны одинаковые даты", Toast.LENGTH_SHORT).show();
        else {
            listDates.add(editText.getText().toString());
            do {
                if (daysOfWeek.contains(c1.get(Calendar.DAY_OF_WEEK)))
                    listDates.add(c1.get(Calendar.DAY_OF_MONTH) + "." + (c1.get(Calendar.MONTH) + 1) + "." + c1.get(Calendar.YEAR));
                // увеличиваем дату на 1 день
                c1.add(Calendar.DAY_OF_YEAR, 1);
            } while (!c1.equals(c2));
            if (daysOfWeek.contains(c1.get(Calendar.DAY_OF_WEEK)))
                listDates.add(c1.get(Calendar.DAY_OF_MONTH) + "." + (c1.get(Calendar.MONTH) + 1) + "." + c1.get(Calendar.YEAR));
            //System.out.println(c1.get(Calendar.DAY_OF_MONTH) + "." + (c1.get(Calendar.MONTH)+1) + "." + c1.get(Calendar.YEAR));
            //System.out.println(c1.getTime());
            //System.out.println(c1.get(Calendar.DAY_OF_WEEK));
            // System.out.println(date);
        }
        return listDates;
    }

    private Set<Integer> createSetOfWeek() {
        Set<Integer> daysOfWeek = new HashSet<>();
        if (checkButton0.isChecked()) daysOfWeek.add(2);
        if (checkButton1.isChecked()) daysOfWeek.add(3);
        if (checkButton2.isChecked()) daysOfWeek.add(4);
        if (checkButton3.isChecked()) daysOfWeek.add(5);
        if (checkButton4.isChecked()) daysOfWeek.add(6);
        if (checkButton5.isChecked()) daysOfWeek.add(7);
        if (checkButton6.isChecked()) daysOfWeek.add(1); //Sunday
        return daysOfWeek;
    }

    // установка начальных даты и времени
    private void setInitialDateTime1() {
        String[] sBad = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR).split("/");
        myButton1.setText(sBad[1] +"/"+sBad[0] +"/"+sBad[2]);
    }

    private void setInitialDateTime2() {
        String[] sBad = DateUtils.formatDateTime(this,
                dateAndTime.getTimeInMillis(),
                DateUtils.FORMAT_NUMERIC_DATE | DateUtils.FORMAT_SHOW_YEAR).split("/");

        myButton2.setText(sBad[1] +"/"+sBad[0] +"/"+sBad[2]);
    }

    // отображаем диалоговое окно для выбора даты
    public void setDate(View v, DatePickerDialog.OnDateSetListener d) {
        new DatePickerDialog(MainActivity.this, d,
                dateAndTime.get(Calendar.YEAR),
                dateAndTime.get(Calendar.MONTH),
                dateAndTime.get(Calendar.DAY_OF_MONTH))
                .show();
    }

    // установка обработчика выбора даты
    DatePickerDialog.OnDateSetListener d1 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime1();
        }
    };
    DatePickerDialog.OnDateSetListener d2 = new DatePickerDialog.OnDateSetListener() {
        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            dateAndTime.set(Calendar.YEAR, year);
            dateAndTime.set(Calendar.MONTH, monthOfYear);
            dateAndTime.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            setInitialDateTime2();
        }
    };
}
