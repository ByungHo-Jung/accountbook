package com.example.accountbook;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddDialog extends Dialog {

    RadioGroup IORadioGroup;
    RadioGroup PayRadioGroup;
    RadioButton CashRadio;
    RadioButton CardRadio;
    RadioButton AFRadio;
    DatePicker datePicker;
    EditText MoneyText;
    EditText TitleText;
    EditText et_Date;
    TextView OKButton;
    TextView NOButton;
    LinearLayout expenselayout;
    SQLiteDatabase sqLiteDatabase;
    IncomeDBManager incomeDBManager;
    OutcomeDBManager outcomeDBManager;
    int payflag = 0; // 0:수입, 1:소비
    int paytypeflag; // 0:현금, 1:카드, 2:계좌
    Context context;

    Calendar myCalendar = Calendar.getInstance();

    DatePickerDialog.OnDateSetListener myDatePicker = new DatePickerDialog.OnDateSetListener() {
        @Override
        public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, month);
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
            updateLabel();
        }
    };
    public AddDialog(@NonNull Context context) {
        super(context);
        this.context = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_register);
        et_Date = (EditText) findViewById(R.id.et_date);
        et_Date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick (View v){
                new DatePickerDialog(context, myDatePicker, myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH), myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        MoneyText = findViewById(R.id.Input_Money);
        TitleText = findViewById(R.id.Input_Title);
        CashRadio = findViewById(R.id.CashRadio);
        CardRadio = findViewById(R.id.CardRadio);
        AFRadio = findViewById(R.id.AFRadio);
        expenselayout = findViewById(R.id.expenselayout);
        PayRadioGroup = findViewById(R.id.PayTypeRadioGroup);
        PayRadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.CashRadio:
                        paytypeflag = 0;
                        break;
                    case R.id.CardRadio:
                        paytypeflag = 1;
                        break;
                    case R.id.AFRadio:
                        paytypeflag = 2;
                        break;
                }
            }
        });

        IORadioGroup = findViewById(R.id.IOTypeRadio);
        IORadioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.IncomeRadioButton:
                        payflag = 0;
                        break;
                    case R.id.OutcomeRadioButton:
                        payflag = 1;
                        break;
                }
                RadioButton IncomeRadioButton = findViewById(R.id.IncomeRadioButton);
                if(IncomeRadioButton.isChecked()){
                    expenselayout.setVisibility(View.INVISIBLE);
                }
                else{
                    expenselayout.setVisibility(View.VISIBLE);
                }
            }
        });
        RadioButton radioButton = findViewById(R.id.IncomeRadioButton);
        radioButton.setChecked(true);

        NOButton = findViewById(R.id.add_no_text);
        NOButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        OKButton = findViewById(R.id.add_ok_text);
        OKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = TitleText.getText().toString();
                int year = myCalendar.get(Calendar.YEAR);
                int month = myCalendar.get(Calendar.MONTH);
                int day = myCalendar.get(Calendar.DAY_OF_MONTH);
                String s = MoneyText.getText().toString();
                int money = Integer.parseInt(MoneyText.getText().toString());
                if(name == null){
                    Toast.makeText(getContext(), "지출 이름을 입력해주세요!", Toast.LENGTH_SHORT);
                }
                else if(MoneyText.getText().toString() == null){
                    Toast.makeText(getContext(), "금액을 입력해주세요!", Toast.LENGTH_SHORT);
                }
                else {
                    if (payflag == 0) {
                        try{
                            incomeDBManager = new IncomeDBManager(getContext());
                            sqLiteDatabase = incomeDBManager.getWritableDatabase();
                            ContentValues values = new ContentValues();
                            values.put("year", year);
                            values.put("month", month);
                            values.put("day", day);
                            values.put("money", money);
                            values.put("name", name);
                            long newRowId = sqLiteDatabase.insert("Income", null, values);
                            sqLiteDatabase.close();
                            incomeDBManager.close();
                        }
                        catch (SQLException e){
                            Toast.makeText(getContext(), "Failed to Save", Toast.LENGTH_SHORT);
                        }
                    } else if (payflag == 1) {
                        try{
                            ContentValues values = new ContentValues();
                            values.put("year", year);
                            values.put("month", month);
                            values.put("day", day);
                            values.put("money", money);
                            values.put("name", name);
                            switch(paytypeflag){
                                case 0: // 현금
                                    values.put("type", "현금 결제");
                                    break;
                                case 1: // 카드
                                    values.put("type", "카드 결제");
                                    break;
                                case 2: // 계좌이체
                                    values.put("type", "계좌 이체");
                                    break;
                            }
                            OutcomeDBManager dbManager = new OutcomeDBManager(getContext());
                            sqLiteDatabase = dbManager.getWritableDatabase();
                            long newRowId = sqLiteDatabase.insert("Outcome", null, values);
                            sqLiteDatabase.close();
                            dbManager.close();
                        }
                        catch (SQLException e){
                            Toast.makeText(getContext(), "Failed to Save", Toast.LENGTH_SHORT);
                        }
                    }

                }

                dismiss();
            }
        });
    }
    private void updateLabel() {
        String myFormat = "yyyy / MM / dd";    // 출력형식   2018/11/28
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat, Locale.KOREA);
        EditText et_date = (EditText) findViewById(R.id.et_date);
        et_date.setText(sdf.format(myCalendar.getTime()));
    }
}
