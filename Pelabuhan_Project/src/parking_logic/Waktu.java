package parking_logic;

public class Waktu {
  private Date date;
  private Time time;

  public Waktu() {
    this.date = new Date();
    this.time = new Time();
  }

  public Waktu(Date date, Time time) {
    this.date = date;
    this.time = time;
  }

  public Date getDate() {
    return this.date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public Time getTime() {
    return this.time;
  }

  public void setTime(Time time) {
    this.time = time;
  }

  @Override
  public String toString() {
    return date + " " + time;
  }
}

