/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.solucfx;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import tn.esprit.utils.FXRouter;

/**
 * FXML Controller class
 *
 * @author ksamih
 */
public class ClaimController implements Initializable {

    @FXML
    private AnchorPane anchore;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        FXRouter.when("new", "CreateClaim.fxml");
        FXRouter.setRouteContainer("new", anchore);
        
        FXRouter.when("myclaims", "myClaims.fxml");
        FXRouter.setRouteContainer("myclaims", anchore);
        try { 
            FXRouter.goTo("myclaims");
        } catch (IOException ex) {
            Logger.getLogger(ClaimController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }    

    @FXML
    private void createBtn(ActionEvent event) throws IOException {
        FXRouter.goTo("new");
    }

    @FXML
    private void show(ActionEvent event) throws IOException {
        FXRouter.goTo("myclaims"); 
        System.out.println("msg");
    }
    
}
