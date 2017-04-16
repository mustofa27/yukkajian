package entity;

/**
 * Created by LENOVO on 4/14/2017.
 */

public class User {
    public static String tabel = "user",column_id = "id"
            , column_sid = "sid", column_1 = "username", column_2 = "password", column_3 = "email",
            column_4 = "nama", column_5 = "alamat_asal", column_6 = "jenis_kelamin", column_7 = "alamat_domisili",
            column_8 = "nomor_hp", column_9 = "pekerjaan",column_10 = "tempat_lahir", column_11 = "tanggal_lahir";
    int id,sid;
    String username,password,email,nama,alamat_asal,jenis_kelamin,alamat_domisili,nomor_hp,pekerjaan,tempat_lahir,tanggal_lahir;

    public User(int id, int sid, String username, String password, String email, String nama, String alamat_asal, String jenis_kelamin,
                String alamat_domisili, String nomor_hp, String pekerjaan, String tempat_lahir, String tanggal_lahir) {
        this.id = id;
        this.sid = sid;
        this.username = username;
        this.password = password;
        this.email = email;
        this.nama = nama;
        this.alamat_asal = alamat_asal;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat_domisili = alamat_domisili;
        this.nomor_hp = nomor_hp;
        this.pekerjaan = pekerjaan;
        this.tempat_lahir = tempat_lahir;
        this.tanggal_lahir = tanggal_lahir;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat_asal() {
        return alamat_asal;
    }

    public void setAlamat_asal(String alamat_asal) {
        this.alamat_asal = alamat_asal;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat_domisili() {
        return alamat_domisili;
    }

    public void setAlamat_domisili(String alamat_domisili) {
        this.alamat_domisili = alamat_domisili;
    }

    public String getNomor_hp() {
        return nomor_hp;
    }

    public void setNomor_hp(String nomor_hp) {
        this.nomor_hp = nomor_hp;
    }

    public String getPekerjaan() {
        return pekerjaan;
    }

    public void setPekerjaan(String pekerjaan) {
        this.pekerjaan = pekerjaan;
    }

    public String getTempat_lahir() {
        return tempat_lahir;
    }

    public void setTempat_lahir(String tempat_lahir) {
        this.tempat_lahir = tempat_lahir;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }
}
