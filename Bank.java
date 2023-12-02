// Program Bank Minimalis menggunakan bahasa java
// Fitur-fitur: simpan/setor, tarik uang, transfer antar nasabah, tambah nasabah
// Ada admin dan nasabah (log in)
// Admin bisa lihat seluruh info nasabah (seperti teller)
// Nasabah hanya bisa melihat informasi sendiri (detail transaksi, riwayat transaksi)

// Class Akun sebagai superclass untuk class Admin dan Nasabah
// Mengimplementasikan konsep inheritance

import java.util.ArrayList;
import java.util.Scanner;



class Akun {
    // Atribut bersama untuk admin dan nasabah
    protected String username;
    protected String password;

    // Konstruktor untuk class Akun
    public Akun(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Method untuk mengecek apakah username dan password sesuai
    public boolean login(String username, String password) {
        return this.username.equals(username) && this.password.equals(password);
    }
}

// Class Admin sebagai subclass dari class Akun
// Mengimplementasikan konsep inheritance
class Admin extends Akun {
    // Konstruktor untuk class Admin
    public Admin(String username, String password) {
        super(username, password); // Memanggil konstruktor superclass
    }

    // Method untuk menampilkan seluruh info nasabah
    public void lihatInfoNasabah(Bank bank) {
        System.out.println("Daftar nasabah bank " + bank.getNama() + ":");
        for (Nasabah n : bank.getNasabah()) {
            System.out.println(n); // Memanggil method toString() dari class Nasabah
        }
    }

    // Method untuk menambahkan nasabah baru ke bank
    public void tambahNasabah(Bank bank, Nasabah nasabah) {
        bank.tambahNasabah(nasabah); // Memanggil method tambahNasabah() dari class Bank
        System.out.println("Nasabah baru berhasil ditambahkan.");
    }
}

// Class Nasabah sebagai subclass dari class Akun
// Mengimplementasikan konsep inheritance
class Nasabah extends Akun {
    // Atribut khusus untuk nasabah
    private String nama;
    private String alamat;
    private String noRekening;
    private double saldo;
    private ArrayList<Transaksi> riwayat; // Mengimplementasikan konsep agregasi

    // Konstruktor untuk class Nasabah
    public Nasabah(String username, String password, String nama, String alamat, String noRekening, double saldo) {
        super(username, password); // Memanggil konstruktor superclass
        this.nama = nama;
        this.alamat = alamat;
        this.noRekening = noRekening;
        this.saldo = saldo;
        this.riwayat = new ArrayList<Transaksi>(); // Membuat objek ArrayList baru
    }

    // Getter dan setter untuk atribut-atribut nasabah
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNoRekening() {
        return noRekening;
    }

    public void setNoRekening(String noRekening) {
        this.noRekening = noRekening;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public ArrayList<Transaksi> getRiwayat() {
        return riwayat;
    }

    public void setRiwayat(ArrayList<Transaksi> riwayat) {
        this.riwayat = riwayat;
    }

    // Method untuk menampilkan detail transaksi terakhir
    public void lihatDetailTransaksi() {
        if (riwayat.isEmpty()) { // Jika riwayat kosong
            System.out.println("Anda belum melakukan transaksi apapun.");
        } else { // Jika riwayat ada
            Transaksi t = riwayat.get(riwayat.size() - 1); // Mengambil transaksi terakhir
            System.out.println("Detail transaksi terakhir Anda:");
            System.out.println(t); // Memanggil method toString() dari class Transaksi
        }
    }

    // Method untuk menampilkan riwayat transaksi
    public void lihatRiwayatTransaksi() {
        if (riwayat.isEmpty()) { // Jika riwayat kosong
            System.out.println("Anda belum melakukan transaksi apapun.");
        } else { // Jika riwayat ada
            System.out.println("Riwayat transaksi Anda:");
            for (Transaksi t : riwayat) {
                System.out.println(t); // Memanggil method toString() dari class Transaksi
            }
        }
    }

    // Method untuk menambahkan transaksi baru ke riwayat
    public void tambahTransaksi(Transaksi transaksi) {
        riwayat.add(transaksi); // Menambahkan objek Transaksi ke ArrayList riwayat
    }

    // Method untuk melakukan simpan/setor uang
    public void simpanUang(double jumlah) {
        saldo += jumlah; // Menambahkan saldo dengan jumlah uang yang disimpan
        Transaksi t = new Transaksi("Simpan", jumlah, saldo); // Membuat objek Transaksi baru
        tambahTransaksi(t); // Menambahkan transaksi baru ke riwayat
        System.out.println("Anda berhasil menyimpan uang sebesar Rp " + jumlah);
    }

    // Method untuk melakukan tarik uang
    public void tarikUang(double jumlah) {
        if (jumlah > saldo) { // Jika jumlah uang yang ditarik melebihi saldo
            System.out.println("Saldo Anda tidak cukup untuk melakukan penarikan.");
        } else { // Jika jumlah uang yang ditarik tidak melebihi saldo
            saldo -= jumlah; // Mengurangi saldo dengan jumlah uang yang ditarik
            Transaksi t = new Transaksi("Tarik", jumlah, saldo); // Membuat objek Transaksi baru
            tambahTransaksi(t); // Menambahkan transaksi baru ke riwayat
            System.out.println("Anda berhasil menarik uang sebesar Rp " + jumlah);
        }
    }

    // Method untuk melakukan transfer uang ke nasabah lain
    public void transferUang(Nasabah penerima, double jumlah) {
        if (jumlah > saldo) { // Jika jumlah uang yang ditransfer melebihi saldo
            System.out.println("Saldo Anda tidak cukup untuk melakukan transfer.");
        } else { // Jika jumlah uang yang ditransfer tidak melebihi saldo
            saldo -= jumlah; // Mengurangi saldo dengan jumlah uang yang ditransfer
            penerima.saldo += jumlah; // Menambahkan saldo penerima dengan jumlah uang yang ditransfer
            Transaksi t1 = new Transaksi("Transfer", jumlah, saldo, penerima); // Membuat objek Transaksi baru untuk
                                                                               // pengirim
            Transaksi t2 = new Transaksi("Terima", jumlah, penerima.saldo, this); // Membuat objek Transaksi baru untuk
                                                                                  // penerima
            tambahTransaksi(t1); // Menambahkan transaksi baru ke riwayat pengirim
            penerima.tambahTransaksi(t2); // Menambahkan transaksi baru ke riwayat penerima
            System.out.println("Anda berhasil mentransfer uang sebesar Rp " + jumlah + " ke " + penerima.nama);
        }
    }

    // Method untuk menampilkan informasi nasabah
    public String toString() {
        return "Nama: " + nama + "\nAlamat: " + alamat + "\nNo. Rekening: " + noRekening + "\nSaldo: Rp " + saldo;
    }
}


// Class Transaksi sebagai class yang merepresentasikan transaksi yang dilakukan
// oleh nasabah
// Mengimplementasikan konsep komposisi
class Transaksi {
    // Atribut untuk transaksi
    private String jenis; // Jenis transaksi: Simpan, Tarik, Transfer, atau Terima
    private double jumlah; // Jumlah uang yang terlibat dalam transaksi
    private double saldo; // Saldo setelah transaksi
    private Nasabah lawan; // Lawan transaksi, jika ada

    // Konstruktor untuk transaksi Simpan atau Tarik
    public Transaksi(String jenis, double jumlah, double saldo) {
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.saldo = saldo;
        this.lawan = null; //
    }

    // Konstruktor untuk transaksi Transfer atau Terima
    public Transaksi(String jenis, double jumlah, double saldo, Nasabah lawan) {
        this.jenis = jenis;
        this.jumlah = jumlah;
        this.saldo = saldo;
        this.lawan = lawan; // Mengimplementasikan konsep asosiasi
    }

    // Getter dan setter untuk atribut-atribut transaksi
    public String getJenis() {
        return jenis;
    }

    public void setJenis(String jenis) {
        this.jenis = jenis;
    }

    public double getJumlah() {
        return jumlah;
    }

    public void setJumlah(double jumlah) {
        this.jumlah = jumlah;
    }

    public double getSaldo() {
        return saldo;
    }

    public void setSaldo(double saldo) {
        this.saldo = saldo;
    }

    public Nasabah getLawan() {
        return lawan;
    }

    public void setLawan(Nasabah lawan) {
        this.lawan = lawan;
    }

    // Method untuk menampilkan informasi transaksi
    public String toString() {
        String info = "Jenis: " + jenis + "\nJumlah: Rp " + jumlah + "\nSaldo: Rp " + saldo;
        if (lawan != null) { // Jika ada lawan transaksi
            info += "\nLawan: " + lawan.getNama(); // Menambahkan nama lawan transaksi
        }
        return info;
    }
}

// Class Bank sebagai class yang merepresentasikan bank yang memiliki nasabah
// Mengimplementasikan konsep agregasi
class Bank {
    // Atribut untuk bank
    private String nama;
    private ArrayList<Nasabah> nasabah; // Mengimplementasikan konsep agregasi

    // Konstruktor untuk class Bank
    public Bank(String nama) {
        this.nama = nama;
        this.nasabah = new ArrayList<Nasabah>(); // Membuat objek ArrayList baru
    }

    // Getter dan setter untuk atribut-atribut bank
    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
        this.nama = nama;
    }

    public ArrayList<Nasabah> getNasabah() {
        return nasabah;
    }

    public void setNasabah(ArrayList<Nasabah> nasabah) {
        this.nasabah = nasabah;
    }

    // Method untuk menambahkan nasabah baru ke bank
    public void tambahNasabah(Nasabah nasabah) {
        this.nasabah.add(nasabah); // Menambahkan objek Nasabah ke ArrayList nasabah
    }

    // Method untuk mencari nasabah berdasarkan nomor rekening
    public Nasabah cariNasabah(String noRekening) {
        for (Nasabah n : nasabah) { // Looping untuk setiap nasabah di bank
            if (n.getNoRekening().equals(noRekening)) { // Jika nomor rekening sesuai
                return n; // Mengembalikan objek Nasabah yang dicari
            }
        }
        return null; // Mengembalikan null jika tidak ditemukan
    }
}


// Class Aplikasi sebagai class yang merepresentasikan aplikasi bank minimalis
// Mengimplementasikan konsep polimorfisme
class Aplikasi {
    // Atribut untuk aplikasi
    private Bank bank; // Mengimplementasikan konsep asosiasi
    private Akun akun; // Mengimplementasikan konsep asosiasi
    private Scanner input; // Mengimplementasikan konsep komposisi

    // Konstruktor untuk class Aplikasi
    public Aplikasi(Bank bank) {
        this.bank = bank;
        this.akun = null;
        this.input = new Scanner(System.in); // Membuat objek Scanner baru
    }

    // Method untuk menampilkan menu utama
    public void tampilMenuUtama() {
        System.out.println("Selamat datang di aplikasi bank minimalis " + bank.getNama());
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("1. Login sebagai admin");
        System.out.println("2. Login sebagai nasabah");
        System.out.println("3. Keluar");
        System.out.print("Masukkan pilihan Anda: ");
    }

    // Method untuk menampilkan menu admin
    public void tampilMenuAdmin() {
        System.out.println("Anda login sebagai admin");
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("1. Lihat info nasabah");
        System.out.println("2. Tambah nasabah");
        System.out.println("3. Logout");
        System.out.print("Masukkan pilihan Anda: ");
    }

    // Method untuk menampilkan menu nasabah
    public void tampilMenuNasabah() {
        System.out.println("Anda login sebagai nasabah");
        System.out.println("Silakan pilih salah satu opsi berikut:");
        System.out.println("1. Lihat detail transaksi");
        System.out.println("2. Lihat riwayat transaksi");
        System.out.println("3. Simpan uang");
        System.out.println("4. Tarik uang");
        System.out.println("5. Transfer uang");
        System.out.println("6. Logout");
        System.out.print("Masukkan pilihan Anda: ");
    }

    // Method untuk memproses input dari user
    public void prosesInput(int pilihan) {
        switch (pilihan) {
            case 1: // Jika pilihan 1
                if (akun == null) { // Jika belum login
                    loginAdmin(); // Memanggil method loginAdmin()
                } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                    lihatInfoNasabah(); // Memanggil method lihatInfoNasabah()
                } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                    lihatDetailTransaksi(); // Memanggil method lihatDetailTransaksi()
                }
                break;
            case 2: // Jika pilihan 2
                if (akun == null) { // Jika belum login
                    loginNasabah(); // Memanggil method loginNasabah()
                } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                    tambahNasabah(); // Memanggil method tambahNasabah()
                } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                    lihatRiwayatTransaksi(); // Memanggil method lihatRiwayatTransaksi()
                }
                break;
            case 3: // Jika pilihan 3
                if (akun == null) { // Jika belum login
                    keluar(); // Memanggil method keluar()
                } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                    logout(); // Memanggil method logout()
                } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                    simpanUang(); // Memanggil method simpanUang()
                }
                break;
            case 4: // Jika pilihan 4
                if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                    tarikUang(); // Memanggil method tarikUang()
                } else { // Jika belum login atau login sebagai admin
                    System.out.println("Pilihan tidak valid.");
                }
                break;
            case 5: // Jika pilihan 5
                if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                    transferUang(); // Memanggil method transferUang()
                } else { // Jika belum login atau login sebagai admin
                    System.out.println("Pilihan tidak valid.");
                }
                break;
            case 6: // Jika pilihan 6
                if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                    logout(); // Memanggil method logout()
                } else { // Jika belum login atau login sebagai admin
                    System.out.println("Pilihan tidak valid.");
                }
                break;
            default: // Jika pilihan lainnya
                System.out.println("Pilihan tidak valid.");
                break;
        }
    }

    // Method untuk login sebagai admin
    public void loginAdmin() {
        System.out.print("Masukkan username admin: ");
        String username = input.next(); // Membaca input username
        System.out.print("Masukkan password admin: ");
        String password = input.next(); // Membaca input password
        if (bank.getNama().equals(username) && bank.getNama().equals(password)) { // Jika username dan password sesuai
                                                                                  // dengan nama bank
            akun = new Admin(username, password); // Membuat objek Admin baru
            System.out.println("Login berhasil.");
        } else { // Jika username atau password tidak sesuai
            System.out.println("Login gagal. Username atau password salah.");
        }
    }

    // Method untuk login sebagai nasabah
    public void loginNasabah() {
        System.out.print("Masukkan nomor rekening: ");
        String noRekening = input.next(); // Membaca input nomor rekening
        System.out.print("Masukkan password: ");
        String password = input.next(); // Membaca input password
        Nasabah n = bank.cariNasabah(noRekening); // Mencari nasabah berdasarkan nomor rekening
        if (n != null && n.login(noRekening, password)) { // Jika nasabah ditemukan dan username dan password sesuai
            akun = n; // Mengeset akun dengan objek Nasabah yang ditemukan
            System.out.println("Login berhasil.");
        } else { // Jika nasabah tidak ditemukan atau username atau password tidak sesuai
            System.out.println("Login gagal. Nomor rekening atau password salah.");
        }
    }

    // Method untuk logout dari akun
    public void logout() {
        akun = null; // Mengeset akun dengan null
        System.out.println("Logout berhasil.");
    }

    // Method untuk keluar dari aplikasi
    public void keluar() {
        System.out.println("Terima kasih telah menggunakan aplikasi bank minimalis " + bank.getNama());
        System.exit(0); // Menghentikan program
    }

    // Method untuk menampilkan info nasabah (hanya bisa dilakukan oleh admin)
    public void lihatInfoNasabah() {
        if (akun instanceof Admin) { // Jika akun adalah admin
            Admin a = (Admin) akun; // Melakukan type casting dari Akun ke Admin
            a.lihatInfoNasabah(bank); // Memanggil method lihatInfoNasabah() dari class Admin
        } else { // Jika akun bukan admin
            System.out.println("Anda tidak memiliki akses untuk melihat info nasabah.");
        }
    }

    // Method untuk menambahkan nasabah baru (hanya bisa dilakukan oleh admin)
    public void tambahNasabah() {
        if (akun instanceof Admin) { // Jika akun adalah admin
            Admin a = (Admin) akun; // Melakukan type casting dari Akun ke Admin
            System.out.print("Masukkan nama nasabah: ");
            String nama = input.next(); // Membaca input nama
            System.out.print("Masukkan alamat nasabah: ");
            String alamat = input.next(); // Membaca input alamat
            System.out.print("Masukkan nomor rekening nasabah: ");
            String noRekening = input.next(); // Membaca input nomor rekening
            System.out.print("Masukkan saldo awal nasabah: ");
            double saldo = input.nextDouble(); // Membaca input saldo
            System.out.print("Masukkan password nasabah: ");
            String password = input.next(); // Membaca input password
            Nasabah n = new Nasabah(noRekening, password, nama, alamat, noRekening, saldo); // Membuat objek Nasabah
                                                                                            // baru
            a.tambahNasabah(bank, n); // Memanggil method tambahNasabah() dari class Admin
        } else { // Jika akun bukan admin
            System.out.println("Anda tidak memiliki akses untuk menambahkan nasabah.");
        }
    }

    // Method untuk menampilkan detail transaksi terakhir (hanya bisa dilakukan oleh
    // nasabah
    public void lihatDetailTransaksi() {
        if (akun instanceof Nasabah) { // Jika akun adalah nasabah
            Nasabah n = (Nasabah) akun; // Melakukan type casting dari Akun ke Nasabah
            n.lihatDetailTransaksi(); // Memanggil method lihatDetailTransaksi() dari class Nasabah
        } else { // Jika akun bukan nasabah
            System.out.println("Anda tidak memiliki akses untuk melihat detail transaksi.");
        }
    }

    // Method untuk menampilkan riwayat transaksi (hanya bisa dilakukan oleh
    // nasabah
    public void lihatRiwayatTransaksi() {
        if (akun instanceof Nasabah) { // Jika akun adalah nasabah
            Nasabah n = (Nasabah) akun; // Melakukan type casting dari Akun ke Nasabah
            n.lihatRiwayatTransaksi(); // Memanggil method lihatRiwayatTransaksi() dari class Nasabah
        } else { // Jika akun bukan nasabah
            System.out.println("Anda tidak memiliki akses untuk melihat riwayat transaksi.");
        }
    }

    // Method untuk melakukan simpan uang (hanya bisa dilakukan oleh nasabah)
    public void simpanUang() {
        if (akun instanceof Nasabah) { // Jika akun adalah nasabah
            Nasabah n = (Nasabah) akun; // Melakukan type casting dari Akun ke Nasabah
            System.out.print("Masukkan jumlah uang yang ingin disimpan: ");
            double jumlah = input.nextDouble(); // Membaca input jumlah uang
            n.simpanUang(jumlah); // Memanggil method simpanUang() dari class Nasabah
        } else { // Jika akun bukan nasabah
            System.out.println("Anda tidak memiliki akses untuk menyimpan uang.");
        }
    }

    // Method untuk melakukan tarik uang (hanya bisa dilakukan oleh nasabah)
    public void tarikUang() {
        if (akun instanceof Nasabah) { // Jika akun adalah nasabah
            Nasabah n = (Nasabah) akun; // Melakukan type casting dari Akun ke Nasabah
            System.out.print("Masukkan jumlah uang yang ingin ditarik: ");
            double jumlah = input.nextDouble(); // Membaca input jumlah uang
            n.tarikUang(jumlah); // Memanggil method tarikUang() dari class Nasabah
        } else { // Jika akun bukan nasabah
            System.out.println("Anda tidak memiliki akses untuk menarik uang.");
        }
    }

    // Method untuk melakukan transfer uang (hanya bisa dilakukan oleh nasabah)
    public void transferUang() {
        if (akun instanceof Nasabah) { // Jika akun adalah nasabah
            Nasabah n = (Nasabah) akun; // Melakukan type casting dari Akun ke Nasabah
            System.out.print("Masukkan nomor rekening penerima: ");
            String noRekening = input.next(); // Membaca input nomor rekening penerima
            Nasabah p = bank.cariNasabah(noRekening); // Mencari nasabah penerima berdasarkan nomor rekening
            if (p != null) { // Jika nasabah penerima ditemukan
                System.out.print("Masukkan jumlah uang yang ingin ditransfer: ");
                double jumlah = input.nextDouble(); // Membaca input jumlah uang
                n.transferUang(p, jumlah); // Memanggil method transferUang() dari class Nasabah
            } else { // Jika nasabah penerima tidak ditemukan
                System.out.println("Nomor rekening penerima tidak valid.");
            }
        } else { // Jika akun bukan nasabah
            System.out.println("Anda tidak memiliki akses untuk mentransfer uang.");
        }
    }

    // Method untuk menjalankan aplikasi
    public void run() {
        int pilihan;
        do {
            if (akun == null) { // Jika belum login
                tampilMenuUtama(); // Menampilkan menu utama
            } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                tampilMenuAdmin(); // Menampilkan menu admin
            } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                tampilMenuNasabah(); // Menampilkan menu nasabah
            }
            pilihan = input.nextInt(); // Membaca input pilihan
            prosesInput(pilihan); // Memproses input pilihan
        } while (pilihan != 3 || akun != null); // Looping selama pilihan bukan 3 atau akun tidak null
    }

    // Class Main sebagai class yang berisi method main untuk menjalankan program
public class Main {
    // Method main untuk menjalankan program
    public static void main(String[] args) {
        // Membuat objek Scanner untuk input
        Scanner input = new Scanner(System.in);

        // Membuat objek Bank dengan nama Bank Minimalis
        Bank bank = new Bank("Bank Minimalis");

        // Membuat objek Admin dengan username admin dan password admin
        Admin admin = new Admin("admin", "admin");

        // Membuat objek Nasabah dengan data-data berikut
        Nasabah n1 = new Nasabah("n1", "n1", "Budi", "Jl. Merdeka No. 1", "111111", 1000000);
        Nasabah n2 = new Nasabah("n2", "n2", "Ani", "Jl. Kemerdekaan No. 2", "222222", 2000000);
        Nasabah n3 = new Nasabah("n3", "n3", "Cici", "Jl. Merah Putih No. 3", "333333", 3000000);

        // Menambahkan nasabah-nasabah tersebut ke bank
        bank.tambahNasabah(n1);
        bank.tambahNasabah(n2);
        bank.tambahNasabah(n3);

        // Membuat variabel untuk menyimpan akun yang sedang login
        Akun akun = null;

        // Membuat variabel untuk menyimpan pilihan menu
       } // Looping selama pilihan bukan 3

        // Method untuk menampilkan menu utama
        public void tampilMenuUtama() {
            System.out.println("Selamat datang di aplikasi bank minimalis " + bank.getNama());
            System.out.println("Silakan pilih salah satu opsi berikut:");
            System.out.println("1. Login sebagai admin");
            System.out.println("2. Login sebagai nasabah");
            System.out.println("3. Keluar");
            System.out.print("Masukkan pilihan Anda: ");
        }

        // Method untuk menampilkan menu admin
        public void tampilMenuAdmin() {
            System.out.println("Anda login sebagai admin");
            System.out.println("Silakan pilih salah satu opsi berikut:");
            System.out.println("1. Lihat info nasabah");
            System.out.println("2. Tambah nasabah");
            System.out.println("3. Logout");
            System.out.print("Masukkan pilihan Anda: ");
        }

        // Method untuk menampilkan menu nasabah
        public void tampilMenuNasabah() {
            System.out.println("Anda login sebagai nasabah");
            System.out.println("Silakan pilih salah satu opsi berikut:");
            System.out.println("1. Lihat detail transaksi");
            System.out.println("2. Lihat riwayat transaksi");
            System.out.println("3. Simpan uang");
            System.out.println("4. Tarik uang");
            System.out.println("5. Transfer uang");
            System.out.println("6. Logout");
            System.out.print("Masukkan pilihan Anda: ");
        }

        // Method untuk memproses input pilihan dari user
        public void prosesInput(int pilihan) {
            switch (pilihan) {
                case 1: // Jika pilihan 1
                    if (akun == null) { // Jika belum login
                        loginAdmin(); // Memanggil method loginAdmin()
                    } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                        lihatInfoNasabah(); // Memanggil method lihatInfoNasabah()
                    } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                        lihatDetailTransaksi(); // Memanggil method lihatDetailTransaksi()
                    }
                    break;
                case 2: // Jika pilihan 2
                    if (akun == null) { // Jika belum login
                        loginNasabah(); // Memanggil method loginNasabah()
                    } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                        tambahNasabah(); // Memanggil method tambahNasabah()
                    } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                        lihatRiwayatTransaksi(); // Memanggil method lihatRiwayatTransaksi()
                    }
                    break;
                case 3: // Jika pilihan 3
                    if (akun == null) { // Jika belum login
                        keluar(); // Memanggil method keluar()
                    } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                        logout(); // Memanggil method logout()
                    } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                        simpanUang(); // Memanggil method simpanUang()
                    }
                    break;
                case 4: // Jika pilihan 4
                    if (akun == null) { // Jika belum login
                        System.out.println("Pilihan tidak valid.");
                    } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                        System.out.println("Pilihan tidak valid.");
                    } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                        tarikUang(); // Memanggil method tarikUang()
                    }
                    break;
                case 5: // Jika pilihan 5
                    if (akun == null) { // Jika belum login
                        System.out.println("Pilihan tidak valid.");
                    } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                        System.out.println("Pilihan tidak valid.");
                    } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                        transferUang(); // Memanggil method transferUang()
                    }
                    break;
                case 6: // Jika pilihan 6
                    if (akun == null) { // Jika belum login
                        System.out.println("Pilihan tidak valid.");
                    } else if (akun instanceof Admin) { // Jika sudah login sebagai admin
                        System.out.println("Pilihan tidak valid.");
                    } else if (akun instanceof Nasabah) { // Jika sudah login sebagai nasabah
                        logout(); // Memanggil method logout()
                    }
                    break;
                default: // Jika pilihan lainnya
                    System.out.println("Pilihan tidak valid.");
                    break;
            }
        }
    }
}