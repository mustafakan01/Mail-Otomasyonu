package sample.com.IsteMySQL.util;

public class LogKayitlar {
    private int log_id;
    private String log_adres;
    private String log_icerik;
    private String log_tarih;

    public LogKayitlar(){

    }

    public LogKayitlar(int log_id,String log_adres,String log_icerik,String log_tarih) {
        this.log_id = log_id;
        this.log_adres = log_adres;
        this.log_icerik = log_icerik;
        this.log_tarih = log_tarih;
    }

    public int getLog_id() {
        return log_id;
    }

    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }

    public String getLog_adres() {
        return log_adres;
    }

    public void setLog_adres(String log_adres) {
        this.log_adres = log_adres;
    }

    public String getLog_icerik() {
        return log_icerik;
    }

    public void setLog_icerik(String log_icerik) {
        this.log_icerik = log_icerik;
    }

    public String getLog_tarih() {
        return log_tarih;
    }

    public void setLog_tarih(String log_tarih) {
        this.log_tarih = log_tarih;
    }
}
