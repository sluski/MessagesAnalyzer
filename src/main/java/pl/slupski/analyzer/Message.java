package pl.slupski.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

@RequiredArgsConstructor
@Getter
public class Message implements Comparable<Message> {

    private final String user;
    private final String date;
    private final String time;
    private final String content;

    @SneakyThrows
    @Override
    public int compareTo(Message o) {
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy HH:mm");
        Date tDate = format.parse(date + " " + time);
        Date oDate = format.parse(o.date + " " + o.time);
        if(tDate.equals(oDate)) return 0;
        return tDate.before(oDate) ? 1 : -1;
    }
}
