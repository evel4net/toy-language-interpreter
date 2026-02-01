package model.program_state;

import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;
import model.statements.Statement;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.List;
import java.util.Map;

public interface IProceduresTable {
    void addNewProcedure(String name, List<String> formalParameters, Statement body) throws KeyAlreadyExistsException;
    Pair<List<String>, Statement> getPair(String procedure) throws KeyNotDefinedException;
    boolean existsProcedure(String procedure);
    Map<String, Pair<List<String>, Statement>> getContent();

    String toString();
    String tologFileString();
}
