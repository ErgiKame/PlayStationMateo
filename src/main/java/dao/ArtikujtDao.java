package dao;

import javafx.scene.control.Alert.AlertType;
import model.Artikujt;
import model.Perdoruesit;
import utils.Utils;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;


public class ArtikujtDao extends DAO {

	public ArtikujtDao() {
		super();
	}
	
	public void addArtikujt(Artikujt artikujt) {
		try {
			String insert_artikujt= "INSERT INTO artikujt " +
				"(emri_artikullit, created_date) VALUES (?,?)";
		stm = connector.prepareStatement(insert_artikujt);

		stm.setString(1, artikujt.getEmri_artikullit());
		stm.setString(2, LocalDate.now().toString());

		stm.executeUpdate();
		stm.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	

	public List<Artikujt> viewArtikujt() throws SQLException{
		List<Artikujt> data = new ArrayList<Artikujt>();
		String query = "SELECT artikull_id, emri_artikullit FROM artikujt";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);
		
		while(rs.next()) {
			Artikujt artikujt = new Artikujt();
			artikujt.setArtikull_id(rs.getInt(1));
			artikujt.setEmri_artikullit(rs.getString(2));
			data.add(artikujt);
		}
		rs.close();
		stm1.close();
		return data;
	}
	
	public void deleteArtikull(int artikull_id) throws SQLException{
		String query = "DELETE FROM artikujt where artikull_id=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, artikull_id);
		
		stm.execute();
		stm.close();
		
	}

	public void updateArtikull(Artikujt artikujt) throws SQLException {
		String update = "UPDATE artikujt SET emri_artikullit = ?WHERE artikull_id = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, artikujt.getEmri_artikullit());
		
		stm.setInt(2, artikujt.getArtikull_id());
		
		stm.executeUpdate();
		stm.close();
	}

	public List<String> getEmriArtikullit() throws SQLException{
		List<String> data = new ArrayList<>();
		String query = "Select emri_artikullit from artikujt ORDER BY emri_artikullit;";

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
