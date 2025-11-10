/*
Nama      : Rosa Andini Ismayanti, Alesha Naila, Achmad Faruq Mahdison
NPM       : 240048, 240066, 240080
Kelas     : B
Tanggal   : 27/10/25 (Disesuaikan)
Deskripsi : Program OOP untuk menghitung waktu dan biaya parkir dari sekumpulan data kendaraan (Linked List) dengan menu manajemen.
*/
package parking_logic; 

import java.util.Scanner;
import java.util.InputMismatchException;
import java.time.LocalDateTime;

// PROGRAM UTAMA 
public class Main {
    private static Scanner scanner = new Scanner(System.in);
    // Mengganti MahasiswaLinkedList menjadi KendaraanLinkedList
    private static KendaraanLinkedList list = new KendaraanLinkedList(); 

    public static void main(String[] args) {
        int pilihan = -1;
        
        while (pilihan != 0) {
            tampilkanMenu();
            try {
                pilihan = scanner.nextInt();
                scanner.nextLine();

                try {
                    switch (pilihan) {
                        case 1: insertFirst(); break;
                        case 2: insertLast(); break;
                        case 3: insertAfter(); break;
                        case 4: deleteFirst(); break;
                        case 5: deleteLast(); break;
                        case 6: deleteAfter(); break;
                        case 7: search(); break;
                        case 8: list.tampilkanData();; break;
                        case 9: isiDataContoh(); break;
                        case 0: System.out.println("Terima kasih. Program selesai."); break;
                        default: System.out.println("Pilihan tidak valid!"); break;
                    }
                } catch (DataNotFoundException e) {
                    System.err.println("Error: " + e.getMessage());
                } catch (Exception e) {
                    System.err.println("Terjadi error: " + e.getMessage());
                }
            } catch (InputMismatchException e) {
                System.err.println("Input salah! Harap masukkan angka.");
                scanner.nextLine();
                pilihan = -1;
            }
        }
        scanner.close();
    }

    private static void tampilkanMenu() {
        System.out.println("\n===== Menu Kelola Data Kendaraan Parkir (Linked List) =====");
        System.out.println("1. Insert First");
        System.out.println("2. Insert Last");
        System.out.println("3. Insert After (by No Kendaraan)"); // Diubah dari NPM
        System.out.println("4. Delete First");
        System.out.println("5. Delete Last");
        System.out.println("6. Delete After (by No Kendaraan)"); // Diubah dari NPM
        System.out.println("7. Search (by No Kendaraan)"); // Diubah dari NPM
        System.out.println("8. Tampilkan Semua Data (Traversal)");
        System.out.println("9. Isi Data Contoh (Mobil, Motor, Truk)"); // Diubah dari Mahasiswa
        System.out.println("0. Keluar");
        System.out.print("Masukkan pilihan Anda: ");
    }

    private static String inputString(String pesan) {
        String input = "";
        
        while (input == null || input.trim().isEmpty()) {
            System.out.print(pesan);
            input = scanner.nextLine().trim();
            
            if (input.isEmpty()) {
                System.err.println("Input tidak boleh kosong!");
            }
        }
        return input;
    }
    
    // Pengecekan No Kendaraan (was inputNPM)
    private static String inputNoKendaraan() {
        while (true) {
            String noKend = inputString("Masukkan No Kendaraan (harus unik): ");
            
            if (list.find(noKend) != null) {
                System.err.println("Error: Plat nomor '" + noKend + "' sudah ada di dalam list!");
            } else {
                return noKend;
            }
        }
    }

    // Pengecekan waktu dan tanggal (was inputWaktu)
    private static Waktu inputWaktu(String pesan) {
        // Asumsi format input: dd_mm_yyyy hh:mm:ss
        while (true) {
            System.out.print(pesan + " (dd_mm_yyyy hh:mm:ss): ");
            String waktuStr = scanner.nextLine().trim();
            
            try {
                String[] parts = waktuStr.split(" ");
                if (parts.length != 2) {
                    throw new IllegalArgumentException("Format harus 'dd_mm_yyyy hh:mm:ss'");
                }

                String[] dateParts = parts[0].split("_");
                String[] timeParts = parts[1].split(":");

                if (dateParts.length != 3 || timeParts.length != 3) {
                     throw new IllegalArgumentException("Format harus 'dd_mm_yyyy hh:mm:ss'");
                }
                
                int hari = Integer.parseInt(dateParts[0]);
                int bulan = Integer.parseInt(dateParts[1]);
                int tahun = Integer.parseInt(dateParts[2]);

                int jam = Integer.parseInt(timeParts[0]);
                int menit = Integer.parseInt(timeParts[1]);
                int detik = Integer.parseInt(timeParts[2]);

                // Gunakan konstruktor Date dan Time untuk validasi
                Date date = new Date(hari, bulan, tahun);
                Time time = new Time(jam, menit, detik);

                return new Waktu(date, time);

            } catch (NumberFormatException e) {
                System.err.println("Format input salah! Pastikan semua adalah angka yang valid.");
            } catch (IllegalArgumentException e) {
                System.err.println("Format waktu salah! " + e.getMessage() + ".");
            } catch (Exception e) {
                // Tangkap error jika Date/Time validasi gagal (seperti hari ke-32, jam ke-25, dll)
                System.err.println("Terjadi kesalahan saat parsing waktu: " + e.getMessage());
            }
        }
    }

    private static boolean konfirmasi(String aksi) {
        System.out.print("Apakah Anda yakin ingin " + aksi + "? (Y/N): ");
        String konfirmasi = scanner.nextLine().trim().toUpperCase();
        return konfirmasi.equals("Y");
    }

    // Input data Kendaraan (was inputMahasiswa)
    private static Kendaraan inputKendaraan() {
        String nama = inputString("Masukkan Nama Pemilik: ");
        Person person = new Person(nama);
        String noKend = inputNoKendaraan();
        
        int jenis = -1;
        while (jenis < 1 || jenis > 3) {
            try {
                System.out.println("Jenis Kendaraan:");
                System.out.println("1. Mobil");
                System.out.println("2. Motor");
                System.out.println("3. Truk");
                System.out.print("Masukkan Jenis Kendaraan (1-3): ");
                jenis = scanner.nextInt();
                scanner.nextLine();
                if (jenis < 1 || jenis > 3) {
                    System.err.println("Pilihan jenis kendaraan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Input salah! Harap masukkan angka.");
                scanner.nextLine();
                jenis = -1;
            }
        }

        int status = -1;
        while (status < 1 || status > 2) {
            try {
                System.out.println("Status Kendaraan:");
                System.out.println("1. Biasa");
                System.out.println("2. Menginap");
                System.out.print("Masukkan Status Kendaraan (1/2): ");
                status = scanner.nextInt();
                scanner.nextLine();
                if (status < 1 || status > 2) {
                    System.err.println("Pilihan status kendaraan tidak valid.");
                }
            } catch (InputMismatchException e) {
                System.err.println("Input salah! Harap masukkan angka.");
                scanner.nextLine();
                status = -1;
            }
        }

        Waktu datang, pulang;
        while (true) {
            datang = inputWaktu("Masukkan Waktu Datang");
            pulang = inputWaktu("Masukkan Waktu Pulang");
            
            // Perbandingan total detik untuk validasi (Asumsi method toTotalDetik() ada di Waktu/Time/Date)
            long totalDetikDatang = datang.getDate().toTotalHari() * 24 * 3600 + datang.getTime().toSeconds();
            long totalDetikPulang = pulang.getDate().toTotalHari() * 24 * 3600 + pulang.getTime().toSeconds();

            if (totalDetikPulang < totalDetikDatang) {
                System.err.println("Error: Waktu Pulang tidak boleh sebelum Waktu Datang!");
            } else {
                break;
            }
        }

        switch (jenis) {
            case 1: return new Mobil(person, noKend, status, datang, pulang);
            case 2: return new Motor(person, noKend, status, datang, pulang);
            case 3: return new Truk(person, noKend, status, datang, pulang);
            default: return null; // Tidak akan terjadi karena ada looping validasi
        }
    }

    private static void insertFirst() {
        System.out.println("--- Insert First ---");
        Kendaraan kdr = inputKendaraan();
        list.insertFirst(kdr);
    }

    private static void insertLast() {
        System.out.println("--- Insert Last ---");
        Kendaraan kdr = inputKendaraan();
        list.insertLast(kdr);
    }

    private static void insertAfter() throws DataNotFoundException {
        System.out.println("--- Insert After ---");
        String noKendKey = inputString("Masukkan No Kendaraan target (data baru akan disisipkan setelah plat ini): ");
        
        // Memastikan target ada
        if (list.find(noKendKey) == null) {
            throw new DataNotFoundException("No Kendaraan target " + noKendKey + " tidak ditemukan.");
        }

        System.out.println("Masukkan data kendaraan baru:");
        Kendaraan kdr = inputKendaraan(); 
        
        list.insertAfter(noKendKey, kdr);
    }

    private static void deleteFirst() throws DataNotFoundException {
        if (list.isEmpty()) {
            System.out.println("List sudah kosong, tidak ada yang dihapus.");
            return;
        }

        // Ambil No Kendaraan untuk konfirmasi
        String noKend = list.getFirst().data.getNoKendaraan();

        if (konfirmasi("menghapus data terdepan (" + noKend + ")")) {
            list.deleteFirst();
        } else {
            System.out.println("Operasi delete dibatalkan.");
        }
    }

    private static void deleteLast() throws DataNotFoundException {
        if (list.isEmpty()) {
            System.out.println("List sudah kosong, tidak ada yang dihapus.");
            return;
        }

        if (konfirmasi("menghapus data terakhir")) {
            list.deleteLast();
        } else {
            System.out.println("Operasi delete dibatalkan.");
        }
    }

    private static void deleteAfter() throws DataNotFoundException {
        System.out.println("--- Delete After ---");
        String noKendKey = inputString("Masukkan No Kendaraan target (data setelah plat ini akan dihapus): ");
        Node target = list.find(noKendKey);
        
        if (target == null) {
            throw new DataNotFoundException("No Kendaraan target " + noKendKey + " tidak ditemukan.");
        }
        
        if (target.next == null) {
            throw new DataNotFoundException("Tidak ada data setelah No Kendaraan " + noKendKey + ".");
        }
        
        String deletedNoKend = target.next.data.getNoKendaraan();

        if (konfirmasi("menghapus data setelah " + target.data.getNoKendaraan() + " (" + deletedNoKend + ")")) {
            list.deleteAfter(noKendKey);
        } else {
            System.out.println("Operasi delete dibatalkan.");
        }
    }

    private static void search() {
        System.out.println("--- Search ---");
        // Ganti NPM menjadi No Kendaraan
        String noKendKey = inputString("Masukkan No Kendaraan yang dicari: ");
        // Ganti Mahasiswa mhs menjadi Kendaraan kdr
        Kendaraan kdr = list.search(noKendKey);
        
        if (kdr == null) {
            System.out.println("Data kendaraan dengan No Kendaraan " + noKendKey + " tidak ditemukan.");
        } else {
            System.out.println("Data ditemukan:");
            list.tampilkanData();
            System.out.println("Hasil Pencarian Spesifik:");
            long biaya = kdr.getBiayaParkir();

            System.out.println("\n\n\t\t\t\t\tDaftar Kendaraan Parkir Pelabuhan");
            System.out.println("=========================================================================================================================================");
            System.out.printf("%-4s %-15s %-12s %-11s %-10s %-12s %-12s %-10s %-10s %-10s %-10s %-12s\n",
                "No", "Nama Pemilik", "NoKend", "JenisKend", "Status", "TglDatang", "TglPulang", "JamDatang", "JamPulang", "Lama hari", "Lama Jam", "BiayaParkir");
            System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
            System.out.println(kdr.formatUntukTabel(1, biaya));
            System.out.println("=========================================================================================================================================");
        }
    }

    private static void isiDataContoh() {
        System.out.println("--- Menambahkan Data Dummy ---");
        
        // Tentukan Waktu Datang dan Waktu Pulang untuk data contoh
        Waktu waktuDatang = new Waktu(new Date(10, 11, 2025), new Time(8, 0, 0));
        // Mobil: 1 jam 30 menit (Biasa)
        Waktu waktuPulang1 = new Waktu(new Date(10, 11, 2025), new Time(9, 30, 0)); 
        // Motor: 1 hari 3 jam (Menginap)
        Waktu waktuPulang2 = new Waktu(new Date(11, 11, 2025), new Time(11, 0, 0)); 
        // Truk: 4 jam (Biasa)
        Waktu waktuPulang3 = new Waktu(new Date(10, 11, 2025), new Time(12, 0, 0)); 

        try {
            // Cek duplikasi, jika ada, hapus data contoh yang lama
            if (list.find("B1001A") != null) list.deleteAfter("B1001A"); 
            if (list.find("AD2002B") != null) list.deleteAfter("AD2002B");
            if (list.find("T3003C") != null) list.deleteAfter("T3003C");
            
            Kendaraan mobil = new Mobil(new Person("Ali"), "B1001A", 1, waktuDatang, waktuPulang1);
            Kendaraan motor = new Motor(new Person("Budi"), "AD2002B", 2, waktuDatang, waktuPulang2);
            Kendaraan truk = new Truk(new Person("Clara"), "T3003C", 1, waktuDatang, waktuPulang3);
            
            list.insertLast(mobil);
            list.insertLast(motor);
            list.insertLast(truk);
            
            System.out.println("Data contoh berhasil ditambahkan!");
        } catch (Exception e) {
             System.err.println("Gagal memasukkan data contoh: " + e.getMessage());
        }
        
    }
}