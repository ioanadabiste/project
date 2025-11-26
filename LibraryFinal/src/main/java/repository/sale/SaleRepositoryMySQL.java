package repository.sale;

import model.Sale;
import model.builder.SaleBuilder;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SaleRepositoryMySQL implements SaleRepository {
    private final Connection connection;
    public SaleRepositoryMySQL(Connection connection) {
        this.connection = connection;
    }

    public boolean createSale(Sale sale) {
        String sql = "INSERT INTO sale (book_id, quantity, price) VALUES (?, ?, ?)";

        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setLong(1, sale.getBookId());
            ps.setLong(2, sale.getQuantity());
            ps.setDouble(3, sale.getPrice());
            ps.executeUpdate();
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Sale> findAll(){
        List<Sale> sales = new ArrayList<>();
        String sql = "SELECT * FROM sale";
        try{
            Statement st=connection.createStatement();
            ResultSet rs=st.executeQuery(sql);

            while (rs.next()) {
                Sale sale = new SaleBuilder()
                        .setId(rs.getLong("id"))
                        .setBookId(rs.getLong("book_id"))
                        .setQuantity(rs.getLong("quantity"))
                        .setPrice(rs.getDouble("price"))
                        .setSaleDate(rs.getTimestamp("sale_date").toLocalDateTime())
                        .build();

                sales.add(sale);
            }

        }
        catch(SQLException e){
            e.printStackTrace();
        }
        return sales;
    }
}
