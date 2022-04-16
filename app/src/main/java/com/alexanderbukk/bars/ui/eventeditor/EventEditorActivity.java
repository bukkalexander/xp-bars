package com.alexanderbukk.bars.ui.eventeditor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
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
import com.alexanderbukk.bars.data.event.Event;
import com.alexanderbukk.bars.data.eventinstance.EventInstance;
import com.alexanderbukk.bars.data.group.Group;

import java.sql.Timestamp;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EventEditorActivity extends AppCompatActivity {

    private String groupName;
    private String eventName;
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

    private LiveData<Event> event;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_editor);

        groupName = getIntent().getExtras().getString("groupName");
        eventName = getIntent().getExtras().getString("eventName");
        setTitle(eventName);

        eventEditorViewModel = new ViewModelProvider(this).get(EventEditorViewModel.class);

        // =========================================================================================
        // Initialize views
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
        // Initialize calendars
        // =========================================================================================
        calendarStart = Calendar.getInstance();
        calendarEnd = Calendar.getInstance();

        // =========================================================================================
        // Add view model observers for updating views from selected Event
        // =========================================================================================
        eventEditorViewModel.getEventByGroupAndName(groupName, eventName).observe(this, new Observer<Event>() {
            @Override
            public void onChanged(@Nullable Event event) {
                etDescription.setText(event.description);
                etBarsExtra.setText(Integer.toString(event.barsExtra));
                tvBarsPerOccurrenceNum.setText(Integer.toString(event.barsPerOccurrence));
                tvBarsPerHourNum.setText(Integer.toString(event.barsPerHour));
                tvBarsForYesterdayNum.setText(Integer.toString(event.barsForYesterday));
                tvBarsPerOccurrenceLimitNum.setText(Integer.toString(event.barsPerOccurrenceLimit));
                tvBarsDailyLimitNum.setText(Integer.toString(event.barsDailyLimit));
                addMinutesToCalendar(calendarEnd, event.durationMinutes);
                tvEndDate.setText(calendarToDateString(calendarEnd));
                tvEndTime.setText(calendarToTimeString(calendarEnd));
            }
        });

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
        datePickerDialogStart = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                LocalDateTime dateTimeCalendarStart = getLocalDateTime(calendarStart);
                calendarStart.set(year, month, day);
                LocalDateTime dateTimeCalendarStartNew = getLocalDateTime(calendarStart);
                calendarEnd.add(Calendar.SECOND, (int) Duration.between(dateTimeCalendarStart, dateTimeCalendarStartNew).getSeconds());

                tvStartDate.setText(calendarToDateString(calendarStart));
                tvEndDate.setText(calendarToDateString(calendarEnd));
            }
        }, calendarStart.get(Calendar.YEAR), calendarStart.get(Calendar.MONTH), calendarStart.get(Calendar.DAY_OF_MONTH));

        // Start date text
        tvStartDate.setText(calendarToDateString(calendarStart));
        tvStartDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogStart.show();
            }
        });

        // End date
        datePickerDialogEnd = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                // Make sure end date is later than start date
                Calendar calendarEndNew = (Calendar) calendarEnd.clone();
                calendarEndNew.set(year, month, day);
                LocalDateTime dateTimeCalendarStart = getLocalDateTime(calendarStart);
                LocalDateTime dateTimeCalendarEndNew = getLocalDateTime(calendarEndNew);
                int duration = (int) Duration.between(dateTimeCalendarStart, dateTimeCalendarEndNew).getSeconds();
                if (duration >= 0) {
                    calendarEnd.set(year, month, day);
                    tvEndDate.setText(calendarToDateString(calendarEnd));
                }
            }
        }, calendarEnd.get(Calendar.YEAR), calendarEnd.get(Calendar.MONTH), calendarEnd.get(Calendar.DAY_OF_MONTH));

        // End date text
        tvEndDate.setText(calendarToDateString(calendarEnd));
        tvEndDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                datePickerDialogEnd.getDatePicker().updateDate(
                        calendarEnd.get(Calendar.YEAR),
                        calendarEnd.get(Calendar.MONTH),
                        calendarEnd.get(Calendar.DAY_OF_MONTH));
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
                calendarStart.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarStart.set(Calendar.MINUTE, minute);
                tvStartTime.setText(calendarToTimeString(calendarStart));
            }
        }, calendarStart.get(Calendar.HOUR_OF_DAY), calendarStart.get(Calendar.MINUTE), true);

        // Start time
        tvStartTime.setText(calendarToTimeString(calendarStart));
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
                calendarEnd.set(Calendar.HOUR_OF_DAY, hourOfDay);
                calendarEnd.set(Calendar.MINUTE, minute);
                tvEndTime.setText(calendarToTimeString(calendarEnd));
            }
        }, calendarEnd.get(Calendar.HOUR_OF_DAY), calendarEnd.get(Calendar.MINUTE), true);

        // End time
        tvEndTime.setText(calendarToTimeString(calendarEnd));
        tvEndTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timePickerDialogEnd.show();
            }
        });
    }

    private void updateDateTimeViews() {
        tvStartDate.setText(calendarToDateString(calendarStart));
        tvStartTime.setText(calendarToTimeString(calendarStart));
        tvEndDate.setText(calendarToDateString(calendarEnd));
        tvEndTime.setText(calendarToTimeString(calendarEnd));
    }

    private void addDateTimeToCalendar(Calendar calendarEnd, LocalDateTime dateTimeStartDifference) {
        Log.d("","");
    }

    private LocalDateTime getLocalDateTimeDifference(LocalDateTime dateTimeCalendarStart, LocalDateTime dateTimeCalendarStartNew) {
        Timestamp timestamp = (Timestamp) Timestamp.from(dateTimeCalendarStart.toInstant(ZoneOffset.ofHours(Calendar.ZONE_OFFSET)));
        return LocalDateTime.now();
    }

    private void addMinutesToCalendar(Calendar calendarEnd, int durationMinutes) {
        calendarEnd.add(Calendar.MINUTE, durationMinutes);
    }

    private String calendarToTimeString(Calendar calendar) {
        LocalTime now = LocalTime.from(((GregorianCalendar) calendar).toZonedDateTime());
        String timeStr = now.format(DateTimeFormatter.ofPattern("HH:mm"));
        return timeStr;
    }

    private String calendarToDateString(Calendar calendar) {
        LocalDate now = LocalDate.from(((GregorianCalendar) calendar).toZonedDateTime());
        String dateStr = now.format(DateTimeFormatter.ofPattern("EEE, MMM dd, yyyy"));
        return dateStr;
    }

    private void createEventInstance() {

        EventInstance eventInstance = new EventInstance(
            etTitle.getText().toString(),
            eventName,
            groupName,
            etDescription.getText().toString(),
            toNonNullInt(etBarsExtra.getText().toString()),
            toNonNullInt(tvBarsPerOccurrenceNum.getText().toString()),
            toNonNullInt(tvBarsPerHourNum.getText().toString()),
            toNonNullInt(tvBarsForYesterdayNum.getText().toString()),
            toNonNullInt(tvBarsPerOccurrenceLimitNum.getText().toString()),
            toNonNullInt(tvBarsDailyLimitNum.getText().toString()),
            getLocalDateTime(calendarStart),
            getLocalDateTime(calendarEnd)
        );

        eventEditorViewModel.insertEventInstance(eventInstance);

        Log.d(this.getLocalClassName(), "createEventInstance() called");
    }

    private int toNonNullInt(String str) {
        return Integer.getInteger(str) != null ?  Integer.getInteger(str) : 0;
    }

    private LocalDateTime getLocalDateTime(Calendar calendar) {
        return LocalDateTime.from(((GregorianCalendar) calendar).toZonedDateTime());
    }

    private int getDurationMinutes() {
        return 0;
    }
}
