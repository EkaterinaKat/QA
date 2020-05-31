package utils;

import model.QA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DateManager {
    private static final DateManager INSTANCE = new DateManager();
    private SimpleDateFormat dateFormat = new SimpleDateFormat();
    private Date twoMonthAgo;

    public static DateManager getInstance() {
        return INSTANCE;
    }

    private DateManager() {
        dateFormat.applyPattern("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        twoMonthAgo = calendar.getTime();
    }

    public void removeQAThatYounger2Month(List<QA> list) {
        Iterator<QA> iterator = list.iterator();
        while (iterator.hasNext()) {
            String dateString = iterator.next().getDate();
            if (lessThan2MonthAgo(dateString))
                iterator.remove();
        }
    }

    private boolean lessThan2MonthAgo(String stingDate) {
        Date date = stringToDate(stingDate);
        return date.after(twoMonthAgo);
    }

    private Date stringToDate(String s) {
        Date date = null;
        try {
            date = dateFormat.parse(s);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public void sortByDate(List<QA> list) {
        list.sort((o1, o2) -> {
            Date date1 = stringToDate(o1.getDate());
            Date date2 = stringToDate(o2.getDate());
            return date1.compareTo(date2);
        });
    }

    public String getCurrentDateString() {
        Calendar calendar = Calendar.getInstance();
        return dateFormat.format(calendar.getTime());
    }
}
