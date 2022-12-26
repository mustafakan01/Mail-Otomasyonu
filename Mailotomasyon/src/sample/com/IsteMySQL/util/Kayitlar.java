package sample.com.IsteMySQL.util;

public class Kayitlar {
    private int kayit_id;
    private String kayit_ad;
    private String kayit_soyad;
    private String kayit_mail;

    public Kayitlar() {
    }

    public Kayitlar(int kayit_id, String kayit_ad, String kayit_soyad, String kayit_mail) {
        this.kayit_id = kayit_id;
        this.kayit_ad = kayit_ad;
        this.kayit_soyad = kayit_soyad;
        this.kayit_mail = kayit_mail;
    }

    public int getKayit_id() {
        return kayit_id;
    }

    public void setKayit_id(int kayit_id) {
        this.kayit_id = kayit_id;
    }

    public String getKayit_ad() {
        return kayit_ad;
    }

    public void setKayit_ad(String kayit_ad) {
        this.kayit_ad = kayit_ad;
    }

    public String getKayit_soyad() {
        return kayit_soyad;
    }

    public void setKayit_soyad(String kayit_soyad) {
        this.kayit_soyad = kayit_soyad;
    }

    public String getKayit_mail() {
        return kayit_mail;
    }

    public void setKayit_mail(String kayit_mail) {
        this.kayit_mail = kayit_mail;
    }
}
