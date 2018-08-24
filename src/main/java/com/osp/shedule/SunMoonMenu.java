package com.osp.shedule;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

import java.nio.file.*;
import java.nio.file.attribute.*;
import java.util.Set;
/**
 * Created by Admin on 2/3/2018.
 */
//@Component
public class SunMoonMenu {
    public static boolean sun;

//    @Scheduled(cron = "0 0/30 5 * * *")
//    public void scheduleTaskUsingCronExpression() {
//        sun=true;
//    }
//    @Scheduled(cron = "0 0 17 * * *")
//    public void scheduleTaskUsingCronExpression1() {
//        sun=false;
//    }
//
//    public boolean isSun() {
//        return sun;
//    }
//
//    public void setSun(boolean sun) {
//        this.sun = sun;
//    }
//
//    @Scheduled(fixedDelay = 360000000)
//    public void scheduleFixedDelayTask() throws Exception {
//        Calendar calendar = Calendar.getInstance();
//        int hour=calendar.get(Calendar.HOUR_OF_DAY);
//        int minute=calendar.get(Calendar.MINUTE);
//        if(hour>=17 || (hour>=16 && minute>=50)){
//            sun=false;
//        }else{
//            sun=true;
//        }
//    }



}
