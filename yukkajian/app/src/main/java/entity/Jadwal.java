package entity;

/**
 * Created by LENOVO on 2/2/2017.
 */

public class Jadwal {
    public static String tabel = "jadwal",column_id = "id"
            , column_1 = "lat", column_2 = "long", column_3 = "subuh",
            column_4 = "terbit", column_5 = "dzuhur", column_6 = "asr", column_7 = "maghrib",
            column_8 = "isya", column_9 = "tanggal";

    private int id;
    private String latitude;
    private String longitude;
    private String subuh;
    private String terbit;
    private String dzuhur;
    private String asr;
    private String maghrib;
    private String isya;
    private String tanggal;

    public Jadwal(int id, String latitude, String longitude, String subuh, String terbit,
                  String dzuhur, String asr, String maghrib, String isya, String tanggal) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.subuh = subuh;
        this.terbit = terbit;
        this.dzuhur = dzuhur;
        this.asr = asr;
        this.maghrib = maghrib;
        this.isya = isya;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getSubuh() {
        return subuh;
    }

    public void setSubuh(String subuh) {
        this.subuh = subuh;
    }

    public String getTerbit() {
        return terbit;
    }

    public void setTerbit(String terbit) {
        this.terbit = terbit;
    }

    public String getDzuhur() {
        return dzuhur;
    }

    public void setDzuhur(String dzuhur) {
        this.dzuhur = dzuhur;
    }

    public String getAsr() {
        return asr;
    }

    public void setAsr(String asr) {
        this.asr = asr;
    }

    public String getMaghrib() {
        return maghrib;
    }

    public void setMaghrib(String maghrib) {
        this.maghrib = maghrib;
    }

    public String getIsya() {
        return isya;
    }

    public void setIsya(String isya) {
        this.isya = isya;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
