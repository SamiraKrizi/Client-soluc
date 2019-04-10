/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.PieChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import tn.esprit.entities.EtatReclamation;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.TypeReclamation;
import tn.esprit.iservices.ReclamationFacadeRemote;
import tn.esprit.utils.Proxy;

/**
 * FXML Controller class
 *
 * @author ksamih
 */
public class DashboardController implements Initializable {

    @FXML
    private TableView<Reclamation> tbData;
    @FXML
    private TableColumn<Reclamation, String> date;
    @FXML
    private TableColumn<Reclamation, String> username;
    @FXML
    private TableColumn<Reclamation, String> type;
    @FXML
    private TableColumn<Reclamation, String> state;

    public static ObservableList<Reclamation> claimsTable;
    @FXML
    private Button showBtn;
    public static Reclamation recAdmin = new Reclamation();
    @FXML
    private Label total;
    @FXML
    private Label system;
    @FXML
    private Label financial;
    @FXML
    private Label service;

    /*
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        loadData();
        showBtn.setDisable(true);
        Proxy proxy = new Proxy();
        ReclamationFacadeRemote rec = proxy.getReclamationProxy();
        List<Reclamation> reclamations = rec.All();
        
        int countF = 0, countSer = 0, CountSys = 0, countSugg = 0, CountOther = 0;

        for (Reclamation R : reclamations) {
                CountOther++;
             if (R.getType() == TypeReclamation.Financier) {
                countF++;
            } else if (R.getType() == TypeReclamation.Service) {
                countSer++;
            } else if (R.getType() == TypeReclamation.Systeme) {
                CountSys++;
            } else if (R.getType() == TypeReclamation.Suggestion) {
                countSugg++;
            }
            
            total.setText(Integer.toString(CountOther));
            system.setText(Integer.toString(CountSys));
            financial.setText(Integer.toString(countF));
            service.setText(Integer.toString(countSer));
        }
    }

    @FXML
    private void showClaimBtn(ActionEvent event) throws IOException {

        recAdmin = this.getSelected();
        if (recAdmin.getEtat() != EtatReclamation.Traite) {
            System.out.println("to send " + this.getSelected());
            FXMLLoader loader = new FXMLLoader();
            final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/ReplyAdmin.fxml"));
            Scene scene = new Scene(root);
            //hide actual window
            final Node source = (Node) event.getSource();
            final Stage ThisStage = (Stage) source.getScene().getWindow();
            ThisStage.close();
            Stage stage = new Stage();
            stage.setTitle("Reply to the claim ");
            stage.setScene(scene);
            stage.show();
        } else {

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("Warning");
            alert.setContentText("You have alrady responded to this claim");

            ButtonType buttonTypeOk = new ButtonType("OK");
            ButtonType buttonTypeCancel = new ButtonType("Cancel", ButtonData.CANCEL_CLOSE);

            alert.getButtonTypes().setAll(buttonTypeOk, buttonTypeCancel);
            Optional<ButtonType> option = alert.showAndWait();

            if (option.get() == null) {

            } else if (option.get() == buttonTypeOk) {
                FXMLLoader loader = new FXMLLoader();
                final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/ReplyAdmin.fxml"));
                Scene scene = new Scene(root);
                //hide actual window
                final Node source = (Node) event.getSource();
                final Stage ThisStage = (Stage) source.getScene().getWindow();
                ThisStage.close();
                Stage stage = new Stage();
                stage.setTitle("Reply to the claim ");
                stage.setScene(scene);
                stage.show();

            } else if (option.get() == buttonTypeCancel) {

            } else {

            }

        }

    }

    private void init() {
        date.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        state.setCellValueFactory(new PropertyValueFactory<>("etat"));
        username.setCellValueFactory(new PropertyValueFactory<>("username"));
    }

    private void loadData() {
        Proxy proxy = new Proxy();
        ReclamationFacadeRemote rec = proxy.getReclamationProxy();
        List<Reclamation> reclamations = rec.All();
        claimsTable = FXCollections.observableArrayList(reclamations);
        tbData.setItems(claimsTable);
    }

    @FXML
    private void clickClaim(MouseEvent event) {
        showBtn.setDisable(false);
    }

    private Reclamation getSelected() {
        return tbData.getSelectionModel().getSelectedItem();
    }

    @FXML
    private void stats(ActionEvent event) {

        Proxy proxy = new Proxy();
        ReclamationFacadeRemote rec = proxy.getReclamationProxy();
        List<Reclamation> reclamations = rec.All();

        //Financier, Service, Systeme, Suggestion, Autre
        int countF = 0, countSer = 0, CountSys = 0, countSugg = 0, CountOther = 0;

        for (Reclamation R : reclamations) {
            if (R.getType() == TypeReclamation.Autre) {
                CountOther++;
            } else if (R.getType() == TypeReclamation.Financier) {
                countF++;
            } else if (R.getType() == TypeReclamation.Service) {
                countSer++;
            } else if (R.getType() == TypeReclamation.Systeme) {
                CountSys++;
            } else if (R.getType() == TypeReclamation.Suggestion) {
                countSugg++;
            }
        }

        Stage stage = new Stage();
        Scene scene = new Scene(new Group());
        stage.setTitle("Stats");
        stage.setWidth(500);
        stage.setHeight(500);

        ObservableList<PieChart.Data> pieChartData
                = FXCollections.observableArrayList(
                        new PieChart.Data("Autre", CountOther),
                        new PieChart.Data("Financier", countF),
                        new PieChart.Data("Service", countSer),
                        new PieChart.Data("Systeme", CountSys),
                        new PieChart.Data("Suggestion", countSugg));
        final PieChart chart = new PieChart(pieChartData);
        chart.setTitle("Claim Stats");

        ((Group) scene.getRoot()).getChildren().add(chart);
        stage.setScene(scene);
        stage.show();
    }

}
