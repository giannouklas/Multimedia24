package org.example.multimedia24;

import javafx.collections.FXCollections;
import javafx.collections.transformation.FilteredList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.example.multimedia24.Models.*;

import java.io.IOException;
import java.util.Objects;

public class LoginController {
    String Message = Admin.checkCreateAdd("medialab", "medialab_2024");

    @FXML private PasswordField passwordField;
    @FXML private TextField usernameField;
    @FXML private Label ErrorText;

    @FXML private TableView<Book> top5BookTableView;
    @FXML private TableColumn<Book, String> titleColumn;
    @FXML private TableColumn<Book, String> authorColumn;
    @FXML private TableColumn<Book, String> isbnColumn;
    @FXML private TableColumn<Book, Double> ratingColumn;
    @FXML private TableColumn<Book, Integer> numberOfRatingsColumn;

    @FXML
    public void initialize() {
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        authorColumn.setCellValueFactory(new PropertyValueFactory<>("author"));
        isbnColumn.setCellValueFactory(new PropertyValueFactory<>("ISBN"));
        ratingColumn.setCellValueFactory(new PropertyValueFactory<>("averageRating"));
        numberOfRatingsColumn.setCellValueFactory(new PropertyValueFactory<>("numberOfRatings"));

        FilteredList<Book> top5BookData = new FilteredList<>(FXCollections.observableArrayList(Book.getTop5Books()), p -> true);
        top5BookTableView.setItems(top5BookData);
    }

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        Member currentMemberInfo;
        Admin currentAdminInfo = Admin.searchByUsernameAndPassword(usernameField.getText(), passwordField.getText());
        if(currentAdminInfo == null){
            currentMemberInfo = Member.searchByUsernameAndPassword(usernameField.getText(), passwordField.getText());
            if(currentMemberInfo == null){
                ErrorText.setText("Oops, Something's Wrong");
            }
            else{
                ErrorText.setText("");
                CurrentUserInfo.setCurrentMember(currentMemberInfo);
                navigateToMemberHome(event);
            }
        }
        else{
            ErrorText.setText("");
            CurrentUserInfo.setCurrentAdmin(currentAdminInfo);
            navigateToAdminHome(event);
        }
    }

    @FXML
    void onCreateAccountButtonClick(ActionEvent event) {
        try {
            Parent signUpRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("createaccount.fxml"), "Resource createaccount.fxml not found"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(signUpRoot);
            stage.setScene(scene);
            stage.setTitle("Create Account");
            stage.show();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
        } catch (NullPointerException e) {
            Utilities.showErrorAlert("Error", "Failed to load the FXML file", e.getMessage());
        }
    }

    public void navigateToMemberHome(ActionEvent event) {
        try {
            Parent mem = FXMLLoader.load(getClass().getResource("memberhome.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(mem);
            stage.setScene(scene);
            stage.setTitle("MemberHome");
            stage.show();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }

    }

    public void navigateToAdminHome(ActionEvent event){
        try {
            //AdminHomeController.setCurrentAdmin(currentAdminInfo);
            Parent adm = FXMLLoader.load(getClass().getResource("adminhome.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(adm);
            stage.setScene(scene);
            stage.setTitle("AdminHome");
            stage.show();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }

    }




}
