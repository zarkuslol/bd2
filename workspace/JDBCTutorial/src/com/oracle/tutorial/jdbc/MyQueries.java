package com.oracle.tutorial.jdbc;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

// ./comp MyQueries properties/javadb-sample-properties.xml

public class MyQueries {
  
  Connection con;
  JDBCUtilities settings;  
  
  public MyQueries(Connection connArg, JDBCUtilities settingsArg) {
    this.con = connArg;
    this.settings = settingsArg;
  }

  public static void getMyData(Connection con) throws SQLException {
      Statement stmt = null;
      String query =
          "SELECT I.COF_NAME, SUM(I.QUAN * C.PRICE) AS MONTANTE " +
          "FROM COF_INVENTORY I JOIN COFFEES C " +
          "USING (SUP_ID) " +
          "GROUP BY I.SUP_ID, I.COF_NAME";

      try {
          stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(query);
          System.out.println("Cafes e receita potencial agrupados por fornecedor:");
          System.out.println("-----------------------------------------------------");
          while (rs.next()) {
              String coffeeName = rs.getString(1); // COF_NAME
              double revenue = rs.getDouble(2);    // TOTAL_POTENTIAL_REVENUE
              System.out.printf("Cafe: %-20s | Receita estimada: R$ %.2f%n", coffeeName, revenue);
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

 	MyQueries.getMyData(myConnection);

    } catch (SQLException e) {
      JDBCUtilities.printSQLException(e);
    } finally {
      JDBCUtilities.closeConnection(myConnection);
    }

  }
}
