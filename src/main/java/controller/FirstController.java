package controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import main.RestComunication;
import org.json.JSONObject;

import java.io.IOException;

public class FirstController extends ElementsController implements ControllerInter {
    @FXML
    public TextField adres_linku;
    @FXML
    public TextField ilosc_wyswietlen;
    @FXML
    public TextField ilosc_komentarzy;
    @FXML
    public TextField ilosc_likow;
    @FXML
    public TextField adres_maliny;
    @FXML
    public TextArea massage;
    RestComunication restComunication = new RestComunication();

    @FXML
    public void send(ActionEvent actionEvent) {

        if (checkIfElementsAreEmptyAndShowErrorOnUI()) {
            JSONObject json;
            json = new JSONObject();
            json.put("adres_linku", adres_linku.getText());
            json.put("ilosc_wyswietlen", ilosc_wyswietlen.getText());
            json.put("ilosc_komentarzy", ilosc_komentarzy.getText());
            json.put("ilosc_likow", ilosc_likow.getText());
            try {

                if (true) {//restComunication.sendToRPI(adres_maliny.getText(), json)) {
                    SecondController second = new SecondController().setWhatIsProcessed(json);
                    massage.getScene().setRoot(second.getContent());

                } else {
                    massage.setVisible(true);
                    massage.setText("Nie wysłano wiadomości");
                }
            } catch (Exception e) {
                massage.setText(e.getLocalizedMessage());
                massage.setVisible(true);
                e.printStackTrace();
            }
            System.out.println(json);
        }

    }

    public Parent getContent() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("../fxml/first.fxml"));
            root.getStylesheets().add(getClass().getResource("../css/errorButtonStyle.css").toExternalForm());
            return root;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
