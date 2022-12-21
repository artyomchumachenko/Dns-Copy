package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.stage.Modality;
import javafx.stage.Stage;
import ru.mai.coursework.dns.MainApplication;
import ru.mai.coursework.dns.controller.branch.ProductViewController;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.helpers.bridge.UserProductHelper;

import java.io.IOException;

public class BasketController {

    @FXML
    private ListView<String> productsInBasket;

    @FXML
    private Button delete;

    @FXML
    void initialize() {
        showProductFromBasket();
        productDoubleClickedListener();
    }

    private void showProductFromBasket() {
        if (MainFormController.getBasketProducts() != null) {
            for (Product pr : MainFormController.getBasketProducts()) {
                productsInBasket.getItems().add(pr.getProductName());
            }
        }
    }

    @FXML
    private void clickToProduct() {
        int productIndex = productsInBasket.getSelectionModel().getSelectedIndex();
        Product selectedProduct = MainFormController.getBasketProducts().get(productIndex);

        if (MainFormController.authState.get()) {
            User authUser = MainFormController.getUser();
            UserProductHelper userProductHelper = new UserProductHelper();
            userProductHelper.deleteByUserAndProduct(authUser, selectedProduct);
        }
        productsInBasket.getItems().remove(productIndex);
        MainFormController.deleteProductByIndex(productIndex);
    }

    private void productCharacteristics() {
        int productIndex = productsInBasket.getSelectionModel().getSelectedIndex();
        Product clickedProduct = MainFormController.getBasketProducts().get(productIndex);
        ProductViewController.setChosenProduct(clickedProduct);
        showProductCharacteristics();
    }

    private void showProductCharacteristics() {
        FXMLLoader loader = new FXMLLoader(
                MainApplication.class.getResource("/fxml/show-chs.fxml"));
        Stage authorizationWindow = new Stage();
        try {
            authorizationWindow.setScene(new Scene(loader.load()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        authorizationWindow.getIcons().add(new Image("/images/more-details-icon-3.jpg"));
        authorizationWindow.setTitle("Характеристики");
        authorizationWindow.initModality(Modality.WINDOW_MODAL);
        authorizationWindow.initOwner(MainApplication.primaryStage);
        authorizationWindow.show();
        authorizationWindow.setResizable(false);

        MainFormController.showChs = authorizationWindow;
    }

    private void productDoubleClickedListener() {
        productsInBasket.setOnMouseClicked(event -> {
            if(event.getButton().equals(MouseButton.PRIMARY) && event.getClickCount() == 2) {
                productCharacteristics();
            }
        });
    }
}
