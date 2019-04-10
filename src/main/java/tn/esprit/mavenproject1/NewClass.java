/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tn.esprit.mavenproject1;

import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author ksamih
 */
public class NewClass extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        FXMLLoader loader = new FXMLLoader();
        //final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/Dashboard.fxml"));
        final Parent root = (Parent) loader.load(getClass().getResourceAsStream("/fxml/Claim.fxml"));
        Scene scene = new Scene(root);
        //scene.getStylesheets().add("/styles/Styles.css");
        
        stage.setTitle("JavaFX and Maven");
        stage.setScene(scene);
        stage.show();
    }

    /**
     * The main() method is ignored in correctly deployed JavaFX application.
     * main() serves only as fallback in case the application can not be
     * launched through deployment artifacts, e.g., in IDEs with limited FX
     * support. NetBeans ignores main().
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    /*
    public static void main(String[] args) throws NamingException {
        String jn = "Soluc-ejb/ReclamationFacade!tn.esprit.iservices.ReclamationFacadeRemote";
        Context context = new InitialContext();
        ReclamationFacadeRemote proxy = (ReclamationFacadeRemote) context.lookup(jn);

        Reclamation re = new Reclamation();
        re.setDescription("description");
        re.setEtat(EtatReclamation.EnAttente);

        proxy.create(re);
        System.out.println("reclamation " + re);
    }*/
}
