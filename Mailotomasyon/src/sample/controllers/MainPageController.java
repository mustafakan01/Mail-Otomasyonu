package sample.controllers;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainPageController extends LoginPageController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor_fitted;

    @FXML
    private AnchorPane anchor_sol_menu;

    @FXML
    private ImageView img_menu;

    @FXML
    private Button btn_menu_mailgonder;

    @FXML
    private Button btn_menu_yonet;

    @FXML
    private Button btn_menu_log;

    @FXML
    private Button btn_menu_cikis;

    @FXML
    private Button btn_oturum;

    @FXML
    void btn_oturum_click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Oturumu Kapat");
        alert.setHeaderText(null);
        alert.setContentText("Oturumu kapatmak istediğinize emin misiniz? ");

        ButtonType buttonTypeOk = new ButtonType("Evet", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Hayır", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOk,buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            System.out.println("Oturum Kapandı");
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("./../fxml_files/LoginPage.fxml"));
                AnchorPane panel = (AnchorPane) loader.load();
                anchor_fitted.getChildren().setAll(panel);

            } catch (Exception e) {
                System.out.println("Oturum Kapatma Hatası");
            }

        } else {
            alert.close();
        }

    }

    @FXML
    private AnchorPane anchor_govde;

    @FXML
    private Label lbl_usermail;
    public void displayUsermail(String usermail) {
        lbl_usermail.setText(usermail);
    }

    public Label getLbl_username() {
        return lbl_username;
    }

    @FXML
    private Label lbl_username;
    public void displayUsername(String username) {
        lbl_username.setText(username);
    }


    @FXML
    void btn_menu_cikis_click(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Çıkış İşlemi");
        alert.setHeaderText("Programdan çıkmak üzeresiniz.");
        alert.setContentText("Programdan çıkış yapmak istediğinize emin misiniz? ");

        ButtonType buttonTypeOk = new ButtonType("Evet", ButtonBar.ButtonData.YES);
        ButtonType buttonTypeCancel = new ButtonType("Hayır", ButtonBar.ButtonData.CANCEL_CLOSE);
        alert.getButtonTypes().setAll(buttonTypeOk,buttonTypeCancel);

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == buttonTypeOk){
            System.out.println("cikis");
            Stage stage = (Stage) btn_menu_cikis.getScene().getWindow();
            stage.close();

        } else {
            alert.close();
        }

    }

    @FXML
    void btn_menu_mailgonder_click(ActionEvent event) {
        System.out.println("gonder");
        try {
            FXMLLoader loader2 = new FXMLLoader(getClass().getResource("../fxml_files/SendMail.fxml"));
            AnchorPane panel2 = (AnchorPane) loader2.load();
            panel2.getStylesheets().add(getClass().getResource("../css_files/SendMail.css").toExternalForm());
            SendMailController smController = loader2.getController();
            smController.displayContname(lbl_username.getText());
            anchor_govde.getChildren().setAll(panel2);

        } catch (Exception e) {e.printStackTrace();}
    }

    @FXML
    void btn_menu_yonet_click(ActionEvent event) {
        System.out.println("yonet");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml_files/Contacts.fxml"));
            AnchorPane panel = (AnchorPane) loader.load();
            ContactsController controller = loader.getController();
            controller.displayContname(lbl_username.getText());

            panel.getStylesheets().add(getClass().getResource("../css_files/Contacts.css").toExternalForm());
            anchor_govde.getChildren().setAll(panel);
        } catch (Exception e) {e.printStackTrace();}

    }

    @FXML
    void btn_menu_log_click(ActionEvent event) {
        System.out.println("log");
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../fxml_files/LogKayit.fxml"));
            AnchorPane panel = (AnchorPane) loader.load();
            LogKayitController lkCont = loader.getController();
            lkCont.displayContname(lbl_username.getText());

            panel.getStylesheets().add(getClass().getResource("../css_files/LogKayit.css").toExternalForm());
            anchor_govde.getChildren().setAll(panel);
        } catch (Exception e) {e.printStackTrace();}
    }


    @FXML
    void initialize() {

    }
}
