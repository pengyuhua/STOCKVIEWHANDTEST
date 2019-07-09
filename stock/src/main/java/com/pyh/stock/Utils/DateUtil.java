package com.pyh.stock.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    private static final Logger logger= LoggerFactory.getLogger(DateUtil.class);

    public static int longOfTwoDate(Date first, Date second) throws ParseException {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(first);
        int cnt = 0;
        while (calendar.getTime().compareTo(second) != 0) {
            calendar.add(Calendar.DATE, 1);
            cnt++;
        }
        logger.info("转换结果:" + cnt);
        return cnt;
    }
}
