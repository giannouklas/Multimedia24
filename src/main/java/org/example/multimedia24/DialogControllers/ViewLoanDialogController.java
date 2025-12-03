package org.example.multimedia24.DialogControllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import org.example.multimedia24.Models.Book;
import org.example.multimedia24.Models.Loan;
import org.example.multimedia24.Utilities;

import java.io.IOException;

public class ViewLoanDialogController {
    private Dialog<?> dialog;
    private Book bookLoaned;
    public void setDialog(Dialog<?> dialog) {
        this.dialog = dialog;
    }

    @FXML private Label averageRatingLabel;
    @FXML private Label bookAuthorLabel;
    @FXML private Label bookISBNLabel;
    @FXML private Label bookTitleLabel;
    @FXML private Label dateOfLoanLabel;
    @FXML private Label dateOfReturnLabel;

    @FXML
    public void setLoanData(Loan loan) {
        bookLoaned = loan.getBookLoaned();
        bookTitleLabel.setText(loan.getBookTitle());
        bookAuthorLabel.setText(loan.getBookAuthor());
        bookISBNLabel.setText(loan.getBookISBN());
        averageRatingLabel.setText(String.valueOf(loan.getAverageRating()));
        dateOfLoanLabel.setText(loan.getDateOfLoan());
        dateOfReturnLabel.setText(loan.getDateOfReturn());
    }

    @FXML
    void onLeaveFeedbackClick() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/GiveFeedbackDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            GiveFeedbackDialogController controller = loader.getController();
            controller.setData(bookLoaned);
            Dialog<Void> dialog = new Dialog<>();
            dialog.setTitle("Give Feedback");
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }
    }


}

