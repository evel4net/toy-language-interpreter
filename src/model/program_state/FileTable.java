package model.program_state;

import exceptions.FileAlreadyExistsException;
import model.adt.dictionary.ADTDictionary;
import model.adt.dictionary.IADTDictionary;
import model.adt.dictionary.KeyNotDefinedException;
import model.values.StringValue;
import exceptions.FileNotFoundException;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileTable {
    private final IADTDictionary<StringValue, BufferedReader> table = new ADTDictionary<>();

    public boolean existsFile(StringValue fileName) {
        return this.table.exists(fileName);
    }

    public void openFile(StringValue fileName) throws FileNotFoundException, FileAlreadyExistsException {
        if (this.existsFile(fileName)) throw new FileAlreadyExistsException(fileName.getValue());

        try {
            BufferedReader fileReader = new BufferedReader(new FileReader(fileName.getValue()));

            this.table.insert(fileName, fileReader);
        } catch (IOException e) {
            throw new FileNotFoundException(fileName.getValue());
        }
    }

    public void closeFile(StringValue fileName) throws FileNotFoundException{
        try {
            BufferedReader fileReader = this.getReader(fileName);
            fileReader.close();
            this.table.remove(fileName);
        } catch (KeyNotDefinedException | IOException e) {
            throw new FileNotFoundException(fileName.getValue());
        }
    }

    public BufferedReader getReader(StringValue fileName) throws KeyNotDefinedException {
        return this.table.get(fileName);
    }

    @Override
    public String toString() {
        return this.table.getKeys().toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (StringValue fileName : this.table.getKeys()) {
            logFileEntry += fileName.getValue();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
