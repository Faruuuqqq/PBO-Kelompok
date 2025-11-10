package parking_logic;

public class Date {
  private int hari;
  private int bulan;
  private int tahun;

  public Date() {
    this.hari = 0;
    this.bulan = 0;
    this.tahun = 0;
  }

  public Date(int hari, int bulan, int tahun) {
    setDate(hari, bulan, tahun);
  }

  public void setDate(int hari, int bulan, int tahun) {
    if (hari <= 0 || bulan <= 0 || tahun <= 0) {
      throw new IllegalArgumentException("Tanggal tidak boleh nol atau negatif");
  }
    if (tahun < 0) tahun = 1;

    if (bulan < 1) bulan = 1;

    while (bulan > 12) {
      bulan -= 12;
      tahun++;
    }

    if (hari < 1) hari = 1;

    // akan looping terus sampai dapat return false
    while (true) {
      int maxHariDalamSebulan = getJumlahHariPerBulan(bulan, tahun);
      
      if (hari <= maxHariDalamSebulan) {
        break;
      } else {
        hari -= maxHariDalamSebulan;
        bulan++;
      }

      if (bulan > 12) {
        bulan = 1;
        tahun++;
      }
    }

    this.hari = hari;
    this.bulan = bulan;
    this.tahun = tahun;
  }
  
  public void setTahun(int tahun) {
    setDate(this.hari, this.bulan, tahun);
  }

  public void setBulan(int bulan) {
    setDate(this.hari, bulan, this.tahun);
  }

  public void setHari(int hari) {
    setDate(hari, this.bulan, this.tahun);
  }

  public int getTahun(){
    return this.tahun;
  }

  public int getBulan(){
    return this.bulan;
  }

  public int getHari(){
    return this.hari;
  }

  public boolean isKabisat(int tahun) {
    if ((tahun % 4 == 0 && tahun % 100 != 0) || (tahun % 400 == 0)) {
      return true;
    } else {
      return false;
    }
  }

  public int getJumlahHariPerBulan(int bulan, int tahun) {
    if (bulan == 2) return isKabisat(tahun) ? 29 : 28;

    if (bulan == 4 || bulan == 6 || bulan == 9 || bulan == 11) return 30;
    return 31;
  }

  public long toTotalHari() {
    if (tahun < 1) {
      return this.hari;
    }

    long y = this.tahun - 1;
    long totalHari = y * 365;
    totalHari = totalHari + (y / 4) - (y / 100) + (y / 400);

    for (int i = 1; i < this.bulan; i++) {
      totalHari += getJumlahHariPerBulan(i, this.tahun);
    }

    totalHari += this.hari; 
    return totalHari;
  }

  @Override
  public String toString() {
  
    return String.format("%02d/%02d/%04d", hari, bulan, tahun);
  }
}
