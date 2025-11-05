package exceptions;

public class FileAlreadyExistsException extends ProgramException {
    public FileAlreadyExistsException(String filePath) {
        super("File " + filePath + " already exists.");
    }
}
