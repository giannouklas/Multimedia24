package org.example.multimedia24;

import javafx.scene.control.Alert;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class Utilities {

    public static void serializeList(List<? extends Serializable> list, String filename) {
        String currentWorkingDirectory = System.getProperty("user.dir");
        String newDirectory = currentWorkingDirectory + File.separator + "medialab";
        File folder = new File(newDirectory);

        // We create the folder if it does not exist
        if (!folder.exists()) {
            folder.mkdirs();
        }
        String filePath = folder + File.separator + filename;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(list);
        } catch (IOException e) {
            Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());//e.printStackTrace();
        }
    }

    public static List<? extends Serializable> deserializeList(String filename) {
        String currentWorkingDirectory = System.getProperty("user.dir");
        String directoryPath = currentWorkingDirectory + File.separator + "medialab";
        String filePath = directoryPath + File.separator + filename;

        List<? extends Serializable> list;
        File file = new File(filePath);
        if (file.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
                list = (List<? extends Serializable>) ois.readObject();
            } catch (IOException | ClassNotFoundException e) {
                Utilities.showErrorAlert("Error", "IO Exception", e.getMessage());
                return new ArrayList<>();
            }
        } else {
            Utilities.showErrorAlert("Error", "File not Found", "The file " + filePath + " does not exist.");
            return new ArrayList<>();
        }
        return list;
    }



    public static void showErrorAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    public static void showInfoAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

}
