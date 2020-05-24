package vn.edu.vinaenter.utils;

import java.sql.Date;
import java.sql.Time;
import java.time.LocalDate;

public class DateUtils {

    /**
     * Convert from SQLDate To UtilDate
     *
     * @param java.sql.Date date
     * @return java.util.Date
     */
    public static java.util.Date convertSQLDateToUtilDate(Date date) {
        if (date == null) {
            return null;
        }

        return new Date(date.getTime());
    }

    /**
     * Convert from SQLTime to UtilDate
     *
     * @param time java.sql.Time time
     * @return java.util.Date
     */
    public static java.util.Date convertSQLTimeToUtilDate(Time time) {
        if (time == null) {
            return null;
        }

        LocalDate localDate = LocalDate.now();

        return new java.util.Date(time.getTime());
    }
}
