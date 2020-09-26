package pl.slupski.analyzer;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class Recipient {
    private final String id;
    private final String recipient;
    private final ConversationType type;
}
