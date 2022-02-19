package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Statue;
import hu.petrik.muzeumfrontendjavafx.StatueApi;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;

public class HozzadStatueController extends Controller {
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerHeight;
    @javafx.fxml.FXML
    private TextField textFieldPerson;
    @javafx.fxml.FXML
    private Spinner<Integer> spnnerPrice;

    @javafx.fxml.FXML
    public void addStatueButtonClick(ActionEvent actionEvent) {
        String person = textFieldPerson.getText().trim();
        int height, price;

        if (person.isEmpty()){
            alert("Person is required");
            return;
        }

        try {
            height = spinnerHeight.getValue();
        } catch (NullPointerException ex){
            alert("Height is required");
            return;
        } catch (Exception ex){
            alert("Height have to between 1 and 99999");
            return;
        }

        try {
            price = spnnerPrice.getValue();
        } catch (NullPointerException ex){
            alert("Price is required");
            return;
        } catch (Exception ex){
            alert("Price have to between 1 and 9999999");
            return;
        }

        try {
            Statue newStatue = new Statue(0, person, height, price);
            Statue newS = StatueApi.addStatue(newStatue);
            if (newS != null){
                alert("Added succesfully");
            } else {
                alert("Add failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
