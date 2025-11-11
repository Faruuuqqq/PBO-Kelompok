package parking_logic;

public class Node {
    public Kendaraan data;
    public Node next;

  public Node() {}

  public Node(Kendaraan data) {
    this.data = data;
    this.next = null;
  }

  public Kendaraan getData() {
    return this.data;
  }

  public void setData(Kendaraan data) {
    this.data = data;
  }
}
