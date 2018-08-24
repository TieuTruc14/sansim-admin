package com.osp.common;

import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * Created by Admin on 1/3/2018.
 */
public class Utils {
    public static String getIpClient(HttpServletRequest request){
        String remoteAddr = "";
        if (request != null) {
            remoteAddr = request.getHeader("X-FORWARDED-FOR");
            if (remoteAddr == null || "".equals(remoteAddr)) {
                remoteAddr = request.getRemoteAddr();
            }
        }
        return remoteAddr;
    }
    public static String trim(String str){
        if(StringUtils.isNotBlank(str)){
            str.trim();
        }
        return str;
    }

    public static void trimAllFieldOfObject(Object item) {
        if (item == null) {
            return;
        }
        Field[] fields = item.getClass().getDeclaredFields();
        if (fields == null) {
            return;
        }

        for (Field f : fields) {
            if (f.getType().isPrimitive()) {
                continue;
            }
            if (f.getType().equals(String.class)) {
                try {
                    f.setAccessible(true);
                    String value = (String) f.get(item);
                    f.set(item, org.apache.commons.lang3.StringUtils.trimToNull(value));
                } catch (IllegalAccessException e) {
                }

            }
        }
    }

}
