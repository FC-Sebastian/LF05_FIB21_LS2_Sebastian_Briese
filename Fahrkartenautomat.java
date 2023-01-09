import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.lang.Math;


class Fahrkartenautomat {
    public static void main(String[] args) {

        Scanner tastatur = new Scanner(System.in);

        double zuZahlenderBetrag = 0;
        double eingezahlterGesamtbetrag;
        double eingeworfeneMuenze;
        double rueckgabebetrag;
        double nochZuZahlen;
        boolean gueltig;
        boolean bezahlen = false;

        // 1 Geldbetrag festlegen
        while (bezahlen == false) {

            begruessung();
            zuZahlenderBetrag += fahrkartenBestellungErfassen(tastatur);
            bezahlen = bestellungFortsetzen(tastatur);

        }

        // 2 Geldeinwurf
        eingezahlterGesamtbetrag = fahrKartenBezahlen(tastatur, zuZahlenderBetrag);

        // 3 Fahrscheinausgabe
        fahrkartenAusgabe();

        // 4 Rückgeldberechnung und -ausgabe
        rueckgeldAusgeben(eingezahlterGesamtbetrag,zuZahlenderBetrag);

        tastatur.close();
    }

    public static void begruessung() {
        System.out.println("Wählen Sie ihre Wunschfahrkarte für Berlin AB aus:");
        System.out.println("    Kurzstrecke AB [2,00 EUR] (1)");
        System.out.println("    Einzelfahrschein AB [3,00 EUR] (2)");
        System.out.println("    Tageskarte AB [8,80 EUR] (3)");
        System.out.println("    4-Fahrten-Karte AB [9,40 EUR] (4)");
    }

    public static double fahrkartenBestellungErfassen(Scanner tastatur){
        double[] fahrscheinpreise = {2.0, 3.0, 8.8, 9.4};
        int o = 0;
        int auswahl;
        int ticketAnzahl;
        double fahrscheinpreis;

        auswahl = tastatur.nextInt();
        System.out.println("Ihre Wahl: " + auswahl);
        while ( auswahl > fahrscheinpreise.length || auswahl < 0) {
            System.out.println(">>falsche Eingabe<<");
            auswahl = tastatur.nextInt();
            System.out.println("Ihre Wahl: " + auswahl);
        }

        fahrscheinpreis = fahrscheinpreise[auswahl-1];

        System.out.println("Anzahl der Tickets: (1-10)");
        ticketAnzahl = tastatur.nextInt();
        while (ticketAnzahl <= 0  || ticketAnzahl > 10) {
            System.out.println(">>falsche Eingabe<<");
            System.out.println("Anzahl der Tickets: (1-10)");
            ticketAnzahl = tastatur.nextInt();
        }
        return fahrscheinpreis * ticketAnzahl;
    }

    public static boolean bestellungFortsetzen(Scanner tastatur) {
        System.out.println("Bestellung Fortsetzen? (1 fuer ja 0 fuer nein)");
        int auswahl = tastatur.nextInt();
        while (auswahl != 1 && auswahl != 0) {
            System.out.println(">>falsche Eingabe<<");
            System.out.println("Bestellung Fortsetzen? (1 fuer ja 0 fuer nein)");
            auswahl = tastatur.nextInt();
        }
        return auswahl != 1;
    }

    public static double fahrKartenBezahlen(Scanner tastatur, double zuZahlenderBetrag) {
        double[] gueltigeGeldeingaben = {0.05, 0.10, 0.20, 0.50, 1, 2, 5, 10, 20};
        double eingezahlterGesamtbetrag = 0.0;
        double nochZuZahlen = 0.0;
        double eingeworfeneMuenze;
        boolean gueltig;

        while (eingezahlterGesamtbetrag < zuZahlenderBetrag) {
            gueltig = false;
            nochZuZahlen = zuZahlenderBetrag - eingezahlterGesamtbetrag;
            System.out.printf("Noch zu zahlen: %.2f \n",nochZuZahlen);
            System.out.println("Eingabe (mind. 5 Cent, höchstens 20 Euro): ");
            eingeworfeneMuenze = tastatur.nextDouble();
            for (double muenze: gueltigeGeldeingaben) {
                if (eingeworfeneMuenze == muenze) {
                    gueltig = true;
                }
            }
            if (gueltig) {
                eingezahlterGesamtbetrag = eingezahlterGesamtbetrag + eingeworfeneMuenze;
            } else {
                System.out.println(">> Kein gültiges Zahlungsmittel");
            }
        }
        return eingezahlterGesamtbetrag;
    }

    public static void fahrkartenAusgabe() {
        System.out.println("\nFahrschein wird ausgegeben");
        for (int i = 0; i < 8; i++) {
            System.out.print("=");
            try {
                Thread.sleep(200);
            }
            catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println("\n\n");
    }

    public static void rueckgeldAusgeben(double eingezahlterGesamtbetrag, double zuZahlenderBetrag) {
        double rueckgabebetrag = eingezahlterGesamtbetrag - zuZahlenderBetrag;
        if (rueckgabebetrag > 0.0) {
            System.out.printf("Der Rückgabebetrag in Höhe von %.2f Euro\n",rueckgabebetrag);
            System.out.println("wird in folgenden Münzen ausgezahlt:");

            rueckgabebetrag = muenzAusgabe(rueckgabebetrag, 2.0, "2 Euro");
            rueckgabebetrag = muenzAusgabe(rueckgabebetrag, 1.0, "1 Euro");
            rueckgabebetrag = muenzAusgabe(rueckgabebetrag, 0.5, "50 Cent");
            rueckgabebetrag = muenzAusgabe(rueckgabebetrag, 0.2, "20 Cent");
            rueckgabebetrag = muenzAusgabe(rueckgabebetrag, 0.1, "10 Cent");
            rueckgabebetrag = muenzAusgabe(rueckgabebetrag, 0.05, "5 Cent");
        }
        System.out.println("\nVergessen Sie nicht, den Fahrschein\n" + "vor Fahrtantritt entwerten zu lassen!\n"
                + "Wir wünschen Ihnen eine gute Fahrt.");

    }

    public static double muenzAusgabe(double rueckgabebetrag, double muenzWert, String muenzString) {
        while (rueckgabebetrag >= muenzWert) {
            System.out.println(muenzString);
            rueckgabebetrag = rueckgabebetrag - muenzWert;
            rueckgabebetrag = Math.round(rueckgabebetrag * 100) / 100.0;
        }
        return rueckgabebetrag;
    }
}
