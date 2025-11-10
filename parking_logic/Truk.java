package parking_logic;

public class Truk extends Kendaraan {
    public Truk() {
      super();
    }
    
    public Truk(Person person, String noKendaraan, int status, Waktu waktuDatang, Waktu waktuPulang) {
        super(person, noKendaraan, status, waktuDatang, waktuPulang);
    }

    @Override
    public String getJenisAsString() { 
      return "Truk";
      }

    @Override
    protected long getTarifRegular() {
      return 5000;
    }
    
    @Override
    protected long getTarifMenginap() {
      return 50000;
    }
}
