/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author nguye
 */
public class Validation {

    public static boolean checkValidDate(String date) {
        boolean result = true;
        String[] t = date.split("-");
        int day = Integer.parseInt(t[2]);
        int month = Integer.parseInt(t[1]);
        int year = Integer.parseInt(t[0]);
        if (day <= 0 || day > 31) {
            result = false;
        }
        if (month <= 0 || month > 12) {
            result = false;
        }
        if (year <= 0 || year > 2021) {
            result = false;
        }
        if ((month == 1) || (month == 3) || (month == 5) || (month == 7) || (month == 8) || (month == 10) || (month == 12)) {
            if (!(day <= 31)) {
                result = false;
            }
        } else if ((month == 4) || (month == 6) || (month == 9) || (month == 11)) {
            if (!(day <= 30)) {
                result = false;
            }
        } else if (month == 2) {
            int dmax = ((year % 4) == 0) ? 29 : 28;
            if (day > dmax) {
                result = false;
            }
        }
        return result;
    }

    public static boolean checkFormatdate(String date) {
        boolean result = true;
        if (!date.matches("^[0-9]{1,}-[0-9]{1,2}-[0-9]{1,2}$")) {
            result = false;
        }
        return result;
    }

    public static Date convertStringToDate(String date) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date day = new Date();
        try {
            day = (Date) sdf.parse(date);
        } catch (ParseException ex) {
            ex.printStackTrace();
        }
        return day;
    }
}
