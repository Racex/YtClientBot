package controller;

import javafx.collections.ObservableList;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by Maciek on 2017-02-10.
 */
public abstract class ElementsController {
    public TextField adres_linku;
    public TextField ilosc_wyswietlen;
    public TextField ilosc_komentarzy;
    public TextField ilosc_likow;
    public TextField adres_maliny;
    public TextArea massage;
    public static ArrayList<TextField> requiredElements = new ArrayList<TextField>();
    public static ArrayList<TextField> notRequiredElements = new ArrayList<TextField>();

    public boolean elementsIsNotEmpty() {
        boolean isNotEmpty = true;
        if (requiredElements.isEmpty() || notRequiredElements.isEmpty())
            prepareAllTextField();
        if (isTextFieldEmpty()) {
            isNotEmpty = false;
            massage.setText("Wypełnij wymagane pola");
            massage.setVisible(true);
        }
        return isNotEmpty;
    }


    public boolean isTextFieldEmpty() {
        int count = 0;
        for (TextField x : requiredElements) {
            if (!x.getText().equals("")) {
                setFrame(x, false);
                count++;
            } else {
                setFrame(x, true);
            }
            if (count == 2) {
                for (TextField y : notRequiredElements) {
                    System.out.println(y.toString());

                    if (!y.getText().equals("")) {
                        setFrame(y, false);
                        return false;
                    } else {
                        setFrame(y, true);
                    }
                }
            }
        }
        return true;
    }

    private void setFrame(TextField text, Boolean status) {
        ObservableList<String> styleClass = text.getStyleClass();
        if (status) {
            if (!styleClass.contains("error")) styleClass.add("error");
        } else {
            massage.setVisible(false);
            styleClass.removeAll(Collections.singleton("error"));
        }
    }

    private void prepareAllTextField() {
        requiredElements.add(adres_maliny);
        requiredElements.add(adres_linku);
        notRequiredElements.add(ilosc_wyswietlen);
        notRequiredElements.add(ilosc_komentarzy);
        notRequiredElements.add(ilosc_likow);
    }
}
