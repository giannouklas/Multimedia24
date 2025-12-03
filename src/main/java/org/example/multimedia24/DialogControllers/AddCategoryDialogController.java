package org.example.multimedia24.DialogControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.multimedia24.Models.Category;

import static org.example.multimedia24.Utilities.showInfoAlert;

public class AddCategoryDialogController {

    @FXML private Label AddCategoryErrorText;
    @FXML private TextField AddCatgeoryNameTextField;

    @FXML
    void onCreateCategoryButtonClick(ActionEvent event) {
        String message = Category.checkCreateAdd(AddCatgeoryNameTextField.getText());
        AddCategoryErrorText.setText(message);
        if (message == null){
            showInfoAlert("Success","Category created Successfully", "" );
            AddCatgeoryNameTextField.setText("");
        }
    }

}
