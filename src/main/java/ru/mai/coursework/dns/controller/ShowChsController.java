package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import ru.mai.coursework.dns.controller.branch.ProductViewController;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.bridge.ProductCh;

public class ShowChsController {

    @FXML
    private ListView<String> chName;

    @FXML
    private ListView<String> chValue;

    @FXML
    private Label productName;

    private static Product openedProduct = null;

    public ShowChsController() {
        openedProduct = ProductViewController.getChosenProduct();
    }

    @FXML
    void initialize() {
        showCharacteristics();
    }

    private void showCharacteristics() {
        System.out.println(openedProduct.getProductName());
        productName.setText(openedProduct.getProductName());
        for (ProductCh ch : openedProduct.getProductChs()) {
            chName.getItems().add(ch.getCharacteristic().getChName());
            chValue.getItems().add(ch.getChValue());
        }
    }

    @FXML
    private void addProductToBasket() {
        MainFormController.addProductToBasket(openedProduct);
    }
}
