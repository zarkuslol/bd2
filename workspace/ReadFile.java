// package workspace;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class ReadFile {
    public static void main(String[] args) {
        BufferedReader inputStream = null;
        Scanner scannedLine = null;
        String line;
        String value[] = new String[7];
        int countv;

        try {
            inputStream = new BufferedReader(new FileReader("debitos.tsv"));
            while ((line = inputStream.readLine()) != null) {
                countv = 0;
                System.out.println("<<");
                scannedLine = new Scanner(line);
                scannedLine.useDelimiter("\t");
                while (scannedLine.hasNext()) {
                    System.out.println(value[countv++] = scannedLine.next());
                }
                if (scannedLine != null) { scannedLine.close(); }
                System.out.println(">>");
                System.out.println("insert into debito (numero_debito, valor_debito, motivo_debito, data_debito, numero_conta, nome_agencia, nome_cliente) values ("
                + value[0] 
                + ", " + value[1] 
                + ", " + value[2] 
                + ", " + value[3] 
                + ", " + value[4] 
                + ", " + value[5] 
                + ", " + value[6] 
                + ");");
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
