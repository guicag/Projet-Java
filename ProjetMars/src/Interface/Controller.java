package Interface;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import Equipements.Equipement;
import Equipements.Minerai;
import Partie.Jeu;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Controller {
	
    @FXML
    private Button btnJouer;

    @FXML
    private Button btnEdit;

    @FXML
    private Button btnQuitter;

    @FXML
    void ouvrirPageGame(ActionEvent event) {
         try {
        	 Parent root = FXMLLoader.load(getClass().getResource("game.fxml"));
             Stage stage = new Stage();
             stage.setScene(new Scene(root));
             stage.show();
             // Hide this current window (if this is what you want)
             ((Node)(event.getSource())).getScene().getWindow().hide();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }

    @FXML
    void ouvrirPageEdit(ActionEvent event) {
    	System.out.println("edit");
    }

    @FXML
    void fermerApp(ActionEvent event) {
    	System.out.println("quitter");
    	((Node)(event.getSource())).getScene().getWindow().hide();
    }
   
}
