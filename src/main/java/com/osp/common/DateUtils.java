package com.osp.common;

import org.apache.commons.lang3.StringUtils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Admin on 1/6/2018.
 */
public class DateUtils {

    /**
     * add days to date
     * @author:manhpt
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, days);

        return cal.getTime();
    }
    /**
     * subtract days to date
     * @param date
     * @param days
     * @return
     */
    public static Date subtractDays(Date date, int days) {
        GregorianCalendar cal = new GregorianCalendar();
        cal.setTime(date);
        cal.add(Calendar.DATE, -days);

        return cal.getTime();
    }


    //Convert String to Date
    public static Date strToDate(String input, String format) throws java.text.ParseException {
        Date result = null;
        if (!input.isEmpty()) {
            try {
                SimpleDateFormat formatter = new SimpleDateFormat(format);
                result = formatter.parse(input);
            } catch (ParseException ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    //Convert Date to String
    public static String dateToStr(Date input, String oFormat) {
        String result = "";
        if (input != null) {
            try {
                DateFormat df = new SimpleDateFormat(oFormat);
                result = df.format(input);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return result;
    }

    public static Date genDate(String date,boolean isFrom) throws Exception{
        try{
            Date result=null;
            if(StringUtils.isNotBlank(date)){
                if(isFrom){
                    result=strToDate(date+" 00:00:00","dd/MM/yyyy HH:mm:ss");
                }else{
                    result=strToDate(date+" 23:59:59","dd/MM/yyyy HH:mm:ss");
                }
            }
            return result;
        }catch (Exception e){
            throw new Exception();
        }
    }

    public static Calendar lastDayOfWeek(Calendar calendar){
        Calendar cal = (Calendar) calendar.clone();
        int day = cal.get(Calendar.DAY_OF_YEAR);
        while(cal.get(Calendar.DAY_OF_WEEK) != Calendar.SATURDAY){
            cal.set(Calendar.DAY_OF_YEAR, ++day);
        }
        return cal;
    }


}
