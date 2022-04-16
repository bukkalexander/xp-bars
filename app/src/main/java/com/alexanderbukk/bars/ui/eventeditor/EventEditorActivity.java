package com.alexanderbukk.bars.ui.eventeditor;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

public class EventEditorActivity extends AppCompatActivity {

    private static final double SECONDS_TO_HOURS = 1.0/(60.0*60.0);

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

    private Event event;
    private List<EventInstance> eventInstances;
    private boolean isUpdateViewsFromEvent = false;

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
        eventEditorViewModel.getEventByGroupAndName(groupName, eventName).observe(this,
                new Observer<Event>() {
            @Override
            public void onChanged(@Nullable Event event) {
                setEvent(event);
                updateViewsFromEvent();
            }
        });

        eventEditorViewModel.getAllEventsFromGroupAndName(groupName, eventName).observe(this,
                new Observer<List<EventInstance>>() {
            @Override
            public void onChanged(@Nullable List<EventInstance> eventInstances) {
                setEventInstances(eventInstances);
                updateViewsFromEventInstances();
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

                updateTotalBars();
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

                updateTotalBars();
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
                updateTotalBars();
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
                updateTotalBars();
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

        // =========================================================================================
        // Set on change listeners
        // =========================================================================================
        etBarsExtra.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                updateTotalBars();
            }
        });
    }

    private void updateViewsFromEventInstances() {
        updateTotalBars();
    }

    private void updateViewsFromEvent() {
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
        isUpdateViewsFromEvent = true;
    }

    private void setEvent(Event event) {
        this.event = event;
    }

    private void setEventInstances(List<EventInstance> eventInstances) {
        this.eventInstances = eventInstances;
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
        if (str == null)
            return 0;
        else if(str.equals(""))
            return 0;
        else
            return Integer.parseInt(str);
    }

    private LocalDateTime getLocalDateTime(Calendar calendar) {
        return LocalDateTime.from(((GregorianCalendar) calendar).toZonedDateTime());
    }

    private void updateTotalBars() {
        if (!isUpdateViewsFromEvent)
            return;

        // Calculate total bars
        int totalBars = 0;
        double barsExtra = toNonNullInt(etBarsExtra.getText().toString());
        double barsPerOccurens = toNonNullInt(tvBarsPerOccurrenceNum.getText().toString());

        double barsPerHour = toNonNullInt(tvBarsPerHourNum.getText().toString());
        double durationSeconds = (double) Duration.between(getLocalDateTime(calendarStart), getLocalDateTime(calendarEnd)).getSeconds();
        double durationHours = secondsToHours(durationSeconds);
        double barsPerHourTotal = barsPerHour*durationHours;

        double barsForYesterday = 0.0;
        if (hasBarsFromYesterday())
            barsForYesterday = toNonNullInt(tvBarsForYesterdayNum.getText().toString());


        double barsPerOccurrenceLimit = toNonNullInt(tvBarsPerOccurrenceLimitNum.getText().toString());
        double barsDailyLimit = toNonNullInt(tvBarsDailyLimitNum.getText().toString());

        ArrayList<Double> barsList = new ArrayList<>();
        barsList.add(barsExtra + barsPerOccurens + barsPerHourTotal + barsForYesterday);
        if (barsPerOccurrenceLimit > 0)
            barsList.add(barsPerOccurrenceLimit);
        if (barsDailyLimit > 0)
            barsList.add(barsDailyLimit);

        int totalMin = barsList.stream().min(Double::compare).get().intValue();

        totalBars = totalMin;

        // Build Bars total calc string
        String barsTotalCalcString = "";
        barsTotalCalcString += "⌊";
        barsTotalCalcString += String.format("min{%.0f+%.0f+%.1f*%.0f+%.0f", barsExtra, barsPerOccurens,
                durationHours, barsPerHour, barsForYesterday);

        if (barsPerOccurrenceLimit > 0)
            barsTotalCalcString += String.format(", %.0f", barsPerOccurrenceLimit);

        if (barsDailyLimit > 0)
            barsTotalCalcString += String.format(", %.0f", barsDailyLimit);

        barsTotalCalcString += "}";
        barsTotalCalcString +="⌋";


        tvBarsTotalCalc.setText(barsTotalCalcString);
        tvBarsTotalNum.setText(Integer.toString(totalBars));
    }

    private double secondsToHours(double durationSeconds) {
        return durationSeconds*SECONDS_TO_HOURS;
    }

    private boolean hasBarsFromYesterday() {
        return false;
    }
}
