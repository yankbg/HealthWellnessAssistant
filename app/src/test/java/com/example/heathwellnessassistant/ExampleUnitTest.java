package com.example.heathwellnessassistant;

import org.junit.Test;

import static org.junit.Assert.*;

import com.example.heathwellnessassistant.Database.Converter;

import java.sql.Date;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    Converter converter = new Converter();
    Date date = new Date(1672531200000L);
    Long timestamp = 1672531200000L;
    Date result = converter.fromTimeStamp(timestamp);
    Long result01 = converter.dateToTimestamp(date);

    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }
    @Test
    public void dateToTimestamp_test(){
        assertEquals(date, result);
//        assertEquals(timestamp,result01);
    }
}