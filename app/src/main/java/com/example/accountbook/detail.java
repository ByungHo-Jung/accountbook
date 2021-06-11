package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.accountbook.R.drawable.round_button1;

public class detail extends AppCompatActivity {

    EditText et_Date1;  //시작 날짜
    EditText et_Date2;  //마지막 날짜
    TextView recent;  //최신 순
    TextView old;     //오래된 순
    TextView high;   //금액 높은 순
    TextView low;    //금액 낮은 순
    int start_year;
    int start_month;
    int start_day;
    int end_year;
    int end_month;
    int end_day;
    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    ArrayList<SQLDatabase> records = new ArrayList<>();

    EditText et_date1;
    EditText et_date2;

    DatePickerDialog.OnDateSetListener myDatePicker1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, month);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel1();
        }
    };
    DatePickerDialog.OnDateSetListener myDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel2();
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        et_Date1 = (EditText) findViewById(R.id.et_date1);
        et_Date2 = (EditText) findViewById(R.id.et_date2);
        recent = (TextView) findViewById(R.id.recent);
        old = (TextView) findViewById(R.id.old);
        high = (TextView) findViewById(R.id.high);
        low = (TextView) findViewById(R.id.low);
        et_Date1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(detail.this, myDatePicker1, myCalendar1.get(Calendar.YEAR), myCalendar1.get(Calendar.MONTH), myCalendar1.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        et_date2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(detail.this, myDatePicker2, myCalendar2.get(Calendar.YEAR), myCalendar2.get(Calendar.MONTH), myCalendar2.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        recent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recent.setBackgroundResource(R.drawable.round_button1);
                recent.setTextColor(Color.WHITE);
                old.setTextColor(Color.BLACK);
                old.setBackgroundResource(R.drawable.round_button2);
                high.setBackgroundResource(R.drawable.round_button2);
                high.setTextColor(Color.BLACK);
                low.setBackgroundResource(R.drawable.round_button2);
                low.setTextColor(Color.BLACK);
            }
        });
        old.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recent.setBackgroundResource(R.drawable.round_button2);
                recent.setTextColor(Color.BLACK);
                old.setBackgroundResource(R.drawable.round_button1);
                old.setTextColor(Color.WHITE);
                high.setBackgroundResource(R.drawable.round_button2);
                high.setTextColor(Color.BLACK);
                low.setBackgroundResource(R.drawable.round_button2);
                low.setTextColor(Color.BLACK);

            }
        });
        high.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recent.setBackgroundResource(R.drawable.round_button2);
                recent.setTextColor(Color.BLACK);
                old.setBackgroundResource(R.drawable.round_button2);
                old.setTextColor(Color.BLACK);
                high.setBackgroundResource(R.drawable.round_button1);
                high.setTextColor(Color.WHITE);
                low.setBackgroundResource(R.drawable.round_button2);
                low.setTextColor(Color.BLACK);
            }
        });
        low.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recent.setBackgroundResource(R.drawable.round_button2);
                recent.setTextColor(Color.BLACK);
                old.setBackgroundResource(R.drawable.round_button2);
                old.setTextColor(Color.BLACK);
                high.setBackgroundResource(R.drawable.round_button2);
                high.setTextColor(Color.BLACK);
                low.setBackgroundResource(R.drawable.round_button1);
                low.setTextColor(Color.WHITE);
            }
        });

    }
    
    private void updateLabel1() {
        String myFormat = "yyyy / MM / dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        EditText et_date1 = (EditText) findViewById(R.id.et_date1);
    }
    private void updateLabel2(){
        String myFormat = "yyyy / MM / dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        EditText et_date2 = (EditText) findViewById(R.id.et_date2);
        et_date2.setText(sdf.format(myCalendar2.getTime()));
    }

}