import java.util.Objects;

public class Product {
    private String name;
    private double prise;

    public Product()
    {
        name="None";
        prise= 0;

    }
    public Product(String name, double prise) throws Exception
    {
        setName(name);
        setPrise(prise);

    }
    public Product(String str) throws Exception
    {
        String[] a = str.trim().split(" +");
        try {
            setName(a[0]);
            setPrise(Double.parseDouble(a[1]));
        }
        catch (Exception e)
        {
            throw new Exception("Line entry error");

        }

    }

    public double getPrise() {
        return prise;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrise(double prise) throws Exception
    {
        if(prise<0)
            throw new Exception("Prise error");
        this.prise = prise;
    }

    @Override
    public String toString() {
        return  name + " " + prise;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return Double.compare(product.prise, prise) == 0 && Objects.equals(name, product.name);
    }

    //todo
    @Override
    public int hashCode() {
        return Objects.hash(name, prise);
    }
}
