package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.Alert.AlertType;
import model.Perdoruesit;
import utils.Utils;


public class PerdoruesitDao extends DAO { 

	public PerdoruesitDao() {
		super();
	}
	
	public void addPerdorues(Perdoruesit user) {
		try {
			if(this.check_username(user.getUsername())) {
				Utils.alerti("Warning!", "Username exists!", AlertType.WARNING);
				return;
			}
		
		String insert_user = "INSERT INTO perdoruesit " + 
				"(username, password, name, rights, telefon, created_date) VALUES (?,?,?,?,?,?)";
		stm = connector.prepareStatement(insert_user);

		stm.setString(1, user.getUsername());
		stm.setString(2, Utils.encrypt(Utils.key, Utils.initVector, user.getUsername()));
		stm.setString(3, user.getName());
		stm.setString(4, user.getAccess());
		stm.setString(5, user.getTelefon());
		stm.setString(6, LocalDate.now().toString());
		
		
		stm.executeUpdate();
		stm.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	public boolean check_username(String username) throws SQLException {
		String query = "select * from perdoruesit where username = '"+username+"'";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);
		boolean exists = false;
		if(rs.next() == true)
			exists = true;
		else
			exists = false;

		rs.close();
		stm1.close();
		return exists;
	}
	
	public List<Perdoruesit> viewPerdoruesit() throws SQLException{
		List<Perdoruesit> data = new ArrayList<Perdoruesit>();
		String query = "SELECT userid, username, name, telefon, "
				+ "rights FROM perdoruesit";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);
		
		while(rs.next()) {
			Perdoruesit r = new Perdoruesit();
			r.setUserid(rs.getInt(1));
			r.setUsername(rs.getString(2));
			r.setName(rs.getString(3));
			r.setTelefon(rs.getString(4));
			r.setAccess(rs.getString(5));
			data.add(r);
		}
		rs.close();
		stm1.close();
		return data;
	}
	
	public void deletePerdoruesit(int userid) throws SQLException{
		String query = "DELETE FROM perdoruesit where userid=?";
		stm = connector.prepareStatement(query);
		stm.setInt(1, userid);
		
		stm.execute();
		stm.close();
		
	}

	public void updatePerdorues(Perdoruesit p) throws SQLException {
		String update = "UPDATE perdoruesit SET username = ?, name = ?,"
				+ " telefon = ?, rights = ?, password = ?, created_date = ? WHERE userid = ?";
		stm = connector.prepareStatement(update);

		stm.setString(1, p.getUsername());
		stm.setString(2, p.getName());
		stm.setString(3, p.getTelefon());
		stm.setString(4, p.getAccess());
		stm.setString(5, Utils.encrypt(Utils.key, Utils.initVector, p.getPassword()));
		stm.setString(6, LocalDate.now().toString());
		
		stm.setInt(7, p.getUserid());
		
		stm.executeUpdate();
		stm.close();
	}

	public List<String> getEmriPerdoruesit() throws SQLException{
		List<String> data = new ArrayList<>();
		String query = "Select name from perdoruesit ORDER BY name;";

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
