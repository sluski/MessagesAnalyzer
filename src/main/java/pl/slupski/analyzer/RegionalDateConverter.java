package pl.slupski.analyzer;

import pl.slupski.analyzer.execption.UnsupportedLanguageException;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class RegionalDateConverter {

    public static String convertDate(String date, String lang) throws ParseException {
        if (lang.equals("en")) return enConvertDate(date);
        else if (lang.equals("pl")) return plConvertDate(date);
        throw new UnsupportedLanguageException("Language is not supported: " + lang);
    }

    private static String enConvertDate(String date) throws ParseException {
        String[] splitedDateTime = date.split(",");

        String day = splitedDateTime[0].trim().split(" ")[1];
        day = day.length() < 2 ? day = "0" + day : day;
        String month = enMonthToNum(splitedDateTime[0].trim().split(" ")[0]);
        String year = splitedDateTime[1].trim();

        DateFormat f = new SimpleDateFormat("hh:mm aa");
        Date d = f.parse(splitedDateTime[2].trim());
        DateFormat fd = new SimpleDateFormat("HH:mm");
        String hour = fd.format(d).split(":")[0];
        String minute = fd.format(d).split(":")[1];
        return day + "-" + month + "-" + year + " " + hour + ":" + minute;
    }

    private static String plConvertDate(String date) {
        String[] dateTimeSplited = date.split(",");
        String[] dateSplited = dateTimeSplited[0].trim().split(" ");
        String[] timeSplited = dateTimeSplited[1].trim().split(":");
        String day = dateSplited[0];
        String month = plMonthToNumber(dateSplited[1]);
        String year = dateSplited[2];

        String hour = timeSplited[0];
        String minute = timeSplited[1];
        return day + "-" + month + "-" + year + " " + hour + ":" + minute;
    }

    private static String enMonthToNum(String month) {
        if (month.equals("Jan")) {
            return "01";
        } else if (month.equals("Feb")) {
            return "02";
        } else if (month.equals("Mar")) {
            return "03";
        } else if (month.equals("Apr")) {
            return "04";
        } else if (month.equals("May")) {
            return "05";
        } else if (month.equals("Jun")) {
            return "06";
        } else if (month.equals("Jul")) {
            return "07";
        } else if (month.equals("Aug")) {
            return "08";
        } else if (month.equals("Sep")) {
            return "09";
        } else if (month.equals("Oct")) {
            return "10";
        } else if (month.equals("Nov")) {
            return "11";
        } else if (month.equals("Dec")) {
            return "12";
        }
        return "-1";
    }

    private static String plMonthToNumber(String month) {
        if (month.equals("sty")) {
            return "01";
        } else if (month.equals("lut")) {
            return "02";
        } else if (month.equals("mar")) {
            return "03";
        } else if (month.equals("kwi")) {
            return "04";
        } else if (month.equals("maj")) {
            return "05";
        } else if (month.equals("cze")) {
            return "06";
        } else if (month.equals("lip")) {
            return "07";
        } else if (month.equals("sie")) {
            return "08";
        } else if (month.equals("wrz")) {
            return "09";
        } else if (month.equals("paź")) {
            return "10";
        } else if (month.equals("lis")) {
            return "11";
        } else if (month.equals("gru")) {
            return "12";
        }
        return "-1";
    }
}
