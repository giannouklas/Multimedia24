package org.example.multimedia24.DialogControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.multimedia24.Models.Member;
import org.example.multimedia24.Utilities;

public class EditMemberDialogController {
    private Member memberDisplayed;
    private Dialog<?> dialog;

    @FXML private Label ErrorText;
    @FXML private TextField memberNameText;
    @FXML private TextField memberSurnameText;
    @FXML private TextField memberUsernameText;
    @FXML private TextField memberEmailText;
    @FXML private TextField memberIDText;

    public void setDialog(Dialog<?> dialog) {
        this.dialog = dialog;
    }

    public void setMemberData(Member member) {
        memberDisplayed = member;
        memberNameText.setText(member.getName());
        memberSurnameText.setText(member.getSurname());
        memberUsernameText.setText(member.getUsername());
        memberEmailText.setText(member.getEmail());
        memberIDText.setText(String.valueOf(member.getId()));
    }

    @FXML
    public void onDeleteButtonClicked() {
        String m = Member.removeMember(memberDisplayed.getUsername());
        Utilities.showInfoAlert("Member Deletion","",m);
        if (dialog != null) {
            dialog.close();
        }
    }

    @FXML
    public void onSaveChangesButtonClicked() {
        String m = memberDisplayed.edit(memberNameText.getText(), memberSurnameText.getText(),memberUsernameText.getText(),
                memberEmailText.getText(), memberIDText.getText());
        if (m==null){
            Utilities.showInfoAlert("Member Edited", "Member Details have been updated","");
            if (dialog != null) {
                dialog.close();
            }
        }
        else
            ErrorText.setText(m);
    }
}
