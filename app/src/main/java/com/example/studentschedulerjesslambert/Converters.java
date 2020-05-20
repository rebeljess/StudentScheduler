package com.example.studentschedulerjesslambert;

import androidx.room.TypeConverter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class Converters {
    static Date freshDate;
    @TypeConverter
    public static Date toDate(String stringDate){
        SimpleDateFormat stringFormat = new SimpleDateFormat("MM-dd-yyyy");
        try {
            freshDate = stringFormat.parse(stringDate);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return freshDate;
//        return stringDate == null ? null : new Date(stringDate);
    }
    public static Calendar toCalendar(Date date){
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    @TypeConverter
    public static String fromDate(Date date){
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        return dateFormat.format(date);
    }


}
