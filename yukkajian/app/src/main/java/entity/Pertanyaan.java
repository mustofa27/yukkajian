package entity;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class Pertanyaan {
    public static String tabel = "pertanyaan",column_id = "id"
            , column_sid = "sid", column_1 = "id_kajian", column_2 = "id_user", column_3 = "pertanyaan";
    int id,sid,id_kajian,id_user;
    String pertanyaan;

    public Pertanyaan(int id, int sid, int id_kajian, int id_user, String pertanyaan) {
        this.id = id;
        this.sid = sid;
        this.id_kajian = id_kajian;
        this.id_user = id_user;
        this.pertanyaan = pertanyaan;
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

    public int getId_kajian() {
        return id_kajian;
    }

    public void setId_kajian(int id_kajian) {
        this.id_kajian = id_kajian;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getPertanyaan() {
        return pertanyaan;
    }

    public void setPertanyaan(String pertanyaan) {
        this.pertanyaan = pertanyaan;
    }
}
