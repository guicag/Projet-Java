package Interface;

import java.net.URL;
import java.util.ResourceBundle;

import Partie.Jeu;
import equipements.Equipement;
import equipements.Minerai;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class ControllerGame implements Initializable{
	
    @FXML
    private TableView<Equipement> listEquip;
    
    @FXML
    private TableView<Minerai> listMinerais;
    
    @FXML
    private Label carte;
    
	Jeu jeu;
	
    @FXML
    private Label score;

    @FXML
    private Label deplacement;
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
		jeu = new Jeu();
		
		carte.setText(jeu.getCarte().toString());
		carte.setFont(Font.font ("MonoSpaced", 17));
		carte.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
		
		score.setText(String.valueOf(jeu.getRobot().getScore()));
		score.setFont(Font.font("MonoSpaces", 20));
		score.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
		
		loadDataEquipement();
		loadDataMinerais();
		loadDataDeplacement();
		
	}
	
	public void loadDataEquipement() {
		TableColumn nomColonne = new TableColumn("Nom");
		nomColonne.setCellValueFactory(new PropertyValueFactory<>("nom"));

		TableColumn coutColonne = new TableColumn("Cout");
		coutColonne.setCellValueFactory(new PropertyValueFactory<>("cout"));
		
		TableColumn ratioColonne = new TableColumn("Ratio");
		ratioColonne.setCellValueFactory(new PropertyValueFactory<>("ratio"));

		listEquip.getColumns().addAll(nomColonne, ratioColonne, coutColonne);
		
		int sizeEquipements = jeu.getRobot().getEquipements_disponibles().size();
		for (int i = 0; i < sizeEquipements; i++) {
			listEquip.getItems().add(jeu.getRobot().getEquipements_disponibles().get(i));
		}
	}
	
	public void loadDataMinerais() {
		TableColumn symbolColonne = new TableColumn("Symbol");
		symbolColonne.setCellValueFactory(new PropertyValueFactory<>("caractere"));

		TableColumn nomColonne = new TableColumn("Nom");
		nomColonne.setCellValueFactory(new PropertyValueFactory<>("nom"));
		
		TableColumn valeurColonne = new TableColumn("Valeur");
		valeurColonne.setCellValueFactory(new PropertyValueFactory<>("valeur"));
		
		listMinerais.getColumns().addAll(symbolColonne, nomColonne, valeurColonne);
		
		int sizeMinerais = jeu.getCarte().getMinerais_disponibles().size();
		for (int i = 0; i < sizeMinerais; i++) {
			listMinerais.getItems().add(jeu.getCarte().getMinerais_disponibles().get(i));
		}
	}
	
	public void loadDataDeplacement() {
		int sizeListDeplacement = jeu.getParcoursRobot().size();
		String deplacements = "";
		if (sizeListDeplacement != 0) {
			for (int i = 0; i < sizeListDeplacement; i++) {
				deplacements += jeu.getParcoursRobot().get(i) + " ";
			}
		}
		deplacement.setText(deplacements);
		deplacement.setFont(Font.font("MonoSpaced", 15));
		deplacement.setBackground(new Background(new BackgroundFill(Color.rgb(255, 255, 255, 0.7), new CornerRadii(5.0), new Insets(-5.0))));
	}
}
