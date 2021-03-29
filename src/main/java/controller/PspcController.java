package controller;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.CMYKColor;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.jfoenix.controls.JFXAlert;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDatePicker;
import com.jfoenix.controls.JFXTextField;
import dao.ControlDAO;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Callback;
import model.Artikujt;
import model.Inventari;
import model.Pspc;
import model.Shitje;
import utils.HelperMethods;
import utils.Utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.Calendar;
import java.util.Date;

public class PspcController extends VBox {

    @FXML
    private JFXTextField txtSearch;

    @FXML
    private JFXDatePicker datePickerFrom, datePickerTo;

    @FXML
    private JFXButton btnAdd, btnEdit, btnExcel, btnPdf;

    @FXML
    private TableView<Pspc> tblPspc;

    @FXML
    private TableColumn<Pspc, String> tblColPspcId, tblColData, tblColLloji, tblColXhiro, tblColKoment;

    @FXML
    private TableColumn<Pspc, Pspc> tblColDelete;

    private ObservableList<Pspc> tableViewData = FXCollections.observableArrayList();

    public static Pspc pspcDataHolder = new Pspc();

    public static boolean edit = false;

    public PspcController() {
        FXMLLoader fxml = new FXMLLoader(getClass().getResource("/fxml/pspc.fxml"));

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
            HelperMethods.convertDatepicker(datePickerFrom, datePickerTo);
            datePickerTo.setValue(LocalDate.now());
            datePickerFrom.setValue(datePickerTo.getValue().minusDays(15));
            loadPspc();
            searchTableview();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void searchTableview() {
        txtSearch.textProperty().addListener(new InvalidationListener() {
            @Override
            public void invalidated(Observable o) {

                if (txtSearch.textProperty().get().isEmpty()) {
                    tblPspc.setItems(tableViewData);
                    return;
                }

                ObservableList<Pspc> tableItems = FXCollections.observableArrayList();
                ObservableList<TableColumn<Pspc, ?>> cols = tblPspc.getColumns();

                for (int i = 0; i < tableViewData.size(); i++) {
                    for (int j = 1; j < cols.size(); j++) {
                        TableColumn<Pspc, ?> col = cols.get(j);
                        String cellValue = col.getCellData(tableViewData.get(i)).toString();
                        cellValue = cellValue.toLowerCase();
                        if (cellValue.contains(txtSearch.textProperty().get().toLowerCase())) {
                            tableItems.add(tableViewData.get(i));
                            break;
                        }
                    }
                }

                tblPspc.setItems(tableItems);
            }
        });
    }

    private void loadPspc() throws SQLException {
        tblPspc.getItems().clear();
        tableViewData.addAll(ControlDAO.getControlDao().getPspcDao().viewPspc(datePickerFrom.getValue(), datePickerTo.getValue()));

        tblColPspcId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tblColData.setCellValueFactory(new PropertyValueFactory<>("created_date"));
        tblColLloji.setCellValueFactory(new PropertyValueFactory<>("lloji"));
        tblColXhiro.setCellValueFactory(new PropertyValueFactory<>("xhiro"));
        tblColKoment.setCellValueFactory(new PropertyValueFactory<>("koment"));
        tblColKoment.setCellFactory(tc -> {
		    TableCell<Pspc, String> cell = new TableCell<>();
		    Text text = new Text();
		    cell.setStyle("-fx-alignment:center;  -fx-text-fill: #B74636;");
		    cell.setGraphic(text);
		    cell.setPrefHeight(Control.USE_COMPUTED_SIZE);
		    text.wrappingWidthProperty().bind(tblColKoment.widthProperty());
		    text.textProperty().bind(cell.itemProperty());
		    return cell ;
		});
        tblColDelete.setStyle("-fx-alignment:center;");
        tblColDelete.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        tblColDelete.setCellFactory(param -> new TableCell<Pspc, Pspc>() {

            Button delete = new Button("");

            protected void updateItem(Pspc sh, boolean empty) {
                super.updateItem(sh, empty);

                if (sh == null) {
                    setGraphic(null);
                    return;
                }

                setGraphic(delete);
                Utils.style_delete_button(delete);

                delete.setOnMouseClicked(event -> {
                    JFXAlert alert = new JFXAlert((Stage) tblPspc.getScene().getWindow());
                    JFXButton anullo = new JFXButton("Anullo");
                    anullo.setStyle("-fx-background-color: #DA251E; -fx-text-fill: white;-fx-cursor: hand;");
                    JFXButton konfirmo = new JFXButton("Konfirmo");
                    konfirmo.setStyle("-fx-background-color: #0093DC; -fx-text-fill: white;-fx-cursor: hand;");
                    Utils.alert_fshirje(alert, "?", konfirmo, anullo, false, "");
                    konfirmo.setOnAction(e -> {
                        delete(sh.getId());
                        alert.close();
                    });
                    anullo.setOnAction(e1 -> {
                        alert.close();
                    });
                    HelperMethods.refresh_focus_table(tblPspc);
                });
            }
        });


        if(Utils.rights.contentEquals("User")) {
            btnEdit.setVisible(false);
            tblColDelete.setVisible(false);
        }

        tblPspc.setItems(tableViewData);

    }

    private void delete(int id) {
        try {
            ControlDAO.getControlDao().getPspcDao().deletePspc(id);
            loadPspc();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }


    @FXML
    private void add() throws IOException, SQLException {
        edit = false;
        new Utils().openEditScene("pspcShto", "sales");
        loadPspc();
    }

    @FXML
    private void edit() throws IOException, SQLException {
        edit = true;
        if (tblPspc.getSelectionModel().getSelectedItem() != null)
            getData();
        else
            Utils.alerti("Kujdes!", "Zgjidh nje rresht nga tabela!", Alert.AlertType.WARNING);
    }

    private void getData() throws IOException, SQLException {

        Pspc pspc = tblPspc.getSelectionModel().getSelectedItem();

        pspcDataHolder.setCreated_date(pspc.getCreated_date());
        pspcDataHolder.setLloji(pspc.getLloji());
        pspcDataHolder.setXhiro(pspc.getXhiro());
        pspcDataHolder.setKoment(pspc.getKoment());
        pspcDataHolder.setId(pspc.getId());

        new Utils().openEditScene("pspcShto", "sales");
        loadPspc();
    }


    @FXML
    private void filterDate() throws SQLException {
        loadPspc();
        searchTableview();
    }

    @FXML
    private void excel() {
        try {

            Stage stage = (Stage) btnExcel.getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("Comma Delimited (*.csv)", "*.csv");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(stage);
            FileWriter fileWriter = new FileWriter(file);

            String text = "";
            String header = "Nr" + "," + "Data" + "," + "Lloji" + "," + "Xhiro" + "," + "Koment" + "\n";

            fileWriter.write(header);
            for (int i = 0; i < tableViewData.size(); i++) {
                text = i + 1 + "," + tableViewData.get(i).getCreated_date() + ","
                        + tableViewData.get(i).getLloji() + "," + tableViewData.get(i).getXhiro()
                        + "," + tableViewData.get(i).getKoment()  +  "\n";
                fileWriter.write(text);
            }

            fileWriter.close();

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML
    private void pdf() {
        try {

            Stage stage = (Stage) btnExcel.getScene().getWindow();

            FileChooser fileChooser = new FileChooser();
            FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("PDF (*.PDF, *.pdf)", "*.pdf",
                    "*.PDF");
            fileChooser.getExtensionFilters().add(extFilter);

            File file = fileChooser.showSaveDialog(stage);

            Document document = new Document(PageSize.A4.rotate(), 5f, 5f, 5f, 5f);
            PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(file.getAbsolutePath()));

            document.open();

            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date date = new Date();
            Calendar cal = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm");

            Anchor anchorTarget = new Anchor("Data " + dateFormat.format(date) + " Ora " + sdf.format(cal.getTime()));

            Paragraph paragraph1 = new Paragraph();
            paragraph1.setAlignment(Paragraph.ALIGN_RIGHT);
            paragraph1.setSpacingBefore(15);
            paragraph1.setSpacingAfter(15);

            paragraph1.add(anchorTarget);
            document.add(paragraph1);

            Paragraph p2 = new Paragraph("Game Zone Zogu Zi",
                    FontFactory.getFont(FontFactory.TIMES, 18, Font.BOLD, new CMYKColor(169, 169, 169, 0)));
            p2.setAlignment(Paragraph.ALIGN_CENTER);
            p2.setSpacingBefore(20);
            document.add(p2);
            Paragraph p3 = new Paragraph("PS/PC",
                    FontFactory.getFont(FontFactory.TIMES, 14, Font.BOLD, new BaseColor(183, 70, 54)));
            p3.setAlignment(Paragraph.ALIGN_CENTER);
            p3.setSpacingBefore(25);
            document.add(p3);

            PdfPTable t = new PdfPTable(4);
            t.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            t.setSpacingBefore(30);
            t.setWidthPercentage(95);
            t.setSpacingAfter(5);
            Font bold = new Font(Font.FontFamily.HELVETICA, 14, Font.BOLD);

            Phrase phrase1 = new Phrase("Data", bold);
            PdfPCell c1 = new PdfPCell(phrase1);
            c1.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(c1);
            Phrase phrase2 = new Phrase("Lloji", bold);
            PdfPCell c2 = new PdfPCell(phrase2);
            c2.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(c2);
            Phrase phrase3 = new Phrase("Xhiro", bold);
            PdfPCell c3 = new PdfPCell(phrase3);
            c3.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(c3);
            Phrase phrase4 = new Phrase("Koment", bold);
            PdfPCell c4 = new PdfPCell(phrase4);
            c4.setHorizontalAlignment(Element.ALIGN_CENTER);
            t.addCell(c4);

            for (Pspc tablePdf : tableViewData) {
                t.addCell(tablePdf.getCreated_date());
                t.addCell(tablePdf.getLloji());
                t.addCell(String.valueOf(tablePdf.getXhiro()));
                t.addCell(tablePdf.getKoment());

            }
            document.add(t);

            PdfPTable table = new PdfPTable(1);
            table.setWidthPercentage(95);
            table.setSpacingBefore(50);
            table.getDefaultCell().setUseAscender(true);
            table.getDefaultCell().setUseDescender(true);
            document.add(table);
            document.close();
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


}
