package parking_logic;

public class Person {
    private String nama;

    public Person() {
      this.nama = "";
    }
    
    public Person(String nama) {
        this.nama = nama;
    }

    public String getNama() {
        return nama;
    }

    public void setNama(String nama) {
      this.nama = nama;
    }
}
