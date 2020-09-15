package pl.slupski.analyzer;

import lombok.Getter;

@Getter
public class DayInfo {
    private final long dateId;
    private int numberOfMessages;

    public DayInfo(long dateId) {
        this.dateId = dateId;
        this.numberOfMessages = 0;
    }

    public void addMessage() {
        this.numberOfMessages += 1;
    }
}
