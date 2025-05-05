import java.sql.*;
public class MyQueries {
  public static void main(String[] args) {
    String url = "jdbc:postgresql://localhost:5432/IB2";
    String user = "postgres";
    String password = "postgres";
    try {
      Class.forName("org.postgresql.Driver");
      System.out.println("Tentando conectar ao banco...");
      Connection con = DriverManager.getConnection(url, user, password);
      System.out.println("Conexao bem-sucedida!");
      recuperarContas(con);
      con.close();
    } catch (Exception e) { e.printStackTrace(); }
  }
  public static void recuperarContas(Connection con) {
    String query = "SELECT ct.numero_conta, ag.nome_agencia, ct.saldo_conta AS saldo " +
                   "FROM conta ct " +
                   "JOIN agencia ag ON ct.nome_agencia = ag.nome_agencia";
    try (PreparedStatement pstmt = con.prepareStatement(query);
         ResultSet rs = pstmt.executeQuery()) {
      System.out.println("Resultados da consulta:");
      while (rs.next()) {
        int numeroConta = rs.getInt("numero_conta");
        String nomeAgencia = rs.getString("nome_agencia");
        double saldo = rs.getDouble("saldo");
        System.out.println("Conta: " + numeroConta + ", Agencia: " + nomeAgencia + ", Saldo: " + saldo);
      }
    } catch (SQLException e) { e.printStackTrace(); }
  }
}
