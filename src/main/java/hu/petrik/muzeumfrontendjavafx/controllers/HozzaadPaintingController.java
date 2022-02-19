package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Painting;
import hu.petrik.muzeumfrontendjavafx.PaintingApi;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

public class HozzaadPaintingController extends Controller {
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerPaintingYear;
    @javafx.fxml.FXML
    private CheckBox checkboxPaintingOnDisplay;
    @javafx.fxml.FXML
    private TextField textFieldPaintingTitle;

    @javafx.fxml.FXML
    public void addNewPaintingButtonClick(ActionEvent actionEvent) {
        String title = textFieldPaintingTitle.getText().trim();
        int year;
        boolean onDisplay = checkboxPaintingOnDisplay.isSelected();

        if (title.isEmpty()){
            alert("Title is required");
            return;
        }
        try {
            year = spinnerPaintingYear.getValue();
        }catch (NullPointerException e){
            alert("Year is required");
            return;
        }catch (Exception e){
            alert("Year must be between -1000 and 2022");
            return;
        }

        try {
            Painting newPainting = new Painting(0, title,year,onDisplay);
            Painting newP = PaintingApi.paintingAdd(newPainting);
            if (newP != null){
                alert("Added succesfully");
            }else {
                alert("Add failed");
            }
        }catch (Exception e){
            hibaKiir(e);
        }


    }
}
