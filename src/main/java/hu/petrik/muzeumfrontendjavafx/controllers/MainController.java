package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.*;
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
    @FXML
    private TableColumn<Statue, Integer> colHeight;
    @FXML
    private TableColumn<Statue, String> colPerson;
    @FXML
    private TableColumn<Statue, Integer> colPrice;
    @FXML
    private TableView<Statue> statueTableView;


    public void initialize(){
        colTitle.setCellValueFactory(new PropertyValueFactory<>("title"));
        colYear.setCellValueFactory(new PropertyValueFactory<>("year"));
        colOndDsplay.setCellValueFactory(new PropertyValueFactory<>("on_display"));
        paintingUpload();
        colHeight.setCellValueFactory(new PropertyValueFactory<>("height"));
        colPerson.setCellValueFactory(new PropertyValueFactory<>("person"));
        colPrice.setCellValueFactory(new PropertyValueFactory<>("price"));
        statueUpload();

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
            ModositPaintingController mod= (ModositPaintingController) ujAblak("modosit-painting-view","Painting Modification",320,400);
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
            alert("Select an item");
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

    @FXML
    public void newStatueButtonClick(ActionEvent actionEvent) {
        try {
            Controller add = ujAblak("hozzaad-statue-view.fxml","Add Statue",320,400);
            add.getStage().setOnCloseRequest(event -> statueUpload());
            add.getStage().show();
        }catch (Exception e){
            hibaKiir(e);
        }
    }

    private void statueUpload() {
        try {
            List<Statue> statueList = StatueApi.getStatues();
            statueTableView.getItems().clear();
            for (Statue statue:statueList){
                statueTableView.getItems().add(statue);
            }
        }catch (IOException e){
            hibaKiir(e);
        }
    }

    @FXML
    public void modStatueButtonClick(ActionEvent actionEvent) {

        int selectedIndex = statueTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("A módosításhoz válasszon ki egy elemet a táblázatból");
            return;
        }
        Statue modificion = statueTableView.getSelectionModel().getSelectedItem();
        try {
            ModositStatueController mod= (ModositStatueController) ujAblak("modosit-statue-view","Statue Modification",320,400);
            mod.setModositando(modificion);
            mod.getStage().setOnHiding(event -> statueTableView.refresh());
            mod.getStage().show();

        }catch (IOException e){
            hibaKiir(e);
        }
    }

    @FXML
    public void deleteStatueButtonClick(ActionEvent actionEvent) {
        int selectedIndex = statueTableView.getSelectionModel().getSelectedIndex();
        if (selectedIndex == -1){
            alert("Select an item");
            return;
        }
        Statue delete = statueTableView.getSelectionModel().getSelectedItem();
        if (!confirm("Are you sure?: "+delete.getPerson())){
            return;
        }
        try {
            boolean sikeres = StatueApi.deleteStatue(delete.getId());
            alert(sikeres ? "SUccesful delete": "Delete failed");
            statueUpload();
        } catch (IOException e) {
            hibaKiir(e);
        }
    }
}