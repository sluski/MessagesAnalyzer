package pl.slupski.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.*;

@Getter
@RequiredArgsConstructor
public class Conversation {

    private final String id;
    private final ConversationType type;
    private final String recipient;
    private final Map<Integer, Year> years;

}
