package model.exceptions;

public class FileNotFoundException extends ProgramException {
    public FileNotFoundException(String filePath) {
        super("File " + filePath + " not found.");
    }
}
