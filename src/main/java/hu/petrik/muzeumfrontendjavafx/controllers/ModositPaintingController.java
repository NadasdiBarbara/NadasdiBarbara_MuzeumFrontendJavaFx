package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Painting;
import hu.petrik.muzeumfrontendjavafx.PaintingApi;
import javafx.event.ActionEvent;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;
import java.security.PublicKey;

public class ModositPaintingController extends Controller {
    @javafx.fxml.FXML
    private CheckBox ckboxModOnDisplay;
    @javafx.fxml.FXML
    private TextField textfieldModTitle;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerModYear;

    private Painting mod;

    @javafx.fxml.FXML
    public void modPaintingButtonClick(ActionEvent actionEvent) {
        String title = textfieldModTitle.getText().trim();
        boolean onDisplay = ckboxModOnDisplay.isSelected();
        int year;

        if (title.isEmpty()){
            alert("Title is required");
            return;
        }
        try {
            year = spinnerModYear.getValue();
        }catch (NullPointerException e){
            alert("Year is required");
            return;
        }catch (Exception e){
            alert("Year must be between -1000 and 2022");
            return;
        }

        mod.setTitle(title);
        mod.setYear(year);
        mod.setOn_display(onDisplay);

        try {
            Painting modi = PaintingApi.paintingModification(mod);
            if (modi != null){
                alertWait("Modification successful");
                this.stage.close();
            }else {
                alert("Modification failed");
            }
        }catch (IOException e){
            e.printStackTrace();
        }

    }
    public Painting getModication(){
        return mod;
    }
    public void setModification(Painting mod){
        this.mod = mod;
        valueSetting();

    }
    private void valueSetting(){
        textfieldModTitle.setText(mod.getTitle());
        spinnerModYear.getValueFactory().setValue(mod.getYear());
        ckboxModOnDisplay.setSelected(mod.isOn_display());
    }
}
