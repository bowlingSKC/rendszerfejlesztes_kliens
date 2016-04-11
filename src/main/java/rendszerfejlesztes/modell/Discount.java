package rendszerfejlesztes.modell;

public class Discount {
    private Integer id;
    private String name;
    private Double value;

    public Discount() {
    }

    public Discount(String name, Double value) {
        this.name = name;
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "Discount{" +
                "name='" + name + '\'' +
                ", value=" + value +
                '}';
    }
}

