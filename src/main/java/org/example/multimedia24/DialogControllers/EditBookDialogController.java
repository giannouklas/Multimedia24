package org.example.multimedia24.DialogControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.multimedia24.Models.Book;
import org.example.multimedia24.Utilities;

public class EditBookDialogController {
    private Book bookDisplayed;
    private Dialog<?> dialog;

    @FXML private Label ErrorText;
    @FXML private TextField bookAuthorText;
    @FXML private TextField bookISBNText;
    @FXML private TextField bookPublisherText;
    @FXML private TextField bookTitleText;
    @FXML private TextField bookYearText;
    @FXML private TextField noOfCopiesText;
    @FXML private TextField bookCategoryText;

    public void setDialog(Dialog<?> dialog) {
        this.dialog = dialog;
    }

    public void setBookData(Book book) {
        bookDisplayed = book;
        bookTitleText.setText(book.getTitle());
        bookAuthorText.setText(book.getAuthor());
        bookISBNText.setText(book.getISBN());
        bookPublisherText.setText(book.getPublisher());
        bookYearText.setText(String.valueOf(book.getYear()));
        noOfCopiesText.setText(String.valueOf(book.getNoOfCopies()));
        bookCategoryText.setText(book.getCategory());
    }

    @FXML
    public void onDeleteButtonClicked() {
        String m = Book.removeBook(bookDisplayed.getISBN());
        Utilities.showInfoAlert("Book Deletion","",m);
        if (dialog != null) {
            dialog.close();
        }
    }

    @FXML
    public void onSaveChangesButtonClicked() {
        String m = bookDisplayed.edit(bookTitleText.getText(),bookAuthorText.getText(), bookPublisherText.getText(), bookISBNText.getText(),
                bookYearText.getText(), noOfCopiesText.getText(), bookCategoryText.getText());
        if (m==null){
            Utilities.showInfoAlert("Book Edited", "Book Details have been updated","");
            if (dialog != null) {
                dialog.close();
            }
        }
        else
            ErrorText.setText(m);
    }

}
