package hu.petrik.muzeumfrontendjavafx.controllers;

import hu.petrik.muzeumfrontendjavafx.Controller;
import hu.petrik.muzeumfrontendjavafx.Statue;
import hu.petrik.muzeumfrontendjavafx.StatueApi;
import javafx.event.ActionEvent;
import javafx.scene.control.Spinner;
import javafx.scene.control.TextField;

import java.io.IOException;


public class ModositStatueController extends Controller {
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerHeight;
    @javafx.fxml.FXML
    private Spinner<Integer> spinnerPrice;
    @javafx.fxml.FXML
    private TextField txtPerson;

    private Statue mod;

    @javafx.fxml.FXML
    public void modStatueButtonClick(ActionEvent actionEvent) {
        String person = txtPerson.getText().trim();
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
            price = spinnerPrice.getValue();
        } catch (NullPointerException ex){
            alert("Price is required");
            return;
        } catch (Exception ex){
            alert("Price have to between 1 and 9999999");
            return;
        }

        mod.setPerson(person);
        mod.setHeight(height);
        mod.setPrice(price);

        try {
            Statue modositott = StatueApi.modStatue(mod);
            if (modositott != null){
                alertWait("Modification successful");
                this.stage.close();
            } else {
                alert("Modification failed");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public Statue getModositando() {
        return mod;
    }

    public void setModositando(Statue mod) {
        this.mod = mod;
        valueSetting();
    }

    private void valueSetting() {
        txtPerson.setText(mod.getPerson());
        spinnerHeight.getValueFactory().setValue(mod.getHeight());
        spinnerPrice.getValueFactory().setValue(mod.getPrice());
    }
}

