package pl.slupski.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@RequiredArgsConstructor
public class Day {
    private final int dayOfMonth;
    private List<Message> messages = new ArrayList<>();

}