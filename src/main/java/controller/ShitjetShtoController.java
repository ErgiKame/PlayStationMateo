package controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import dao.ControlDAO;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import model.Artikujt;
import model.Inventari;
import model.Perdoruesit;
import model.Shitje;
import utils.Combo;
import utils.HelperMethods;
import utils.Utils;

import java.net.URL;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ResourceBundle;

public class ShitjetShtoController implements Initializable {

    @FXML
    private JFXTextField txtSasia, txtCmimi;

    @FXML
    private JFXCheckBox checkStaff;

    @FXML
    private Label lblError;

    @FXML
    private JFXButton btnAnullo;

    @FXML
    private JFXComboBox<String> cmbArtikulli;

    private int shitje_id = 0;
    private double sasiaVjeter = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        HelperMethods.makeTextfieldDecimal(txtCmimi);
        HelperMethods.makeTextfieldDecimal(txtSasia);
        try {
            Combo.populate_combo(cmbArtikulli, ControlDAO.getControlDao().getArtikujtDao().getEmriArtikullit());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        if(ShitjetController.edit == true) {
            shitje_id = ShitjetController.shitjeDataHolder.getId();
            txtSasia.setText(ShitjetController.shitjeDataHolder.getSasia() + "");
            txtCmimi.setText(ShitjetController.shitjeDataHolder.getCmimi() + "");
            if(ShitjetController.shitjeDataHolder.getStaff() == 0)
                checkStaff.setSelected(false);
            else
                checkStaff.setSelected(true);
            cmbArtikulli.setValue(ShitjetController.shitjeDataHolder.getArtikull_id().getEmri_artikullit());
            sasiaVjeter = Double.parseDouble(txtSasia.getText());
        }
        else
            shitje_id = 0;

    }

    @FXML
    void anullo() {
        Utils.close_stage(btnAnullo);
    }

    @FXML
    void ruaj() throws Exception {
        if(HelperMethods.checkEmptyText(txtCmimi.getText() , txtSasia.getText())
                || HelperMethods.checkEmptyCombobox(cmbArtikulli)) {
            lblError.setText("Ploteso fushat e detyruara!*");
            return;
        }

        Perdoruesit perdoruesit = new Perdoruesit();
        perdoruesit.setUserid(Utils.idP);

        Artikujt artikujt = new Artikujt();
        artikujt.setEmri_artikullit(cmbArtikulli.getValue());
        artikujt.setArtikull_id(ControlDAO.getControlDao().getFurnizimDao().getIdFromArtikull(cmbArtikulli.getValue()));

        Shitje shitje = new Shitje();
        shitje.setArtikull_id(artikujt);
        shitje.setId(shitje_id);
        shitje.setCmimi(Double.parseDouble(txtCmimi.getText()));
        shitje.setSasia(Double.parseDouble(txtSasia.getText()));
        shitje.setVlera(shitje.getCmimi() * shitje.getSasia());
        shitje.setCreated_date(LocalDate.now().toString());
        shitje.setUserid(perdoruesit);
        if(checkStaff.isSelected())
            shitje.setStaff(1);
        else
            shitje.setStaff(0);

        Inventari inventari = new Inventari();
        inventari.setArtikull_id(artikujt);

        double gjendjaVjeter = ControlDAO.getControlDao().getInventariDao().getGjendja(artikujt);

        if(shitje_id == 0) {
            ControlDAO.getControlDao().getShitjeDao().addShitjet(shitje);
            inventari.setGjendja(String.valueOf(gjendjaVjeter - shitje.getSasia()));
            checkArtikujt(artikujt, inventari);
        }

        else {
            ControlDAO.getControlDao().getShitjeDao().updateShitje(shitje);
            inventari.setGjendja(String.valueOf(gjendjaVjeter - shitje.getSasia() + sasiaVjeter));
            checkArtikujt(artikujt, inventari);
        }


        Utils.close_stage(btnAnullo);
    }

    private void checkArtikujt(Artikujt artikujt, Inventari inventari) throws SQLException {
        if(ControlDAO.getControlDao().getFurnizimDao().checkArtikujInventar(artikujt.getArtikull_id()))
            ControlDAO.getControlDao().getInventariDao().updateGjendje(inventari);

        else
            ControlDAO.getControlDao().getInventariDao().addGjendje(inventari);
    }

}
