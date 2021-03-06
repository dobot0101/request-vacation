package com.test.requestvacation.common;

import com.ibm.icu.util.ChineseCalendar;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HolidayUtil {
    private static String[] solar = {"0101", "0301", "0505", "0606", "0815", "1003", "1009", "1225"};
    private static String[] lunar = {"0101", "0102", "0408", "0814", "0815", "0816", "1231"};

    /**
     * 주말 또는 공휴일인지 확인
     * @param date
     * @return
     */
    public static boolean isWeekendOrHoliday(Date date) {
        // 주말 체크
        if (isWeekend(date)) {
            return true;
        }

        String yearMonthDay = new SimpleDateFormat("yyyyMMdd").format(date);

        // 양력 공휴일 체크
        // 1. 현재 년도와 양력 공휴(mmdd) 합차기
        // 2. 파라미터로 받은 휴가일자와 비교
        String monthDay = yearMonthDay.substring(4);
        for (String solarMonthDay : solar) {
            if (solarMonthDay.equals(monthDay)) {
                return true;
            }
        }

        // 음력 공휴일 체크
        // 1. 현재 년도와 음력 공휴일(mmdd)을 합치기
        // 2. 합쳐진 년월일(yyyyMMdd)을 양력 일자로 변경
        // 3. 파라미터로 받은 휴가일자와 비교
        String year = yearMonthDay.substring(0, 4);
        for (String lunarMonthDay : lunar) {
            String lunarHoliday = convertLunarToSolar(year + lunarMonthDay);
            if (yearMonthDay.equals(lunarHoliday)) {
                return true;
            }
        }

        return false;
    }

    /**
     * 양력 -> 음력 변환
     *
     * @param date
     * @return 년월일(yyyyMMdd)
     */
    private static String convertSolarToLunar(String date) {
        ChineseCalendar cc = new ChineseCalendar();
        Calendar cal = Calendar.getInstance();

        cal.set(Calendar.YEAR, Integer.parseInt(date.substring(0, 4)));
        cal.set(Calendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

        cc.setTimeInMillis(cal.getTimeInMillis());

        int y = cc.get(ChineseCalendar.EXTENDED_YEAR) - 2637;
        int m = cc.get(ChineseCalendar.MONTH) + 1;
        int d = cc.get(ChineseCalendar.DAY_OF_MONTH);

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%04d", y));
        sb.append(String.format("%02d", m));
        sb.append(String.format("%02d", d));

        return sb.toString();
    }

    /**
     * 음력 -> 양력 변환
     *
     * @param date
     * @return
     */
    private static String convertLunarToSolar(String date) {
        ChineseCalendar cc = new ChineseCalendar();
        Calendar cal = Calendar.getInstance();

        cc.set(ChineseCalendar.EXTENDED_YEAR, Integer.parseInt(date.substring(0, 4)) + 2637);
        cc.set(ChineseCalendar.MONTH, Integer.parseInt(date.substring(4, 6)) - 1);
        cc.set(ChineseCalendar.DAY_OF_MONTH, Integer.parseInt(date.substring(6)));

        cal.setTimeInMillis(cc.getTimeInMillis());

        int y = cal.get(Calendar.YEAR);
        int m = cal.get(Calendar.MONTH) + 1;
        int d = cal.get(Calendar.DAY_OF_MONTH);

        StringBuffer sb = new StringBuffer();
        sb.append(String.format("%04d", y));
        sb.append(String.format("%02d", m));
        sb.append(String.format("%02d", d));

        return sb.toString();
    }


    /**
     * 주말 여부 확인 메서드
     *
     * @param date
     * @return
     */
    public static boolean isWeekend(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        if (dayOfWeek == Calendar.SATURDAY || dayOfWeek == Calendar.SUNDAY) {
            return true;
        }

        return false;
    }
}
