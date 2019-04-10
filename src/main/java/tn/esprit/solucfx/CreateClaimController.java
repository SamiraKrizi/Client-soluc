/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
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
public class CreateClaimController implements Initializable {

    @FXML
    private AnchorPane anchore;
    @FXML
    private TextArea contentClaim;
    @FXML
    private ComboBox<String> typeClaim;
    @FXML
    private Button addClaim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        List<String> list = new ArrayList<>();
        list.add("Financier");
        list.add("Service");
        list.add("Systeme");
        list.add("Suggestion");
        list.add("Autre");
        typeClaim.getItems().addAll(list);
    }    

    @FXML
    private void addButton(ActionEvent event) {
        Proxy proxy = new Proxy();
        ReclamationFacadeRemote service = proxy.getReclamationProxy();

        String contString = contentClaim.getText();
        String element = typeClaim.getValue();
        if (contString.equals("") || typeClaim.getSelectionModel().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error");
            alert.setContentText("Missing data! please check your inputs");
            alert.showAndWait();
            return;
        }

        Reclamation rec = new Reclamation();
        if (element.equals("Financier")) {
            rec.setType(TypeReclamation.Financier);
        } else if (element.equals("Service")) {
            rec.setType(TypeReclamation.Service);
        } else if (element.equals("Systeme")) {
            rec.setType(TypeReclamation.Systeme);
        } else if (element.equals("Suggestion")) {
            rec.setType(TypeReclamation.Suggestion);
        } else if (element.equals("Autre")) {
            rec.setType(TypeReclamation.Autre);
        }
        rec.setDescription(contString);
        rec.setDateReclamation(new Date());
        rec.setEtat(EtatReclamation.EnAttente);
        service.create(rec);
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setHeaderText("Success");
        alert.setContentText("Now you have succesfully added a new claim");
        alert.showAndWait();
        
        typeClaim.setId("Financier");
        contentClaim.setText(""); 
        
    }
    
}
