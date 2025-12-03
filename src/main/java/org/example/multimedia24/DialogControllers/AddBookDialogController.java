package org.example.multimedia24.DialogControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.multimedia24.Models.Book;

import static org.example.multimedia24.Utilities.showInfoAlert;

public class AddBookDialogController {
    @FXML private Label AddBookErrorText;
    @FXML private TextField AddBookTitleTextField;
    @FXML private TextField AddBookAuthorTextField;
    @FXML private TextField AddBookPublisherTextField;
    @FXML private TextField AddBookYearTextField;
    @FXML private TextField AddBookNoofCopiesTextField;
    @FXML private TextField AddBookCategoryTextField;
    @FXML private TextField AddBookISBNTextField;

    @FXML
    void onAddBookButtonClick(ActionEvent event) {
        String message = Book.checkCreateAdd(AddBookTitleTextField.getText(), AddBookAuthorTextField.getText(), AddBookPublisherTextField.getText(),
                AddBookISBNTextField.getText(), AddBookYearTextField.getText(), AddBookNoofCopiesTextField.getText(), AddBookCategoryTextField.getText());
        AddBookErrorText.setText(message);
        if (message == null){
            showInfoAlert("Success","Book added Successfully", "" );
            AddBookTitleTextField.setText("");
            AddBookAuthorTextField.setText("");
            AddBookPublisherTextField.setText("");
            AddBookYearTextField.setText("");
            AddBookNoofCopiesTextField.setText("");
            AddBookCategoryTextField.setText("");
            AddBookISBNTextField.setText("");
        }
    }
}
