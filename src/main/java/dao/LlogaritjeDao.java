package dao;

import java.sql.SQLException;
import java.sql.Statement;
import java.text.DecimalFormat;
import java.time.LocalDate;

public class LlogaritjeDao extends DAO{

	public LlogaritjeDao() {

	}


	public String getLlogaritjePijeStafi(String perdoruesi, String pije, LocalDate dateFrom, LocalDate dateTo) throws SQLException {

		double llogaritja = 0.0;
		String query = "select sum(s.sasia) from shitje s join artikujt a on s.artikull_id = a.artikull_id join perdoruesit p on s.userid = p.userid " +
				"where s.staff = 1 and p.name ='"+perdoruesi+"' and a.emri_artikullit='"+pije+"' and s.created_date between '"+dateFrom+"' and '"+dateTo+"'; " ;
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);

		while(rs.next()) {
			llogaritja = rs.getDouble(1);
		}
		DecimalFormat df = new DecimalFormat("#.#");
		df.format(llogaritja);
		rs.close();
		stm1.close();
		return df.format(llogaritja);
	}

	public String getLlogaritjeXhiroPije(String perdoruesi, LocalDate dateFrom, LocalDate dateTo) throws SQLException {

		double llogaritja = 0.0;
		String query = "select sum(s.sasia*s.cmimi) from shitje s join artikujt a on s.artikull_id = a.artikull_id join perdoruesit p on s.userid = p.userid " +
				"where p.name ='"+perdoruesi+"' and s.created_date between '"+dateFrom+"' and '"+dateTo+"'; " ;
		Statement stm1 = connector.createStatement();
		rs = stm1.executeQuery(query);

		while(rs.next()) {
			llogaritja = rs.getDouble(1);
		}
		DecimalFormat df = new DecimalFormat("#.#");
		df.format(llogaritja);
		rs.close();
		stm1.close();
		return df.format(llogaritja);
	}

	public String getLlogaritjeXhiroPc(LocalDate dateFrom, LocalDate dateTo) throws SQLException {

		double llogaritja = 0.0;
		String query = "select sum(xhiro) from pspc where lloji ='PC' and created_date between '"+dateFrom+"' and '"+dateTo+"'";
		Statement stm = connector.createStatement();
		rs = stm.executeQuery(query);

		while(rs.next()) {
			llogaritja = rs.getDouble(1);
		}
		DecimalFormat df = new DecimalFormat("#.#");
		df.format(llogaritja);
		rs.close();
		stm.close();
		return df.format(llogaritja);
	}

	public String getLlogaritjeXhiroPs(LocalDate dateFrom, LocalDate dateTo) throws SQLException {

		double llogaritja = 0.0;
		String query = "select sum(xhiro) from pspc where lloji ='PS' and created_date between '"+dateFrom+"' and '"+dateTo+"'";
		Statement stm = connector.createStatement();
		rs = stm.executeQuery(query);

		while(rs.next()) {
			llogaritja = rs.getDouble(1);
		}
		DecimalFormat df = new DecimalFormat("#.#");
		df.format(llogaritja);
		rs.close();
		stm.close();
		return df.format(llogaritja);
	}


}
