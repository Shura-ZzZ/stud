import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Catalog {

    private ArrayList<Product> catalog;
    public Catalog()
    {
        catalog=new ArrayList<Product>();
    }

    @Override
    public String toString() {
        String str = "List of products";
        for (Product p: catalog) {
            str+="\n"+p.toString()+" rub.";

        }
        return str;

    }
    public String FileString()
    {
        String str = "";
        for (Product p: catalog) {
            str+=p.toString()+"\n";

        }
        return str;

    }


    //todo
    public boolean contains( Product p)
    {
        return catalog.contains(p);


    }
    public boolean contains(String  name) {

        long c = catalog.stream().filter(p -> p.getName().equals(name.trim())).count();
        if (c == 0) return false;
        return true;
    }

    public void addProduct(Product p)
    {
        catalog.add(p);

    }
    public void deleteProduct(Product p)
    {
        catalog.remove(p);
    }

    public Product searchProduct(String name)
    {

        Product x  = new Product();
        try {
            x=  catalog.stream().filter(p-> p.getName().equals(name.trim())).findFirst().orElse(new Product());
        }
        catch (Exception e)
        {

        }
        return x;

    }
}
