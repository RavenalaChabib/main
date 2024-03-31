import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

class Person {
    private String nama;
    private String alamat;

    public Person(String nama, String alamat) {
        this.nama = nama;
        this.alamat = alamat;
    }

    public String getNama() {
        return nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    @Override
    public String toString() {
        return nama + "(" + alamat + ")";
    }
}

class Student extends Person {
    private List<String> kursus = new ArrayList<>();
    private List<Integer> nilai = new ArrayList<>();

    public Student(String nama, String alamat) {
        super(nama, alamat);
    }

    public void tambahKursusNilai(String kursus, int nilai) {
        this.kursus.add(kursus);
        this.nilai.add(nilai);
    }

    public void cetakNilai() {
        System.out.println("Nilai: " + Arrays.toString(nilai.toArray()));
    }

    public double getRataRataNilai() {
        if (nilai.isEmpty()) return 0;
        int total = 0;
        for (int skor : nilai) {
            total += skor;
        }
        return (double) total / nilai.size();
    }

    @Override
    public String toString() {
        return "Mahasiswa:" + super.toString();
    }
}

class Teacher extends Person {
    private static final HashMap<String, Integer> idGuru = new HashMap<>();
    private List<String> kursus = new ArrayList<>();
    private int id;

    public Teacher(String nama, String alamat) {
        super(nama, alamat);
        this.id = idGuru.getOrDefault(nama, idGuru.size() + 1);
        idGuru.putIfAbsent(nama, this.id);
    }

    public boolean tambahKursus(String kursus) {
        if (this.kursus.contains(kursus)) {
            return false; // Kursus sudah ada
        } else {
            this.kursus.add(kursus);
            return true; // Kursus berhasil ditambahkan
        }
    }

    public boolean hapusKursus(String kursus) {
        return this.kursus.remove(kursus); // Mengembalikan true jika item ada dan berhasil dihapus
    }

    @Override
    public String toString() {
        return "Dosen ID#" + id + ":" + super.toString();
    }
    
    public void cetakKursus() {
        System.out.println("Kursus yang diajar: " + kursus);
    }
}

public class Utama {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        ArrayList<Teacher> daftarGuru = new ArrayList<>();

        // Input untuk Mahasiswa
        System.out.print("Masukkan nama Mahasiswa: ");
        String namaMahasiswa = scanner.nextLine();
        System.out.print("Masukkan alamat Mahasiswa: ");
        String alamatMahasiswa = scanner.nextLine();
        Student mahasiswa = new Student(namaMahasiswa, alamatMahasiswa);

        System.out.print("Masukkan jumlah kursus yang diikuti: ");
        int jumlahKursus = Integer.parseInt(scanner.nextLine());

        for (int i = 0; i < jumlahKursus; i++) {
            System.out.print("Masukkan nama kursus ke-" + (i + 1) + ": ");
            String kursus = scanner.nextLine();
            System.out.print("Masukkan nilai untuk " + kursus + ": ");
            int nilai = Integer.parseInt(scanner.nextLine());
            mahasiswa.tambahKursusNilai(kursus, nilai);
            
            // Input untuk Dosen setelah kursus
            System.out.print("Masukkan nama Dosen untuk kursus " + kursus + ": ");
            String namaDosen = scanner.nextLine();
            System.out.print("Masukkan alamat Dosen untuk kursus " + kursus + ": ");
            String alamatDosen = scanner.nextLine();
            Teacher dosen = new Teacher(namaDosen, alamatDosen);
            if (dosen.tambahKursus(kursus)) {
                System.out.println("Kursus " + kursus + " berhasil ditambahkan ke dosen.");
                daftarGuru.add(dosen);
            } else {
                System.out.println("Kursus " + kursus + " sudah ada pada dosen.");
            }
        }

        // Menampilkan semua hasil akhir
        System.out.println("\nHasil Akhir:");
        System.out.println(mahasiswa);
        mahasiswa.cetakNilai();
        System.out.println("Rata-Rata Nilai: " + mahasiswa.getRataRataNilai());

        for (Teacher guru : daftarGuru) {
            System.out.println("\n" + guru);
            guru.cetakKursus();
        }
    }
}
