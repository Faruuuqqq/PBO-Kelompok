package parking_logic;

public class Mobil extends Kendaraan {
    public Mobil() {
      super();
    }

    public Mobil(Person person, String noKendaraan, int status, Waktu waktuDatang, Waktu waktuPulang) {
        super(person, noKendaraan, status, waktuDatang, waktuPulang);
    }
    
    @Override
    public String getJenisAsString() {
      return "Mobil";
    }
    
    @Override
    protected long getTarifRegular() {
      return 3000;
    }
    
    @Override
    protected long getTarifMenginap() {
      return 25000;
    }
}