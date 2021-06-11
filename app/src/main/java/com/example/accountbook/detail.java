package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;

import static com.example.accountbook.R.drawable.round_button1;

public class detail extends AppCompatActivity {

    TextView recent;  //최신 순
    TextView old;     //오래된 순
    TextView high;   //금액 높은 순
    TextView low;    //금액 낮은 순
    TextView income;
    TextView outcome;
    TextView search;
    int start_year = -1;
    int start_month = -1;
    int start_day = -1;
    int end_year = -1;
    int end_month = -1;
    int end_day = -1;
    int income_flag = 0; // 1 : 클릭되었을 때
    int outcome_flag = 0; // 1 : 클릭되었을 때
    int ordertype = -1; // 0 : 최신순, 1 : 오래된 순, 2 : 금액 높은 순, 3 : 금액 낮은 순

    Calendar myCalendar1 = Calendar.getInstance();
    Calendar myCalendar2 = Calendar.getInstance();
    ArrayList<SQLDatabase> records = new ArrayList<>();
    RecyclerView rcv;
    RadioGroup radioGroup;

    EditText et_date1;
    EditText et_date2;

    DatePickerDialog.OnDateSetListener myDatePicker1 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar1.set(Calendar.YEAR, year);
            myCalendar1.set(Calendar.MONTH, month);
            myCalendar1.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            start_year = year;
            start_month = month;
            start_day = dayOfMonth;
            updateLabel1();
        }
    };
    DatePickerDialog.OnDateSetListener myDatePicker2 = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar2.set(Calendar.YEAR, year);
            myCalendar2.set(Calendar.MONTH, month);
            myCalendar2.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            end_year = year;
            end_month = month;
            end_day = dayOfMonth;
            updateLabel2();
        }
    };

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        rcv = findViewById(R.id.detail_rcv);
        LinearLayoutManager manager = new LinearLayoutManager(detail.this, LinearLayoutManager.VERTICAL, false);
        rcv.setLayoutManager(manager);
        search = findViewById(R.id.detail_search);
        et_date1 = (EditText) findViewById(R.id.et_date1);
        et_date2 = (EditText) findViewById(R.id.et_date2);
        recent = (TextView) findViewById(R.id.recent);
        old = (TextView) findViewById(R.id.old);
        high = (TextView) findViewById(R.id.high);
        low = (TextView) findViewById(R.id.low);
        income = (TextView) findViewById(R.id.detail_income);
        outcome = (TextView) findViewById(R.id.detail_outcome);
        income.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                income.setBackgroundResource(round_button1);
                income.setTextColor(Color.WHITE);
                outcome.setBackgroundResource(R.drawable.round_button);
                outcome.setTextColor(Color.GRAY);
                income_flag=1;
                outcome_flag=0;
            }
        });
        outcome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                income.setBackgroundResource(R.drawable.round_button);
                income.setTextColor(Color.GRAY);
                outcome.setBackgroundResource(R.drawable.round_button1);
                outcome.setTextColor(Color.WHITE);
                income_flag=0;
                outcome_flag=1;
            }
        });
        et_date1.setOnClickListener(new View.OnClickListener() {
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
                ordertype = 0;
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
                ordertype = 1;
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
                ordertype = 2;
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
                ordertype = 3;
            }
        });
        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(income_flag==0&&outcome_flag==0) Toast.makeText(detail.this, "수입이나 지출을 선택해주세요!", Toast.LENGTH_SHORT).show();
                else if(start_year == -1 || end_year == -1) Toast.makeText(detail.this, "조회하시려는 범위를 선택해주세요!", Toast.LENGTH_SHORT).show();
                else if(start_year>end_year) Toast.makeText(detail.this, "조회 범위가 옳지 않습니다!", Toast.LENGTH_SHORT).show();
                else if(start_year==end_year && start_month>end_month) Toast.makeText(detail.this, "조회 범위가 옳지 않습니다!", Toast.LENGTH_SHORT).show();
                else if(start_year==end_year && start_month==end_month && start_day>end_day) Toast.makeText(detail.this, "조회 범위가 옳지 않습니다!", Toast.LENGTH_SHORT).show();
                else if(ordertype==-1) Toast.makeText(detail.this, "정렬 기준을 선택해주세요!", Toast.LENGTH_SHORT).show();

                if(income_flag==1){
                    records.clear();
                    IncomeDBManager incomeDBManager = new IncomeDBManager(detail.this);
                    SQLiteDatabase database1 = incomeDBManager.getReadableDatabase();
                    String s = null;
                    switch (ordertype){
                        case 0:
                            s = "SELECT * FROM Income where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by year desc, month desc, day desc";
                            break;
                        case 1:
                            s = "SELECT * FROM Income where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by year asc, month asc, day asc";
                            break;
                        case 2:
                            s = "SELECT * FROM Income where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by money desc";
                            break;
                        case 3:
                            s = "SELECT * FROM Income where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by money asc";
                            break;
                    }
                    Cursor cursor = database1.rawQuery(s, null);
                    while (cursor.moveToNext()) {
                        int year = cursor.getInt(cursor.getColumnIndex("year"));
                        int month = cursor.getInt(cursor.getColumnIndex("month"));
                        int day = cursor.getInt(cursor.getColumnIndex("day"));
                        int money = cursor.getInt(cursor.getColumnIndex("money"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        SQLDatabase temp = new SQLDatabase(year, month, day, money, name);
                        records.add(temp);
                    }

                    RCVadapter rcVadapter = new RCVadapter(records,detail.this);
                    rcv.setAdapter(rcVadapter);
                }
                else if(outcome_flag==1){
                    records.clear();
                    OutcomeDBManager outcomeDBManager = new OutcomeDBManager(detail.this);
                    SQLiteDatabase database2 = outcomeDBManager.getReadableDatabase();
                    String s = null;
                    switch (ordertype){
                        case 0:
                            s = "SELECT * FROM Outcome where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by year desc, month desc, day desc";
                            break;
                        case 1:
                            s = "SELECT * FROM Outcome where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by year asc, month asc, day asc";
                            break;
                        case 2:
                            s = "SELECT * FROM Outcome where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by money desc";
                            break;
                        case 3:
                            s = "SELECT * FROM Outcome where year*10000 + month*10 + day between " +
                                    start_year+"*10000 + "+ start_month+"*10 + " + start_day+" and "+end_year+"*10000 + "+ end_month+"*10 + " + end_day+
                                    " order by money asc";
                            break;
                    }
                    Cursor cursor = database2.rawQuery(s, null);
                    while (cursor.moveToNext()) {
                        int year = cursor.getInt(cursor.getColumnIndex("year"));
                        int month = cursor.getInt(cursor.getColumnIndex("month"));
                        int day = cursor.getInt(cursor.getColumnIndex("day"));
                        int money = cursor.getInt(cursor.getColumnIndex("money"));
                        String name = cursor.getString(cursor.getColumnIndex("name"));
                        String type = cursor.getString(cursor.getColumnIndex("type"));
                        SQLDatabase temp = new SQLDatabase(year, month, day, money, name, type);
                        records.add(temp);
                    }

                    RCVadapter rcVadapter = new RCVadapter(records,detail.this);
                    rcv.setAdapter(rcVadapter);

                }
            }
        });
    }

    private void updateLabel1() {
        String myFormat = "yyyy / MM / dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        EditText et_date1 = (EditText) findViewById(R.id.et_date1);
        et_date1.setText(sdf.format(myCalendar1.getTime()));
    }

    private void updateLabel2(){
        String myFormat = "yyyy / MM / dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        EditText et_date2 = (EditText) findViewById(R.id.et_date2);
        et_date2.setText(sdf.format(myCalendar2.getTime()));
    }

}