public class DessertJava {
    int flavor;
    int price;
    static int numDesserts = 0;
    public DessertJava(int newflavor, int newprice) {
        flavor = newflavor;
        price = newprice;
        numDesserts += 1;
    }
    public void printDessert() {
        System.out.print(flavor + " " + price + " " + numDesserts);
    }
    public static void main(String[] args) {
        System.out.print("I love dessert!");
    }
}
