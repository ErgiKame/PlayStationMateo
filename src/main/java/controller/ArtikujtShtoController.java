package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Artikujt;
import model.Perdoruesit;
import utils.Utils;

import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class ArtikujtShtoController implements Initializable {

    @FXML
    private JFXTextField txtEmri;

    @FXML
    private Label lblError;

    @FXML
    private JFXButton btnAnullo;

    private int artikull_id = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        if(ArtikujController.edit == true) {
            artikull_id = ArtikujController.artikujtDataHolder.getArtikull_id();
            txtEmri.setText(ArtikujController.artikujtDataHolder.getEmri_artikullit());
        }
        else {
            artikull_id = 0;
        }
    }

    @FXML
    void anullo() {
        Utils.close_stage(btnAnullo);
    }

    @FXML
    void ruaj() throws SQLException {

        Artikujt a = new Artikujt();
        a.setEmri_artikullit(txtEmri.getText());
        a.setArtikull_id(artikull_id);

        if(artikull_id == 0) {
            ControlDAO.getControlDao().getArtikujtDao().addArtikujt(a);
        }
        else {
            ControlDAO.getControlDao().getArtikujtDao().updateArtikull(a);
        }


        Utils.close_stage(btnAnullo);
    }
}
