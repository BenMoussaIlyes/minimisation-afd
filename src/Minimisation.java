import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class Minimisation {

    private int nEtats;
    private ArrayList<Etat> etats;
    private ArrayList<Etat> automateInversee;
    private ArrayList<Etat> pileColonne1 ;
    private ArrayList<Etat> pileColonne2 ;
    private ArrayList<Etat> etatsDistinctsColonne1 ;
    private ArrayList<Etat> etatsDistinctsColonne2 ;
    private ArrayList<Etat> etatsNonDistinctsColonne1 ;
    private ArrayList<Etat> etatsNonDistinctsColonne2 ;

    public Minimisation() {
        etats = new ArrayList<>(10);
        automateInversee = new ArrayList<>(10);
        pileColonne1 = new ArrayList<>(10);
        pileColonne2 = new ArrayList<>(10);
        etatsDistinctsColonne1 = new ArrayList<>(10);
        etatsDistinctsColonne2 = new ArrayList<>(10);

        etatsNonDistinctsColonne1 = new ArrayList<>(10);
        etatsNonDistinctsColonne2 = new ArrayList<>(10);
    }

    public void getNombreEtats() {
        Scanner sc = new Scanner(System.in);
        int nEtats;
        while (true) {
            System.out.print("Entrer nombre d'etats :");
            nEtats = sc.nextShort();
            System.out.println("");
            if (nEtats > 10 || nEtats < 2) {
                System.out.println("[-] input doit etre entre 2 et 10");
            } else {
                break;
            }
        }
        this.nEtats = nEtats;
        sc.nextLine();
    }


    public void remplirEtats() throws FileNotFoundException {
        Scanner sc = new Scanner(new File("entree.txt"));

        for (int i = 0; i < this.nEtats; i++) {
            System.out.println("etat " + i);
            String a;
            String b;
            String nom = this.CreateNameWithIndex(i);


            do {
                System.out.print("a :");
                a = sc.nextLine();
            }
            while (a.length() != nEtats);


            do {
                System.out.print("b :");
                b = sc.nextLine();
            }
            while (b.length() != nEtats);

            String f;
            do {
                System.out.print("est il final ? (t/f):");
                f = sc.nextLine();

            }
            while (!(f.equals("t") || f.equals("f")));


            boolean boo = f.equals("t");
            this.etats.add(new Etat(nom, a, b, boo));
        }
    }

    public void createAutomateInversee(){
        for (int i = 0 ; i < this.etats.size() ; i++ ){
            String newA = "0".repeat(this.nEtats);
            String newB = "0".repeat(this.nEtats);
            for (Etat e1 : this.etats){
                if ( e1.getA().charAt(i) == '1' ){
                    char[] charsDeA = newA.toCharArray();
                    charsDeA[this.etats.indexOf(e1)] = '1';
                    newA = String.valueOf(charsDeA);
                }
                if ( e1.getB().charAt(i) == '1' ){
                    char[] charsDeB = newB.toCharArray();
                    charsDeB[this.etats.indexOf(e1)] = '1';
                    newB = String.valueOf(charsDeB);
                }
            }
            Etat e = new Etat (etats.get(i).getNom() ,newA ,newB,etats.get(i).isF());
            this.automateInversee.add(e);
        }

    }
    public void initialisePile(){
        for (Etat e : this.automateInversee) {
            if (e.isF() == true){
                for (Etat e1 : this.automateInversee) {
                    if (e1.isF() == false){
                        if (! coupleExisteDansEtatsDistincts(e,e1)){

                            this.pileColonne1.add(e);
                            this.pileColonne2.add(e1);

                            int index = this.automateInversee.indexOf( e ) ;
                            int index1 = this.automateInversee.indexOf( e1 ) ;
                            this.etatsDistinctsColonne1.add(this.etats.get(index));
                            this.etatsDistinctsColonne2.add(this.etats.get(index1));
                        }
                    }
                }
            }
        }
    }



    public void trouverEtatsDistincts(){
        while (pileColonne1.size()>0){

            Etat e = pileColonne1.get(0);
            Etat e1 = pileColonne2.get(0);

            for (int i = 0 ; i<e.getA().length() ; i++){
               if (e.getA().charAt(i) == '1'){
                   for (int j = 0 ; j<e1.getA().length() ; j++){
                       if (e1.getA().charAt(j) == '1'){

                           Etat eOriginal = this.etats.get(i);
                           Etat e1Original = this.etats.get(j);
                           if (! coupleExisteDansEtatsDistincts(eOriginal,e1Original)) {

                               this.pileColonne1.add(this.automateInversee.get(i));
                               this.pileColonne2.add(this.automateInversee.get(j));

                               this.etatsDistinctsColonne1.add(eOriginal);
                               this.etatsDistinctsColonne2.add(e1Original);

                           }
                       }
                   }
               }
            }

            for (int i = 0 ; i<e.getB().length() ; i++){
                if (e.getB().charAt(i) == '1'){
                    for (int j = 0 ; j<e1.getB().length() ; j++){
                        if (e1.getB().charAt(j) == '1'){

                            Etat eOriginal = this.etats.get(i);
                            Etat e1Original = this.etats.get(j);
                            if (! coupleExisteDansEtatsDistincts(eOriginal,e1Original)) {

                                this.pileColonne1.add(this.automateInversee.get(i));
                                this.pileColonne2.add(this.automateInversee.get(j));

                                this.etatsDistinctsColonne1.add(eOriginal);
                                this.etatsDistinctsColonne2.add(e1Original);

                            }
                        }
                    }
                }
            }
            this.pileColonne1.remove(0);
            this.pileColonne2.remove(0);
        }

    }

    public void printEtats() {
        for (Etat e : this.etats) {
            System.out.print("Nom : " + e.getNom() + " ## ");
            System.out.print("A : " + e.getA() + " ## ");
            System.out.print("B : " + e.getB() + " ## ");
            System.out.println("final? : " + e.isF());
        }
    }
    public void printEtatsDistingables(){
        for (int i = 0 ; i<this.etatsDistinctsColonne1.size() ; i++) {
            System.out.print(etatsDistinctsColonne1.get(i).getNom());
            System.out.print(" --- ");
            System.out.println(etatsDistinctsColonne2.get(i).getNom());
        }
    }
    public void trouverEtatsIndistingables(){
        for (Etat e : this.etats) {
                for (Etat e1 : this.etats) {
                        if (e != e1 && ! coupleExisteDansEtatsDistincts(e,e1) && ! coupleExisteDansEtatsNonDistincts(e,e1)){

                            this.etatsNonDistinctsColonne1.add(e);
                            this.etatsNonDistinctsColonne2.add(e1);

                        }

                }

        }
    }

    public void printEtatsNonDistingables(){
        for (int i = 0 ; i<this.etatsNonDistinctsColonne1.size() ; i++) {
            System.out.print(etatsNonDistinctsColonne1.get(i).getNom());
            System.out.print(" --- ");
            System.out.println(etatsNonDistinctsColonne2.get(i).getNom());
        }
    }

    public void printautomateInversee() {
        for (Etat e : this.automateInversee) {
            System.out.print("Nom : " + e.getNom() + " ## ");
            System.out.print("A : " + e.getA() + " ## ");
            System.out.print("B : " + e.getB() + " ## ");
            System.out.println("final? : " + e.isF());
        }
    }

    private boolean coupleExisteDansEtatsDistincts(Etat e,Etat e1){
        for (int i = 0 ; i<this.etatsDistinctsColonne1.size() ; i++){
            if ( (etatsDistinctsColonne1.get(i) == e && etatsDistinctsColonne2.get(i)==e1 ) || (etatsDistinctsColonne1.get(i)==e1 && etatsDistinctsColonne2.get(i)==e) ){
                return true;
            }
        }
        return false;
    }
    private boolean coupleExisteDansEtatsNonDistincts(Etat e,Etat e1){
        for (int i = 0 ; i<this.etatsNonDistinctsColonne1.size() ; i++){
            if ( (etatsNonDistinctsColonne1.get(i) == e && etatsNonDistinctsColonne2.get(i)==e1 ) || (etatsNonDistinctsColonne1.get(i)==e1 && etatsNonDistinctsColonne2.get(i)==e) ){
                return true;
            }
        }
        return false;
    }

    private String CreateNameWithIndex(int index) {
        String nom = "0".repeat(this.nEtats);
        char[] charsDeNom = nom.toCharArray();
        charsDeNom[index] = '1';
        return String.valueOf(charsDeNom);
    }




}