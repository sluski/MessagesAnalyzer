package pl.slupski.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Getter
@RequiredArgsConstructor
public class Year {
    private final int year;
    private Map<Integer, Month> months = new HashMap<>();
}
