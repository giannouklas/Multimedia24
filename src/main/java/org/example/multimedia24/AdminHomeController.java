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
import org.example.multimedia24.DialogControllers.*;
import org.example.multimedia24.Models.*;

import java.io.IOException;

public class AdminHomeController {
    //Admin Details Tab
    @FXML private Label AdminUsernameText;
    @FXML
    void onLogOutButtonClick(ActionEvent event) {
        try {
            CurrentUserInfo.setCurrentAdmin(null);
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

    //Manage Books Tab
    @FXML private TableView<Book> bookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> publisherColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Integer> yearColumn;
    @FXML private TableColumn<Book, Integer> copiesColumn;
    @FXML private TableColumn<Book, String> categoryColumn;
    @FXML private TableColumn<Book, Double> ratingColumn;
    @FXML private TextField BookSearchField;

    @FXML
    public void onAddBookButtonClicked(){
        showDialogForBookOrCategory(true);
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
        bookTableView.setRowFactory(tv -> {
            TableRow<Book> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Book rowData = row.getItem();
                    showEditBookDialog(rowData); // Placeholder for the editing method
                }
            });
            return row;
        });

        FilteredList<Book> filteredData = new FilteredList<>(FXCollections.observableArrayList(Book.getBookList()), p -> true);
        BookSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(book -> {
                // If filter text is empty, display all books.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (book.getTitle().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches book title.
                } else if (book.getAuthor().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches author.
                }
                else if(String.valueOf(book.getYear()).contains(newValue)) { // Directly using newValue since year is numeric
                    return true; // Filter matches year.
                } else return book.getCategory().toLowerCase().contains(lowerCaseFilter); // Filter matches author.
// Does not match.
            });
        });
        bookTableView.setItems(filteredData);
        //bookTableView.getItems().setAll(filteredData);
    }

    private void showEditBookDialog(Book rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/EditBookDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            EditBookDialogController controller = loader.getController();
            controller.setBookData(rowData);
            Dialog<Void> dialog = new Dialog<>();
            controller.setDialog(dialog);
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage()); //e.printStackTrace();
        }
    }



    //Manage Categories
    @FXML private TableView<Category> categoryTableView;
    @FXML private TableColumn<Category, String> categoryNameColumn;
    @FXML private TextField CategorySearchField;

    @FXML
    public void onAddCategoryButtonClicked(){
        showDialogForBookOrCategory(false);
    }

    @FXML
    public void initializeCategories() {
        categoryNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        categoryTableView.setRowFactory(tv -> {
            TableRow<Category> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Category rowData = row.getItem();
                    showEditCategoryDialog(rowData);
                }
            });
            return row;
        });
        FilteredList<Category> filteredData = new FilteredList<>(FXCollections.observableArrayList(Category.getCategoryList()), p -> true);
        CategorySearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(category -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                return category.getName().toLowerCase().contains(lowerCaseFilter); // Filter matches
// Does not match.
            });
        });
        categoryTableView.setItems(filteredData);
    }
    private void showEditCategoryDialog(Category rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/EditCategoryDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            EditCategoryDialogController controller = loader.getController();
            controller.setCateg(rowData);
            Dialog<Void> dialog = new Dialog<>();
            controller.setDialog(dialog);
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }
    }

    //Manage Users Tab
    @FXML private TableView<Member> memberTableView;
    @FXML private TableColumn<Member, String> nameColumn;
    @FXML private TableColumn<Member, String> surnameColumn;
    @FXML private TableColumn<Member, String> usernameColumn;
    @FXML private TableColumn<Member, String> idColumn;
    @FXML private TableColumn<Member, String> emailColumn;
    @FXML private TextField UserSearchField;

    @FXML
    public void initializeMembers() {
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("surname"));
        usernameColumn.setCellValueFactory(new PropertyValueFactory<>("username"));
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));
        memberTableView.setRowFactory(tv -> {
            TableRow<Member> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Member rowData = row.getItem();
                    showEditMemberDialog(rowData);
                }
            });
            return row;
        });
        FilteredList<Member> filteredData = new FilteredList<>(FXCollections.observableArrayList(Member.getMemberList()), p -> true);
        // Set the filter Predicate whenever the filter changes.
        UserSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (member.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches name.
                } else if (member.getSurname().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches surname.
                } else if (member.getUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches username.
                }else return member.getEmail().toLowerCase().contains(lowerCaseFilter); // Filter matches email.
            });
        });
        memberTableView.setItems(filteredData);
    }

    private void showEditMemberDialog(Member rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/EditMemberDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            EditMemberDialogController controller = loader.getController();
            controller.setMemberData(rowData);
            Dialog<Void> dialog = new Dialog<>();
            controller.setDialog(dialog);
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage()); //e.printStackTrace();
        }
    }

    @FXML
    public void showDialogForBookOrCategory(boolean isForBook){
        try {
            Dialog<Void> dialog = new Dialog<>();
            FXMLLoader loader;
            AnchorPane dialogContent;
            if(isForBook){ //opens AddBookDialog
                loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/AddBookDialog.fxml"));
            }
            else { //opens AddCategory Dialog
                loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/AddCategoryDialog.fxml"));
            }
            dialogContent = loader.load();
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }
    }


    //Manage Loans Tab
    @FXML private TableView<Loan> LoansTableView;
    @FXML private TableColumn<Loan, String> loanedBookTitleColumn;
    @FXML private TableColumn<Loan, String> loanedBookISBNColumn;
    @FXML private TableColumn<Loan, String> returnDateColumn;
    @FXML private TableColumn<Loan, String> loanStatus;
    @FXML private TableColumn<Loan, String> borrowerUsernameColumn;
    @FXML private TableColumn<Loan, String> borrowerEmailColumn;
    @FXML private TextField loanSearchField;

    @FXML
    public void initializeLoans() {
        loanedBookTitleColumn.setCellValueFactory(new PropertyValueFactory<>("bookTitle"));
        loanedBookISBNColumn.setCellValueFactory(new PropertyValueFactory<>("bookISBN"));
        borrowerUsernameColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerUsername"));
        borrowerEmailColumn.setCellValueFactory(new PropertyValueFactory<>("borrowerEmail"));
        returnDateColumn.setCellValueFactory(new PropertyValueFactory<>("dateOfReturn"));
        loanStatus.setCellValueFactory(new PropertyValueFactory<>("loanStatus"));
        LoansTableView.setRowFactory(tv -> {
            TableRow<Loan> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Loan rowData = row.getItem();
                    showTerminateLoanDialog(rowData);
                }
            });
            return row;
        });
        FilteredList<Loan> filteredData = new FilteredList<>(FXCollections.observableArrayList(Loan.getLoanList()), p -> true);
        // Set the filter Predicate whenever the filter changes.
        loanSearchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(loan -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (loan.getBorrowerUsername().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches username.
                } else if (loan.getBorrowerEmail().toLowerCase().contains(lowerCaseFilter)) {
                    return true; // Filter matches Email.
                } else return loan.getBookISBN().toLowerCase().contains(lowerCaseFilter); // Filter matches ISBN.
// Does not match.
            });
        });
        LoansTableView.setItems(filteredData);
    }

    private void showTerminateLoanDialog(Loan rowData) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/multimedia24/dialogs/TerminateLoanDialog.fxml"));
            AnchorPane dialogContent = loader.load();
            TerminateLoanDialogController controller = loader.getController();
            controller.setLoanData(rowData);
            Dialog<Void> dialog = new Dialog<>();
            controller.setDialog(dialog);
            dialog.getDialogPane().setContent(dialogContent);
            dialog.getDialogPane().getButtonTypes().addAll(ButtonType.CANCEL);
            dialog.showAndWait();
            initialize();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage()); //e.printStackTrace();
        }
    }

    @FXML
    public void initialize() {
        initializeCategories();
        initializeBooks();
        initializeMembers();
        initializeLoans();
        AdminUsernameText.setText(CurrentUserInfo.getCurrentAdmin().getUsername());
    }
}