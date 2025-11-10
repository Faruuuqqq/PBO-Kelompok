package parking_logic;

import java.time.LocalDateTime;
import java.util.InputMismatchException;
import java.util.Scanner;

public class KendaraanLinkedList {
    private Node first;
    private int jumlahKendaraan; // Menambah atribut untuk melacak jumlah kendaraan

    public KendaraanLinkedList() {
        this.first = null;
        this.jumlahKendaraan = 0;
    }

    public boolean isEmpty() {
        return this.first == null;
    }

    public Node getFirst() {
        return this.first;
    }

    // Memperbaiki method getJumlahKendaraan agar sesuai dengan atribut
    public int getJumlahKendaraan() {
        return this.jumlahKendaraan;
    }

    public Node find(String noKendaraan) {
        Node current = this.first;
        
        while (current != null) {
            if (current.data.getNoKendaraan().equalsIgnoreCase(noKendaraan)) { // Gunakan equalsIgnoreCase
                return current;
            }
            current = current.next;
        }
        return null;
    }

    public void insertFirst(Kendaraan data) {
        Node newNode = new Node(data);
        
        if (isEmpty()) {
            this.first = newNode;
        } else {
            newNode.next = this.first;
            this.first = newNode;
        }
        this.jumlahKendaraan++; // Tambah counter
        System.out.println("InsertFirst sukses: Plat nomor " + data.getNoKendaraan() + " dengan pemilik " + data.getPerson().getNama());
    }

    public void insertLast(Kendaraan data) {
        Node newNode = new Node(data);
        
        if (isEmpty()) {
            this.first = newNode;
        } else {
            Node current = this.first;
            
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
        this.jumlahKendaraan++; // Tambah counter
        System.out.println("InsertLast sukses: Plat nomor " + data.getNoKendaraan() + " dengan pemilik " + data.getPerson().getNama());
    }

    public void insertAfter(String noKendaraanKey, Kendaraan data) throws DataNotFoundException {
        Node target = find(noKendaraanKey);
        
        if (target == null) {
            throw new DataNotFoundException("Gagal InsertAfter: Plat nomor " + noKendaraanKey + " tidak ditemukan.");
        }
        
        Node newNode = new Node(data);
        newNode.next = target.next;
        target.next = newNode;
        this.jumlahKendaraan++; // Tambah counter
        System.out.println("InsertAfter " + noKendaraanKey + " sukses: Plat nomor " + data.getNoKendaraan() + " dengan pemilik " + data.getPerson().getNama());
    }

    public Kendaraan deleteFirst() throws DataNotFoundException {
        if (isEmpty()) {
            throw new DataNotFoundException("Gagal DeleteFirst: List kosong.");
        }
        
        Node temp = this.first;
        this.first = this.first.next;
        temp.next = null;
        this.jumlahKendaraan--; // Kurangi counter
        
        System.out.println("DeleteFirst sukses: Plat nomor " + temp.data.getNoKendaraan() + " dengan pemilik " + temp.data.getPerson().getNama());
        return temp.data;
    }

    public Kendaraan deleteLast() throws DataNotFoundException {
        if (isEmpty()) {
            throw new DataNotFoundException("Gagal DeleteLast: List kosong.");
        }

        Node temp;
        if (this.first.next == null) {
            temp = this.first;
            this.first = null;
        } else {
            Node prev = this.first;
            Node current = this.first.next;
            
            while (current.next != null) {
                prev = current;
                current = current.next;
            }
            temp = current;
            prev.next = null;
        }
        this.jumlahKendaraan--; // Kurangi counter
        System.out.println("DeleteLast sukses: Plat nomor " + temp.data.getNoKendaraan() + " dengan pemilik " + temp.data.getPerson().getNama());
        return temp.data;
    }

    public Kendaraan deleteAfter(String noKendaraanKey) throws DataNotFoundException {
        Node target = find(noKendaraanKey);
        
        if (target == null) {
            throw new DataNotFoundException("Gagal DeleteAfter: Plat nomor " + noKendaraanKey + " tidak ditemukan.");
        }
        if (target.next == null) {
            throw new DataNotFoundException("Gagal DeleteAfter: " + noKendaraanKey + " adalah data terakhir.");
        }
        
        Node temp = target.next;
        target.next = temp.next;
        temp.next = null;
        this.jumlahKendaraan--; // Kurangi counter
        System.out.println("DeleteAfter " + noKendaraanKey + " sukses: Plat nomor " + temp.data.getNoKendaraan() + " dengan pemilik " + temp.data.getPerson().getNama());
        return temp.data;
    }

    public void tampilkanData() {
        System.out.println("\n\n\t\t\t\t\tDaftar Kendaraan Parkir Pelabuhan");
        System.out.println("=========================================================================================================================================");
        System.out.printf("%-4s %-15s %-12s %-11s %-10s %-12s %-12s %-10s %-10s %-10s %-10s %-12s\n",
            "No", "Nama Pemilik", "NoKend", "JenisKend", "Status", "TglDatang", "TglPulang", "JamDatang", "JamPulang", "Lama hari", "Lama Jam", "BiayaParkir");
        System.out.println("-----------------------------------------------------------------------------------------------------------------------------------------");
        
        long totalBiayaParkir = 0;

        if (isEmpty()) {
            System.out.println("|                                    TIDAK ADA DATA                                    |");
        } else {
            
            Node current = this.first;
            int i = 0;
            while (current != null) {
                long biaya = current.data.getBiayaParkir();
                totalBiayaParkir += biaya;
                System.out.print(current.data.formatUntukTabel(i, biaya));
                current = current.next;
                i++;
            }
        }

        System.out.println("=========================================================================================================================================");
        System.out.printf("Total Biaya Parkir : %,d\n", totalBiayaParkir);
    }

    public Kendaraan search(String noKendaraan) {
        Node result = find(noKendaraan);
        if (result != null) {
            return result.data;
        }
        return null;
    }


    /**
     * utk parsing input format dd_mm_yyyy atau hh:mm:ss.
     * melempar exception jika format salah
     */
    private int[] parseInput(String inputString) throws NumberFormatException, ArrayIndexOutOfBoundsException {
        String[] parts = inputString.split("[:_]");

        if (parts.length != 3) { // Validasi tambahan
            throw new ArrayIndexOutOfBoundsException();
        }

        int[] result = new int[3]; 
        for (int i = 0; i < 3; i++) {
            result[i] = Integer.parseInt(parts[i].trim());
            if (result[i] < 0) {
                throw new NumberFormatException("Input tidak boleh negatif");
            }
        }
        return result;
    }

    private int getIntInput(Scanner input, String pesan, int min, int max) {
        int value = 0;
        boolean valid = false;
        do {
            try {
                System.out.print(pesan);
                value = input.nextInt();
                
                if (value >= min && value <= max) {
                    valid = true;
                } else {
                    System.out.println("Input tidak valid. Harap masukkan angka antara " + min + " dan " + max + ".");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input salah. Harap masukkan angka yang valid.");
            } finally {
                input.nextLine();
            }
        } while (!valid);
        return value;
    }
    
    /**
     * fungsi utk memasukkan waktu (date & time) secara manual
     */
    private Waktu getWaktuInput(Scanner input, String pesan) {
        Waktu waktuObj = null;
        boolean valid = false;
        do {
            try {
                System.out.println(pesan);
                System.out.print("Tanggal (DD_MM_YYYY) : ");
                String dateInput = input.nextLine();
                int[] dateParts = parseInput(dateInput);

                System.out.print("Waktu (HH:MM:SS) : ");
                String timeInput = input.nextLine();
                int[] timeParts = parseInput(timeInput);

                int hari = dateParts[0];
                int bulan = dateParts[1];
                int tahun = dateParts[2];
                int jam = timeParts[0];
                int menit = timeParts[1];
                int detik = timeParts[2];

                // Proses carry-over jam ke hari, menit ke jam, detik ke menit, dst.
                // Logika ini sudah dihandle di constructor Time dan Date, 
                // tapi kita tambahkan validasi untuk memastikan nilai non-negatif.
                
                Date tgl = new Date(hari, bulan, tahun);
                Time wkt = new Time(jam, menit, detik);
                waktuObj = new Waktu(tgl, wkt);

                valid = true;

            } catch (NumberFormatException e) {
                System.out.println("Format angka salah. Pastikan semua adalah angka. Coba lagi.");
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Format input salah. Pastikan format DD_MM_YYYY dan HH:MM:SS. Coba lagi.");
            } catch (IllegalArgumentException e) {
                System.out.println("Input tanggal/waktu tidak valid: " + e.getMessage() + ". Coba lagi.");
            }
        } while (!valid);
        return waktuObj;
    }

    /**
     * Method helper untuk mengambil waktu sistem saat ini
     */
    private Waktu getWaktuSekarang() {
        LocalDateTime now = LocalDateTime.now();
        Date dateNow = new Date(now.getDayOfMonth(), now.getMonthValue(), now.getYear());
        Time timeNow = new Time(now.getHour(), now.getMinute(), now.getSecond());
        return new Waktu(dateNow, timeNow);
    }

    /**
     * Method input data (MODE 1: MANUAL)
     * Menggunakan insertLast untuk Linked List
     */
    public void inputDataManual(Scanner input, int jumlah) {
        for (int i = 0; i < jumlah; i++) {
            System.out.println("\n-- Masukkan data kendaraan ke-" + (i + 1) + " (MANUAL) --");

            System.out.print("Nama Pemilik : ");
            String nama = input.nextLine();
            Person person = new Person(nama);

            String noKend;
            while (true) {
                System.out.print("No Kendaraan : ");
                noKend = input.nextLine();
                if (find(noKend) == null) {
                    break;
                }
                System.out.println("Plat nomor sudah ada. Masukkan plat nomor lain.");
            }
            

            int jenis = getIntInput(input, "Jenis (1.Mobil/2.Motor/3.Truk) : ", 1, 3);
            int status = getIntInput(input, "Status (1.Regular/2.Menginap) : ", 1, 2);

            Waktu waktuDatang = getWaktuInput(input, "-- Waktu Datang (Manual) --");
            Waktu waktuPulang = getWaktuInput(input, "-- Waktu Pulang (Manual) --");

            Kendaraan newKendaraan = createKendaraan(person, noKend, jenis, status, waktuDatang, waktuPulang);
            this.insertLast(newKendaraan);
        }
    }

    /**
     * Method input data (MODE 2: WAKTU SISTEM)
     * Menggunakan insertLast untuk Linked List
     */
    public void inputDataSystemTime(Scanner input, int jumlah) {
        for (int i = 0; i < jumlah; i++) {
            System.out.println("\n-- Masukkan data kendaraan ke-" + (i + 1) + " (WAKTU SISTEM) --");

            System.out.print("Nama Pemilik : ");
            String nama = input.nextLine();
            Person person = new Person(nama);

            String noKend;
            while (true) {
                System.out.print("No Kendaraan : ");
                noKend = input.nextLine();
                if (find(noKend) == null) {
                    break;
                }
                System.out.println("Plat nomor sudah ada. Masukkan plat nomor lain.");
            }

            int jenis = getIntInput(input, "Jenis (1.Mobil/2.Motor/3.Truk) : ", 1, 3);
            int status = getIntInput(input, "Status (1.Regular/2.Menginap) : ", 1, 2);

            System.out.println("\n== PENGAMBILAN WAKTU DATANG ==");
            System.out.println("  Silakan ATUR WAKTU SISTEM Anda ke WAKTU DATANG.");
            System.out.print("  Tekan ENTER jika sudah siap untuk mengambil Waktu Datang...");
            input.nextLine();

            Waktu waktuDatang = getWaktuSekarang();
            System.out.println("  -> Waktu Datang dicatat: " + waktuDatang.toString());

            System.out.println("\n== PENGAMBILAN WAKTU PULANG ==");
            System.out.println("  Silakan ATUR WAKTU SISTEM Anda ke WAKTU PULANG.");
            System.out.print("  Tekan ENTER jika sudah siap untuk mengambil Waktu Pulang...");
            input.nextLine();

            Waktu waktuPulang = getWaktuSekarang();
            System.out.println("  -> Waktu Pulang dicatat: " + waktuPulang.toString());

            Kendaraan newKendaraan = createKendaraan(person, noKend, jenis, status, waktuDatang, waktuPulang);
            this.insertLast(newKendaraan);
        }
    }

    // Method helper private untuk membuat objek Kendaraan.
    private Kendaraan createKendaraan(Person person, String noKend, int jenis, int status, Waktu waktuDatang,
        Waktu waktuPulang) {
        switch (jenis) {
            case 1:
                return new Mobil(person, noKend, status, waktuDatang, waktuPulang);
            case 2:
                return new Motor(person, noKend, status, waktuDatang, waktuPulang);
            case 3:
                return new Truk(person, noKend, status, waktuDatang, waktuPulang);
            default:
                System.out.println("Terjadi error, diasumsikan sebagai Mobil.");
                return new Mobil(person, noKend, status, waktuDatang, waktuPulang);
        }
    }
}