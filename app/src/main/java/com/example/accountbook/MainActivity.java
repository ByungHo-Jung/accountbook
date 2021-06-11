package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
import com.github.mikephil.charting.interfaces.datasets.IPieDataSet;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private PieChart chart;
    ImageButton addbutton;
    TextView date;
    Button intentbutton;
    Button chkbutton;
    Button deletebutton;

    String name;
    String type;
    int year;
    int month;
    int day;
    int money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        date = findViewById(R.id.main_datetextView);
        Calendar cal = Calendar.getInstance();
        int nowyear = cal.get(Calendar.YEAR);
        int nowmonth = cal.get(Calendar.MONTH);
        int nowday = cal.get(Calendar.DATE);

        try {
            int totalincome = 0;
            int totaloutcome = 0;
            int total = 0;
            IncomeDBManager incomeDBManager = new IncomeDBManager(MainActivity.this);
            SQLiteDatabase database1 = incomeDBManager.getReadableDatabase();
            Cursor cursor = database1.rawQuery("SELECT * FROM Income " +
                    "where year = "+nowyear+" and month = "+ nowmonth+" and day between 1 and "+nowday, null);
            while (cursor.moveToNext()) {
                year = cursor.getInt(cursor.getColumnIndex("year"));
                month = cursor.getInt(cursor.getColumnIndex("month"));
                day = cursor.getInt(cursor.getColumnIndex("day"));
                money = cursor.getInt(cursor.getColumnIndex("money"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                totalincome += money;
            }
            OutcomeDBManager outcomeDBManager = new OutcomeDBManager(MainActivity.this);
            SQLiteDatabase database2 = outcomeDBManager.getReadableDatabase();
            cursor = database2.rawQuery("SELECT * FROM Outcome " +
                    "where year = "+nowyear+" and month = "+ nowmonth+" and day between 1 and "+nowday, null);
            while (cursor.moveToNext()) {
                year = cursor.getInt(cursor.getColumnIndex("year"));
                month = cursor.getInt(cursor.getColumnIndex("month"));
                day = cursor.getInt(cursor.getColumnIndex("day"));
                money = cursor.getInt(cursor.getColumnIndex("money"));
                name = cursor.getString(cursor.getColumnIndex("name"));
                type = cursor.getString(cursor.getColumnIndex("type"));
                totaloutcome += money;
            }

            total = totalincome + totaloutcome;

            chart = findViewById(R.id.pie_chart);

            ArrayList<PieEntry> values = new ArrayList<>();

            float val = (float) (totalincome);
            values.add(new PieEntry(val, "수입"));
            val = (float) (totaloutcome);
            values.add(new PieEntry(val, "지출"));

            PieDataSet set1 = new PieDataSet(values, "");

            // create a data object with the data sets
            PieData data = new PieData(set1);

            // black lines and points
            set1.setColors(ColorTemplate.COLORFUL_COLORS);
            set1.setSliceSpace(3f);
            set1.setSelectionShift(5f);

            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);
            chart.setTransparentCircleRadius(61f);
            chart.setDrawHoleEnabled(false);
            chart.getDescription().setEnabled(false);
            // set data
            chart.setData(data);

        }catch (SQLException e){

        }

        nowmonth++;
        String d = ""+nowyear+"년 "+nowmonth+"월 1일 ~ "+nowyear+"년 "+nowmonth+"월 "+nowday+"일";
        date.setText(d);

        addbutton = (ImageButton)findViewById(R.id.AddButton);
        addbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddDialog addDialog = new AddDialog(MainActivity.this);
                addDialog.show();
            }
        });

        chkbutton = (Button)findViewById(R.id.checkbtn);
        chkbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, detail.class);
                startActivity(intent);
            }
        });

        intentbutton = (Button)findViewById(R.id.intentButton);
        intentbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    IncomeDBManager incomeDBManager = new IncomeDBManager(MainActivity.this);
                    SQLiteDatabase database1 = incomeDBManager.getReadableDatabase();
                    Cursor cursor = database1.rawQuery("SELECT * FROM Income", null);
                    while (cursor.moveToNext()) {
                        year = cursor.getInt(cursor.getColumnIndex("year"));
                        month = cursor.getInt(cursor.getColumnIndex("month"));
                        day = cursor.getInt(cursor.getColumnIndex("day"));
                        money = cursor.getInt(cursor.getColumnIndex("money"));
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        System.out.println("year:" + year + "/month:" + month + "/day:" + day + "/money:" + money + "/name:" + name);
                    }
                    OutcomeDBManager outcomeDBManager = new OutcomeDBManager(MainActivity.this);
                    SQLiteDatabase database2 = outcomeDBManager.getReadableDatabase();
                    cursor = database2.rawQuery("SELECT * FROM Outcome", null);
                    while (cursor.moveToNext()) {
                        year = cursor.getInt(cursor.getColumnIndex("year"));
                        month = cursor.getInt(cursor.getColumnIndex("month"));
                        day = cursor.getInt(cursor.getColumnIndex("day"));
                        money = cursor.getInt(cursor.getColumnIndex("money"));
                        name = cursor.getString(cursor.getColumnIndex("name"));
                        type = cursor.getString(cursor.getColumnIndex("type"));
                        System.out.println("year:" + year + "/month:" + month + "/day:" + day + "/money:" + money + "/name:" + name+"/type:"+type);
                    }
                }catch (SQLException e){

                }
            }
        });

        deletebutton = findViewById(R.id.DBdeleteButton);
        deletebutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                IncomeDBManager incomeDBManager = new IncomeDBManager(MainActivity.this);
                SQLiteDatabase database1 = incomeDBManager.getReadableDatabase();
                database1.execSQL("Delete from Income");
                OutcomeDBManager outcomeDBManager = new OutcomeDBManager(MainActivity.this);
                SQLiteDatabase database2 = outcomeDBManager.getReadableDatabase();
                database2.execSQL("Delete from Outcome");
            }
        });

    }
}