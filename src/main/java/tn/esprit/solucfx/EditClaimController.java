/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import tn.esprit.entities.EtatReclamation;
import tn.esprit.entities.Reclamation;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.entities.TypeReclamation;
import tn.esprit.iservices.ReclamationFacadeRemote;
import tn.esprit.iservices.ReponseReclamationFacadeRemote;
import static tn.esprit.solucfx.myClaimsController.reclamation;
import tn.esprit.utils.Proxy;

/**
 * FXML Controller class
 *
 * @author ksamih
 */
public class EditClaimController implements Initializable {

    @FXML
    private TextArea contentClaim;
    @FXML
    private ComboBox<String> typeClaim;
    @FXML
    private AnchorPane anchore;
    @FXML
    private Button btn;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        contentClaim.setText(reclamation.getDescription());
        List<String> list = new ArrayList<>();
        list.add("Financier");
        list.add("Service");
        list.add("Systeme");
        list.add("Suggestion");
        list.add("Autre");
        typeClaim.getItems().addAll(list);

        System.out.println(reclamation.getType() == TypeReclamation.Systeme);

        if (reclamation.getType() == (TypeReclamation.Autre)) {
            typeClaim.setValue("Autre");
        } else if (reclamation.getType() == (TypeReclamation.Financier)) {
            typeClaim.setValue("Financier");
        } else if (reclamation.getType() == TypeReclamation.Service) {
            typeClaim.setValue("Service");
        } else if (reclamation.getType() == TypeReclamation.Suggestion) {
            typeClaim.setValue("Suggestion");
        } else if (reclamation.getType() == (TypeReclamation.Systeme)) {
            typeClaim.setValue("Systeme");
        }

        Proxy proxy = new Proxy();
        ReponseReclamationFacadeRemote service = proxy.getResponseReclamationProxy();
        ReponseReclamation response = service.getResponse(reclamation);
        if (response.getIdReponse() != 0) {
            contentClaim.setEditable(false);
            btn.setText("Go back"); 
        }
        else{
            contentClaim.setEditable(true);
            btn.setText("Save changes");
        }
    }

    @FXML
    private void saveChangesBtn(ActionEvent event) throws IOException {

        Proxy proxy = new Proxy();
        ReclamationFacadeRemote service = proxy.getReclamationProxy();

        ReponseReclamationFacadeRemote service2 = proxy.getResponseReclamationProxy();
        ReponseReclamation response = service2.getResponse(reclamation);
        if (response.getIdReponse() != 0) {

            FXMLLoader loader = new FXMLLoader();
            final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/Claim.fxml"));

            //hide actual window
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            //load next window
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
            
        } else {
            String contString = contentClaim.getText();
            String element = typeClaim.getValue();
            if (contString.equals("")) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error");
                alert.setContentText("Missing data! please check your inputs");
                alert.showAndWait();
                return;
            }

            Reclamation rec = reclamation;
            System.out.println("claim " + rec);
            if (typeClaim.getValue().equals("Financier")) {
                rec.setType(TypeReclamation.Financier);
            } else if (typeClaim.getValue().equals("Service")) {
                rec.setType(TypeReclamation.Service);
            } else if (typeClaim.getValue().equals("Systeme")) {
                rec.setType(TypeReclamation.Systeme);
            } else if (typeClaim.getValue().equals("Suggestion")) {
                rec.setType(TypeReclamation.Suggestion);
            } else if (typeClaim.getValue().equals("Autre")) {
                rec.setType(TypeReclamation.Autre);
            }

            rec.setDescription(contString);
            rec.setEtat(EtatReclamation.EnAttente);
            System.out.println("edited " + rec);
            service.edit(rec);
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setHeaderText("Success");
            alert.setContentText("Now you have succesfully updated your claim");
            alert.showAndWait();

            FXMLLoader loader = new FXMLLoader();
            final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/Claim.fxml"));

            //hide actual window
            final Node source = (Node) event.getSource();
            final Stage stage = (Stage) source.getScene().getWindow();
            stage.close();

            //load next window
            Scene newScene = new Scene(root);
            Stage newStage = new Stage();
            newStage.setScene(newScene);
            newStage.show();
        }

    }

}
