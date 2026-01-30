package model.program_state;

import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;
import model.statements.Statement;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class ProceduresTable implements IProceduresTable {
    private Map<String, Pair<ArrayList<String>, Statement>> proceduresTable = new ConcurrentHashMap<>();

    public synchronized void addNewProcedure(String name, ArrayList<String> formalParameters, Statement body) throws KeyAlreadyExistsException {
        if (this.existsProcedure(name)) throw new KeyAlreadyExistsException("Procedure " + name + " already exists.");

        this.proceduresTable.put(name, new Pair<>(formalParameters, body));
    }

//    public synchronized void updateProcedure(int address, Value value) throws KeyNotDefinedException {
//        if (!this.existsProcedure(address)) throw new KeyNotDefinedException(Integer.toString(address));
//
//        this.proceduresTable.put(address, value);
//    }

    public synchronized Pair<ArrayList<String>, Statement> getPair(String procedure) throws KeyNotDefinedException {
        if (!this.existsProcedure(procedure)) throw new KeyNotDefinedException(procedure);

        return this.proceduresTable.get(procedure);
    }

    public synchronized boolean existsProcedure(String procedure) {
        return this.proceduresTable.containsKey(procedure);
    }

    public synchronized Map<String, Pair<ArrayList<String>, Statement>> getContent() {
        return Map.copyOf(this.proceduresTable);
    }

    @Override
    public String toString() {
        return this.proceduresTable.toString();
    }

    public String tologFileString() {
        String logFileEntry = "";

        for (String proc : this.proceduresTable.keySet()) {
            logFileEntry += proc + " : " + this.proceduresTable.get(proc).toString();
            logFileEntry += "\n";
        }

        return logFileEntry;
    }
}
