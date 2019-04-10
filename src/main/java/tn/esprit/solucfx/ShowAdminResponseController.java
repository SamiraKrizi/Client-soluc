/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.text.Text;
import tn.esprit.entities.ReponseReclamation;
import tn.esprit.iservices.ReponseReclamationFacadeRemote;
import static tn.esprit.solucfx.myClaimsController.reclamation;
import tn.esprit.utils.Proxy;

/**
 * FXML Controller class
 *
 * @author ksamih
 */
public class ShowAdminResponseController implements Initializable {

    @FXML
    private Text textClaim;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        Proxy proxy = new Proxy();
        ReponseReclamationFacadeRemote service = proxy.getResponseReclamationProxy();

        ReponseReclamationFacadeRemote serviceResponse = proxy.getResponseReclamationProxy();
        System.out.println(reclamation);
        ReponseReclamation response = service.getResponse(reclamation);
        if (response.getIdReponse() != 0) {
            textClaim.setText(response.getDescription());
            System.out.println("reponse admin");
            
        } else System.out.println("nothing");

    }

}
