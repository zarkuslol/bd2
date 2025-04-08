package com.oracle.tutorial.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// ./comp MyQueries properties/javadb-sample-properties.xml

public class MyQueries2 {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries2(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getMyData(Connection con) throws SQLException {
    Statement stmt = null;
    String query =
        "SELECT nome_cliente " +
        "FROM cliente " +
        "WHERE nome_cliente NOT IN (SELECT nome_cliente FROM deposito) " +
        "AND nome_cliente NOT IN (SELECT nome_cliente FROM emprestimo)";

    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);
        System.out.println("Clientes que nao fizeram depositos nem emprestimos:");
        System.out.println("-----------------------------------------------------");
        while (rs.next()) {
            String nomeCliente = rs.getString(1);
            System.out.println("Cliente: " + nomeCliente);
        }
    } catch (SQLException e) {
        JDBCUtilities.printSQLException(e);
    } finally {
        if (stmt != null) { stmt.close(); }
    }
  }



  public static void main(String[] args) {
    JDBCUtilities myJDBCUtilities;
    Connection myConnection = null;
    if (args[0] == null) {
      System.err.println("Properties file not specified at command line");
      return;
    } else {
      try {
        myJDBCUtilities = new JDBCUtilities(args[0]);
      } catch (Exception e) {
        System.err.println("Problem reading properties file " + args[0]);
        e.printStackTrace();
        return;
      }
    }

    try {
      myConnection = myJDBCUtilities.getConnection();
 	    MyQueries2.getMyData(myConnection);
    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
