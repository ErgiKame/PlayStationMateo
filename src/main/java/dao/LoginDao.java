package dao;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import model.Perdoruesit;
import utils.Utils;


public class LoginDao extends DAO {

	public LoginDao() {
		super();
	}
	
	
	public String get_pass() throws SQLException {
		String pass = "";
		String sql = "SELECT password FROM perdoruesit  where userid = '"+Utils.idP+"' ";
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(sql);
		
		while(rs.next()) {
			pass =  Utils.decrypt(Utils.key, Utils.initVector, rs.getString(1));
		}
		stm1.close();
		rs.close();
		return pass;
	}
	
	public boolean check_user_and_pass(String user,String pass) {
		String sql = "SELECT username, password, userid , rights, name from perdoruesit  WHERE username = ? and password = ? ";

		try {
			PreparedStatement stm1 = connector.prepareStatement(sql);
			stm1.setString(1, user);
			stm1.setString(2, pass);
			rs = stm1.executeQuery();
			
			boolean areValid = false;

			while(rs.next()) {
				Utils.username = rs.getString(1);
				Utils.idP = rs.getInt(3);
				Utils.rights = rs.getString(4);
				Utils.name = rs.getString(5);
				areValid = rs.getString(1).equals(user) && rs.getString(2).equals(pass);
				rs.close();
				stm1.close();
				return areValid;
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		return false;

	}
	
	public void update_password(Perdoruesit p) throws SQLException {
		String update_password = "update perdoruesit set password = ? where userid = ?";

		stm = connector.prepareStatement(update_password);
		stm.setString(1, Utils.encrypt(Utils.key, Utils.initVector,p.getPassword()));
		stm.setInt(2, Utils.idP);

		stm.executeUpdate();
		stm.close();
	}
	
}
