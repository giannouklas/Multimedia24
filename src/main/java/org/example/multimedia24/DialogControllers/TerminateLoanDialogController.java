package org.example.multimedia24.DialogControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import org.example.multimedia24.Models.Loan;
import org.example.multimedia24.Utilities;

public class TerminateLoanDialogController {
    private Loan loan;
    private Dialog<?> dialog;


    @FXML private Label bookTitleLabel;
    @FXML private Label bookISBNLabel;
    @FXML private Label usernameLabel;
    @FXML private Label emailLabel;
    @FXML private Label dateOfReturnLabel;
    @FXML private Label loanStatusLabel;

    public void setDialog(Dialog<Void> dialog) {
        this.dialog = dialog;
    }
    @FXML
    public void setLoanData(Loan rowData) {
        this.loan = rowData;
        bookTitleLabel.setText(loan.getBookTitle());
        bookISBNLabel.setText(loan.getBookISBN());
        usernameLabel.setText(loan.getBorrowerUsername());
        emailLabel.setText(loan.getBorrowerEmail());
        dateOfReturnLabel.setText(loan.getDateOfReturn());
        loanStatusLabel.setText(loan.getLoanStatus());
    }

    @FXML
    void onTerminateLoanButtonClick() {
        String m = Loan.removeLoan(usernameLabel.getText(),bookISBNLabel.getText(), loan.getDateOfLoan());
        Utilities.showInfoAlert("Loan Termination",m,"");
        if (dialog != null) {
            dialog.close();
        }
    }
}
