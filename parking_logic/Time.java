package parking_logic;

public class Time {
    private int jam;
    private int menit;
    private int detik;

    public Time() {
        this.jam = 0;
        this.menit = 0;
        this.detik = 0;
    }

    public Time(int jam, int menit, int detik) {
        setTime(jam, menit, detik);
    }

    public void setTime(int jam, int menit, int detik) {
        if (jam < 0 || menit < 0 || detik < 0) {
        throw new IllegalArgumentException("Jam/menit/detik tidak boleh negatif");
        }

        if (detik >= 60) {
        menit += detik / 60;
        detik %= 60;
        }

        if (menit >= 60) {
        jam += menit / 60;
        menit %= 60;
        }

        if (jam >= 24) {
        jam %= 24;
        }

        this.jam = jam;
        this.menit = menit;
        this.detik = detik;
    }

    public void setJam (int jam) {
        setTime(jam, this.menit, this.detik);
    }
    
    public void setMenit (int menit) {
        setTime(this.jam, menit, this.detik);
    }

    public void setDetik (int detik) {
        setTime(this.jam, this.menit, detik);
    }

    public int getJam() {
        return this.jam;
    }
    
    public int getMenit() {
        return this.menit;
    }
    
    public int getDetik() {
        return this.detik;
    }

    public int toSeconds() {
        return this.jam * 3600 + this.menit * 60 + this.detik;
    }

    @Override
    public String toString() {
        return String.format("%02d:%02d:%02d", jam, menit, detik);
    }
}
