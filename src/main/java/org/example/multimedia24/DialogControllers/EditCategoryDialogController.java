package org.example.multimedia24.DialogControllers;

import javafx.fxml.FXML;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.example.multimedia24.Models.Category;
import org.example.multimedia24.Utilities;

public class EditCategoryDialogController {
    private Category categoryDisplayed;
    private Dialog<?> dialog;
    //private String cn;

    @FXML private TextField categoryNameText;
    @FXML private Label ErrorText;

    public void setDialog(Dialog<Void> dialog) {
        this.dialog = dialog;
    }
    public void setCateg(Category rowData) {
        this.categoryDisplayed = rowData;
        categoryNameText.setText(categoryDisplayed.getName());
    }

    @FXML
    public void onDeleteButtonClicked() {
        String m = Category.removeCategory(categoryDisplayed.getName());
        Utilities.showInfoAlert("Category Deletion","",m);
        if (dialog != null) {
            dialog.close();
        }
    }

    @FXML
    public void onSaveChangesButtonClicked() {
        String newName = categoryNameText.getText();
        String m = categoryDisplayed.edit(newName);
        if (m==null){
            Utilities.showInfoAlert("Category Edited", "Category name has been updated","");
            if (dialog != null) {
                dialog.close();
            }
        }
        else
            ErrorText.setText(m);
    }
}
