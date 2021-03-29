package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.ControlDAO;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import model.*;
import utils.Combo;
import utils.HelperMethods;
import utils.Utils;

import javax.xml.soap.Text;
import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.*;
import java.util.ResourceBundle;

public class PspcShtoController implements Initializable  {

    @FXML
    private JFXTextField txtXhiro;

    @FXML
    private TextArea txtKoment;

    @FXML
    private Label lblError;

    @FXML
    private JFXButton btnAnullo;

    @FXML
    private JFXComboBox<String> cmbLloji;

    private int pspc_id = 0;

    List<String> lloji = new ArrayList<String>();


    @FXML
    private void anullo() {
        Utils.close_stage(btnAnullo);
    }

    @FXML
    private void ruaj() throws SQLException {
        if (Utils.check_empty_text(txtXhiro.getText(), cmbLloji.getValue())) {
            lblError.setText("Ploteso fushat e detyruara!*");
            return;
        }
        Pspc p = new Pspc();

        p.setLloji(cmbLloji.getValue());
        p.setKoment(txtKoment.getText());
        p.setCreated_date(LocalDate.now().toString());
        p.setXhiro(Double.parseDouble(txtXhiro.getText()));
        p.setId(pspc_id);

        if(pspc_id == 0) {
            ControlDAO.getControlDao().getPspcDao().addPspc(p);
        }
        else {
            ControlDAO.getControlDao().getPspcDao().updatePspc(p);
        }


        Utils.close_stage(btnAnullo);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HelperMethods.makeTextfieldDecimal(txtXhiro);
        cmbLloji.getItems().setAll("Play Station", "PC");
        if(PspcController.edit == true) {
            pspc_id = PspcController.pspcDataHolder.getId();
            cmbLloji.setValue(PspcController.pspcDataHolder.getLloji());
            txtXhiro.setText(PspcController.pspcDataHolder.getXhiro() + "");
            txtKoment.setText(PspcController.pspcDataHolder.getKoment());
        }
        else {
            cmbLloji.getItems().setAll("Play Station", "PC");
            pspc_id = 0;
        }
    }
}
