package it.siw.persistence;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Codice fattorizzato in un'unica classe per chiudere le connessioni, statement
 * o result.
 **/

public class DAOUtility {
    public static void close(Connection connection) {
	try {
	    if (connection != null) {
		connection.close();
	    }
	} catch (SQLException e) {
	    System.err.println("Errore nel DAO ! (Chiusura Connection)");
	    e.printStackTrace();
	}
    }

    public static void close(Statement statement) {
	try {
	    if (statement != null) {
		statement.close();
	    }
	} catch (SQLException e) {
	    System.err.println("Errore nel DAO ! (Chiusura Statement)");
	    e.printStackTrace();
	}
    }

    public static void close(ResultSet result) {
	try {
	    if (result != null) {
		result.close();
	    }
	} catch (SQLException e) {
	    System.err.println("Errore nel DAO ! (Chiusura ResultSet)");
	    e.printStackTrace();
	}
    }

}
