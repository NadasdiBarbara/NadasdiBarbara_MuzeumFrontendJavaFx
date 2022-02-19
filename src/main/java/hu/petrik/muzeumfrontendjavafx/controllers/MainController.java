package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Painting;
import hu.petrik.muzeumfrontendjavafx.PaintingApi;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.io.IOException;
import java.util.List;

public class MainController extends Controller {

    @FXML
    private TableView<Painting> paintingTable;
    @FXML
    private TableColumn<Painting, Boolean> colOndDsplay;
    @FXML
    private TableColumn<Painting, Integer> colYear;
    @FXML
    private TableColumn<Painting, String> colTitle;


    public void initialize(){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colOndDsplay.setCellValueFactory(new PropertyValueFactory<>("on_display"));
        paintingUpload();
    }
    @FXML
    public void modificationPaintingButtonClick(ActionEvent actionEvent) {
        int selectedIndex = paintingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A módosításhoz válasszon ki egy elemet a táblázatból");
            return;
        }
        Painting modificion = paintingTable.getSelectionModel().getSelectedItem();
        try {
            ModositController mod= (ModositController) ujAblak("modosit-painting-view","Painting Modification",320,400);
            mod.setModification(modificion);
            mod.getStage().setOnHiding(event -> paintingTable.refresh());
            mod.getStage().show();

        }catch (IOException e){
            hibaKiir(e);
        }

    }

    @FXML
    public void newPaintingButtonClick(ActionEvent actionEvent) {
        try {
            Controller add = ujAblak("hozzaad-painting-view.fxml","Add Painting",320,400);
            add.getStage().setOnCloseRequest(event -> paintingUpload());
            add.getStage().show();
        }catch (Exception e){
            hibaKiir(e);
        }
    }

    @FXML
    public void deletePaintingButtonClick(ActionEvent actionEvent) {
        int selectedIndex = paintingTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A törléshez válasszon ki egy elemet a táblázatból");
            return;
        }
        Painting delete = paintingTable.getSelectionModel().getSelectedItem();
        if (!confirm("Are you sure? " +delete.getTitle())){
            return;
        }try {
            boolean succes = PaintingApi.paintingDelete(delete.getId());
            alert(succes ? "SUccesful delete" : "Delete failed");
            paintingUpload();
        }catch (IOException e){
            hibaKiir(e);
        }
    }

    private void paintingUpload(){
        try {
            List<Painting> paintingList = PaintingApi.getPaintings();
            paintingTable.getItems().clear();
            for (Painting painting: paintingList){
                paintingTable.getItems().add(painting);
            }
        }catch (IOException e){
            hibaKiir(e);
        }
    }
}