package controller;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import model.Perdoruesit;
import utils.Utils;

public class PerdoruesitShtoController implements Initializable {

	@FXML
	private JFXTextField txtEmri, txtMbiemri, txtUsername, txtTelefon, txtEmail;
	
	@FXML
	private ComboBox<String> cmbTeDrejtat;
	
	@FXML
	private Label lblError;
	
	@FXML
	private JFXButton btnAnullo;
	
	
	private int userid = 0;
	
	@Override
	public void initialize(URL location, ResourceBundle resources) {
		cmbTeDrejtat.getItems().setAll("Admin", "User");
		
		if(PerdoruesitController.edit == true) {
			userid = PerdoruesitController.perdoruesitDataHolder.getUserid();
			txtUsername.setText(PerdoruesitController.perdoruesitDataHolder.getUsername());
			txtEmri.setText(PerdoruesitController.perdoruesitDataHolder.getName());
			txtTelefon.setText(PerdoruesitController.perdoruesitDataHolder.getTelefon());
			cmbTeDrejtat.setValue(PerdoruesitController.perdoruesitDataHolder.getAccess());

		}
		else {
			cmbTeDrejtat.getItems().setAll("Admin", "User");
			userid = 0;
		}
		
	}

	@FXML
	public void ruaj() throws SQLException {
		
		if (Utils.check_empty_text(txtUsername.getText(), cmbTeDrejtat.getValue())) {
			lblError.setText("Ploteso fushat e detyruara!*");
			return;
		}
		Perdoruesit p = new Perdoruesit();
		p.setUsername(txtUsername.getText());
		p.setPassword(txtUsername.getText());
		p.setName(txtEmri.getText());
		p.setTelefon(txtTelefon.getText());
		p.setAccess(cmbTeDrejtat.getValue());
		p.setUserid(userid);

		if(userid == 0) {
			ControlDAO.getControlDao().getPerdoruesitDao().addPerdorues(p);
		}
		else {
			ControlDAO.getControlDao().getPerdoruesitDao().updatePerdorues(p);
		}

		
		Utils.close_stage(btnAnullo);
		
	}
	
	@FXML
	public void anullo() {
		Utils.close_stage(btnAnullo);
	}
}
