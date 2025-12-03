package org.example.multimedia24;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.example.multimedia24.DialogControllers.ViewBookDialogController;
import org.example.multimedia24.DialogControllers.ViewLoanDialogController;
import org.example.multimedia24.Models.*;

import java.io.IOException;

public class MemberHomeController {
    Member currentMemberInfo = CurrentUserInfo.getCurrentMember();

    //Search Books Tab
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, Integer> copiesColumn;
    @FXML private TableColumn<Book, String> categoryColumn;
    @FXML private TableColumn<Book, Double> ratingColumn;
    @FXML private TableColumn<Book, Integer> numberOfRatingsColumn;
    @FXML private TextField BookTitleSearchField;
    @FXML private TextField BookAuthorSearchField;
    @FXML private TextField BookYearSearchField;

    private void updateFilter(FilteredList<Book> filteredData) {
        filteredData.setPredicate(book -> {
            // If filter text in all search fields is empty, display all books.
            if ((BookTitleSearchField.getText() == null || BookTitleSearchField.getText().isEmpty()) &&
                    (BookAuthorSearchField.getText() == null || BookAuthorSearchField.getText().isEmpty()) &&
                    (BookYearSearchField.getText() == null || BookYearSearchField.getText().isEmpty())) {
                return true;
            }
            // Check each condition
            boolean titleMatches = BookTitleSearchField.getText() == null || BookTitleSearchField.getText().isEmpty() || book.getTitle().toLowerCase().contains(BookTitleSearchField.getText().toLowerCase());
            boolean authorMatches = BookAuthorSearchField.getText() == null || BookAuthorSearchField.getText().isEmpty() || book.getAuthor().toLowerCase().contains(BookAuthorSearchField.getText().toLowerCase());
            boolean yearMatches = BookYearSearchField.getText() == null || BookYearSearchField.getText().isEmpty() || String.valueOf(book.getYear()).contains(BookYearSearchField.getText());
            return titleMatches && authorMatches && yearMatches;
        });
    }

    @FXML
    public void initializeBooks() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        publisherColumn.setCellValueFactory(new PropertyValueFactory<>("publisher"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        yearColumn.setCellValueFactory(new PropertyValueFactory<>("year"));
        copiesColumn.setCellValueFactory(new PropertyValueFactory<>("noOfCopies"));
        categoryColumn.setCellValueFactory(new PropertyValueFactory<>("Category"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        numberOfRatingsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRatings"));

        bookTableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book rowData = row.getItem();
                    showViewBookDialog(rowData);
                }
            });
            return row;
        });

        FilteredList<Book> filteredData = new FilteredList<>(FXCollections.observableArrayList(Book.getBookList()), p -> true);
        BookTitleSearchField.textProperty().addListener((observable, oldValue, newValue) -> {// Listener for Title Search Field
            updateFilter(filteredData);
        });
        BookAuthorSearchField.textProperty().addListener((observable, oldValue, newValue) -> {// Listener for Author Search Field
            updateFilter(filteredData);
        });
        BookYearSearchField.textProperty().addListener((observable, oldValue, newValue) -> {// Listener for Year Search Field
            updateFilter(filteredData);
        });
        bookTableView.setItems(filteredData);
    }

    private void showViewBookDialog(Book rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/ViewBookDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            ViewBookDialogController controller = loader.getController();
            controller.setBookData(rowData);
            Dialog<Void> dialog = new Dialog<>();
            controller.setDialog(dialog);
            dialog.setTitle("Book Details");
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
        }
    }

    //Profile Tab
    @FXML private Label MemberNameText;
    @FXML private Label MemberSurnameText;
    @FXML private Label MemberUsernameText;
    @FXML private Label MemberEmailText;
    @FXML private TableView<Loan> myLoansTableView;
    @FXML private TableColumn<Loan, String> loanedBookTitleColumn;
    @FXML private TableColumn<Loan, String> loanedBookAuthorColumn;
    @FXML private TableColumn<Loan, String> loanedBookISBNColumn;
    @FXML private TableColumn<Loan, Double> loanedBookRatingColumn;
    @FXML private TableColumn<Loan, String> loanDateColumn;
    @FXML private TableColumn<Loan, String> returnDateColumn;
    @FXML private TableColumn<Loan, String> loanStatus;
    @FXML
    public void initializeMyLoans() {
        loanedBookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        loanedBookAuthorColumn.setCellValueFactory(new PropertyValueFactory<>("bookAuthor"));
        loanedBookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        loanedBookRatingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        loanDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfLoan"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfReturn"));
        loanStatus.setCellValueFactory(new PropertyValueFactory<>("loanStatus"));

        myLoansTableView.setRowFactory(tv -> {
            TableRow<Loan> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Loan rowData = row.getItem();
                    showViewLoanDialog(rowData);
                }
            });
            return row;
        });
        FilteredList<Loan> Data = new FilteredList<>(FXCollections.observableArrayList(CurrentUserInfo.getMyLoans()), p -> true);
        myLoansTableView.setItems(Data);
    }

    private void showViewLoanDialog(Loan rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/ViewLoanDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            ViewLoanDialogController controller = loader.getController();
            controller.setLoanData(rowData);
            Dialog<Void> dialog = new Dialog<>();
            controller.setDialog(dialog);
            dialog.setTitle("Loan Details");
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
        }
    }

    @FXML
    void onLogOutButtonClick(ActionEvent event) {
        try {
            CurrentUserInfo.setCurrentMember(null);
            CurrentUserInfo.clearMyLoans();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("login.fxml"));
            Parent loginScreenRoot = loader.load();
            Stage currentStage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginScreenRoot);
            currentStage.setScene(scene);
            currentStage.show();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
        }
    }

    @FXML
    public void initialize() {
        MemberNameText.setText(currentMemberInfo.getName());
        MemberSurnameText.setText(currentMemberInfo.getSurname());
        MemberUsernameText.setText(currentMemberInfo.getUsername());
        MemberEmailText.setText(currentMemberInfo.getEmail());
        initializeBooks();
        initializeMyLoans();
    }
}
