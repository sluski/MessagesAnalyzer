package pl.slupski.analyzer.execption;

public class FileContentException extends RuntimeException {
    public FileContentException(String message) {
        super(message);
    }
}
