import database.JDBConnectionWrapper;
import database.SQLTableCreationFactory;
import database.Constants;
import java.sql.Connection;
import java.sql.Statement;

public class CreateTablesExample {
    public static void main(String[] args) {
        try {
            // Creez o conexiune la baza de date
            Connection connection = new JDBConnectionWrapper("library").getConnection();

            // Creez obiectul care genereaza SQL-ul
            SQLTableCreationFactory factory = new SQLTableCreationFactory();
            Statement statement = connection.createStatement();

            // Parcurg toate tabelele definite in Constants.Tables
            for (String table : Constants.Tables.ORDERED_TABLES_FOR_CREATION) {
                String createSQL = factory.getCreateSQLForTable(table);
                statement.execute(createSQL);
                System.out.println("Tabel creat: " + table);
            }

            System.out.println("Toate tabelele au fost create cu succes!");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
