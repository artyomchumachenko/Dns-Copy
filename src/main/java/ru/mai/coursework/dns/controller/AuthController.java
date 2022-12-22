package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.helpers.UserHelper;

public class AuthController {

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passwordTextField;

    @FXML
    void initialize() {

    }

    @FXML
    private void tryToAuth() {
        User user = null;
        String login = loginTextField.getText();
        String password = passwordTextField.getText();

        if (!login.equals("") && !password.equals("")) {
            UserHelper userHelper = new UserHelper();
            user = userHelper.getUsersByLoginAndPassword(login, password);
        }
        if (user != null) {
            MainFormController.setUser(user);
            if (user.isAdmin()) {
                MainFormController.adminState.setValue(true);
            }
            MainFormController.authStage.close();
            MainFormController.authState.setValue(true);
            MainFormController.showAlertWithoutHeaderText(
                    "Вход",
                    "Вход выполнен успешно, " + user.getLogin() + ", приятной работы!"
            );
            MainFormController.setUser(user);
            MainFormController.loadBasketAfterUserAuth();
        } else {
            MainFormController.showAlertWithoutHeaderText(
                    "Вход",
                    "Неправильный логин или пароль"
            );
        }
    }
}
