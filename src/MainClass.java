import java.io.FileNotFoundException;

public class MainClass {

    public static void main(String[] args) throws FileNotFoundException {

        Minimisation a = new Minimisation();
        a.getNombreEtats();
        a.remplirEtats();
        a.printEtats();
        a.createAutomateInversee();
        a.initialisePile();
        System.out.println("-------------------------");
        a.trouverEtatsDistincts();
        a.printEtatsDistingables();
        System.out.println("-------------------------");
        a.trouverEtatsIndistingables();
        a.printEtatsNonDistingables();
        //a.printautomateInversee();





        }
}
