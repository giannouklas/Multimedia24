package org.example.multimedia24.DialogControllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import org.example.multimedia24.Models.Book;
import org.example.multimedia24.Models.Comment;
import org.example.multimedia24.CurrentUserInfo;
import org.example.multimedia24.Utilities;

import java.io.IOException;
import java.util.ArrayList;

public class ViewBookDialogController {
    private Book bookDisplayed;
    private Dialog<?> dialog;
    public void setDialog(Dialog<?> dialog) {
        this.dialog = dialog;
    }

    @FXML private Label averageRatingLabel;
    @FXML private Label bookAuthorLabel;
    @FXML private Label bookISBNLabel;
    @FXML private Label bookPublisherLabel;
    @FXML private Label bookTitleLabel;
    @FXML private Label bookYearLabel;
    @FXML private Label noOfCopiesLabel;
    @FXML private ListView<String> commentsListView;


    @FXML
    public void setBookData(Book book) {
        bookDisplayed = book;
        bookTitleLabel.setText(book.getTitle());
        bookAuthorLabel.setText(book.getAuthor());
        bookISBNLabel.setText(book.getISBN());
        averageRatingLabel.setText(String.format(book.getAverageRating()+"  ("+book.getNumberOfRatings()+")"));
        bookPublisherLabel.setText(book.getPublisher());
        bookYearLabel.setText(String.valueOf(book.getYear()));
        noOfCopiesLabel.setText(String.valueOf(book.getNoOfCopies()));
        initializeComments();
    }

    @FXML
    void onBorrowBookButtonClick() {
        CurrentUserInfo.getCurrentMember().borrowBook(bookDisplayed);
        if (dialog != null) {
            dialog.close();
        }
    }

    @FXML
    public void initializeComments(){
        ObservableList<Comment> lc = FXCollections.observableArrayList(Comment.getCommentsForSpecificBook(bookDisplayed.getISBN()));
        ObservableList<String> lcs = FXCollections.observableArrayList(new ArrayList<>());
        for (Comment c: lc){
            lcs.add(c.toString());
        }
        commentsListView.setItems(lcs);
    }

    @FXML
    void onLeaveFeedbackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/GiveFeedbackDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            GiveFeedbackDialogController controller = loader.getController();
            controller.setData(bookDisplayed);
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Give Feedback");
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initializeComments();
            setBookData(bookDisplayed);
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }
    }

}

