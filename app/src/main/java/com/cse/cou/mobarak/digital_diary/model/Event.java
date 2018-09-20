package com.cse.cou.mobarak.digital_diary.model;

/**
 * Created by mobarak on 7/16/2018.
 */

public class Event {
    String id;
    String date;
    String title;
    String message;
    String alarmId;
    String time;

    public Event(String id, String date, String title, String message, String alarmId, String time) {
        this.id = id;
        this.date = date;
        this.title = title;
        this.message = message;
        this.alarmId = alarmId;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getAlarmId() {
        return alarmId;
    }

    public void setAlarmId(String alarmId) {
        this.alarmId = alarmId;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
