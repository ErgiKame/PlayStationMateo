package dao;

import java.sql.Statement;
import java.util.Date;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import model.Artikujt;
import model.Furnizim;
import org.sqlite.SQLiteConfig;
import org.sqlite.date.FastDateFormat;

public class FurnizimDao extends DAO {
	public FurnizimDao() {
		super();
	}

	public DecimalFormat decimalFormat = new DecimalFormat("#.##");
	
	public List<Furnizim> getFurnizim() throws SQLException {
		List<Furnizim> data = new ArrayList<Furnizim>();
		String query = "select f.id, f.sasia, f.cmimi, a.artikull_id, a.emri_artikullit, f.created_date " +
				"from furnizim f inner join artikujt a on f.artikull_id = a.artikull_id " +
				"group by f.id;";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);

		while(rs.next()) {
			Artikujt artikujt = new Artikujt();
			artikujt.setArtikull_id(rs.getInt(4));
			artikujt.setEmri_artikullit(rs.getString(5));
			
			Furnizim furnizim = new Furnizim();
			furnizim.setId(rs.getInt(1));
			furnizim.setSasia(rs.getDouble(2));
			furnizim.setCmimi(rs.getDouble(3));
			furnizim.setCreated_date(rs.getString(6));
			furnizim.setArtikull_id(artikujt);
			furnizim.setVlera(Double.parseDouble(decimalFormat.format(furnizim.getSasia() * furnizim.getCmimi())));
			
			data.add(furnizim);
		}
		rs.close();
		stm1.close();
		return data;
	}
	
	public void addFurnizim(Furnizim furnizim) throws SQLException {
		String insert_furnizim = "INSERT INTO furnizim " +
				"(sasia, cmimi, artikull_id, created_date) VALUES (?,?,?,?)";
		stm = connector.prepareStatement(insert_furnizim);

		stm.setDouble(1, furnizim.getSasia());
		stm.setDouble(2, furnizim.getCmimi());
		stm.setInt(3, furnizim.getArtikull_id().getArtikull_id());
		stm.setString(4, furnizim.getCreated_date().toString());

		stm.executeUpdate();
		stm.close();
	}

	public void updateFurnizim(Furnizim furnizim) throws SQLException {
		String update = "UPDATE furnizim SET sasia = ?, cmimi = ?, artikull_id = ? WHERE id = ?";
		stm = connector.prepareStatement(update);

		stm.setDouble(1, furnizim.getSasia());
		stm.setDouble(2, furnizim.getCmimi());
		stm.setInt(3, furnizim.getArtikull_id().getArtikull_id());
		
		stm.setInt(4, furnizim.getId());
		

		stm.executeUpdate();
		stm.close();
	}

	public void deleteFurnizim(int id) throws SQLException {
		String query = "DELETE FROM furnizim where id=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, id);

		stm.execute();
		stm.close();
		
	}
	
	public int getIdFromArtikull(String emri) throws Exception {
		String sql_query = "select artikull_id, emri_artikullit from artikujt where emri_artikullit = '"+emri+"'";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(sql_query);
		int id = 0;
		while(rs.next())
			id = rs.getInt(1);

		rs.close();
		stm1.close();

		return id;
	}
	
	public boolean checkArtikujInventar(int artikull_id) throws SQLException {
		String query = "select * from inventari where artikull_id = '"+artikull_id+"'";
		boolean artikull_exists = false;
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);
		if(rs.next() == true)
			artikull_exists = true;
		else
			artikull_exists = false;

		rs.close();
		stm1.close();

		return artikull_exists;
	}

}
