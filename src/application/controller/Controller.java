package application.controller;

import application.config.GenericDAO;
import application.config.IGenericDAO;
import application.model.Service;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    private TextField host;

    @FXML
    private TextField port;

    @FXML
    private Label status;

    @FXML
    private ListView<String> servicesList;

    private IGenericDAO genericDAO;

    private Stage newServiceStage;

    @FXML
    private void createNewService() {
        Platform.runLater(() -> {
            Parent root = null;
            newServiceStage = new Stage();

            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/application/view/new.fxml"));
                loader.setController(this);
                root = loader.load();
            } catch (IOException e) {
                e.printStackTrace();
            }

            Scene scene = new Scene(root, 215, 220);
            newServiceStage.setScene(scene);
            newServiceStage.showAndWait();
        });
    }

    @FXML
    private void removeService() {
        if ( servicesList.getSelectionModel().getSelectedItem() != null ) {
            List<Service> list = genericDAO.list(Service.class);
            list.forEach(item -> {
                if ( servicesList.getSelectionModel().getSelectedItem().equals(item.getHost() + ":" + item.getPort()) ) {
                    genericDAO.remove(item);
                    fillServicesList();
                }
            });
        }
    }


    @FXML
    private void okAction() {
        if ( !"".equals(host.getText()) && !"".equals(port.getText()) ) {
            genericDAO.add(new Service(host.getText(), port.getText()));
            fillServicesList();
        }

        ((Stage) host.getScene().getWindow()).close();
    }

    private void fillServicesList() {
        List<Service> serviceListFromDB =  genericDAO.list(Service.class);
        List<String> finalList = new ArrayList<>();

        serviceListFromDB.forEach(item -> finalList.add(item.getHost() + ":" + item.getPort()));
        servicesList.refresh();
        servicesList.setItems(FXCollections.observableArrayList(finalList));
    }

    public void initialize(URL url, ResourceBundle resourceBundle) {
        genericDAO = new GenericDAO();

        fillServicesList();

        servicesList.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {

        });
    }
}
