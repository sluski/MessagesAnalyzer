package pl.slupski.analyzer.execption;

public class InvalidDateFormatException extends RuntimeException {

    public InvalidDateFormatException(String message) {
        super(message);
    }
}
