package com.oracle.tutorial.jdbc;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Scanner;

public class MyQueries {

    Connection con;
    JDBCUtilities settings;

    public MyQueries(Connection connArg, JDBCUtilities settingsArg) {
        this.con = connArg;
        this.settings = settingsArg;
    }

    public static void populateClientes(Connection con) throws SQLException, IOException {
      Statement stmt = null;
      BufferedReader inputStream = null;
      String line;
  
      try {
          stmt = con.createStatement();
          stmt.executeUpdate("TRUNCATE TABLE cliente CASCADE;");
  
          inputStream = new BufferedReader(
              new InputStreamReader(new FileInputStream("/home/joselucas/workspace/clientes.tsv"), StandardCharsets.UTF_8)
          );

          while ((line = inputStream.readLine()) != null) {
              // Ignora cabecalho
              if (line.toLowerCase().contains("nome_cliente")) continue;
  
              String[] value = line.split("\t");
              if (value.length < 3) {
                  System.out.println("Linha incompleta ignorada: " + line);
                  continue;
              }
  
              String nomeCliente = "'" + value[0].trim().replace("'", "''") + "'";
              String ruaCliente = "'" + value[1].trim().replace("'", "''") + "'";
              String cidadeCliente = "'" + value[2].trim().replace("'", "''") + "'";
  
              String insert = "INSERT INTO cliente (nome_cliente, rua_cliente, cidade_cliente) " +
                              "VALUES (" + nomeCliente + ", " + ruaCliente + ", " + cidadeCliente + ");";
  
              stmt.executeUpdate(insert);
          }
      } finally {
          if (stmt != null) stmt.close();
          if (inputStream != null) inputStream.close();
      }
    }
  

    public static void populateContas(Connection con) throws SQLException, IOException {
      Statement stmt = null;
      BufferedReader inputStream = null;
      String line;
  
      try {
          stmt = con.createStatement();
          stmt.executeUpdate("TRUNCATE TABLE conta CASCADE;");
  
          inputStream = new BufferedReader(
            new InputStreamReader(new FileInputStream("/home/joselucas/workspace/contas.tsv"), StandardCharsets.UTF_8)
          );

          while ((line = inputStream.readLine()) != null) {
              // Ignora cabecalho, se houver
              if (line.toLowerCase().contains("numero_conta")) continue;
  
              String[] value = line.split("\t");
  
              if (value.length < 4) {
                  System.out.println("Linha incompleta ignorada: " + line);
                  continue;
              }
  
              Integer numeroConta = Integer.parseInt(value[0]);
              String nomeAgencia = "'" + value[1].replace("'", "''") + "'";
              String nomeCliente = "'" + value[2].replace("'", "''") + "'";
              Integer saldoConta = Integer.parseInt(value[3]);
  
              String insert = "INSERT INTO conta (numero_conta, nome_agencia, nome_cliente, saldo_conta) " +
                      "VALUES (" + numeroConta + ", " + nomeAgencia + ", " + nomeCliente + ", " + saldoConta + ");";
  
              stmt.executeUpdate(insert);
          }
      } finally {
          if (stmt != null) stmt.close();
          if (inputStream != null) inputStream.close();
      }
    }

    public static void populateDepositos(Connection con) throws SQLException, IOException {
      Statement stmt = null;
      BufferedReader inputStream = null;
      String line;
  
      try {
          stmt = con.createStatement();
          stmt.executeUpdate("TRUNCATE TABLE deposito;");
  
          inputStream = new BufferedReader(
            new InputStreamReader(new FileInputStream("/home/joselucas/workspace/depositos.tsv"), StandardCharsets.UTF_8)
          );

          while ((line = inputStream.readLine()) != null) {
              // Ignora cabecalho
              if (line.toLowerCase().contains("numero_deposito")) continue;
  
              String[] value = line.split("\t");
              if (value.length < 6) {
                  System.out.println("Linha incompleta ignorada: " + line);
                  continue;
              }
  
              Integer numeroDeposito = Integer.parseInt(value[0]);
              Integer numeroConta = Integer.parseInt(value[1]);
              String nomeAgencia = "'" + value[2].replace("'", "''") + "'";
              String nomeCliente = "'" + value[3].replace("'", "''") + "'";
              String dataDeposito = value[4].isEmpty() ? "NULL" : "'" + value[4] + "'";
              Double saldoDeposito = Double.parseDouble(value[5]);
  
              String insert = "INSERT INTO deposito (numero_deposito, numero_conta, nome_agencia, nome_cliente, data_deposito, saldo_deposito) " +
                              "VALUES (" + numeroDeposito + ", " + numeroConta + ", " + nomeAgencia + ", " + nomeCliente + ", " + dataDeposito + ", " + saldoDeposito + ");";
  
              stmt.executeUpdate(insert);
          }
      } finally {
          if (stmt != null) stmt.close();
          if (inputStream != null) inputStream.close();
      }
    }
  

    public static void populateTable(Connection con) throws SQLException, IOException {
      Statement stmt = null;
      BufferedReader inputStream = null;
      Scanner scannedLine = null;
      String line;
      String[] value = new String[7];
  
      try {
          stmt = con.createStatement();
          stmt.executeUpdate("TRUNCATE TABLE debito;");
  
          inputStream = new BufferedReader(
            new InputStreamReader(new FileInputStream("/home/joselucas/workspace/debitos.tsv"), StandardCharsets.UTF_8)
          );

          while ((line = inputStream.readLine()) != null) {
            // Ignora o cabecalho do arquivo
            if (line.toLowerCase().contains("numero_debito")) continue;
        
            int countv = 0;
            scannedLine = new Scanner(line);
            scannedLine.useDelimiter("\t");
        
            while (scannedLine.hasNext()) {
                value[countv++] = scannedLine.next();
            }
            if (scannedLine != null) scannedLine.close();
        
            Integer numeroDebito = Integer.parseInt(value[0]);
            Double valorDebito = Double.parseDouble(value[1]);
            String motivoDebito = value[2].isEmpty() ? "NULL" : "'" + value[2].replace("'", "''") + "'";
            String dataDebito = value[3].isEmpty() ? "NULL" : "'" + value[3] + "'";
            Integer numeroConta = Integer.parseInt(value[4]);
            String nomeAgencia = "'" + value[5].replace("'", "''") + "'";
            String nomeCliente = "'" + value[6].replace("'", "''") + "'";
        
            String insert = "INSERT INTO debito (numero_debito, valor_debito, motivo_debito, data_debito, numero_conta, nome_agencia, nome_cliente) " +
                    "VALUES (" + numeroDebito + ", " + valorDebito + ", " + motivoDebito + ", " + dataDebito + ", " + numeroConta + ", " + nomeAgencia + ", " + nomeCliente + ");";
        
            System.out.println("INSERT GERADO >> " + insert); // opcional pra debug
            stmt.executeUpdate(insert);
        }        
      } catch (SQLException e) {
          JDBCUtilities.printSQLException(e);
      } catch (IOException e) {
          e.printStackTrace();
      } finally {
          if (stmt != null) stmt.close();
          if (inputStream != null) inputStream.close();
      }
    }

    public static void mostrarSaudeFinanceira(Connection con) throws SQLException {
      Statement stmt = null;
      String query =
          "SELECT " +
          "    d.numero_conta, d.nome_agencia, d.nome_cliente, " +
          "    COALESCE(SUM(dep.saldo_deposito), 0) AS total_depositos, " +
          "    COALESCE(SUM(deb.valor_debito), 0) AS total_debitos, " +
          "    COALESCE(SUM(dep.saldo_deposito), 0) - COALESCE(SUM(deb.valor_debito), 0) AS saldo_final " +
          "FROM conta d " +
          "LEFT JOIN deposito dep ON (d.numero_conta = dep.numero_conta AND d.nome_agencia = dep.nome_agencia AND d.nome_cliente = dep.nome_cliente) " +
          "LEFT JOIN debito deb ON (d.numero_conta = deb.numero_conta AND d.nome_agencia = deb.nome_agencia AND d.nome_cliente = deb.nome_cliente) " +
          "GROUP BY d.numero_conta, d.nome_agencia, d.nome_cliente " +
          "ORDER BY saldo_final DESC;";
  
      try {
          stmt = con.createStatement();
          ResultSet rs = stmt.executeQuery(query);
  
          System.out.println("---------------------------------------------------------------");
          System.out.println("Conta\tAgencia\t\tCliente\t\t\tSaldo Final (R$)");
          System.out.println("---------------------------------------------------------------");
  
          while (rs.next()) {
              int conta = rs.getInt("numero_conta");
              String agencia = rs.getString("nome_agencia");
              String cliente = rs.getString("nome_cliente");
              double saldo = rs.getDouble("saldo_final");
  
              System.out.printf("%-7d %-15s %-20s R$ %.2f%n", conta, agencia, cliente, saldo);
          }
      } catch (SQLException e) {
          JDBCUtilities.printSQLException(e);
      } finally {
          if (stmt != null) stmt.close();
      }
    }
  

    public static void main(String[] args) {
        JDBCUtilities myJDBCUtilities;
        Connection myConnection = null;

        if (args.length == 0 || args[0] == null) {
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

            // Executa os metodos
            populateClientes(myConnection);
            populateContas(myConnection);
            populateDepositos(myConnection);
            populateTable(myConnection);
            mostrarSaudeFinanceira(myConnection);

        } catch (SQLException | IOException e) {
            e.printStackTrace();
        } finally {
            JDBCUtilities.closeConnection(myConnection);
        }
    }
}
