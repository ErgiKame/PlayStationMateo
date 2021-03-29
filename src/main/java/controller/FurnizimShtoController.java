package controller;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.ResourceBundle;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;

import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Artikujt;
import model.Furnizim;
import model.Inventari;
import org.sqlite.SQLiteConfig;
import org.sqlite.date.FastDateFormat;
import utils.Combo;
import utils.HelperMethods;

public class FurnizimShtoController implements Initializable{

	@FXML private JFXTextField txtSasia,txtCmimi;
	@FXML private JFXButton btnAnullo;
	@FXML private Label lblError;
	@FXML private JFXComboBox<String> cmbEmriArtikullit;
	
	private int furnizimId = 0;
	private double sasiaVjeter = 0;

	Date now = new Date();
	String date = FastDateFormat.getInstance(SQLiteConfig.DEFAULT_DATE_STRING_FORMAT).format(now);

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		HelperMethods.makeTextfieldDecimal(txtCmimi);
		HelperMethods.makeTextfieldDecimal(txtSasia);
		
		try {
			Combo.populate_combo(cmbEmriArtikullit, ControlDAO.getControlDao().getArtikujtDao().getEmriArtikullit());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		if(FurnizimController.edit == true) {
			furnizimId = FurnizimController.furnizimDataHolder.getId();
			getData(FurnizimController.furnizimDataHolder);
		}
		else
			furnizimId = 0;
	}

	private void getData(Furnizim f) {
		cmbEmriArtikullit.setValue(f.getArtikull_id().getEmri_artikullit());
		txtSasia.setText(f.getSasia() + "");
		txtCmimi.setText(f.getCmimi() + "");
		sasiaVjeter = Double.parseDouble(txtSasia.getText());
	}
	
	@FXML
	public void ruaj() throws Exception {
		if(HelperMethods.checkEmptyText(txtSasia.getText(), txtCmimi.getText()) || HelperMethods.checkEmptyCombobox(cmbEmriArtikullit)) {
			lblError.setText("Ploteso fushat e detyruara!*");
			return;
		}
		
		Artikujt artikujt = new Artikujt();
		artikujt.setEmri_artikullit(cmbEmriArtikullit.getValue());
		artikujt.setArtikull_id(ControlDAO.getControlDao().getFurnizimDao().getIdFromArtikull(cmbEmriArtikullit.getValue()));
		artikujt.setCreatedDate(LocalDate.now().toString());

		Furnizim furnizim = new Furnizim();
		furnizim.setId(furnizimId);
		furnizim.setArtikull_id(artikujt);
		furnizim.setCmimi(Double.parseDouble(txtCmimi.getText()));
		furnizim.setSasia(Double.parseDouble(txtSasia.getText()));
		furnizim.setCreated_date(LocalDate.now().toString());

		Inventari inventari = new Inventari();
		inventari.setArtikull_id(artikujt);
		
		double gjendjaVjeter = ControlDAO.getControlDao().getInventariDao().getGjendja(artikujt);
		
		if(furnizimId == 0) {
			ControlDAO.getControlDao().getFurnizimDao().addFurnizim(furnizim);
			inventari.setGjendja(String.valueOf(gjendjaVjeter + furnizim.getSasia()));
			checkArtikujt(artikujt, inventari);
		}
		else {
			ControlDAO.getControlDao().getFurnizimDao().updateFurnizim(furnizim);
			inventari.setGjendja(String.valueOf(gjendjaVjeter + furnizim.getSasia() - sasiaVjeter));
			checkArtikujt(artikujt, inventari);
		}
		
		HelperMethods.closeStage(btnAnullo);
		
	}
	
	private void checkArtikujt(Artikujt artikujt, Inventari inventari) throws SQLException {
		if(ControlDAO.getControlDao().getFurnizimDao().checkArtikujInventar(artikujt.getArtikull_id()))
			ControlDAO.getControlDao().getInventariDao().updateGjendje(inventari);

		else
			ControlDAO.getControlDao().getInventariDao().addGjendje(inventari);
	}

	@FXML
	private void anullo() {
		HelperMethods.closeStage(btnAnullo);
	}
}
