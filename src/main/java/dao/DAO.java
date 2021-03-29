package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class DAO {

	protected Connection connector = DatabaseConnection.connect();
    protected ResultSet rs;
    protected PreparedStatement stm;
	
	public DAO() {
		
	}
}
