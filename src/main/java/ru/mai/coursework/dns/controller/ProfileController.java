package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mai.coursework.dns.MainApplication;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.entity.bridge.UserProducts;
import ru.mai.coursework.dns.helpers.bridge.UserProductHelper;

import java.io.IOException;
import java.util.List;

public class ProfileController {

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField phoneTextField;

    @FXML
    private Button adminButton;

    public static Stage otchetStage = null;

    @FXML
    void initialize() {
        checkAdminState();
        loadInfoAboutUser();
        adminButtonClickListener();
    }

    private void loadInfoAboutUser() {
        loginTextField.setText("Логин: " + MainFormController.getUser().getLogin());
        if (MainFormController.getUser().getPhone() != null) {
            phoneTextField.setText("Номер: " + MainFormController.getUser().getPhone());
        } else {
            phoneTextField.setText("Номер: не указан");
        }
    }

    @FXML
    private void exitFromProfile() {
        String exit = MainFormController.showConfirmationAlertBeforeExitFromProfile();
        if (exit.equals("OK")) {
            saveBasketInDb();
            MainFormController.authState.setValue(false);
            MainFormController.adminState.setValue(false);
            MainFormController.clearBasketProducts();
            MainFormController.profileStage.close();
        }
    }

    private void saveBasketInDb() {
        User user = MainFormController.getUser();
        List<Product> bufferProducts = MainFormController.getBasketProducts();
        UserProductHelper userProductHelper = new UserProductHelper();
        for (int i = MainFormController.getLastIndexInBasket(); i < bufferProducts.size(); i++) {
            UserProducts userProduct = new UserProducts();
            userProduct.setUser(user);
            userProduct.setProduct(bufferProducts.get(i));
            userProductHelper.save(userProduct);
        }
    }

    private void adminButtonClickListener() {
        adminButton.setOnMouseClicked(event -> showOtchet());
    }

    private void checkAdminState() {
        if (MainFormController.adminState.get()) {
            adminButton.setVisible(true);
            adminButton.setDisable(false);
        } else {
            adminButton.setVisible(false);
            adminButton.setDisable(true);
        }
    }

    void showOtchet() {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/fxml/show-admin-info.fxml"));
        Stage profileWindow = new Stage();
        try {
            profileWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        profileWindow.getIcons().add(new Image("/images/more-details-icon-12.jpg"));
        profileWindow.setTitle("Отчёт");
        profileWindow.initModality(Modality.WINDOW_MODAL);
        profileWindow.initOwner(MainApplication.primaryStage);
        profileWindow.show();
        profileWindow.setResizable(false);

        otchetStage = profileWindow;
    }
}
