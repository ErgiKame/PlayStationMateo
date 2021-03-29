package dao;

import model.Artikujt;
import model.Perdoruesit;
import model.Pspc;
import model.Shitje;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class PspcDao extends DAO {

    public PspcDao() {
        super();
    }

    public void addPspc(Pspc pspc){
        try {
            String insert_shitje= "INSERT INTO pspc " +
                    "(lloji, xhiro, koment, created_date) VALUES (?,?,?,?)";
            stm = connector.prepareStatement(insert_shitje);

            stm.setString(1, pspc.getLloji());
            stm.setDouble(2, pspc.getXhiro());
            stm.setString(3, pspc.getKoment());
            stm.setString(4, LocalDate.now().toString());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Pspc> viewPspc(LocalDate date_from, LocalDate date_to) throws SQLException{

        List<Pspc> data = new ArrayList<Pspc>();
        String query = "SELECT id, lloji, xhiro, koment, created_date FROM pspc " +
                " WHERE created_date between '"+date_from+"' and '"+date_to+"'";
        Statement stm = connector.createStatement();
        rs = stm.executeQuery(query);

        while(rs.next()) {

            Pspc pspc = new Pspc();
            pspc.setId(rs.getInt(1));
            pspc.setCreated_date(rs.getString(5));
            pspc.setLloji(rs.getString(2));
            pspc.setXhiro(rs.getDouble(3));
            pspc.setKoment(rs.getString(4));
            data.add(pspc);
        }
        return data;
    }

    public void updatePspc(Pspc pspc) throws SQLException {
        String update = "UPDATE pspc SET created_date = ?, " +
                " lloji = ?,  xhiro = ?,  koment = ?  WHERE id = ?";
        stm = connector.prepareStatement(update);

        stm.setString(1, pspc.getCreated_date().toString());
        stm.setString(2, pspc.getLloji());
        stm.setDouble(3, pspc.getXhiro());
        stm.setString(4, pspc.getKoment());

        stm.setInt(5, pspc.getId());

        stm.executeUpdate();
        stm.close();
    }

    public void deletePspc(int id) throws SQLException{
        String query = "DELETE FROM pspc where id=?";
        stm = connector.prepareStatement(query);
        stm.setInt(1, id);

        stm.execute();
        stm.close();

    }

    public List<String> getLloji() throws SQLException{
        List<String> data = new ArrayList<>();
        String query = "Select lloji from pspc ORDER BY lloji;";

        Statement stm1 = connector.createStatement();
        rs = stm1.executeQuery(query);

        while(rs.next()) {
            String emri = rs.getString(1);
            data.add(emri);
        }
        rs.close();
        stm1.close();
        return data;
    }
}
