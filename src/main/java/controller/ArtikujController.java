package controller;

import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import dao.ControlDAO;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import model.Artikujt;
import model.Perdoruesit;
import utils.Utils;

import java.io.IOException;
import java.sql.SQLException;

public class ArtikujController extends VBox {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXButton btnAdd, btnEdit, btnPdf, btnExcel;

    @FXML
    private TableView<Artikujt> tblArtikujt;

    @FXML
    private TableColumn<?, ?> tblColEmri;

    @FXML
    private TableColumn<Artikujt, String> tblColArtikullId;

    @FXML
    private TableColumn<Artikujt, Artikujt> tblColDelete;

    private ObservableList<Artikujt> tableViewData = FXCollections.observableArrayList();

    public static Artikujt artikujtDataHolder = new Artikujt();

    public static boolean edit = false;

    public ArtikujController() {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/artikujt.fxml"));

        fxml.setRoot(this);
        fxml.setController(this);
        try {
            fxml.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        try {
            loadArtikujt();
            searchTableview();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void getData() throws IOException, SQLException {
        Artikujt artikujt = tblArtikujt.getSelectionModel().getSelectedItem();
        artikujtDataHolder.setEmri_artikullit(artikujt.getEmri_artikullit());
        artikujtDataHolder.setArtikull_id(artikujt.getArtikull_id());
        new Utils().openEditScene("artikujtShto", "username");
        loadArtikujt();
    }

    public void searchTableview() {
        txtSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {

                if(txtSearch.textProperty().get().isEmpty()) {
                    tblArtikujt.setItems(tableViewData);
                    return;
                }

                ObservableList<Artikujt> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Artikujt, ?>> cols = tblArtikujt.getColumns();

                for(int i=0; i<tableViewData.size(); i++) {
                    for(int j=0; j<6; j++) {
                        TableColumn<Artikujt, ?> col = cols.get(j);
                        String cellValue = col.getCellData(tableViewData.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if(cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                            tableItems.add(tableViewData.get(i));
                            break;
                        }
                    }
                }

                tblArtikujt.setItems(tableItems);
            }
        });
    }

    private void loadArtikujt() throws SQLException {
        tblArtikujt.getItems().clear();
        tableViewData.addAll(ControlDAO.getControlDao().getArtikujtDao().viewArtikujt());
        tblColEmri.setCellValueFactory(new PropertyValueFactory<>("emri_artikullit"));
        tblColArtikullId.setCellValueFactory(new PropertyValueFactory<>("artikull_id"));

        tblColDelete.setStyle("-fx-alignment:center;");
        tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tblColDelete.setCellFactory(param -> new TableCell<Artikujt, Artikujt>() {

            Button delete = new Button("");
            protected void updateItem(Artikujt artikujt, boolean empty) {
                super.updateItem(artikujt, empty);

                if (artikujt == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(delete);
                Utils.style_delete_button(delete);
                delete.setOnMouseClicked(event -> {
                    JFXAlert alert = new JFXAlert((Stage) tblArtikujt.getScene().getWindow());
                    JFXButton anullo = new JFXButton("Anullo");
                    anullo.setStyle("-fx-background-color: #B74636; -fx-text-fill: white;-fx-cursor: hand;");
                    JFXButton konfirmo = new JFXButton("Konfirmo");
                    konfirmo.setStyle("-fx-background-color: #4186CE; -fx-text-fill: white;-fx-cursor: hand;");
                    Utils.alert_fshirje2(alert, konfirmo, anullo, false, "");

                    konfirmo.setOnAction(e-> {
                        delete(artikujt.getArtikull_id());
                        alert.close();
                    });
                    anullo.setOnAction( e1 -> {
                        alert.close();
                    });
                    Utils.refresh_focus_table(tblArtikujt);
                });
            }
        });


        tblArtikujt.setItems(tableViewData);

    }

    private void delete(int artikullId) {
        try {
            ControlDAO.getControlDao().getArtikujtDao().deleteArtikull(artikullId);
            loadArtikujt();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    @FXML
    void add() throws SQLException, IOException {
        edit = false;
        new Utils().openEditScene("artikujtShto", "username");
        loadArtikujt();
    }

    @FXML
    void edit() throws IOException, SQLException {
        edit = true;
        if(tblArtikujt.getSelectionModel().getSelectedItem() != null) {
            getData();
        }

        else
            Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", Alert.AlertType.WARNING);

    }
}
