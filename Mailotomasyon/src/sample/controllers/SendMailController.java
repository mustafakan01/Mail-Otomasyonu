package sample.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import sample.com.IsteMySQL.util.Kayitlar;
import sample.com.IsteMySQL.util.VeritabaniUtil;

import java.util.Date;
import java.util.Properties;

// Java Mail Kütüphaneleri
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class SendMailController {

    public void sendPlainTextEmail(String host, String port,
                                   final String userName, final String password, String toAddress,
                                   String subject, String message) throws AddressException,
            MessagingException {

        // SMTP server kurulumu
        Properties properties = new Properties();
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");

        // Kullanici oturumu.
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        };

        Session session = Session.getInstance(properties, auth);

        // yeni e-mail mesaji
        Message msg = new MimeMessage(session);

        msg.setFrom(new InternetAddress(userName));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
        // mesaj içeriğinin set edilmesi
        msg.setText(message);

        // e-mail'in gönderilmesi
        Transport.send(msg);

    }

    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private Label lbl_controller;
    public void displayContname(String username) {
        lbl_controller.setText(username);
        System.out.println(lbl_controller.getText().trim());
        VeritabaniUtil.returnMail(lbl_controller.getText());
        degerleriGetir(kayitlar_tablo);
    }

    @FXML
    private TableView<Kayitlar> kayitlar_tablo;

    @FXML
    private TableColumn<Kayitlar, Integer> tablo_id;

    @FXML
    private TableColumn<Kayitlar, String > tablo_ad;

    @FXML
    private TableColumn<Kayitlar, String> tablo_soyad;

    @FXML
    private TableColumn<Kayitlar, String> tablo_mail;



    public void degerleriGetir(TableView tablo) {
        baglanti = VeritabaniUtil.Baglan();
        sql="select * from kayitlar where kullanici_id=(select id from kullanicilar where k_adi=?)";
        ObservableList<Kayitlar> kayitlarListe = FXCollections.observableArrayList();
        try {
            sorguIfadesi=baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1,lbl_controller.getText().trim());
            ResultSet getirilen = sorguIfadesi.executeQuery();
            while (getirilen.next()) {
                kayitlarListe.add(new Kayitlar(getirilen.getInt("kayit_id"),getirilen.getString("kayit_adi"),getirilen.getString("kayit_soyadi"),getirilen.getString("kayit_mail")));
            }

            tablo_id.setCellValueFactory(new PropertyValueFactory<>("kayit_id"));
            tablo_ad.setCellValueFactory(new PropertyValueFactory<>("kayit_ad"));
            tablo_soyad.setCellValueFactory(new PropertyValueFactory<>("kayit_soyad"));
            tablo_mail.setCellValueFactory(new PropertyValueFactory<>("kayit_mail"));

            kayitlar_tablo.setItems(kayitlarListe);

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }

    }

    @FXML
    private TextField txt_subject;

    @FXML
    private TextField txt_toAddress;

    @FXML
    private TextArea txt_message;

    @FXML
    private Button mail_gonder;


    @FXML
    void mail_gonder_click(ActionEvent event) {
        if (txt_subject.getText().isEmpty() || txt_message.getText().isEmpty() || txt_toAddress.getText().isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gönderme Hatası");
            alert.setHeaderText(null);
            alert.setContentText("Lütfen \"Mail Konusu\",\"Alıcı Adresi\" ve \"İçerik\" kısımlarını doldurun. .");
            alert.showAndWait();
        } else {

        // SMTP server bilgileri
        String host = "smtp.gmail.com";
        String port = "587";
        String mailFrom = VeritabaniUtil.returnMail(lbl_controller.getText());
        String password = VeritabaniUtil.returnPass(lbl_controller.getText());

        // Gonderilecek mail bilgisi
        String mailTo = txt_toAddress.getText();
        String subject = txt_subject.getText();
        String message = txt_message.getText();

        SendMailController mailer = new SendMailController();

        try {
            mailer.sendPlainTextEmail(host, port, mailFrom, password, mailTo,
                    subject, message);
            //Log kayit
            VeritabaniUtil.logKayit(lbl_controller.getText().trim(),mailTo,message);

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Gönderme Başarılı");
            alert.setHeaderText(null);
            alert.setContentText("\"" + txt_subject.getText() + "\" konulu mail, \"" + txt_toAddress.getText() + "\" adresine gönderildi.");
            alert.showAndWait();
            txt_toAddress.clear();
            txt_message.clear();
            txt_subject.clear();

        } catch (Exception ex) {

            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Gönderme Hatası");
            alert.setContentText("Lütfen \"Alıcı Adresi\" kısmını kontrol ediniz.\n \"Alıcı Adresi\" kısmının geçerli bir mail adresi olmasına özen gösteriniz.");
            alert.setHeaderText(null);
            alert.showAndWait();
        }
    }
    }


    @FXML
    void kayitlar_tablo_click(MouseEvent event) {
        try {

            Kayitlar kayit = new Kayitlar();
            kayit=(Kayitlar) kayitlar_tablo.getItems().get(kayitlar_tablo.getSelectionModel().getSelectedIndex());
            txt_toAddress.setText(kayit.getKayit_mail());

        } catch (Exception e) {
            System.out.println("Tıklanan alanda değer yok");
        }
    }

    @FXML
    void initialize() {
        degerleriGetir(kayitlar_tablo);


        assert kayitlar_tablo != null : "fx:id=\"kayitlar_tablo\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert tablo_id != null : "fx:id=\"tablo_id\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert tablo_ad != null : "fx:id=\"tablo_ad\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert tablo_soyad != null : "fx:id=\"tablo_soyad\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert tablo_mail != null : "fx:id=\"tablo_mail\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert txt_subject != null : "fx:id=\"txt_subject\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert txt_toAddress != null : "fx:id=\"txt_toAddress\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert txt_message != null : "fx:id=\"txt_message\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert mail_gonder != null : "fx:id=\"mail_gonder\" was not injected: check your FXML file 'SendMail.fxml'.";
        assert lbl_controller != null : "fx:id=\"lbl_controller\" was not injected: check your FXML file 'SendMail.fxml'.";
    }
}
