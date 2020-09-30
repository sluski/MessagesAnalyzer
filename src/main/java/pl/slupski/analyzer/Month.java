package pl.slupski.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Month {
    private final int monthOfYear;
    private Map<Integer, Day> days = new HashMap<>();

}
