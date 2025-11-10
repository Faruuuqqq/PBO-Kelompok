package parking_logic;

public abstract class Kendaraan {
  protected Person person;
  protected String noKendaraan;
  protected int status;
  protected Waktu waktuDatang;
  protected Waktu waktuPulang;

  public abstract String getJenisAsString();
  protected abstract long getTarifRegular();
  protected abstract long getTarifMenginap();

  public Kendaraan(){
    this.person = new Person();
    this.noKendaraan = "";
    this.status = 0;
    this.waktuDatang = new Waktu();
    this.waktuPulang = new Waktu();
  }

  // constructor dgn validasi input
  public Kendaraan(Person person, String noKendaraan, int status, Waktu waktuDatang, Waktu waktuPulang) {
    this.person = person;
    
    this.noKendaraan = noKendaraan;
    setStatus(status);
    setWaktuParkir(waktuDatang, waktuPulang);
  }

  // setters
  public void setNoKendaraan(String noKendaraan) {
    this.noKendaraan = noKendaraan;
  }

  public void setStatus(int status) {
    if (status == 1 || status == 2) this.status = status;
    else this.status = 1;
  }

  public void setWaktuParkir(Waktu waktuDatang, Waktu waktuPulang){
    long totalDetikDatang = waktuDatang.getDate().toTotalHari() * 86400 + waktuDatang.getTime().toSeconds();
    long totalDetikPulang = waktuPulang.getDate().toTotalHari() * 86400 + waktuPulang.getTime().toSeconds();

    if (totalDetikPulang < totalDetikDatang) {
        this.waktuDatang = waktuPulang;
        this.waktuPulang = waktuDatang;
        System.out.println("Waktu pulang lebih awal dari waktu datang. Sistem akan menukar waktu pulang dan datang.");
    } 
    else {
        this.waktuDatang = waktuDatang;
        this.waktuPulang = waktuPulang;
    }
  }
  
  public void setWaktuDatang(Waktu waktuDatang) {
    this.waktuDatang = waktuDatang;
  }

  public void setWaktuPulang(Waktu waktuPulang) {
    this.waktuPulang = waktuPulang;
  }

  public String getNoKendaraan() {
    return this.noKendaraan;
  }

  public Person getPerson() {
    return this.person;
  }

  public int getStatus() {
    return this.status;
  }

  public String getStatusAsString(){
    return this.status == 1 ? "Regular" : "Menginap";
  }

  public Waktu getWaktuDatang() {
    return this.waktuDatang;
  }

  public Waktu getWaktuPulang() {
    return this.waktuPulang;
  }

  public long getBiayaParkir() {
      long totalDetik = getLamaParkirDetik();

      if (this.status == 2) {
          double totalHari = Math.ceil(totalDetik / 86400.0);
          if (totalHari <= 0) totalHari = 1;
          return (long) (totalHari * getTarifMenginap());
      } else {
          double totalJam = Math.ceil(totalDetik / 3600.0);
          if (totalJam <= 0) totalJam = 1;
          return (long) (totalJam * getTarifRegular());
      }
  }

  private long getLamaParkirDetik(){
    long totalHariDatang = waktuDatang.getDate().toTotalHari();
    long totalHariPulang = waktuPulang.getDate().toTotalHari();

    long selisihHari = totalHariPulang - totalHariDatang;

    long totalWaktuDatang = waktuDatang.getTime().toSeconds();
    long totalWaktuPulang = waktuPulang.getTime().toSeconds();

    long selisihDetik = totalWaktuPulang - totalWaktuDatang;
    
    long total = (selisihHari * 24 * 3600) + selisihDetik;
    if (total < 0) {
      return 0;
    } else {
      return total;
    }
  }

public String getLamaJam() {
    long totalDetik = getLamaParkirDetik();
    long sisaDetik = totalDetik % (24 * 3600);
    
    long jam = sisaDetik / 3600;
    long menit = (sisaDetik % 3600) / 60;
    long detik = sisaDetik % 60;
    return String.format("%02d:%02d:%02d", jam, menit, detik);
}

  public long getLamaHari() {
      return getLamaParkirDetik() / (24 * 3600);
  }

  public String formatUntukTabel(int no, long biaya) {
    return String.format("%-4d %-15s %-12s %-11s %-10s %-12s %-12s %-10s %-10s %-10d %-10s %,12d\n",
          (no + 1),
          this.getPerson().getNama(),
          this.getNoKendaraan(),
          this.getJenisAsString(),
          this.getStatusAsString(),
          this.getWaktuDatang().getDate().toString(),
          this.getWaktuPulang().getDate().toString(),
          this.getWaktuDatang().getTime().toString(),
          this.getWaktuPulang().getTime().toString(),
          this.getLamaHari(),
          this.getLamaJam(),
          biaya);
  }
}
