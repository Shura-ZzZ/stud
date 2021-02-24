
import java.util.*;
import java.io.*;
import java.util.regex.*;


public class Main {
    public static void main(String[] args) {

        Properties property = new Properties();
        String datapath ="";
        ArrayList<String> filter = new ArrayList<String>();
        try{
            FileInputStream fis = new FileInputStream("src/main/resources/conf.properties");
            property.load(fis);
            datapath = property.getProperty("datapath");
        }
        catch (Exception e){}

        try {
            FileInputStream fis = new FileInputStream("src/main/resources/filter.properties");
            property.load(fis);
            int n = Integer.parseInt(property.getProperty("COUNT"));
            for(int i=0; i<n;i++)
            {
                String tmp = property.getProperty("FILTER"+i);
                filter.add(tmp);
            }
        }
        catch(Exception e)
        {
        }
        Catalog cat = new Catalog();
        File file = new File(datapath);

        try {

            BufferedReader reader = new BufferedReader(new FileReader(file));
            String str;
            str = reader.readLine();
            while (str !=null)
            {
                try {
                    addProduct(cat,str);
                    str = reader.readLine();
                }
                catch (Exception e)
                {
                    System.out.println("Error data");
                }

            }

        }
        catch (Exception e)
        {
            System.out.println("Error file");
        }
        MenuCatalog(cat, file, filter);




    }
    public static void MenuCatalog(Catalog cat, File file, ArrayList<String> filter){

        int oper =-1;
        String  str;
        Scanner sc = new Scanner(System.in);
        System.out.println("1-add product\n2-delete product\n3-search product\n4-catalog\nother-exit\n");
        if(sc.hasNextInt()) {
            oper = sc.nextInt();
        }
        while (oper!=0) {

            switch (oper) {
                case (1): {
                    try {
                        System.out.println("Enter the name and price:");
                        Scanner in = new Scanner(System.in);
                        str = in.nextLine();
                         if (CheckFilters(str,filter))
                         {
                             System.out.println("Prohibited product!");
                             break;
                         }
                        if (addProduct(cat, str)) {
                            SaveInFile(cat,file);
                            System.out.println("Product added");
                        }
                        else
                            System.out.println("Such a product exists");

                    }catch (Exception e)
                    {
                        System.out.println("Adding error");
                    }
                    break;
                }
                case (2): {
                    try {
                        System.out.println("Enter the name and price:");
                        Scanner in = new Scanner(System.in);
                        str = in.nextLine();
                        if(deleteProduct(cat, str))
                            System.out.println("Product removed");
                        else
                            System.out.println("Product not found");

                        SaveInFile(cat,file);

                    }catch (Exception e)
                    {
                        System.out.println("Error delete");
                    }

                    break;
                }
                case (3): {
                    System.out.println("Input name:");
                    Scanner in = new Scanner(System.in);
                    str = in.nextLine();
                    Product p = cat.searchProduct(str);
                    if(p.getName().equals("None"))
                        System.out.println("Product not found");
                    else
                        System.out.println(p);
                    break;
                }
                case (4): {
                    System.out.println(cat);
                    break;
                }

                default: {
                    System.out.println("End");
                    System.exit(0);
                }

            }
            System.out.println("1-add product\n2-delete product\n3-search product\n4-catalog\nother-exit\n");

            try {
                oper = sc.nextInt();

            }
            catch (Exception e)
            {
                System.out.println("End");
                System.exit(0);
            }

        }

    }
    private static boolean deleteProduct(Catalog cat, String str) throws Exception {

        Product p = cat.searchProduct(str);
        if(!p.getName().equals("None")) {
            cat.deleteProduct(p);
            return true;

        }

        else

            return false;

    }

    public static boolean addProduct(Catalog cat, String str) throws Exception {
        String[] a = str.trim().split(" +");
        Product p = cat.searchProduct(a[0]);
        if(p.getName().equals("None")) {
            p = new Product(str);
            cat.addProduct(p);
            return true;

        }
        else
            return false;

    }
    public static void SaveInFile(Catalog cat, File file)
    {
        try(FileWriter writer = new FileWriter(file, false))
        {

            String text = cat.FileString();
            writer.write(text);

            writer.flush();
        }
        catch(Exception e){

            System.out.println("Ошибка сохранения");
        }

    }
    public static boolean CheckFilters(String str, ArrayList<String> filter)
    {
        for(String f: filter)
        {
            Pattern pt = Pattern.compile(f);
            System.out.println(f);
            Matcher mt = pt.matcher(str);
            if (mt.find()) return true;
        }
        return false;

    }
}
