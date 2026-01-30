package model.program_state;

import javafx.util.Pair;
import model.adt.dictionary.KeyNotDefinedException;
import model.statements.Statement;

import javax.management.openmbean.KeyAlreadyExistsException;
import java.util.ArrayList;
import java.util.Map;

public interface IProceduresTable {
    void addNewProcedure(String name, ArrayList<String> formalParameters, Statement body) throws KeyAlreadyExistsException;
    Pair<ArrayList<String>, Statement> getPair(String procedure) throws KeyNotDefinedException;
    boolean existsProcedure(String procedure);
    Map<String, Pair<ArrayList<String>, Statement>> getContent();

    String toString();
    String tologFileString();
}
