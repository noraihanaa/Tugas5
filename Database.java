import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class Database {
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/sekolah";
    private static final String DATABASE_USER = "root";
    private static final String DATABASE_PASSWORD = "Bsdcity100_";
    private Connection connect;

    public Database() {
        koneksiKeDatabase();
    }

    private void koneksiKeDatabase() {
        try {
            connect = DriverManager.getConnection(DATABASE_URL, DATABASE_USER, DATABASE_PASSWORD);
            System.out.println("Koneksi berhasil!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Siswa> getAllSiswa() {
        List<Siswa> daftarSiswa = new ArrayList<>();
        String query = "SELECT * FROM siswa";

        try (Statement stmt = connect.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nama = rs.getString("nama");
                int umur = rs.getInt("umur");
                daftarSiswa.add(new Siswa(id, nama, umur));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return daftarSiswa;
    }

    public void tambahSiswa(String nama, int umur) throws SQLException {
        String query = "INSERT INTO siswa (nama, umur) VALUES (?, ?)";
        try (PreparedStatement pstmt = connect.prepareStatement(query)) {
            pstmt.setString(1, nama);
            pstmt.setInt(2, umur);
            pstmt.executeUpdate();
        }
    }
}
