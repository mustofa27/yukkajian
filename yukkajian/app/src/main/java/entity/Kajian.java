package entity;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class Kajian {
    public static String tabel = "kajian",column_id = "id"
            , column_sid = "sid", column_1 = "tanggal", column_2 = "jam", column_3 = "tema",
            column_4 = "pemateri", column_5 = "alamat", column_6 = "lat", column_7 = "longi",
            column_8 = "deskripsi", column_9 = "jenis_peserta", column_10 = "is_rutin";
    int id,sid,isRutin;
    String tanggal,jam,tema,pemateri,alamat,lat,longi,deskripsi,jenis_peserta;

    public Kajian(int id, int sid, int isRutin, String tanggal, String jam, String tema, String pemateri,
                  String alamat, String lat, String longi, String deskripsi, String jenis_peserta) {
        this.id = id;
        this.sid = sid;
        this.isRutin = isRutin;
        this.tanggal = tanggal;
        this.jam = jam;
        this.tema = tema;
        this.pemateri = pemateri;
        this.alamat = alamat;
        this.lat = lat;
        this.longi = longi;
        this.deskripsi = deskripsi;
        this.jenis_peserta = jenis_peserta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSid() {
        return sid;
    }

    public void setSid(int sid) {
        this.sid = sid;
    }

    public int getIsRutin() {
        return isRutin;
    }

    public void setIsRutin(int isRutin) {
        this.isRutin = isRutin;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }

    public String getJam() {
        return jam;
    }

    public void setJam(String jam) {
        this.jam = jam;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public String getPemateri() {
        return pemateri;
    }

    public void setPemateri(String pemateri) {
        this.pemateri = pemateri;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLongi() {
        return longi;
    }

    public void setLongi(String longi) {
        this.longi = longi;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getJenis_peserta() {
        return jenis_peserta;
    }

    public void setJenis_peserta(String jenis_peserta) {
        this.jenis_peserta = jenis_peserta;
    }
}
