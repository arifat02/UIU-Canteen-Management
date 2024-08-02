package org.example.uiu_cafeteria;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LoginPage {
    @FXML
    private TextField setUserNameField;
    @FXML
    private TextField numberField;
    @FXML
    private TextField emailField;
    @FXML
    private PasswordField setPasswordField;
    @FXML
    private AnchorPane loginPane;
    @FXML
    private AnchorPane createAccountPane;
    @FXML
    private AnchorPane resetPassPane;
    @FXML
    private DatePicker birthdate;
    @FXML
    private TextField userNameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField EmailToUpdatePass;
    @FXML
    private TextField NewPassField;
    @FXML
    private AnchorPane adminLoginPane;
    UserInformation userInformation=new UserInformation();


    public void ClickLoginBtn(ActionEvent actionEvent) {
        try {
            if ( userInformation.LoginUser(userNameField.getText(), passwordField.getText())) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("StudentUi.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("UIU Cafeteria");
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert(Alert.AlertType.WARNING, "Login Failed", "Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while trying to log in. Please try again later.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void RegisterBtn(ActionEvent actionEvent) {
        setPaneVisible(createAccountPane);
    }
    public void ClickCreatBtn(ActionEvent actionEvent) {
        String username = setUserNameField.getText();
        String number = numberField.getText();
        String email = emailField.getText();
        String password = setPasswordField.getText();
        LocalDate selectedDate = birthdate.getValue();

        if (username.isEmpty() || number.isEmpty() || email.isEmpty() || password.isEmpty() || selectedDate == null) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please fill in all fields and select a birthdate.");
            return;
        }

        String formattedDate = selectedDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        UserInformation userRegistation = new UserInformation(username, number, email, password, formattedDate);

        try{
            boolean accountCreated = userRegistation.CreateAccount();
            if (accountCreated) {
            showAlert(Alert.AlertType.INFORMATION, "Account Created", "Your account has been created successfully.");
                setUserNameField.setText("");
                numberField.setText("");
                emailField.setText("");
                setPasswordField.setText("");

            setPaneVisible(loginPane); }
            else {
            showAlert(Alert.AlertType.ERROR, "Account Creation Failed", "An error occurred while creating your account. Please try again.");}
        }
        catch (ClassNotFoundException e) {
            showAlert(Alert.AlertType.ERROR, "Error", "Database driver not found. Please contact support.");
        }

    }



    public void ClickForgotPass(ActionEvent actionEvent) {
        setPaneVisible(resetPassPane);
    }
    public void ClickUpdatePassbtn(ActionEvent actionEvent) {
        if (EmailToUpdatePass.getText().isEmpty() || NewPassField.getText().isEmpty()) {
            showAlert(Alert.AlertType.WARNING, "Validation Error", "Please enter both email and new password.");
            return;
        }
            try {
                boolean updatePassword=  userInformation.resetpass(EmailToUpdatePass.getText(), NewPassField.getText());
                if (updatePassword) {
                    showAlert(Alert.AlertType.INFORMATION, "Password Updated", "Your password has been updated successfully.");
                } else {
                    showAlert(Alert.AlertType.ERROR, "Update Failed", "An error occurred while updating your password. Please try again.");
                }
            } catch (ClassNotFoundException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "Database driver not found. Please contact support.");
                e.printStackTrace();
            } catch (SQLException e) {
                showAlert(Alert.AlertType.ERROR, "Database Error", "An error occurred while accessing the database. Please try again later.");
                e.printStackTrace();
            }
    }



    public void adminBtn(ActionEvent actionEvent) {
        setPaneVisible(adminLoginPane);
    }

    public void adminLoginBtn(ActionEvent actionEvent) {
        try {
            if (userInformation.LoginUser(userNameField.getText(), passwordField.getText())) {
                FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("AdminPage.fxml"));
                Parent root = fxmlLoader.load();
                Stage stage = (Stage) ((Node) actionEvent.getSource()).getScene().getWindow();
                Scene scene = new Scene(root);
                stage.setTitle("UIU Cafeteria");
                stage.setScene(scene);
                stage.show();
            } else {
                showAlert(Alert.AlertType.WARNING, "Login Failed", "Invalid username or password. Please try again.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            showAlert(Alert.AlertType.ERROR, "Error", "An error occurred while trying to log in. Please try again later.");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public void ClickBackbtn(ActionEvent actionEvent) {
        setPaneVisible(loginPane);
    }
    private void setPaneVisible(AnchorPane paneToShow) {


        loginPane.setVisible(false);
        createAccountPane.setVisible(false);
        adminLoginPane.setVisible(false);
        resetPassPane.setVisible(false);
        paneToShow.setVisible(true);
    }
    private void showAlert(Alert.AlertType alertType, String title, String message) {
        Alert alert = new Alert(alertType, message, ButtonType.OK);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.showAndWait();
        }


}
