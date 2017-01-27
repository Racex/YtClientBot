package sample;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.json.JSONObject;

public class Controller {
    @FXML public TextField adres_linku;
    @FXML public TextField ilosc_wyswietlen;
    @FXML public TextField ilosc_komentarzy;
    @FXML public TextField ilosc_likow;
    @FXML public TextField adres_maliny;
    @FXML public ProgressBar progressBar;
//  private Task copyWorker;
    RestComunication restComunication = new RestComunication();

    @FXML public void send(ActionEvent actionEvent) {
//        copyWorker = createWorker();
        JSONObject json = new JSONObject();
        json.put("adres_linku" , adres_linku.getText());
        json.put("ilosc_wyswietlen" , ilosc_wyswietlen.getText());
        json.put("ilosc_komentarzy", ilosc_komentarzy.getText());
        json.put("ilosc_likow" , ilosc_likow.getText());
        restComunication.sendToRPI(adres_maliny.getText() , json);
//        progressBar.progressProperty().unbind();
//        progressBar.progressProperty().bind(copyWorker.progressProperty());
//        new Thread(copyWorker).start();
   System.out.println(json);
   }

//    public Task createWorker() {
//        return new Task() {
//            @Override
//            protected Object call() throws Exception {
//                for (int i = 0; i < 10; i++) {
//                    Thread.sleep(2000);
//                    updateMessage("2000 milliseconds");
//                    updateProgress(i + 1, 10);
//                       JSONObject jsonObject = restComunication.getStatusFromRPI();
//                    System.out.println(progressBar.getProgress());
//                }
//                return true;
//            }
//        };
//    }
}
