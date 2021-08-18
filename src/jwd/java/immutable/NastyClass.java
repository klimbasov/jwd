package immutable;

public class NastyClass {
    private int data;

    public NastyClass(final int data) {
        this.data = data;
    }

    public NastyClass(final NastyClass nastyClass) {
        this.data = nastyClass.data;
    }

    public int getData() {
        return data;
    }

    public void setData(int data) {
        this.data = data;
    }
}
