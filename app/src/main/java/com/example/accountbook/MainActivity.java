package com.example.accountbook;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;

import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private PieChart chart;
    ImageButton addbutton;
    TextView date;
    Button chkbutton;
    String name;
    String type;
    int year;
    int month;
    int day;
    int money;
    public static int flag=0;

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
            int totalincome = 1;
            totalincome=0;
            int totaloutcome = 0;

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

            chart = findViewById(R.id.pie_chart);

            ArrayList<PieEntry> values = new ArrayList<>();

            float val = (float) (totalincome);
            values.add(new PieEntry(val, "수입"));
            val = (float) (totaloutcome);
            values.add(new PieEntry(val, "지출"));

            PieDataSet set1 = new PieDataSet(values, "");

            PieData data = new PieData(set1);

            set1.setColors(Color.parseColor("#3eb489"), Color.parseColor("#87ceaf") );
            set1.setSliceSpace(3f);
            set1.setSelectionShift(5f);

            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);
            chart.setTransparentCircleRadius(61f);
            chart.setDrawHoleEnabled(false);
            chart.getDescription().setEnabled(false);
            chart.setData(data);
            chart.invalidate();

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
                addDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        updateChart();
                    }
                });
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
    }

    @Override
    protected void onPause() {
        super.onPause();
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
            set1.setColors(Color.parseColor("#3eb489"), Color.parseColor("#87ceaf") );
            set1.setSliceSpace(3f);

            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);
            chart.setTransparentCircleRadius(61f);
            chart.setDrawHoleEnabled(false);
            chart.getDescription().setEnabled(false);
            // set data
            chart.invalidate();
            chart.setData(data);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();

        }catch (SQLException e){

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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
            set1.setColors(Color.parseColor("#3eb489"), Color.parseColor("#87ceaf") );
            set1.setSliceSpace(3f);

            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);
            chart.setTransparentCircleRadius(61f);
            chart.setDrawHoleEnabled(false);
            chart.getDescription().setEnabled(false);
            // set data
            chart.invalidate();
            chart.setData(data);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();

        }catch (SQLException e){

        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
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
            set1.setColors(Color.parseColor("#3eb489"), Color.parseColor("#87ceaf") );
            set1.setSliceSpace(3f);

            data.setValueTextSize(10f);
            data.setValueTextColor(Color.YELLOW);
            chart.setTransparentCircleRadius(61f);
            chart.setDrawHoleEnabled(false);
            chart.getDescription().setEnabled(false);
            // set data
            chart.invalidate();
            chart.setData(data);
            data.notifyDataChanged();
            chart.notifyDataSetChanged();
            chart.invalidate();

        }catch (SQLException e){

        }
    }

    void updateChart(){
        if(flag==1){
            flag=0;
            try {
                Calendar cal = Calendar.getInstance();
                int nowyear = cal.get(Calendar.YEAR);
                int nowmonth = cal.get(Calendar.MONTH);
                int nowday = cal.get(Calendar.DATE);

                int totalincome = 1;
                totalincome=0;
                int totaloutcome = 0;

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

                chart = findViewById(R.id.pie_chart);

                ArrayList<PieEntry> values = new ArrayList<>();

                float val = (float) (totalincome);
                values.add(new PieEntry(val, "수입"));
                val = (float) (totaloutcome);
                values.add(new PieEntry(val, "지출"));

                PieDataSet set1 = new PieDataSet(values, "");

                PieData data = new PieData(set1);

                set1.setColors(Color.parseColor("#3eb489"), Color.parseColor("#87ceaf") );
                set1.setSliceSpace(3f);
                set1.setSelectionShift(5f);

                data.setValueTextSize(10f);
                data.setValueTextColor(Color.YELLOW);
                chart.setTransparentCircleRadius(61f);
                chart.setDrawHoleEnabled(false);
                chart.getDescription().setEnabled(false);
                chart.setData(data);
                chart.invalidate();

            }catch (SQLException e){

            }
        }
    }
}