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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;

import com.alexanderbukk.bars.R;
import com.alexanderbukk.bars.data.eventinstance.EventInstance;
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

    private ImageButton ibClose;
    private Button btnSave;
    private EditText etTitle;
    private ImageView ivCategory;
    private TextView tvCategory;
    private TextView tvEvent;
    private TextView tvStartDate;
    private TextView tvEndDate;
    private TextView tvStartTime;
    private TextView tvEndTime;
    private EditText etDescription;
    private EditText etBarsExtra;
    private TextView tvBarsPerOccurrenceNum;
    private TextView tvBarsPerHourNum;
    private TextView tvBarsForYesterdayNum;
    private TextView tvBarsPerOccurrenceLimitNum;
    private TextView tvBarsDailyLimitNum;
    private TextView tvBarsTotalCalc;
    private TextView tvBarsTotalNum;


    private Calendar calendarStart;
    private Calendar calendarEnd;
    private DatePickerDialog datePickerDialogStart;
    private DatePickerDialog datePickerDialogEnd;
    private TimePickerDialog timePickerDialogStart;
    private TimePickerDialog timePickerDialogEnd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);

        String groupName = getIntent().getExtras().getString("groupName");
        String eventName = getIntent().getExtras().getString("eventName");
        setTitle(eventName);

        eventEditorViewModel = new ViewModelProvider(this).get(EventEditorViewModel.class);

        // =========================================================================================
        // Get views by id
        // =========================================================================================
        ibClose = findViewById(R.id.ibClose);
        btnSave = findViewById(R.id.btnSave);
        etTitle = findViewById(R.id.etTitle);
        ivCategory = findViewById(R.id.ivCategory);
        tvCategory = findViewById(R.id.tvCategory);
        tvEvent = findViewById(R.id.tvEvent);
        tvStartDate = findViewById(R.id.tvStartDate);
        tvEndDate = findViewById(R.id.tvEndDate);
        tvStartTime = findViewById(R.id.tvStartTime);
        tvEndTime = findViewById(R.id.tvEndTime);
        etDescription = findViewById(R.id.etDescription);
        etBarsExtra = findViewById(R.id.etBarsExtra);
        tvBarsPerOccurrenceNum = findViewById(R.id.tvBarsPerOccurrenceNum);
        tvBarsPerHourNum = findViewById(R.id.tvBarsPerHourNum);
        tvBarsForYesterdayNum = findViewById(R.id.tvBarsForYesterdayNum);
        tvBarsPerOccurrenceLimitNum = findViewById(R.id.tvBarsPerOccurrenceLimitNum);
        tvBarsDailyLimitNum = findViewById(R.id.tvBarsDailyLimitNum);
        tvBarsTotalCalc = findViewById(R.id.tvBarsTotalCalc);
        tvBarsTotalNum = findViewById(R.id.tvBarsTotalNum);

        // =========================================================================================
        // Set button on click listeners
        // =========================================================================================
        ibClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Save to database
                createEventInstance();

                // Switch back to previous activity
                onBackPressed();
            }
        });
//        ivCategory.setColorFilter(eventEditorViewModel.getGroupByName(groupName).color);
        tvCategory.setText(groupName);
        tvEvent.setText(eventName);
        eventEditorViewModel.getAllGroups().observe(this, new Observer<List<Group>>() {
            @Override
            public void onChanged(@Nullable List<Group> group) {
                ivCategory.setColorFilter(eventEditorViewModel.getGroupByName(groupName).color);
            }
        });


        // =========================================================================================
        // Initialize calendar and date pickers
        // =========================================================================================
        // Start date
        calendarStart = Calendar.getInstance();
        datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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

        // End date
        calendarEnd = Calendar.getInstance();
        datePickerDialogEnd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
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
        // Start time
        timePickerDialogStart = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
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

        // End time
        timePickerDialogEnd = new TimePickerDialog(this, new TimePickerDialog.OnTimeSetListener() {
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

    }

    private void createEventInstance() {

//        EventInstance eventInstance = new EventInstance(
//                findViewById(R.id.etTitle).,
//
//        );
        Log.d(this.getLocalClassName(), "createEventInstance() called");
    }
}
