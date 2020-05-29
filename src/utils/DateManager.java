package utils;

import model.QA;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class DateManager {
    private SimpleDateFormat dateFormat = new SimpleDateFormat();
    private Date twoMonthAgo;

    public DateManager() {
        dateFormat.applyPattern("yyyy-MM-dd");
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.MONTH, -2);
        twoMonthAgo = calendar.getTime();
    }

    public List<QA> removeQAThatYonger2Month(List<QA> list) {
        Iterator<QA> iterator = list.iterator();
        while (iterator.hasNext()) {
            String dateString = iterator.next().getDate();
            if (lessThan2MonthAgo(dateString))
                iterator.remove();
        }
        return list;
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
}
