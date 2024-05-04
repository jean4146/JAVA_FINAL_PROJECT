import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

public class DatabaseToFrenchWords {
    private static final String[] dizaineNames = {
        "", "", "vingt", "trente", "quarante", "cinquante", "soixante", "soixante", "quatre-vingt", "quatre-vingt"
    };

    private static final String[] uniteNames1 = {
        "", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf",
        "dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"
    };

    private static final String[] uniteNames2 = {
        "", "", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix"
    };

    public static void main(String[] args) {
        // Paramètres de connexion à la base de données
        String url = "jdbc:mysql://localhost:3306/gestioncommandes?characterEncoding=latin1";
        String user = "root";
        String password = "";

        // Votre requête SQL
        String query = "SELECT SUM(gestioncommandes.detailscom.`qteCom` * gestioncommandes.produit.`prixUnit`) AS TotalHT FROM gestioncommandes.detailscom INNER JOIN gestioncommandes.commande ON gestioncommandes.detailscom.`numCom` = gestioncommandes.commande.`numCom` INNER JOIN gestioncommandes.client ON gestioncommandes.commande.`numCl` = gestioncommandes.client.`numCl` INNER JOIN gestioncommandes.produit ON gestioncommandes.detailscom.`numProd` = gestioncommandes.produit.`numProd` WHERE gestioncommandes.client.`numCl` = ? GROUP BY gestioncommandes.client.`numCl`";

        try {
            // Établir la connexion

            Connection conn = DriverManager.getConnection(url, user, password);

            // Préparer et exécuter la requête SQL
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, 1);  // Remplacez par le numéro du client dynamique
            ResultSet rs = pstmt.executeQuery();

            // Traiter le résultat
            if (rs.next()) {
                double totalHT = rs.getDouble("TotalHT");
                double totalTTC = totalHT*1.20;
                System.out.println("Total TTC: " + totalTTC);

                // Convertir en mots
                String totalEnLettres = convert((long) totalTTC);
                System.out.println("Arrétée la presente facture à la somme de:" + totalEnLettres +" FCFA toutes taxes comprises");
            }

            // Fermer les connexions
            rs.close();
            pstmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String convert(long number) {
        // Gestion des cas particuliers
        if (number == 0) { return "zéro"; }

        String snumber = Long.toString(number);

        // Pad des "0" pour garantir que la longueur de la chaîne soit multiple de 3
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // Extraction des différentes parties du nombre
        int lesMilliards = Integer.parseInt(snumber.substring(0, 3));
        int lesMillions = Integer.parseInt(snumber.substring(3, 6));
        int lesCentMille = Integer.parseInt(snumber.substring(6, 9));
        int lesMille = Integer.parseInt(snumber.substring(9, 12));

        // Conversion des différentes parties en mots
        String tradMilliards = lesMilliards > 0 ? convertLessThanOneThousand(lesMilliards) + " milliard" + (lesMilliards > 1 ? "s " : " ") : "";
        String tradMillions = lesMillions > 0 ? convertLessThanOneThousand(lesMillions) + " million" + (lesMillions > 1 ? "s " : " ") : "";
        String tradCentMille = lesCentMille > 0 ? convertLessThanOneThousand(lesCentMille) + " mille " : lesMillions > 0 ? "mille " : "";
        String tradMille = convertLessThanOneThousand(lesMille);

        return tradMilliards + tradMillions + tradCentMille + tradMille;
    }

    private static String convertLessThanOneThousand(int number) {
        int hundreds = number / 100;
        int rest = number % 100;
        String sRest = convertZeroToHundred(rest);

        if (hundreds == 0) return sRest;
        else if (hundreds == 1) return "cent " + (rest != 0 ? sRest : "");
        else return uniteNames2[hundreds] + " cent" + (rest != 0 ? " " + sRest : "s");
    }

    private static String convertZeroToHundred(int number) {
        int tens = number / 10;
        int units = number % 10;
        String result = "";

        if (tens >= 2) {
            result = dizaineNames[tens];
            if (units == 1) result += " et un";
            else if (units > 1) result += "-" + uniteNames1[units];
        } else {
            result = uniteNames1[number];
        }

        return result;
    }
}
