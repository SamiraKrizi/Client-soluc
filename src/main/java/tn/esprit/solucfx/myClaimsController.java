/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.EtatReclamation;
import tn.esprit.entities.Reclamation;
import tn.esprit.iservices.ReclamationFacadeRemote;
import tn.esprit.utils.Proxy;

/**
 * FXML Controller class
 *
 * @author ksamih
 */
public class myClaimsController implements Initializable {

    @FXML
    private AnchorPane anchore;
    @FXML
    private TableView<Reclamation> claimTab;
    @FXML
    private TableColumn<Reclamation, String> date, state, type;
    public static ObservableList<Reclamation> tableData;
    @FXML
    private Button deletBtn;
    public static Reclamation reclamation = new Reclamation();
    @FXML
    private Button showResBtn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        init();
        //tableData.clear();
        loadData();
        deletBtn.setDisable(true);
        showResBtn.setDisable(true);
    }

    @FXML
    private void deleteButton(ActionEvent event) {
        Proxy proxy = new Proxy();
        ReclamationFacadeRemote rec = proxy.getReclamationProxy();

        rec.remove(this.getIdSelection());
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success");
        alert.setContentText("claim deleted :( ");
        alert.showAndWait();
        tableData.clear();
        loadData();

        deletBtn.setDisable(true);
        showResBtn.setDisable(true);
    }

    @FXML
    private void editButton(ActionEvent event) throws IOException {
        System.out.println(this.getIdSelection());
        reclamation = this.getIdSelection();
        FXMLLoader loader = new FXMLLoader();
        final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/EditClaim.fxml"));
        Scene scene = new Scene(root);

        Stage stage = new Stage();
        stage.setTitle("Edit your claim");
        stage.setScene(scene);

        //hide actual window
        final Node source = (Node) event.getSource();
        final Stage stage1 = (Stage) source.getScene().getWindow();
        stage1.close();
        stage.show();

        ///////////////////////////
    }

    @FXML
    private void showClaims(MouseEvent event) {
        deletBtn.setDisable(false);
        showResBtn.setDisable(false);
    }

    private void init() {
        date.setCellValueFactory(new PropertyValueFactory<>("dateReclamation"));
        type.setCellValueFactory(new PropertyValueFactory<>("type"));
        state.setCellValueFactory(new PropertyValueFactory<>("etat"));
    }

    private void loadData() {
        Proxy proxy = new Proxy();
        ReclamationFacadeRemote rec = proxy.getReclamationProxy();
        List<Reclamation> reclamations = rec.All();
        tableData = FXCollections.observableArrayList(reclamations);
        claimTab.setItems(tableData);
    }

    public Reclamation getIdSelection() {
        Reclamation R = claimTab.getSelectionModel().getSelectedItem();
        deletBtn.setDisable(false);
        showResBtn.setDisable(false);
        return R;
    }

    @FXML
    private void responseBtn(ActionEvent event) throws IOException {
        showResBtn.setDisable(true);
        if (getIdSelection().getEtat() == (EtatReclamation.Traite)) {

            reclamation = this.getIdSelection();
            System.out.println("treated");
            FXMLLoader loader = new FXMLLoader();
            final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/ShowAdminResponse.fxml"));
            Scene scene = new Scene(root);

            Stage stage = new Stage();
            stage.setTitle("The admin response");
            stage.setScene(scene);
            stage.show();

        }
        else{
            System.out.println("no");
        }

    }

}
