package sample.com.IsteMySQL.util;
import javafx.scene.control.Alert;
import sample.emailValidation.isValidAddress;
import java.sql.*;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class VeritabaniUtil {
    static Connection conn=null;

    public static Connection Baglan() {
        try {
            //"jdbc:mysql://ServerIPAdresi/db_ismi"; "kullanici","sifre"
            conn=DriverManager.getConnection("jdbc:mysql://localhost/otomasyondb"); // "root", "mysql"
            return conn;
        } catch (Exception e) {
            System.out.println(e.getMessage().toString());
            return null;
        }
    }


    public static String returnMail(String kadi) {
        Connection baglanti2=null;
        PreparedStatement sorguIfadesi2=null;
        baglanti2 = VeritabaniUtil.Baglan();
        String kullanici_mail="select mail_adres from kullanicilar where k_adi=?";
        try {
            sorguIfadesi2=baglanti2.prepareStatement(kullanici_mail);
            sorguIfadesi2.setString(1,kadi);
            ResultSet get_kullanici_mail = sorguIfadesi2.executeQuery();
            get_kullanici_mail.next();
            return get_kullanici_mail.getString("mail_adres");

        } catch (Exception e) {
            System.out.println("hata");
        }
        return "Deger Donmedi";
    }
    public static String returnPass(String kadi) {
        Connection baglanti2=null;
        PreparedStatement sorguIfadesi2=null;
        baglanti2 = VeritabaniUtil.Baglan();
        String kullanici_mail="select parola from kullanicilar where k_adi=?";
        try {
            sorguIfadesi2=baglanti2.prepareStatement(kullanici_mail);
            sorguIfadesi2.setString(1,kadi);
            ResultSet get_kullanici_mail = sorguIfadesi2.executeQuery();
            get_kullanici_mail.next();
            return get_kullanici_mail.getString("parola");

        } catch (Exception e) {
            System.out.println("hata");
        }
        return "Deger Donmedi";
    }

    public static Integer returnID(String kadi) {
        Connection baglanti;
        PreparedStatement sorguIfadesi;
        baglanti = VeritabaniUtil.Baglan();
        String kullanici_adi="select id from kullanicilar where k_adi=?";
        try {
            sorguIfadesi=baglanti.prepareStatement(kullanici_adi);
            sorguIfadesi.setString(1,kadi);
            ResultSet get_kullanici_id = sorguIfadesi.executeQuery();
            if (get_kullanici_id.next()) {
                return get_kullanici_id.getInt("id");
            }
        } catch (Exception e) {
            System.out.println("hata");
        }
        return null;
    }

    public static void kayitSil(Integer id) {
        Connection baglanti=null;
        PreparedStatement sorguIfadesi=null;
        String sql;
        baglanti = VeritabaniUtil.Baglan();
        sql = "DELETE FROM kayitlar where kayit_id=" + id;
        try {
            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.executeUpdate();
            System.out.println("Kayıt silindi.");

        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hata");
            alert.setHeaderText(null);
            alert.setContentText("Silme işlemi sırasında bir hata oluştu.");

        }
    }

    public static void kayitEkle(String kadi,String ad,String soyad,String mail) {
        Connection baglanti=null;
        PreparedStatement sorguIfadesi=null;
        String sql;
        baglanti = VeritabaniUtil.Baglan();
        sql="insert into kayitlar (kayit_adi, kayit_soyadi, kayit_mail, kullanici_id) values (?,?,?,?)";
        if (isValidAddress.isValidEmailAddress(mail)) {
            try {
                int getid = VeritabaniUtil.returnID(kadi);

                sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setString(1, ad);
                sorguIfadesi.setString(2, soyad);
                sorguIfadesi.setString(3, mail);
                sorguIfadesi.setInt(4, getid);
                sorguIfadesi.execute();


            } catch (SQLException throwables) {
                throwables.printStackTrace();
            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("");
            alert.setContentText("Mail adresi '@gmail.com' formatında olmalıdır!");
            alert.showAndWait();
        }
    }

    public static void kayitGuncelle(String ad,String soyad,String mail,Integer id) {
        Connection baglanti=null;
        PreparedStatement sorguIfadesi=null;
        String sql;
        baglanti = VeritabaniUtil.Baglan();
        sql="UPDATE kayitlar SET kayit_adi=?, kayit_soyadi=?, kayit_mail=? WHERE kayit_id=?";
        if (isValidAddress.isValidEmailAddress(mail)) {
            try {
                //System.out.println(txt_menu_ad.getText());
                sorguIfadesi = baglanti.prepareStatement(sql);
                sorguIfadesi.setString(1, ad);
                sorguIfadesi.setString(2, soyad);
                sorguIfadesi.setString(3, mail);
                sorguIfadesi.setInt(4, id);
                sorguIfadesi.executeUpdate();
                System.out.println("Kayıt güncellendi.");


            } catch (Exception e) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Hata");
                alert.setHeaderText(null);
                alert.setContentText("Güncelleme işlemi sırasında bir hata oluştu.");

            }
        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("");
            alert.setContentText("Mail adresi '@gmail.com' formatında olmalıdır!");
            alert.showAndWait();
        }
    }

    public static void kayitOl(String kadi,String mail,String kpass,String kpass2) {
        Connection baglanti=null;
        PreparedStatement sorguIfadesi=null;
        String sql;
        baglanti = VeritabaniUtil.Baglan();
        sql="insert into kullanicilar(k_adi,mail_adres,parola) values (?,?,?)";
        if (isValidAddress.isValidEmailAddress(mail)){
            if (kpass.equals(kpass2)) {
                try {
                    sorguIfadesi=baglanti.prepareStatement(sql);
                    sorguIfadesi.setString(1,kadi);
                    sorguIfadesi.setString(2,mail);
                    sorguIfadesi.setString(3,kpass);
                    sorguIfadesi.executeUpdate();

                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Kayıt");
                    alert.setHeaderText("Kayıt Başarılı");
                    alert.setContentText("Kaydınız başarılı bir şekilde oluşturuldu.");
                    alert.showAndWait();

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            else{
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Uyarı");
                alert.setHeaderText("");
                alert.setContentText("Parola ve Parola Takrar birbinine uymamaktadır!");
                alert.showAndWait();
            }

        }
        else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Uyarı");
            alert.setHeaderText("");
            alert.setContentText("Mail adresi '@gmail.com' formatında olmalıdır!");
            alert.showAndWait();
        }
    }

    public static void logKayit(String kadi,String adres,String icerik) {
        Connection baglanti=null;
        PreparedStatement sorguIfadesi=null;
        String sql;
        baglanti = VeritabaniUtil.Baglan();
        sql="insert into logkayit (adres, icerik, tarih, kullanici_id) values (?,?,?,?)";

        try {
            int getid = VeritabaniUtil.returnID(kadi);
            DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            java.util.Date date = new Date();
            String dateStr = dateFormat.format(date).toString();
            System.out.println(dateStr);

            sorguIfadesi = baglanti.prepareStatement(sql);
            sorguIfadesi.setString(1,adres);
            sorguIfadesi.setString(2,icerik);
            sorguIfadesi.setString(3,dateStr);
            sorguIfadesi.setInt(4,getid);
            sorguIfadesi.execute();


        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    /* Mail gönderme işlemi sırasında veritabanındaki şifre ve google serverda
    kullanılan şifre uyuşmadığından mail gönderme işleminde hata veriyor
    bundan dolayı md5 kullanılmadı


    public static String MD5Sifrele(String icerik) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");

            //Byte olarak oku
            byte[] sifrelenmis = md.digest(icerik.getBytes());
            BigInteger no = new BigInteger(1, sifrelenmis);
            //Hex hesapla
            String hashIcerik = no.toString(16);
            while (hashIcerik.length()<32) {
                hashIcerik="0"+hashIcerik;
            }
            return hashIcerik;
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

     */

}
