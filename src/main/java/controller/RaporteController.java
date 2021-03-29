package controller;

import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXDatePicker;
import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import utils.Combo;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class RaporteController extends VBox {

    @FXML
    private JFXDatePicker dpFromXhiroPc, dpToXhiroPc, dpFromXhiroPije, dpToXhiroPije;

    @FXML
    private JFXDatePicker dpFromXhiroPs, dpToXhiroPs, dpFromPijeStafi, dpToPijeStafi;

    @FXML
    private JFXComboBox<String> cmbZgjidhUser, cmbZgjidhPije, cmbZgjidhUserXhiroPije;

    @FXML
    private Label vleraXhiroPc, vleraXhiroPije, vleraXhiroPs, vleraPijeStafi, vleraXhiroDitorePije;

    @FXML
    private Label lblError;

    public RaporteController() {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/raporte_backup.fxml"));

        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() throws SQLException {
        Combo.populate_combo(cmbZgjidhUserXhiroPije, ControlDAO.getControlDao().getPerdoruesitDao().getEmriPerdoruesit());
        Combo.populate_combo(cmbZgjidhPije, ControlDAO.getControlDao().getArtikujtDao().getEmriArtikullit());
        Combo.populate_combo(cmbZgjidhUser, ControlDAO.getControlDao().getPerdoruesitDao().getEmriPerdoruesit());
    }

    private void clearTexts(Label pijeStafi, Label... labels){
        pijeStafi.setText("Sasia eshte: ");
        for(Label l : labels) {
            l.setText("Vlera eshte: ");
        }
    }

    @FXML
    void llogaritPijeStafi() throws SQLException {
        clearTexts(vleraPijeStafi, vleraXhiroPc, vleraXhiroPije, vleraXhiroPs);
        if (Utils.check_datePickers(dpFromPijeStafi, dpToPijeStafi) || Utils.check_empty_combobox(cmbZgjidhPije, cmbZgjidhUser))  {
            lblError.setText("Ploteso fushat e detyruara!*");
            return;
        }

        vleraPijeStafi.setText("Sasia eshte: " + ControlDAO.getControlDao().getLlogaritjeDao().getLlogaritjePijeStafi(cmbZgjidhUser.getValue(),
                cmbZgjidhPije.getValue(), dpFromPijeStafi.getValue(), dpToPijeStafi.getValue()) + " cope");

    }

    @FXML
    void llogaritXhiroPc() throws SQLException {
        clearTexts(vleraPijeStafi, vleraXhiroPc, vleraXhiroPije, vleraXhiroPs);
        if (Utils.check_datePickers(dpFromXhiroPc, dpToXhiroPc))  {
            lblError.setText("Ploteso fushat e detyruara!*");
            return;
        }

        vleraXhiroPc.setText("Vlera eshte: " + ControlDAO.getControlDao().getLlogaritjeDao().getLlogaritjeXhiroPc(dpFromXhiroPc.getValue(), dpToXhiroPc.getValue()) + " ALL");

    }

    @FXML
    void llogaritXhiroPije() throws SQLException {
        clearTexts(vleraPijeStafi, vleraXhiroPc, vleraXhiroPije, vleraXhiroPs);
        if (Utils.check_datePickers(dpFromXhiroPije, dpToXhiroPije) || Utils.check_empty_combobox(cmbZgjidhUserXhiroPije))  {
            lblError.setText("Ploteso fushat e detyruara!*");
            return;
        }

        vleraXhiroPije.setText("Vlera eshte: " + ControlDAO.getControlDao().getLlogaritjeDao().getLlogaritjeXhiroPije(cmbZgjidhUserXhiroPije.getValue(), dpFromXhiroPije.getValue(), dpToXhiroPije.getValue()) + " ALL");
    }

    @FXML
    void llogaritXhiroPs() throws SQLException {
        clearTexts(vleraPijeStafi, vleraXhiroPc, vleraXhiroPije, vleraXhiroPs);
        if (Utils.check_datePickers(dpFromXhiroPs, dpToXhiroPs))  {
            lblError.setText("Ploteso fushat e detyruara!*");
            return;
        }

        vleraXhiroPs.setText("Vlera eshte: " + ControlDAO.getControlDao().getLlogaritjeDao().getLlogaritjeXhiroPs(dpFromXhiroPs.getValue(), dpToXhiroPs.getValue()) + " ALL");

    }


}
