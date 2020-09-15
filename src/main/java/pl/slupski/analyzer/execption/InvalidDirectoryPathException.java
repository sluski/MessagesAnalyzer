package pl.slupski.analyzer.execption;

public class InvalidDirectoryPathException extends RuntimeException {
    public InvalidDirectoryPathException(String message) {
        super(message);
    }
}
