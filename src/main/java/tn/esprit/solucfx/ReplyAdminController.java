/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import tn.esprit.entities.EtatReclamation;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.iservices.ReclamationFacadeRemote;
import tn.esprit.iservices.ReponseReclamationFacadeRemote;
import static tn.esprit.solucfx.DashboardController.recAdmin;
import tn.esprit.utils.Proxy;

/**
 * FXML Controller class
 *
 * @author ksamih
 */
public class ReplyAdminController implements Initializable {

    @FXML
    private TextArea adminResponse;
    @FXML
    private Text claimer;
    @FXML
    private Text claimType;
    @FXML
    private Text claimContent;
    @FXML
    private Button reply;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {

        //System.out.println("rec recieved " +recAdmin.getDescription());
        System.out.println("rec " + recAdmin);
        claimContent.setText(recAdmin.getDescription());
        claimType.setText(recAdmin.getType().toString());

        Proxy proxy = new Proxy();
        ReponseReclamationFacadeRemote service = proxy.getResponseReclamationProxy();

        if (recAdmin.getEtat() == EtatReclamation.Traite) {
            ReponseReclamation response = service.getResponse(recAdmin);
            if (response.getIdReponse() != 0) {
                adminResponse.setText(response.getDescription());
                System.out.println(response.getDescription());
                reply.setText("update");
            }
        }

    }

    @FXML
    private void replyBtn(ActionEvent event) throws IOException {
        Proxy proxy = new Proxy();
        ReponseReclamationFacadeRemote service = proxy.getResponseReclamationProxy();

        String contString = adminResponse.getText();

        ReponseReclamationFacadeRemote serviceResponse = proxy.getResponseReclamationProxy();
        if (recAdmin.getEtat() == EtatReclamation.Traite) {
            ReponseReclamation response = serviceResponse.getResponse(recAdmin);
            if (response.getIdReponse() != 0) {
                System.out.println("edit");

                //ReclamationFacadeRemote serviceRec = proxy.getReclamationProxy();
               // serviceRec.edit(recAdmin);
                // recRep.setReclamation(recAdmin);
               // service.edit(response);
                response.setDescription(contString);
                Alert alert = new Alert(AlertType.CONFIRMATION);
                alert.setHeaderText("Success");
                alert.setContentText("Reply updates with success to this claim");
                alert.showAndWait();
            }
        } else {
            recAdmin.setEtat(EtatReclamation.Traite);
            System.out.println("adding");
            ReponseReclamation recRep = new ReponseReclamation();
            recRep.setDescription(contString);
            ReclamationFacadeRemote serviceRec = proxy.getReclamationProxy();
            serviceRec.edit(recAdmin);
            recRep.setReclamation(recAdmin);
            service.AddReponse(recRep);

            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setHeaderText("Success");
            alert.setContentText("Reply added with success to this claim");
            alert.showAndWait();
        }

        FXMLLoader loader = new FXMLLoader();
        final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/Dashboard.fxml"));
        Scene scene = new Scene(root);

        //hide actual window
        final Node source = (Node) event.getSource();
        final Stage ThisStage = (Stage) source.getScene().getWindow();
        ThisStage.close();

        Stage stage = new Stage();
        stage.setTitle("Reply to the claim ");
        stage.setScene(scene);
        stage.show();
    }

    @FXML
    private void Cancel(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/Dashboard.fxml"));
        Scene scene = new Scene(root);

        //hide actual window
        final Node source = (Node) event.getSource();
        final Stage ThisStage = (Stage) source.getScene().getWindow();
        ThisStage.close();

        Stage stage = new Stage();
        stage.setTitle("Reply to the claim ");
        stage.setScene(scene);
        stage.show();
    }

}
