package ru.mai.coursework.dns.controller;

import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.helpers.UserHelper;

import java.util.List;

public class AdminInfoController {

    @FXML
    private ListView<String> productName;

    private static List<Product> products = null;

    @FXML
    void initialize() {
        loadReportAboutProducts();
    }

    private void loadReportAboutProducts() {
        UserHelper userHelper = new UserHelper();
        if (userHelper.getInformationProducts() != null) {
            products = userHelper.getInformationProducts();
            List<Long> countOfProducts =  userHelper.getInformationAmounts();
            for (int i = 0; i < products.size(); i++) {
                StringBuilder stringBuilder = new StringBuilder();
                stringBuilder
                        .append(countOfProducts.get(i))
                        .append(" шт. :    ")
                        .append(products.get(i).getProductName());
                productName.getItems().add(stringBuilder.toString());
            }
        }
    }

    @FXML
    private void closeStageListener() {
        ProfileController.otchetStage.close();
    }
}
