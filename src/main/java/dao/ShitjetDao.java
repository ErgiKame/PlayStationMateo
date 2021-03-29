package dao;

import model.Artikujt;
import model.Perdoruesit;
import model.Shitje;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class ShitjetDao extends DAO {

    public ShitjetDao() {
        super();
    }

    public DecimalFormat decimalFormat = new DecimalFormat("#.##");

    public void addShitjet(Shitje shitje) {
        try {
            String insert_shitje= "INSERT INTO shitje " +
                    "(artikull_id, created_date, sasia, cmimi, staff, userid) VALUES (?,?,?,?,?,?)";
            stm = connector.prepareStatement(insert_shitje);

            stm.setInt(1, shitje.getArtikull_id().getArtikull_id());
            stm.setString(2, LocalDate.now().toString());
            stm.setDouble(3, shitje.getSasia());
            stm.setDouble(4, shitje.getCmimi());
            stm.setInt(5, shitje.getStaff());
            stm.setInt(6, shitje.getUserid().getUserid());

            stm.executeUpdate();
            stm.close();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Shitje> viewShitje(LocalDate date_from, LocalDate date_to) throws SQLException{

        List<Shitje> data = new ArrayList<Shitje>();
        String query = "SELECT s.id, a.artikull_id, s.created_date, s.sasia, s.cmimi, p.userid, " +
                "p.name, a.emri_artikullit, s.staff  FROM shitje s " +
                "INNER JOIN perdoruesit p on p.userid = s.userid INNER JOIN artikujt a on " +
                "a.artikull_id = s.artikull_id WHERE s.created_date between '"+date_from+"' and '"+date_to+"'";
        Statement stm = connector.createStatement();
        rs = stm.executeQuery(query);

        while(rs.next()) {
            Perdoruesit perdoruesit = new Perdoruesit();
            perdoruesit.setUserid(rs.getInt(6));
            perdoruesit.setName(rs.getString(7));

            Artikujt artikujt = new Artikujt();
            artikujt.setArtikull_id(rs.getInt(2));
            artikujt.setEmri_artikullit(rs.getString(8));

            Shitje shitje = new Shitje();
            shitje.setId(rs.getInt(1));
            shitje.setCreated_date(rs.getString(3));
            shitje.setSasia(rs.getDouble(4));
            shitje.setCmimi(rs.getDouble(5));
            shitje.setUserid(perdoruesit);
            shitje.setArtikull_id(artikujt);
            shitje.setVlera(Double.parseDouble(decimalFormat.format(shitje.getSasia() * shitje.getCmimi())));
            shitje.setStaff(rs.getInt(9));

            data.add(shitje);
        }
        return data;
    }

    public void updateShitje(Shitje shitje) throws SQLException {
        String update = "UPDATE shitje SET artikull_id = ?,  created_date = ?, " +
                " sasia = ?,  cmimi = ?,  staff = ?  WHERE id = ?";
        stm = connector.prepareStatement(update);

        stm.setInt(1, shitje.getArtikull_id().getArtikull_id());
        stm.setString(2, shitje.getCreated_date().toString());
        stm.setDouble(3, shitje.getSasia());
        stm.setDouble(4, shitje.getCmimi());
        stm.setInt(5, shitje.getStaff());

        stm.setInt(6, shitje.getId());

        stm.executeUpdate();
        stm.close();
    }

    public void deleteShitje(int id) throws SQLException{
        String query = "DELETE FROM shitje where id=?";
        stm = connector.prepareStatement(query);
        stm.setInt(1, id);

        stm.execute();
        stm.close();

    }
}
