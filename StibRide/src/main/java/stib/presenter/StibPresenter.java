package stib.presenter;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import org.controlsfx.control.SearchableComboBox;
import org.w3c.dom.Text;
import stib.config.ConfigManager;
import stib.model.Graph;
import stib.model.Model;
import stib.model.Node;
import stib.model.dto.FavoriDto;
import stib.model.dto.StationDto;
import stib.model.exceptions.RepositoryException;
import stib.model.repository.FavoriRepository;
import stib.model.repository.StationRepository;

import java.sql.SQLOutput;
import java.util.List;

public class StibPresenter {
    private Model model;
    @FXML
    private SearchableComboBox<StationDto> origine;

    @FXML
    private SearchableComboBox<StationDto> destination;

    @FXML
    private Button searchBtn;

    @FXML
    private TableView routeTable;

    @FXML
    private TableColumn<Node, String> stationCol;

    @FXML
    private TableColumn<Node, String> lineCol;

    @FXML
    private Label rechercheTermineeLbl;

    @FXML
    private Label nbStationsLbl;

    @FXML
    private TextField nomFav;

    @FXML
    private Button favoriBtn;

    @FXML
    private ListView<FavoriDto> listFavori;

    @FXML
    private Button delBtn;

    @FXML
    private Button selBtn;

    @FXML
    private Button modifBtn;

    public void initialize() {
        //Chargement de la config
        try{
            ConfigManager.getInstance().load();
        }catch(Exception ex){
            System.out.println("Erreur lors du chargement de la config:" + ex.getMessage());
        }
        this.model = new Model();
        //Récupération des stations
        try{
            List<StationDto> all = model.getAllStations();
            origine.setItems(FXCollections.observableList(all));
            destination.setItems(FXCollections.observableList(all));
        }catch(Exception ex){
            System.out.println("Erreur lors du chargement des stations: " + ex.getMessage());
        }
        //Cacher les labels en premier lieu
        rechercheTermineeLbl.setVisible(false);
        nbStationsLbl.setVisible(false);

        //Ajotuer les valeurs au colonnes
        stationCol.setCellValueFactory(new PropertyValueFactory<>("station"));
        lineCol.setCellValueFactory(new PropertyValueFactory<>("lines"));

        searchBtn.setOnAction(actionEvent -> {
            routeTable.getItems().clear();
            StationDto origin_station = origine.getValue();
            StationDto destination_station = destination.getValue();

            Node origin = model.getGraphNode(origin_station.getKey());
            Node destination = model.getGraphNode(destination_station.getKey());

            model.calculateShortestPathFromSource(origin, destination);
            for (Node path : destination.getShortestPath()){
                routeTable.getItems().add(path);
            }
            //On ajoute la destination à la fin
            routeTable.getItems().add(destination);
            nbStationsLbl.setText("Nombre de stations : " + (destination.getShortestPath().size() + 1));

            rechercheTermineeLbl.setVisible(true);
            nbStationsLbl.setVisible(true);
        });
        try{
            //Plus facile de faire is un repo car besoin de faire des insertions etc
            FavoriRepository favoriRepository = new FavoriRepository();
            List<FavoriDto> favoris = model.getAllFavorites();
            listFavori.setItems(FXCollections.observableList(favoris));
            favoriBtn.setOnAction(actionEvent -> {
                if (!nomFav.getText().isEmpty()){
                    StationDto o = origine.getValue();
                    StationDto d = destination.getValue();
                    FavoriDto favori = new FavoriDto(nomFav.getText(), o.getKey(), d.getKey());
                    try {
                        favoriRepository.insert(favori);
                    } catch (RepositoryException ex) {
                        System.out.println("Erreur lors du chargement du comportement du bouton favori: " +
                                ex.getMessage());;
                    }
                }
                update();
            });

            modifBtn.setOnAction(actionEvent -> {
                StationDto o = origine.getValue();
                StationDto d = destination.getValue();
                FavoriDto selectionne = listFavori.getSelectionModel().getSelectedItem();
                if(selectionne != null){
                    FavoriDto nv = new FavoriDto(selectionne.getKey(), o.getKey(), d.getKey());
                    try {
                        favoriRepository.update(nv);
                    } catch (RepositoryException ex) {
                        System.out.println("Erreur lors du chargement du comportement du bouton modifier favori: " +
                                ex.getMessage());;
                    }
                }
                update();
            });

            delBtn.setOnAction(actionEvent -> {
                FavoriDto selectionne = listFavori.getSelectionModel().getSelectedItem();
                if(selectionne != null){
                    try {
                        favoriRepository.delete(selectionne.getKey());
                    } catch (RepositoryException ex) {
                        System.out.println("Erreur lors du chargement du comportement du bouton supprimer favori: " +
                                ex.getMessage());;
                    }
                }
                update();
            });

            selBtn.setOnAction(actionEvent -> {
                FavoriDto selectionne = listFavori.getSelectionModel().getSelectedItem();
                try {
                    StationRepository repo = new StationRepository();
                    if(selectionne != null){
                        origine.setValue(repo.get(selectionne.getOrigin()));
                        destination.setValue(repo.get(selectionne.getDestination()));
                    }
                } catch (RepositoryException ex) {
                    System.out.println("Erreur lors du chargement du comportement du bouton sélectionner favori: " +
                            ex.getMessage());;
                }
            });

        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }

    public void update(){
        try{
            List<FavoriDto> favoris = model.getAllFavorites();
            listFavori.setItems(FXCollections.observableList(favoris));
        }catch(Exception ex){
            System.out.println("Erreur lors de l'update: " + ex.getMessage());
        }
    }
}
