package ru.mai.coursework.dns;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import ru.mai.coursework.dns.controller.MainFormController;
import ru.mai.coursework.dns.entity.Product;
import ru.mai.coursework.dns.entity.User;
import ru.mai.coursework.dns.entity.bridge.UserProducts;
import ru.mai.coursework.dns.helpers.bridge.UserProductHelper;

import java.io.IOException;
import java.util.List;

public class MainApplication extends Application {
    public static Stage primaryStage;
    private Scene scene;
    public Scene getScene() {
        return scene;
    }

    public void setScene(Scene scene) {
        this.scene = scene;
    }

    @Override
    public void start(Stage newPrimaryStage) throws IOException {
        loadFonts("/fonts/18VAG Rounded M Normal.ttf");
        FXMLLoader fxmlLoader = new FXMLLoader(
                MainApplication.class.getResource("/fxml/main-form.fxml"));
        Scene mainScene = new Scene((Parent) fxmlLoader.load());
        setScene(mainScene);
        newPrimaryStage.getIcons().add(new Image("/images/dns_small.png"));
        newPrimaryStage.setTitle("DNS");
        newPrimaryStage.setScene(mainScene);
        newPrimaryStage.show();
        newPrimaryStage.setResizable(false);
        primaryStage = newPrimaryStage;
    }

    private void loadFonts(String fontPath) {
        Font.loadFont(getClass().getResourceAsStream(fontPath), 16);
    }

    @Override
    public void stop(){
        saveBasketInDb();
    }

    private void saveBasketInDb() {
        System.out.println("Stage is closing");
        User user = MainFormController.getUser();
        if (MainFormController.authState.get()) {
            List<Product> bufferProducts = MainFormController.getBasketProducts();
            UserProductHelper userProductHelper = new UserProductHelper();
            for (int i = MainFormController.getLastIndexInBasket(); i < bufferProducts.size(); i++) {
                UserProducts userProduct = new UserProducts();
                userProduct.setUser(user);
                userProduct.setProduct(bufferProducts.get(i));
                userProductHelper.save(userProduct);
            }
        }
    }

    public static void main(String[] args) {
        launch();
    }
}