package com.oracle.tutorial.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// ./comp MyQueries properties/javadb-sample-properties.xml

public class MyQueries3 {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries3(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getMyData(Connection con) throws SQLException {
    Statement stmt = null;
    String query =
        "SELECT nome_agencia, COUNT(*) AS quantidade, SUM(saldo_deposito) AS total " +
        "FROM deposito " +
        "GROUP BY nome_agencia";

    try {
        stmt = con.createStatement();
        ResultSet rs = stmt.executeQuery(query);

        System.out.println("Depositos por agencia:");
        System.out.println("-------------------------------------------");
        while (rs.next()) {
            String nomeAgencia = rs.getString("nome_agencia");
            int quantidade = rs.getInt("quantidade");
            double total = rs.getDouble("total");

            System.out.printf("Agencia: %-20s | Qtd: %-3d | Total: R$ %.2f\n", nomeAgencia, quantidade, total);
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
 	    MyQueries3.getMyData(myConnection);
    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
