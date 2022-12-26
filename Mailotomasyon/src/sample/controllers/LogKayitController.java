package sample.controllers;

import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import sample.com.IsteMySQL.util.LogKayitlar;
import sample.com.IsteMySQL.util.VeritabaniUtil;

public class LogKayitController {

    Connection baglanti=null;
    PreparedStatement sorguIfadesi=null;
    ResultSet getirilen=null;
    String sql;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private AnchorPane anchor_log_main;

    @FXML
    private TableView<LogKayitlar> table_log;

    @FXML
    private TableColumn<LogKayitlar, Integer> column_id;

    @FXML
    private TableColumn<LogKayitlar, String> column_adres;

    @FXML
    private TableColumn<LogKayitlar, String> column_icerik;

    @FXML
    private TableColumn<LogKayitlar, String> column_tarih;

    @FXML
    private Label lbl_controller;
    public void displayContname(String username) {
        lbl_controller.setText(username);
        System.out.println(lbl_controller.getText().trim());
        VeritabaniUtil.returnMail(lbl_controller.getText());
        degerleriGetir(table_log);
    }

    public void degerleriGetir(TableView tablo) {
        baglanti = VeritabaniUtil.Baglan();
        sql="select * from logkayit where kullanici_id=(select id from kullanicilar where k_adi=?)";
        ObservableList<LogKayitlar> kayitlarListe = FXCollections.observableArrayList();
        try {
            sorguIfadesi=baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1,lbl_controller.getText().trim());
            ResultSet getirilen = sorguIfadesi.executeQuery();
            while (getirilen.next()) {
                kayitlarListe.add(new LogKayitlar(getirilen.getInt("log_id"),getirilen.getString("adres"),getirilen.getString("icerik"),getirilen.getString("tarih")));
            }

            column_id.setCellValueFactory(new PropertyValueFactory<>("log_id"));
            column_adres.setCellValueFactory(new PropertyValueFactory<>("log_adres"));
            column_icerik.setCellValueFactory(new PropertyValueFactory<>("log_icerik"));
            column_tarih.setCellValueFactory(new PropertyValueFactory<>("log_tarih"));

            table_log.setItems(kayitlarListe);

        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
        }

    }

    @FXML
    private Button btn_log_yenile;

    @FXML
    void btn_log_yenile_Click(ActionEvent event) {
        degerleriGetir(table_log);
        System.out.println("Tablo yenilendi");
    }

    @FXML
    void initialize() {
        assert anchor_log_main != null : "fx:id=\"anchor_log_main\" was not injected: check your FXML file 'LogKayit.fxml'.";
        assert table_log != null : "fx:id=\"table_log\" was not injected: check your FXML file 'LogKayit.fxml'.";
        assert column_id != null : "fx:id=\"column_id\" was not injected: check your FXML file 'LogKayit.fxml'.";
        assert column_adres != null : "fx:id=\"column_adres\" was not injected: check your FXML file 'LogKayit.fxml'.";
        assert column_icerik != null : "fx:id=\"column_icerik\" was not injected: check your FXML file 'LogKayit.fxml'.";
        assert column_tarih != null : "fx:id=\"column_tarih\" was not injected: check your FXML file 'LogKayit.fxml'.";
        assert btn_log_yenile != null : "fx:id=\"btn_log_yenile\" was not injected: check your FXML file 'LogKayit.fxml'.";

    }
}
