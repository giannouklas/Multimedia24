package org.example.multimedia24;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.example.multimedia24.Models.Member;

import java.io.IOException;
import java.util.Objects;

public class SignupController {

    @FXML private TextField nameField;
    @FXML private TextField surnameField;
    @FXML private TextField usernameField;
    @FXML private TextField emailField;
    @FXML private TextField idField;
    @FXML private PasswordField passwordField;
    @FXML private PasswordField confirmPasswordField;
    @FXML private Label ErrorText;

    @FXML
    void onLoginButtonClick(ActionEvent event) {
        try {
            Parent loginRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("login.fxml"), "Resource login.fxml not found"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(loginRoot);
            stage.setScene(scene);
            stage.setTitle("Login");
            stage.show();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
        } catch (NullPointerException e) {
            Utilities.showErrorAlert("Error", "Failed to load the FXML file", e.getMessage());
        }
    }

    @FXML
    void onSignUpButtonClick(ActionEvent event) {
        if (!passwordField.getText().equals(confirmPasswordField.getText())) {
            ErrorText.setText("Passwords do not match");
        } else {
            ErrorText.setText("");
            String Message = Member.checkCreateAdd(usernameField.getText(), nameField.getText(),
                    surnameField.getText(), emailField.getText(), passwordField.getText(), idField.getText());
            if (Message!=null){
                ErrorText.setText(Message);
            }
            else{
                Member currentMemberInfo = new Member(usernameField.getText(), passwordField.getText(), emailField.getText(),
                               idField.getText(), nameField.getText(), surnameField.getText());
                CurrentUserInfo.setCurrentMember(currentMemberInfo);
                CurrentUserInfo.clearMyLoans();
                navigateToMemberHome(event);
            }
        }

    }

    public void navigateToMemberHome(ActionEvent event) {
        try {
            //MemberHomeController.setCurrentMember(currentMemberInfo);
            Parent mem = FXMLLoader.load(getClass().getResource("memberhome.fxml"));
            Stage stage = (Stage) ((javafx.scene.Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(mem);
            stage.setScene(scene);
            stage.setTitle("MemberHome");
            stage.show();
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
        }
    }
}

