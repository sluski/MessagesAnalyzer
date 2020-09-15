package pl.slupski.analyzer;

import org.junit.BeforeClass;
import org.junit.Test;
import pl.slupski.analyzer.execption.InvalidDirectoryPathException;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import static org.junit.Assert.*;

public class AnalyzerServiceTest {

    private static AnalyzerService service = new AnalyzerService();
    private static File testDirection = new File("/home/sluski/Downloads/messages/inbox"); // todo: branie wartości z properties

    @BeforeClass
    public static void init() throws IOException, ParseException {
        service.init(testDirection);
    }

    @Test(expected = InvalidDirectoryPathException.class)
    public void shouldThrowExceptionIfInvalidDirectoryPathPassed() throws IOException, ParseException {
        // Given
        File invalidFile = new File("invalidPath");

        // When
        service.init(invalidFile);

        // Then exception
    }

    @Test
    public void shouldReturnNonEmptyListOfRecipientsIfServiceInited() {
        assertFalse(service.findAllRecipients().isEmpty());
    }

}
