package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.Artikujt;
import model.Inventari;
public class InventariDao extends DAO {

	public InventariDao() {
		super();
	}

	public List<Inventari> getInventari() throws SQLException {
		List<Inventari> data = new ArrayList<Inventari>();
		String query = "select sum(i.gjendja), a.artikull_id, a.emri_artikullit "
				+ "from inventari i inner join artikujt a " +
				"on i.artikull_id = a.artikull_id group by a.emri_artikullit; ";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);

		while(rs.next()) {
			Artikujt artikujt = new Artikujt();
			artikujt.setArtikull_id(rs.getInt(2));
			artikujt.setEmri_artikullit(rs.getString(3));

			Inventari inventari = new Inventari();
			inventari.setArtikull_id(artikujt);
			inventari.setGjendja(rs.getString(1));
			
			data.add(inventari);
		}
		rs.close();
		stm1.close();
		return data;
	}
	
	public void addGjendje(Inventari inventari) throws SQLException {

		String insert_user = "INSERT INTO inventari " +
				"(artikull_id, gjendja) VALUES (?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setInt(1, inventari.getArtikull_id().getArtikull_id());
		stm.setString(2, inventari.getGjendja());

		stm.executeUpdate();
		stm.close();
	}
	
	public void updateGjendje(Inventari inventari) throws SQLException {
		String update_gjendja = "update inventari set gjendja = ? where artikull_id = ?";
		stm = connector.prepareStatement(update_gjendja);
		
		stm.setString(1, inventari.getGjendja());
		stm.setInt(2, inventari.getArtikull_id().getArtikull_id());
	
		stm.executeUpdate();
		stm.close();
	}

	public double getGjendja(Artikujt artikujt) throws SQLException {
		String sql_query = "select gjendja from inventari where artikull_id = '"+artikujt.getArtikull_id()+"'";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(sql_query);
		double gjendja = 0;
		while(rs.next()) 
			gjendja = rs.getDouble(1);
		rs.close();
		stm1.close();
		return gjendja;
	}

}
