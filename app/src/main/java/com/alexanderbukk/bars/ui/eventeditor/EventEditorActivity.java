package com.alexanderbukk.bars.ui.eventeditor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alexanderbukk.bars.R;
import com.alexanderbukk.bars.data.group.Group;
import com.alexanderbukk.bars.ui.event.EventViewModel;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class EventEditorActivity extends AppCompatActivity {

    private EventEditorViewModel eventEditorViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);

        String groupName = getIntent().getExtras().getString("groupName");
        String eventName = getIntent().getExtras().getString("eventName");
        setTitle(eventName);

        eventEditorViewModel = new ViewModelProvider(this).get(EventEditorViewModel.class);

        // =========================================================================================
        // Buttons
        // =========================================================================================
        findViewById(R.id.ibClose).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        findViewById(R.id.btnSave).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save to database
                onBackPressed();
            }
        });


        // =========================================================================================
        // Title
        // =========================================================================================
        findViewById(R.id.etTitle);


        // =========================================================================================
        // Category (group,event)
        // =========================================================================================
        ImageView ivCategory = findViewById(R.id.ivCategory);
//        ivCategory.setColorFilter(eventEditorViewModel.getGroupByName(groupName).color);

        TextView tvCategory = findViewById(R.id.tvCategory);
        tvCategory.setText(groupName);


        TextView tvEvent = findViewById(R.id.tvEvent);
        tvEvent.setText(eventName);

        eventEditorViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> group) {
                ivCategory.setColorFilter(eventEditorViewModel.getGroupByName(groupName).color);
            }
        });


        // =========================================================================================
        // Date
        // =========================================================================================
        // Start
        TextView tvStartDate = findViewById(R.id.tvStartDate);

        Calendar calendarStart = Calendar.getInstance();
        DatePickerDialog datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                LocalDate now = LocalDate.of(year, month+1, day);
                String dateStr = now.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                tvStartDate.setText(dateStr);
            }
        }, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH));

        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogStart.show();
            }
        });

        // End
        TextView tvEndDate = findViewById(R.id.tvEndDate);

        Calendar calendarEnd = Calendar.getInstance();
        DatePickerDialog datePickerDialogEnd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                LocalDate now = LocalDate.of(year, month+1, day);
                String dateStr = now.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
                tvEndDate.setText(dateStr);
            }
        }, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH));

        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogEnd.show();
            }
        });


        // =========================================================================================
        // Time
        // =========================================================================================
        // Start
        TextView tvStartTime = findViewById(R.id.tvStartTime);
        TimePickerDialog timePickerDialogStart = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                LocalTime now = LocalTime.of(hourOfDay, minute);
                String timeStr = now.format(DateTimeFormatter.ofPattern("HH:mm"));
                tvStartTime.setText(timeStr);
            }
        }, calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE), true);

        tvStartTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialogStart.show();
            }
        });

        // End
        TextView tvEndTime = findViewById(R.id.tvEndTime);
        TimePickerDialog timePickerDialogEnd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int hourOfDay, int minute) {
                LocalTime now = LocalTime.of(hourOfDay, minute);
                String timeStr = now.format(DateTimeFormatter.ofPattern("HH:mm"));
                tvEndTime.setText(timeStr);
            }
        }, calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE), true);

        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialogEnd.show();
            }
        });

        // =========================================================================================
        // Description
        // =========================================================================================
        findViewById(R.id.etDescription);

        // =========================================================================================
        // Bars
        // =========================================================================================
        findViewById(R.id.etBarsExtra);
        findViewById(R.id.tvBarsPerOccurrenceNum);
        findViewById(R.id.tvBarsPerHourNum);
        findViewById(R.id.tvBarsForYesterdayNum);
        findViewById(R.id.tvBarsPerOccurrenceLimitNum);
        findViewById(R.id.tvBarsDailyLimitNum);
        findViewById(R.id.tvBarsTotalCalc);
        findViewById(R.id.tvBarsTotalNum);

    }
}