package parking_logic;

public class Motor extends Kendaraan {
    public Motor() {
      super();
    }

    public Motor(Person person, String noKendaraan, int status, Waktu waktuDatang, Waktu waktuPulang) {
        super(person, noKendaraan, status, waktuDatang, waktuPulang);
    }
    
    @Override
    public String getJenisAsString() {
      return "Motor";
    }
    
    @Override
    protected long getTarifRegular() {
      return 2000;
    }
    
    @Override
    protected long getTarifMenginap() {
      return 15000;
    }
}

