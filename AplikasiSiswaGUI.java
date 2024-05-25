import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class AplikasiSiswaGUI extends JFrame {
    private JTable tabel;
    private DefaultTableModel model;
    private JTextField namaField;
    private JTextField umurField;
    private Database dbManager;

    public AplikasiSiswaGUI() {
        dbManager = new Database();

        setTitle("Database Siswa");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        model = new DefaultTableModel(new String[]{"ID", "Nama", "Umur"}, 0);
        tabel = new JTable(model);
        JScrollPane scrollPane = new JScrollPane(tabel);

        namaField = new JTextField(15);
        umurField = new JTextField(3);
        JButton tambahButton = new JButton("Tambah");

        JPanel inputPanel = new JPanel();
        inputPanel.add(new JLabel("Nama:"));
        inputPanel.add(namaField);
        inputPanel.add(new JLabel("Umur:"));
        inputPanel.add(umurField);
        inputPanel.add(tambahButton);

        add(scrollPane, BorderLayout.CENTER);
        add(inputPanel, BorderLayout.SOUTH);

        tambahButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                tambahSiswa();
            }
        });

        muatSiswa();
        setVisible(true);
    }

    private void muatSiswa() {
        List<Siswa> siswaList = dbManager.getAllSiswa();
        for (Siswa siswa : siswaList) {
            model.addRow(new Object[]{siswa.getId(), siswa.getNama(), siswa.getUmur()});
        }
    }

    private void tambahSiswa() {
        String nama = namaField.getText();
        String umurText = umurField.getText();

        if (nama.isEmpty() || umurText.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Nama dan umur tidak boleh kosong.", "Input Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int umur = Integer.parseInt(umurText);
            dbManager.tambahSiswa(nama, umur);

            model.setRowCount(0);
            muatSiswa();
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Umur harus berupa angka.", "Input Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Gagal menambah data siswa.", "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new AplikasiSiswaGUI();
            }
        });
    }
}
