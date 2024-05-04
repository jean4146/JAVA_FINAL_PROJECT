import javafx.application.Application;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextArea;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ComboBox;
import javafx.scene.paint.Color;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.Modality;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.sql.*;
import java.time.LocalDate;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.AnchorPane;
import java.util.List;
import javax.swing.text.TableView.TableRow;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.SimpleStringProperty;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import com.mysql.jdbc.Driver;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import com.mysql.jdbc.Driver;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;


/* au niveau de l'affichages des éléments dans leur fenetre respective , je m'occupe de 
 de ceux des fenetres filles avant ceux de la fenetre principale
*/


public class GesCom2 extends Application {

    // Informations de connexion à la base de données
    static String url = "jdbc:mysql://localhost:3306/gestioncommandes?characterEncoding=latin1";
    static String utilisateur = "root";
    static String motDePasse = "";

    // // Établissement de la connexion
    // try {
    //     Connection connexion = DriverManager.getConnection(url, utilisateur, motDePasse);
    //     System.out.println("Connexion à la base de données réussie !");


    //     // Fermeture de la connexion
    //     connexion.close();
    // } catch (SQLException e) {
    //     System.out.println("Erreur lors de la connexion à la base de données : " + e.getMessage());
    // }

    // -----------------element de la fenetre principale
    // numcl
    Label Labelnumcl = new Label("Numcl :");
    TextField textFieldnumCl = new TextField();
    // nom
    Label Labelnom = new Label("nom :");
    TextField textFieldnom = new TextField();
    // ville
    Label Labelville = new Label("ville :");
    TextField textFieldville = new TextField();
    // telephone
    Label Labeltel = new Label("telephone :");
    TextField textFieldtel = new TextField();
    // fax
    Label Labelfax = new Label("fax :");
    TextField textFieldfax = new TextField();
    // email
    Label Labelmail = new Label("email :");
    TextField textFieldmail= new TextField();



        // utile pour afficher les info des commandes du client ci

    // Titre
    Label LabelInfoTitreCom = new Label("Informations sur les Commandes du client");
    // NumPro
    Label LabelInfoNumCom = new Label("NumCom :");
    ComboBox<String> comboBoxInfoNumCom = new ComboBox<String>();
    //   Appeler la fonction pour récupérer les identifiants de la table Commande
    List<String> identifiantsCommandes = getIdentifiantsTable("commande", "numCom");

    // Mte total d'une commande sélectionné dans le combobox
    Label LabelInfoMteCom = new Label("Montant de la commande :");
    TextField textFieldBoxInfoMteCom = new TextField();
    // Mte total des commandes du client
    Label LabelInfoMteTtCom = new Label("Montant total des commandes :");
    TextField textFieldBoxInfoMteTtCom = new TextField();

    Button buttontableNDCom = new Button("Afficher les dates de commande");          // tableNDCom : tableau Num Date Commande

    Button buttonInprimerFacture = new Button("Imprimer la facture de cette commande"); 

    //tableau pour afficher les numeros et les dates des commandes d'un client
    // tableNDCom : tableau Num Date Commande
    TableView<List<String>> tableNDCom = new TableView<>();


    // les boutons
    Button buttonPremierF1 = new Button("Premier");
    Button buttonSuivantF1 = new Button("Suivant");
    Button buttonPrecedentF1 = new Button("Precedent");
    Button buttonDernierF1 = new Button("Dernier");
    Button buttonNouveauF1 = new Button("Nouveau");
    Button buttonModifierF1 = new Button("Modifier");
    Button buttonRechercherF1 = new Button("Rechercher");
    Button buttonAjouterF1 = new Button("Ajouter");
    Button buttonProdF1 = new Button("Ouvrir la fenêtre de produit");
    Button buttonSupprimerF1 = new Button("Supprimer");
    Button buttonQuitterF1 = new Button("Quitter");

    


    // Menu
    MenuBar menuBar = new MenuBar();
    Menu menuFichier = new Menu("Fichier");
    Menu menuOcommande = new Menu("Options de Commande");
    Menu menuNavigation = new Menu("Navigation");
    Menu menuTables = new Menu("Afficher la table");

     
      // Element de Fichier
    MenuItem menuQuitter = new MenuItem("Quitter");
  
    // Element de Navigation
    MenuItem menuNPremier = new MenuItem("Premier");
    MenuItem menuNPrecedent = new MenuItem("Precedent");
    MenuItem menuNSuivant = new MenuItem("Suivant");
    MenuItem menuNDernier = new MenuItem("Dernier");

    // Element de Options commande
    MenuItem menuNProduit = new MenuItem("Fenêtre de produit");
    MenuItem menuNCommande = new MenuItem("effectuer une commande");

    // Element de menuTables
    MenuItem menuNclient = new MenuItem("Clients");
    MenuItem menuNproduit = new MenuItem("Produits");
    MenuItem menuNcommande = new MenuItem("Commandes");
    


    // ---------------éléments de la fenetre d'enregistrement de produit

    // numProd
    Label LabelnumProd = new Label("NumProd :");
    TextField textFieldnumProd = new TextField();
    // designation
    Label Labeldesignation = new Label("designation :");
    TextField textFielddesignation = new TextField();
    // Prix unit
    Label LabelPrixUnit = new Label("prix unit :");
    TextField textFieldPrixUnit = new TextField();
    // qte stock
    Label Labelqtestock = new Label("qte stock :");
    TextField textFieldqtestock = new TextField();
    
    Button buttonPremierF2 = new Button("Premier");
    Button buttonSuivantF2 = new Button("Suivant");
    Button buttonPrecedentF2 = new Button("Precedent");
    Button buttonDernierF2 = new Button("Dernier");
    Button buttonNouveauF2 = new Button("Nouveau");
    Button buttonModifierF2 = new Button("Modifier");
    Button buttonRechercherF2 = new Button("Rechercher");
    Button buttonAjouterF2 = new Button("Ajouter");
    Button buttonSupprimerF2 = new Button("Supprimer");
    Button buttonQuitterF2 = new Button("Quitter");

    //-------boutons des etats fonctions de b-e des projet 
    Button buttonimprimerb= new Button("etat_montant_total_de_commande");
    Button buttonimprimerc = new Button("etat_montant_total_de_toutes_les_commandes");
    Button buttonimprimerd = new Button("etat_affiche_les_numeros_et_dates_des_commandes");
    Button buttonimprimere = new Button("etat_nom_du client_ayant_passe_une_commande");


    // ---------------éléments de la fenetre d'enregistrement de visualisations des tableaux

         // creation des tableaux

    // tableaux pour les clients
    TableView<List<String>> tableClient = new TableView<>();

    // tableaux pour les commandes
    TableView<List<String>> tableCommande = new TableView<>();

    // tableaux pour les produits
    TableView<List<String>> tableProduit = new TableView<>();

    // ---------------éléments de la fenetre d'enregistrement de commande

    // numCl
    Label Com_LabelNumCl = new Label("NumCl :");
    ComboBox<String> Com_comboBoxNumCl = new ComboBox<String>();
    //   Appeler la fonction pour récupérer les identifiants de la table client
    List<String> identifiantsClients = getIdentifiantsTable("client", "numCl");

    // nomClient
    Label Com_LabelnomClient = new Label("Nom :");
    TextField Com_textFieldnomClient = new TextField();

    // prenomClient
    Label Com_LabeltelClient = new Label("tel :");
    TextField Com_textFieldtelClient = new TextField();

    // numProd
    Label Com_LabelNumProd = new Label("NumProd :");
    ComboBox<String> Com_comboBoxNumProd = new ComboBox<String>();
    //   Appeler la fonction pour récupérer les identifiants de la table produit
    List<String> identifiantsProduits = getIdentifiantsTable("produit", "numProd");


    // designation
    Label Com_LabelDesignation = new Label("Désignation :");
    TextField Com_textFieldDesignation = new TextField();

    // PrixUnit
    Label Com_LabelprixUnit = new Label("PrixUnit :");
    TextField Com_textFieldprixUnit = new TextField();

    // qte
    Label Com_LabelQte = new Label("Qte :");
    TextField Com_textFieldQte = new TextField();
    
    // prixTotal
    Label Com_LabelPrixTotal = new Label("PrixTotal :");
    TextField Com_textFieldPrixTotal = new TextField();

    // numCom
    Label Com_LabelnumCom = new Label("numCom :");
    TextField Com_textFieldnumCom = new TextField();

    // numLigneDetailsCom
    Label Com_LabelnumLigneDetailsCom = new Label("numLigneDetailsCom :");
    TextField Com_textFieldnumLigneDetailsCom = new TextField();

    // bouton valider
    Button buttonValider = new Button("Valider");

    
    // -----------------Element pour la section pour rechercher le nom du client qui a effectué cette commande

    Label RECH_LabelNumCom = new Label("NumCom :");
    ComboBox<String> RECH_comboBoxNumCom = new ComboBox<String>();
    //   Appeler la fonction pour récupérer les identifiants de la table commande
        // vue que cette operation à deja été effectuée ,j'ai juste appéler la fonction
            // (voir dans la partie du code où il y a remplissage des combobox)

    Button buttonRechercher = new Button("Rechercher");
    Button buttonOuvreRechercher = new Button("Rechercher le nom du client ?");     // le bouton qui nous amene sur cette section rechercher


    // --------------------Bouton d'impression (Etat)
    Button buttonImprimerClient = new Button("Imprimer la liste ");
    Button buttonImprimerCommande = new Button("Imprimer la liste ");
    Button buttonImprimerProduit = new Button("Imprimer la liste ");

    

    @Override
    public void start(Stage primaryStage) {

        // ********   fenetre principale
        Group root = new Group();
        // Créer la scène
        Scene scene = new Scene(root, 900, 600,Color.web("#1BD1B2"));
        // Configurer la fenêtre principale
        primaryStage.setTitle("Application de Gestion de Commandes");


        // ----------   fenetre d'affichages des tables
        Group rootFenViewTable = new Group();
        Scene sceneFenViewTable = new Scene(rootFenViewTable, 600, 500,Color.web("#40A292"));

        Stage fenetreViewTable = new Stage();
        fenetreViewTable.setTitle("Fenetre de visualisation de la  table");
        fenetreViewTable.setScene(sceneFenViewTable);
        fenetreViewTable.initOwner(primaryStage);       // rattache la fenetre à la fenetre parente
        fenetreViewTable.initModality(Modality.APPLICATION_MODAL);      // rendre la fenetre modale


        // ----------   fenetre de commandes
        Group rootFenCom = new Group();
        Scene sceneFenCom = new Scene(rootFenCom, 820, 400,Color.web("#1BD1B2"));

        Stage fenetreCommande = new Stage();
        fenetreCommande.setTitle("Passer une commande");
        fenetreCommande.setScene(sceneFenCom);
        fenetreCommande.initOwner(primaryStage);
        fenetreCommande.initModality(Modality.APPLICATION_MODAL);


        
        // ********  fenetre des produit (ajout ,modification ...)

        Group rootFenAddProd = new Group();
        Scene sceneFenAddProd = new Scene(rootFenAddProd, 500, 400,Color.web("#1BD1B2"));

        Stage fenetreAddProd = new Stage();
        fenetreAddProd.setTitle("Fenêtre d'ajout de produit");
        fenetreAddProd.setScene(sceneFenAddProd);
        fenetreAddProd.initOwner(primaryStage);
        fenetreAddProd.initModality(Modality.APPLICATION_MODAL);


        // positionnement des éléments de la fenetre d'ajout de produit
        int Px=10;
        int Py=10;

        LabelnumProd.setLayoutX(Px);
        LabelnumProd.setLayoutY(Py);
        textFieldnumProd.setLayoutX(Px+70);
        textFieldnumProd.setLayoutY(Py);

        Labeldesignation.setLayoutX(Px);
        Labeldesignation.setLayoutY(Py+70);
        textFielddesignation.setLayoutX(Px+70);
        textFielddesignation.setLayoutY(Py+70);

        LabelPrixUnit.setLayoutX(Px);
        LabelPrixUnit.setLayoutY(Py+140);
        textFieldPrixUnit.setLayoutX(Px+70);
        textFieldPrixUnit.setLayoutY(Py+140);


        Labelqtestock.setLayoutX(Px);
        Labelqtestock.setLayoutY(Py+210);
        textFieldqtestock.setLayoutX(Px+70);
        textFieldqtestock.setLayoutY(Py+210);


        buttonPremierF2.setLayoutX(Px);
        buttonPremierF2.setLayoutY(Py+260);

        buttonSuivantF2.setLayoutX(Px+130);
        buttonSuivantF2.setLayoutY(Py+260);

        buttonPrecedentF2.setLayoutX(Px+220);
        buttonPrecedentF2.setLayoutY(Py+260);

        buttonDernierF2.setLayoutX(Px+310);
        buttonDernierF2.setLayoutY(Py+260);

        buttonNouveauF2.setLayoutX(Px+400);
        buttonNouveauF2.setLayoutY(Py+260);

        buttonModifierF2.setLayoutX(Px);
        buttonModifierF2.setLayoutY(Py+330);

        buttonRechercherF2.setLayoutX(Px+70);
        buttonRechercherF2.setLayoutY(Py+330);

        buttonAjouterF2.setLayoutX(Px+140);
        buttonAjouterF2.setLayoutY(Py+330);

        buttonSupprimerF2.setLayoutX(Px+230);
        buttonSupprimerF2.setLayoutY(Py+330);

        buttonQuitterF2.setLayoutX(Px+300);
        buttonQuitterF2.setLayoutY(Py+330);

        // Position des éléments de la section Rechercher (cette section est affichée sur la page 'rootFenViewTable')

        RECH_LabelNumCom.setLayoutX(Px+250);
        RECH_LabelNumCom.setLayoutY(Py+100);

        RECH_comboBoxNumCom.setLayoutX(Px+320);
        RECH_comboBoxNumCom.setLayoutY(Py+100);

        buttonRechercher.setLayoutX(Px+270);
        buttonRechercher.setLayoutY(Py+150);

        
        //  ajout d'element à la fenetre d'ajout de produit
        rootFenAddProd.getChildren().addAll(LabelnumProd,textFieldnumProd,Labeldesignation,textFielddesignation,LabelPrixUnit,textFieldPrixUnit,Labelqtestock,textFieldqtestock,buttonPremierF2,buttonSuivantF2,buttonPrecedentF2,buttonDernierF2,buttonNouveauF2,buttonModifierF2,buttonRechercherF2,buttonAjouterF2,buttonSupprimerF2,buttonQuitterF2);


        // fenetreAddProd.show();  // affichage de la fenetre de produit



             // les éléments constituant l'affichage de la fenetre principale
        int PosX=100;
        int PosY=50;

        // numcl
        Labelnumcl.setLayoutX(PosX);
        Labelnumcl.setLayoutY(PosY);
        textFieldnumCl.setLayoutX(PosX+60);
        textFieldnumCl.setLayoutY(PosY);
        // nom
        Labelnom.setLayoutX(PosX);
        Labelnom.setLayoutY(PosY+70);
        textFieldnom.setLayoutX(PosX+60);
        textFieldnom.setLayoutY(PosY+70);
        // ville
        Labelville.setLayoutX(PosX);
        Labelville.setLayoutY(PosY+130);
        textFieldville.setLayoutX(PosX+60);
        textFieldville.setLayoutY(PosY+130);
        // tel
        Labeltel.setLayoutX(PosX);
        Labeltel.setLayoutY(PosY+190);
        textFieldtel.setLayoutX(PosX+60);
        textFieldtel.setLayoutY(PosY+190);
        // fax
        Labelfax.setLayoutX(PosX);
        Labelfax.setLayoutY(PosY+250);
        textFieldfax.setLayoutX(PosX+60);
        textFieldfax.setLayoutY(PosY+250);
        // email
        Labelmail.setLayoutX(PosX);
        Labelmail.setLayoutY(PosY+310);
        textFieldmail.setLayoutX(PosX+60);
        textFieldmail.setLayoutY(PosY+310);
        
        // les boutons

        buttonPremierF1.setLayoutX(PosX);
        buttonPremierF1.setLayoutY(PosY+370);
        buttonPremierF1.setStyle("-fx-background-color: #FF6347;");

        buttonSuivantF1.setLayoutX(PosX+140);
        buttonSuivantF1.setLayoutY(PosY+370);

        buttonPrecedentF1.setLayoutX(PosX+230);
        buttonPrecedentF1.setLayoutY(PosY+370);

        buttonDernierF1.setLayoutX(PosX+320);
        buttonDernierF1.setLayoutY(PosY+370);

        buttonNouveauF1.setLayoutX(PosX+410);
        buttonNouveauF1.setLayoutY(PosY+370);
        buttonNouveauF1.setStyle("-fx-background-color: #FF6347;");

        buttonModifierF1.setLayoutX(PosX);
        buttonModifierF1.setLayoutY(PosY+420);
        buttonModifierF1.setStyle("-fx-background-color: #015DAF;");

        buttonRechercherF1.setLayoutX(PosX+90);
        buttonRechercherF1.setLayoutY(PosY+420);

        buttonAjouterF1.setLayoutX(PosX+180);
        buttonAjouterF1.setLayoutY(PosY+420);
        buttonAjouterF1.setStyle("-fx-background-color: #01AF0C;");

        buttonSupprimerF1.setLayoutX(PosX+270);
        buttonSupprimerF1.setLayoutY(PosY+420);
        buttonSupprimerF1.setStyle("-fx-background-color: #FC1B00;");


        buttonQuitterF1.setLayoutX(PosX+360);
        buttonQuitterF1.setLayoutY(PosY+420);

        LabelInfoTitreCom.setLayoutX(PosX+510);
        LabelInfoTitreCom.setLayoutY(PosY);
    
        LabelInfoNumCom.setLayoutX(PosX+400);
        LabelInfoNumCom.setLayoutY(PosY+50);

        comboBoxInfoNumCom.setLayoutX(PosX+480);
        comboBoxInfoNumCom.setLayoutY(PosY+50);
    
        LabelInfoMteCom.setLayoutX(PosX+400);
        LabelInfoMteCom.setLayoutY(PosY+100);

        textFieldBoxInfoMteCom.setLayoutX(PosX+560);
        textFieldBoxInfoMteCom.setLayoutY(PosY+100);

        LabelInfoMteTtCom.setLayoutX(PosX+400);
        LabelInfoMteTtCom.setLayoutY(PosY+150);

        textFieldBoxInfoMteTtCom.setLayoutX(PosX+590);
        textFieldBoxInfoMteTtCom.setLayoutY(PosY+150);


        buttontableNDCom.setLayoutX(PosX+430);
        buttontableNDCom.setLayoutY(PosY+220);
        buttontableNDCom.setStyle("-fx-background-color: #AD7822;");


        buttonInprimerFacture.setLayoutX(PosX+430);
        buttonInprimerFacture.setLayoutY(PosY+290);
        buttonInprimerFacture.setStyle("-fx-background-color: #E5CCA5;");



         buttonimprimerb.setLayoutX(PosX+430);
         buttonimprimerb.setLayoutY(PosY+360);
         buttonimprimerb.setStyle("-fx-background-color: #E5CCA5;");



         buttonimprimerc.setLayoutX(PosX+430);
         buttonimprimerc.setLayoutY(PosY+390);
         buttonimprimerc.setStyle("-fx-background-color: #E5CCA5;");



         buttonimprimerd.setLayoutX(PosX+430);
         buttonimprimerd.setLayoutY(PosY+450);
         buttonimprimerd.setStyle("-fx-background-color: #E5CCA5;");


         buttonimprimere.setLayoutX(PosX+430);
         buttonimprimere.setLayoutY(PosY+490);
         buttonimprimere.setStyle("-fx-background-color: #E5CCA5;");






        // Positionnement des tables avec setLayoutX et setLayoutY

        tableClient.setLayoutX(PosX-20); // Position horizontale
        tableClient.setLayoutY(PosY); // Position verticale

        tableCommande.setLayoutX(PosX+100); // Position horizontale
        tableCommande.setLayoutY(PosY); // Position verticale

        // ce bouton est placé ici parce que c'est lui qui doit nous amener à la section de recherche 
        // ... donc il doit être placé sur la meme page qui la tableau des commandes

        buttonOuvreRechercher.setLayoutX(0);
        buttonOuvreRechercher.setLayoutY(PosY+200);

        tableProduit.setLayoutX(PosX+30); // Position horizontale
        tableProduit.setLayoutY(PosY); // Position verticale

        tableNDCom.setLayoutX(PosX+100); // Position horizontale
        tableNDCom.setLayoutY(PosY); // Position verticale



        // Positionnement des éléments de la fenetre de commande

        Com_LabelNumCl.setLayoutX(PosX);
        Com_LabelNumCl.setLayoutY(PosY);
        Com_comboBoxNumCl.setLayoutX(PosX+60);
        Com_comboBoxNumCl.setLayoutY(PosY);

        Com_LabelnomClient.setLayoutX(PosX);
        Com_LabelnomClient.setLayoutY(PosY+70);
        Com_textFieldnomClient.setLayoutX(PosX+60);
        Com_textFieldnomClient.setLayoutY(PosY+70);

        Com_LabeltelClient.setLayoutX(PosX);
        Com_LabeltelClient.setLayoutY(PosY+140);
        Com_textFieldtelClient.setLayoutX(PosX+60);
        Com_textFieldtelClient.setLayoutY(PosY+140);

        Com_LabelNumProd.setLayoutX(PosX);
        Com_LabelNumProd.setLayoutY(PosY+210);
        Com_comboBoxNumProd.setLayoutX(PosX+60);
        Com_comboBoxNumProd.setLayoutY(PosY+210);

        Com_LabelDesignation.setLayoutX(PosX);
        Com_LabelDesignation.setLayoutY(PosY+300);
        Com_textFieldDesignation.setLayoutX(PosX+75);
        Com_textFieldDesignation.setLayoutY(PosY+300);

        Com_LabelprixUnit.setLayoutX(PosX+400);
        Com_LabelprixUnit.setLayoutY(PosY);
        Com_textFieldprixUnit.setLayoutX(PosX+470);
        Com_textFieldprixUnit.setLayoutY(PosY);

        Com_LabelQte.setLayoutX(PosX+400);
        Com_LabelQte.setLayoutY(PosY+60);
        Com_textFieldQte.setLayoutX(PosX+470);
        Com_textFieldQte.setLayoutY(PosY+60);

        Com_LabelPrixTotal.setLayoutX(PosX+400);
        Com_LabelPrixTotal.setLayoutY(PosY+120);
        Com_textFieldPrixTotal.setLayoutX(PosX+470);
        Com_textFieldPrixTotal.setLayoutY(PosY+120);

        Com_LabelnumCom.setLayoutX(PosX+400);
        Com_LabelnumCom.setLayoutY(PosY+180);
        Com_textFieldnumCom.setLayoutX(PosX+470);
        Com_textFieldnumCom.setLayoutY(PosY+180);

        Com_LabelnumLigneDetailsCom.setLayoutX(PosX+400);
        Com_LabelnumLigneDetailsCom.setLayoutY(PosY+240);
        Com_textFieldnumLigneDetailsCom.setLayoutX(PosX+530);
        Com_textFieldnumLigneDetailsCom.setLayoutY(PosY+240);

        buttonValider.setLayoutX(PosX+450);
        buttonValider.setLayoutY(PosY+290);


        // Positionnement des boutons d'impression

        buttonImprimerClient.setLayoutX(0);
        buttonImprimerClient.setLayoutY(5);

        buttonImprimerCommande.setLayoutX(0);
        buttonImprimerCommande.setLayoutY(PosY+70);

        buttonImprimerProduit.setLayoutX(0);
        buttonImprimerProduit.setLayoutY(PosY+200);


                // remplissage des combobox  de la page de commande

        // Remplir le ComboBox du Com_comboBoxNumCl  avec les identifiants récupérés
        Com_comboBoxNumCl.getItems().addAll(identifiantsClients);
        
        // Remplir le ComboBox du Com_comboBoxNumProd  avec les identifiants récupérés
        Com_comboBoxNumProd.getItems().addAll(identifiantsProduits);

        // Remplir le ComboBox du comboBoxInfoNumCom  avec les identifiants récupérés
        comboBoxInfoNumCom.getItems().addAll(identifiantsCommandes);

        // Remplir le ComboBox de la section "Rechercher"  avec les identifiants récupérés
        RECH_comboBoxNumCom.getItems().addAll(identifiantsCommandes);



        // Ajout des éléments à la fenetre de commande

        rootFenCom.getChildren().addAll(Com_LabelNumCl,Com_comboBoxNumCl,Com_LabelnomClient,Com_textFieldnomClient,Com_LabeltelClient,Com_textFieldtelClient,Com_LabelNumProd,Com_comboBoxNumProd,Com_LabelDesignation,Com_textFieldDesignation,Com_LabelprixUnit,Com_textFieldprixUnit,Com_LabelQte,Com_textFieldQte,Com_LabelPrixTotal,Com_textFieldPrixTotal,Com_LabelnumCom,Com_textFieldnumCom,Com_LabelnumLigneDetailsCom,Com_textFieldnumLigneDetailsCom,buttonValider);

        // evenements

        buttonPremierF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numClient = 1;
                chargerEnreg(numClient, 0);
            }
        });
        buttonPrecedentF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numClient = Integer.parseInt(textFieldnumCl.getText());
                chargerEnreg(numClient, -1);
            }
        });
        buttonSuivantF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numClient = Integer.parseInt(textFieldnumCl.getText());
                chargerEnreg(numClient, 1);
            }
        });
        buttonDernierF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numClient = nbTotalEnreg();
                chargerEnreg(numClient, 2);
            }
        });
        

        buttonRechercherF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numClient = Integer.parseInt(textFieldnumCl.getText());
                try {
                    rechercherClient(numClient);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        
        buttonNouveauF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour réinitialiser les champs de texte
                nouveauClient();
            }
        });
        
        buttonAjouterF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour ajouter un nouveau client
                ajouterClient();
            }
        });
        
        buttonModifierF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour modifier un client existant
                modifierClient();
                
            }
        });
        
        buttonSupprimerF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour supprimer un client
                int numClient = Integer.parseInt(textFieldnumCl.getText());
                supprimerClient(numClient);
            }
        });
        
        buttonQuitterF1.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour quitter l'application
                System.exit(0);
            }
        });

        buttontableNDCom.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                //  remplir la tableau pour afficher les dates et numero des commandes d'un client :tableNDCom

                List<List<String>> commandestableNDCom = listeCom(Integer.parseInt(textFieldnumCl.getText()));

                // Vider le contenu du tableau avant d'ajouter les nouvelles valeurs
                tableNDCom.getItems().clear();
                tableNDCom.getColumns().clear();
                
                // Ajouter les colonnes à la TableView
                for (int i = 0; i < commandestableNDCom.get(0).size(); i++) {
                    TableColumn<List<String>, String> colonneCommande = new TableColumn<>(i == 0 ? "Numéro de commande" : "Date");
                    final int columnIndexCommande = i;
                    colonneCommande.setCellValueFactory(cellData -> {
                        List<String> rowDataCommande = cellData.getValue();
                        return new SimpleStringProperty(rowDataCommande.get(columnIndexCommande));
                    });
                    tableNDCom.getColumns().add(colonneCommande);
                }
                
                // Ajouter les lignes de données à la TableView
                commandestableNDCom.forEach(commande -> tableNDCom.getItems().add(commande));
                

                rootFenViewTable.getChildren().clear();
                rootFenViewTable.getChildren().add(tableNDCom);
                fenetreViewTable.show();

            }
        });


        // buttonInprimerFacture.setOnAction(new EventHandler<ActionEvent>() {
        //     public void handle(ActionEvent event) {



        //     }
        // });

        buttonOuvreRechercher.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                rootFenViewTable.getChildren().clear();
                rootFenViewTable.getChildren().addAll(RECH_LabelNumCom,RECH_comboBoxNumCom,buttonRechercher);
                fenetreViewTable.show();
            }
        });


        buttonRechercher.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

            // Afficher un message de succès

            Alert alertInfo = new Alert(AlertType.INFORMATION);
            alertInfo.setTitle("Informations");
            alertInfo.setHeaderText(null);
            // appele de la fonction de recherche,puis on la passe pour l'affichage
            alertInfo.setContentText(trouverNom(Integer.parseInt(RECH_comboBoxNumCom.getValue())));
            alertInfo.showAndWait();

            }
        });



        // Définition des actions pour les éléments du menu Fichier
        menuQuitter.setOnAction(event -> {
            System.exit(0);
        });

        // Définition des actions pour les éléments du menu Navigation
        menuNPremier.setOnAction(event -> {
            int numClient = 1;
            chargerEnreg(numClient, 0);
        });

        menuNPrecedent.setOnAction(event -> {
            int numClient = Integer.parseInt(textFieldnumCl.getText());
            chargerEnreg(numClient, -1);
        });

        menuNSuivant.setOnAction(event -> {
            int numClient = Integer.parseInt(textFieldnumCl.getText());
            chargerEnreg(numClient, 1);
        });

        menuNDernier.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numClient = nbTotalEnreg();
                chargerEnreg(numClient, 2);
            }
        });

        // definition des évenements des  éléments pour le menu Produit
        menuNProduit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                fenetreAddProd.show();  // affichage de la fenetre de produit
                int numProduit = 1; // on charge le 1er enreg au depart
                chargerEnreg(numProduit, 0);
            }
        });

        menuNCommande.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                fenetreCommande.show();
            }
        });

        // definition des évenements des  éléments pour la fenêtre d'affichage des tables
        menuNclient.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                rootFenViewTable.getChildren().clear();
                rootFenViewTable.getChildren().addAll(tableClient,buttonImprimerClient);
                fenetreViewTable.show();


            }
        });

        menuNcommande.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                
                rootFenViewTable.getChildren().clear();
                rootFenViewTable.getChildren().addAll(tableCommande,buttonOuvreRechercher,buttonImprimerCommande);
                fenetreViewTable.show();
            }
        });

        menuNproduit.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                rootFenViewTable.getChildren().clear();
                rootFenViewTable.getChildren().addAll(tableProduit,buttonImprimerProduit);
                fenetreViewTable.show();
            }
        });

        comboBoxInfoNumCom.setOnAction(event -> {
            int numCom = Integer.parseInt(comboBoxInfoNumCom.getValue());
            textFieldBoxInfoMteCom.setText(String.valueOf(montantComCl(Integer.parseInt(textFieldnumCl.getText()), numCom)));

        });


        // evenement des boutons de la page d'ajout de produit

        buttonPremierF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numProduit = 1;
                chargerEnregProduit(numProduit, 0);
            }
        });
        buttonPrecedentF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numProduit = Integer.parseInt(textFieldnumProd.getText());
                chargerEnregProduit(numProduit, -1);
            }
        });
        buttonSuivantF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numProduit = Integer.parseInt(textFieldnumProd.getText());
                chargerEnregProduit(numProduit, 1);
            }
        });
        buttonDernierF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numProduit = nbTotalEnregProduit();
                chargerEnregProduit(numProduit, 2);
            }
        });

        buttonRechercherF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                int numProduit = Integer.parseInt(textFieldnumProd.getText());
                try {
                    rechercherProduit(numProduit);
                } catch (FileNotFoundException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        });
        
        buttonNouveauF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour réinitialiser les champs de texte
                nouveauProduit();
            }
        });
        
        buttonAjouterF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour ajouter un nouveau client
                ajouterProduit();
            }
        });
        
        buttonModifierF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour modifier un client existant
                modifierProduit();
                
            }
        });
        
        buttonSupprimerF2.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                // Appel de la méthode pour supprimer un client
                int numProduit = Integer.parseInt(textFieldnumProd.getText());
                supprimerProduit(numProduit);
            }
        });
        
        // buttonQuitterF2.setOnAction(new EventHandler<ActionEvent>() {
        //     public void handle(ActionEvent event) {
        //         // Appel de la méthode pour quitter l'application
        //         System.exit(0);
        //     }
        // });

        // definition des evenements de la page des commandes

        Com_comboBoxNumCl.setOnAction(event -> {
            String numCl = Com_comboBoxNumCl.getValue();
            afficherInfosClient(numCl, Com_textFieldnomClient, Com_textFieldtelClient);
        });

        Com_comboBoxNumProd.setOnAction(event -> {
            String numProd = Com_comboBoxNumProd.getValue();
            afficherInfosProduit(numProd, Com_textFieldDesignation, Com_textFieldprixUnit);
        });
        
        Com_textFieldQte.textProperty().addListener((observable, oldValue, newValue) -> {
            try {
                // Vérifie que le nouveau texte est un nombre valide
                double quantite = Double.parseDouble(newValue);
                System.out.println("Quantité convertie en double: " + quantite);
        
                // Tente de récupérer le prix unitaire à partir de l'autre TextField
                double prixUnitaire = Double.parseDouble(Com_textFieldprixUnit.getText());
        
                // Calcule le prix total
                double prixTotal = quantite * prixUnitaire;
                Com_textFieldPrixTotal.setText(String.format("%.2f", prixTotal)); // Formate le résultat avec deux décimales
            } catch (NumberFormatException e) {
                System.out.println("Erreur de format: l'un des textes n'est pas un nombre valide");
                Com_textFieldPrixTotal.setText(""); // Efface le champ du prix total s'il y a une erreur
            }
        });

        buttonValider.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                
                // enregistrerCommande(String numCl, String numProd,String numCom,String numLigne, int qteCom);
                enregistrerCommande(Integer.parseInt(Com_comboBoxNumCl.getValue()), Integer.parseInt(Com_comboBoxNumProd.getValue()), 
                Integer.parseInt(Com_textFieldnumCom.getText()), Integer.parseInt(Com_textFieldnumLigneDetailsCom.getText()), 
                Integer.parseInt(Com_textFieldQte.getText()));


            }
        });

        buttonImprimerClient.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
                EtatClient();
            }
        });
        buttonImprimerCommande.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
                EtatCommande();
            }
        });

        buttonInprimerFacture.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
                EtatFacture(Integer.parseInt(textFieldnumCl.getText()));
            }
        });
            



          buttonimprimerb.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
              EtatmontantCommande(Integer.parseInt(textFieldnumCl.getText()),Integer.parseInt(comboBoxInfoNumCom.getValue()));
            }
        });

        buttonimprimerc.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
              EtatmontantTotal(Integer.parseInt(textFieldnumCl.getText()));
            }
        });

        buttonimprimerd.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
               EtatListeCommande(Integer.parseInt(textFieldnumCl.getText()));
            }
        });

        buttonimprimere.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
            
               EtattrouverNom(Integer.parseInt(comboBoxInfoNumCom.getValue()));
            }
        });  





             //................. ajout des elements dans les tableaux 

        // Récupérer les données de la table 'client'
        List<List<String>> donneesClient = afficher("client");

        // Ajouter les colonnes à la TableView
        for (int i = 0; i < donneesClient.get(0).size(); i++) {
            TableColumn<List<String>, String> colonneClient = new TableColumn<>(donneesClient.get(0).get(i));
            final int columnIndexClient = i;
            colonneClient.setCellValueFactory(cellData -> {
                List<String> rowDataClient = cellData.getValue();
                return new SimpleStringProperty(rowDataClient.get(columnIndexClient));
            });
            tableClient.getColumns().add(colonneClient);
        }

        // Ajouter les lignes de données à la TableView
        donneesClient.remove(0); // Supprimer la première ligne (noms de colonnes)
        tableClient.getItems().addAll(donneesClient);



        // Récupérer les données de la table 'commande'
        List<List<String>> donneesCommande = afficher("commande");

        // Ajouter les colonnes à la TableView
        for (int i = 0; i < donneesCommande.get(0).size(); i++) {
            TableColumn<List<String>, String> colonneCommande = new TableColumn<>(donneesCommande.get(0).get(i));
            final int columnIndexCommande = i;
            colonneCommande.setCellValueFactory(cellData -> {
                List<String> rowDataCommande = cellData.getValue();
                return new SimpleStringProperty(rowDataCommande.get(columnIndexCommande));
            });
            tableCommande.getColumns().add(colonneCommande);
        }

        // Ajouter les lignes de données à la TableView
        donneesCommande.remove(0); // Supprimer la première ligne (noms de colonnes)
        tableCommande.getItems().addAll(donneesCommande);



        // Récupérer les données de la table 'produit'
        List<List<String>> donneesProduit = afficher("produit");

        // Ajouter les colonnes à la TableView
        for (int i = 0; i < donneesProduit.get(0).size(); i++) {
            TableColumn<List<String>, String> colonneProduit = new TableColumn<>(donneesProduit.get(0).get(i));
            final int columnIndexProduit = i;
            colonneProduit.setCellValueFactory(cellData -> {
                List<String> rowDataProduit = cellData.getValue();
                return new SimpleStringProperty(rowDataProduit.get(columnIndexProduit));
            });
            tableProduit.getColumns().add(colonneProduit);
        }

        // Ajouter les lignes de données à la TableView
        donneesProduit.remove(0); // Supprimer la première ligne (noms de colonnes)
        tableProduit.getItems().addAll(donneesProduit);




        // menu

        menuBar.getMenus().addAll(menuFichier,menuNavigation,menuOcommande,menuTables);

        // Ajout des éléments dans le menu Fichier
        menuFichier.getItems().add(menuQuitter);
        
        // Ajout des éléments dans le menu Navigation
        menuNavigation.getItems().addAll(menuNPremier,menuNPrecedent,menuNSuivant,menuNDernier);

        // Ajout des éléments dans le menu Options commande
        menuOcommande.getItems().addAll(menuNProduit,menuNCommande);

        // Ajout des éléments dans le menu Options commande
        menuTables.getItems().addAll(menuNclient,menuNcommande,menuNproduit);

        // affichage des éléments 
        
        root.getChildren().addAll(Labelnumcl,Labelnom,Labelville,Labeltel,Labelfax,Labelmail,textFieldnumCl,textFieldnom,textFieldville,textFieldtel,textFieldfax,textFieldmail,buttonPremierF1,buttonPrecedentF1,buttonSuivantF1,buttonDernierF1,buttonNouveauF1,buttonAjouterF1,buttonSupprimerF1,buttonQuitterF1,buttonModifierF1,buttonRechercherF1,buttonProdF1,menuBar,LabelInfoTitreCom,LabelInfoNumCom,comboBoxInfoNumCom,LabelInfoMteCom,textFieldBoxInfoMteCom,LabelInfoMteTtCom,textFieldBoxInfoMteTtCom,buttontableNDCom,buttonInprimerFacture,buttonimprimerb,buttonimprimerc,buttonimprimerd,buttonimprimere);


        primaryStage.setScene(scene);
        
        // Afficher la fenêtre principale
        primaryStage.show();
        int numClient = 1; // on charge le 1er enreg au depart
        chargerEnreg(numClient, 0);
        
    }



    // fonction principale
    public static void main(String[] args) {
        launch(args);
    }



    // les fonctions
    public int numeroEnreg(int numClient) {
        int numEnreg = 0;
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM Client ;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numEnreg++;
                int numCl = rs.getInt(1);
                if (numCl == numClient)
                    break;
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception sqlExcptn) {
            System.out.println(sqlExcptn);
        }

        return numEnreg;
    }

    public static int trouverCleEnreg(int numLog) {
        int numEnreg = 0;
        int numCl = 0;
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM Client ;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numEnreg++;
                if (numEnreg == numLog) {
                    numCl = rs.getInt(1);
                    break;
                }
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception sqlExcptn) {
            System.out.println(sqlExcptn);
        }
        return numCl;
    }

    public int nbTotalEnreg() {
        int dernierNumClient = 0;
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            String sql = "SELECT MAX(numCl) FROM Client;";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                dernierNumClient = rs.getInt(1);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception sqlExcptn) {
            System.out.println(sqlExcptn);
        }
        return dernierNumClient;
    }
    
    // fonction qui permet de charger un enregistrement sur la page d'affichage
    public void chargerEnreg(int numClient, int sens) {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            ResultSet rs = null;
            
            if (sens == 0) { // Chargement du premier enregistrement
                rs = st.executeQuery("SELECT * FROM Client ORDER BY numCl ASC LIMIT 1");
            } else if (sens == 1) { // Chargement de l'enregistrement suivant
                rs = st.executeQuery("SELECT * FROM Client WHERE numCl > " + numClient + " ORDER BY numCl ASC LIMIT 1");
            } else if (sens == -1) { // Chargement de l'enregistrement précédent
                rs = st.executeQuery("SELECT * FROM Client WHERE numCl < " + numClient + " ORDER BY numCl DESC LIMIT 1");
            } else { // Chargement du dernier enregistrement
                rs = st.executeQuery("SELECT * FROM Client ORDER BY numCl DESC LIMIT 1");  
            }
            
            if (rs.next()) {
                textFieldnumCl.setText(String.valueOf(rs.getInt("numCl")));
                textFieldnom.setText(rs.getString("nom"));
                textFieldville.setText(rs.getString("ville"));
                textFieldtel.setText(String.valueOf(rs.getInt("tel")));
                textFieldfax.setText(String.valueOf(rs.getInt("fax")));
                textFieldmail.setText(rs.getString("email"));
                listeCom(Integer.parseInt(textFieldnumCl.getText()));
                //  affichage du montant total des commandes du client en cours
                textFieldBoxInfoMteTtCom.setText(String.valueOf(montantTComCl(Integer.parseInt(textFieldnumCl.getText()))));
            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    


    
    public void ajouterClient() {
        // Vérifier si les champs obligatoires sont vides
        if (textFieldnumCl.getText().isEmpty() || textFieldnom.getText().isEmpty() || textFieldville.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs obligatoires.");
            alert.showAndWait();
            return;
        }
    
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Client (numCl, nom, ville, tel, fax, email) VALUES (?, ?, ?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(textFieldnumCl.getText()));
            ps.setString(2, textFieldnom.getText());
            ps.setString(3, textFieldville.getText());
            ps.setInt(4, (textFieldtel.getText().isEmpty() ? 0 : Integer.parseInt(textFieldtel.getText())));
            ps.setInt(5, (textFieldfax.getText().isEmpty() ? 0 : Integer.parseInt(textFieldfax.getText())));
            ps.setString(6, textFieldmail.getText());
            ps.executeUpdate();
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Client ajouté avec succès.Veuillez svp relancer l'application pour prendre en compte les modifications");
            alert.showAndWait();
            
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du client : " + e.getMessage());
            alert.showAndWait();
        }
    }
    

    public void nouveauClient() {
        // Effacez ou réinitialisez les champs de texte pour permettre à l'utilisateur d'ajouter un nouveau client
        textFieldnumCl.clear();
        textFieldnom.clear();
        textFieldville.clear();
        textFieldtel.clear();
        textFieldfax.clear();
        textFieldmail.clear();
        textFieldBoxInfoMteCom.clear();
        textFieldBoxInfoMteTtCom.clear();
        
    }
    
    

    public void supprimerClient(int numClient) {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Client WHERE numCl = ?");
            ps.setInt(1, numClient);
            ps.executeUpdate();
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Client supprimé avec succès.Veuillez svp relancer l'application pour prendre en compte les modifications");
            alert.showAndWait();
            
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la suppression du client : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void modifierClient() {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("UPDATE Client SET nom = ?, ville = ?, tel = ?, fax = ?, email = ? WHERE numCl = ?");
            ps.setString(1, textFieldnom.getText());
            ps.setString(2, textFieldville.getText());
            ps.setInt(3, Integer.parseInt(textFieldtel.getText()));
            ps.setInt(4, Integer.parseInt(textFieldfax.getText()));
            ps.setString(5, textFieldmail.getText());
            ps.setInt(6, Integer.parseInt(textFieldnumCl.getText())); // Récupérer le numéro de client saisi par l'utilisateur
            ps.executeUpdate();
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Client modifié avec succès.");
            alert.showAndWait();
            
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la modification du client : " + e.getMessage());
            alert.showAndWait();
        }
    }


    public void rechercherClient(int numClient) throws FileNotFoundException {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Client WHERE numCl = ?");
            ps.setInt(1, numClient);
            ResultSet rs = ps.executeQuery();
            
            // Vérifier si le client avec le numéro spécifié existe
            if (rs.next()) {
                textFieldnom.setText(rs.getString("nom"));
                textFieldville.setText(rs.getString("ville"));
                textFieldtel.setText(String.valueOf(rs.getInt("tel")));
                textFieldfax.setText(String.valueOf(rs.getInt("fax")));
                textFieldmail.setText(rs.getString("email"));
                
            } else {
                // Afficher un message d'avertissement si le client n'est pas trouvé
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("Aucun client trouvé avec le numéro spécifié.");
                alert.showAndWait();
            }
            
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'erreur avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la recherche du client : " + e.getMessage());
            alert.showAndWait();
        }
    }
    

    // fonction specifique à la fenetre d'ajout de produit
    // les fonctions
    public int numeroEnregProduit(int numProd) {
        int numEnreg = 0;
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM Produit ;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numEnreg++;
                int numP = rs.getInt(1);
                if (numP == numProd)
                    break;
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception sqlExcptn) {
            System.out.println(sqlExcptn);
        }

        return numEnreg;
    }

    public static int trouverCleEnregProduit(int numLog) {
        int numEnreg = 0;
        int numP = 0;
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            String sql = "SELECT * FROM Produit ;";
            ResultSet rs = st.executeQuery(sql);
            while (rs.next()) {
                numEnreg++;
                if (numEnreg == numLog) {
                    numP = rs.getInt(1);
                    break;
                }
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception sqlExcptn) {
            System.out.println(sqlExcptn);
        }
        return numP;
    }

    public int nbTotalEnregProduit() {
        int nbTEnreg = 0;
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            String sql = "SELECT count(numProd) FROM Produit ;";
            ResultSet rs = st.executeQuery(sql);
            if (rs.next()) {
                nbTEnreg = rs.getInt(1);
            }
            rs.close();
            st.close();
            conn.close();
        } catch (Exception sqlExcptn) {
            System.out.println(sqlExcptn);
        }
        return nbTEnreg;
    }

    public void chargerEnregProduit(int numProduit, int sens) {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement st = conn.createStatement();
            ResultSet rs = null;
            
            if (sens == 0) { // Chargement du premier enregistrement
                rs = st.executeQuery("SELECT * FROM Produit ORDER BY numProd ASC LIMIT 1");
            } else if (sens == 1) { // Chargement de l'enregistrement suivant
                rs = st.executeQuery("SELECT * FROM Produit WHERE numProd > " + numProduit + " ORDER BY numProd ASC LIMIT 1");
            } else if (sens == -1) { // Chargement de l'enregistrement précédent
                rs = st.executeQuery("SELECT * FROM Produit WHERE numProd < " + numProduit + " ORDER BY numProd DESC LIMIT 1");
            } else { // Chargement du dernier enregistrement
                rs = st.executeQuery("SELECT * FROM Produit ORDER BY numProd DESC LIMIT 1");  
            }
            
            if (rs.next()) {
                textFieldnumProd.setText(String.valueOf(rs.getInt("numProd")));
                textFielddesignation.setText(rs.getString("designation"));
                textFieldPrixUnit.setText(String.valueOf(rs.getInt("prixUnit")));
                textFieldqtestock.setText(String.valueOf(rs.getInt("qteStock")));

            }
            
            rs.close();
            st.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    


    
    public void ajouterProduit() {
        // Vérifier si les champs obligatoires sont vides
        if (textFieldnumProd.getText().isEmpty() || textFielddesignation.getText().isEmpty() || textFieldPrixUnit.getText().isEmpty() || textFieldqtestock.getText().isEmpty()) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Veuillez remplir les champs obligatoires.");
            alert.showAndWait();
            return;
        }
    
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("INSERT INTO Produit (numProd, designation, prixUnit, qteStock) VALUES (?, ?, ?, ?)");
            ps.setInt(1, Integer.parseInt(textFieldnumProd.getText()));
            ps.setString(2, textFielddesignation.getText());
            ps.setInt(3, (textFieldPrixUnit.getText().isEmpty() ? 0 : Integer.parseInt(textFieldPrixUnit.getText())));
            ps.setInt(4, (textFieldqtestock.getText().isEmpty() ? 0 : Integer.parseInt(textFieldqtestock.getText())));
            ps.executeUpdate();
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Produit ajouté avec succès.Veuillez svp relancer l'application pour prendre en compte les modifications");
            alert.showAndWait();
            
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout du Produit : " + e.getMessage());
            alert.showAndWait();
        }
    }
    

    public void nouveauProduit() {
        // Effacez ou réinitialisez les champs de texte pour permettre à l'utilisateur d'ajouter un nouveau client
        textFieldnumProd.clear();
        textFielddesignation.clear();
        textFieldPrixUnit.clear();
        textFieldqtestock.clear();
        
    }
    
    

    public void supprimerProduit(int numProduit) {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("DELETE FROM Produit WHERE numProd = ?");
            ps.setInt(1, numProduit);
            ps.executeUpdate(); 
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Produit supprimé avec succès.Veuillez svp relancer l'application pour prendre en compte les modifications");
            alert.showAndWait();
            
            ps.close();
            conn.close();
            
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la suppression du produit : " + e.getMessage());
            alert.showAndWait();
        }
    }

    public void modifierProduit() {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("UPDATE Produit SET designation = ?, prixUnit = ?, qteStock = ? WHERE numProd = ?");
            ps.setString(1, textFielddesignation.getText());
            ps.setInt(2, Integer.parseInt(textFieldPrixUnit.getText()));
            ps.setInt(3, Integer.parseInt(textFieldqtestock.getText()));
            ps.setInt(4, Integer.parseInt(textFieldnumProd.getText())); // Récupérer le numéro de produit saisi par l'utilisateur
            ps.executeUpdate();
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Produit modifié avec succès.");
            alert.showAndWait();
            
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la modification du Produit : " + e.getMessage());
            alert.showAndWait();
        }
    }


    public void rechercherProduit(int numProduit) throws FileNotFoundException {
        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM Produit WHERE numProd = ?");
            ps.setInt(1, numProduit);
            ResultSet rs = ps.executeQuery();
            
            // Vérifier si le produit avec le numéro spécifié existe
            if (rs.next()) {
                textFielddesignation.setText(rs.getString("designation"));
                textFieldPrixUnit.setText(String.valueOf(rs.getInt("prixUnit")));
                textFieldqtestock.setText(String.valueOf(rs.getInt("qteStock")));
                
            } else {
                // Afficher un message d'avertissement si le client n'est pas trouvé
                Alert alert = new Alert(AlertType.WARNING);
                alert.setTitle("Avertissement");
                alert.setHeaderText(null);
                alert.setContentText("Aucun produit trouvé avec le numéro spécifié.");
                alert.showAndWait();
            }
            
            rs.close();
            ps.close();
            conn.close();
        } catch (SQLException e) {
            // Afficher une boîte de dialogue d'erreur avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de la recherche du produit : " + e.getMessage());
            alert.showAndWait();
        }
    }

    // fonction utile pour remplir le combox de la page de commande avec les ID d une table
    public static List<String> getIdentifiantsTable(String nomTable, String nomColonne) {
        List<String> identifiants = new ArrayList<>();

        try {
            // Établir la connexion à la base de données
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT " + nomColonne + " FROM " + nomTable);

            // Parcourir le résultat de la requête et ajouter les identifiants à la liste
            while (rs.next()) {
                identifiants.add(rs.getString(nomColonne));
            }

            // Fermer les ressources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return identifiants;
    }


    // fonction pour afficher le nom et le prenom du client lorsque la valeur du combobox numcl de la page commande est selectionné
    public static void afficherInfosClient(String numCl, TextField textFieldnomClient, TextField textFieldtelClient) {
        try {
            // Établir la connexion à la base de données
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT nom, tel FROM client WHERE numCl = '" + numCl + "'");

            // Vérifier s'il y a des résultats
            if (rs.next()) {
                // Récupérer les informations du client
                String nomClient = rs.getString("nom");
                String telClient = rs.getString("tel");

                // Afficher les informations dans les champs TextField
                textFieldnomClient.setText(nomClient);
                textFieldtelClient.setText(telClient);
            } else {
                // Effacer les champs si aucun client correspondant n'est trouvé
                textFieldnomClient.clear();
                textFieldtelClient.clear();
            }

            // Fermer les ressources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // fonction pour afficher la designation et le prix du produit lorsque la valeur du combobox numprod de la page commande est selectionné
    public static void afficherInfosProduit(String numProd, TextField textFielddesignation, TextField textFieldprixUnit) {
        try {
            // Établir la connexion à la base de données
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT designation, prixUnit FROM produit WHERE numProd = '" + numProd + "'");

            // Vérifier s'il y a des résultats
            if (rs.next()) {
                // Récupérer les informations du client
                String designation = rs.getString("designation");
                String prixUnit = rs.getString("prixUnit");

                // Afficher les informations dans les champs TextField
                textFielddesignation.setText(designation);
                textFieldprixUnit.setText(prixUnit);
            } else {
                // Effacer les champs si aucun produit correspondant n'est trouvé
                textFielddesignation.clear();
                textFieldprixUnit.clear();
            }

            // Fermer les ressources
            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void enregistrerCommande(int numCl, int numProd, int numCom, int numLigne, int qteCom) {
        Connection conn = null;
        try {
            // Établir la connexion à la base de données
            conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            conn.setAutoCommit(false); // Début de la transaction
            
            // Date de la commande (date système)
            LocalDate dateCom = LocalDate.now();
    
            // Insertion dans la table commande
            String commandeQuery = "INSERT INTO commande (numCom, dateCom, numCl) VALUES (?, ?, ?)";
            PreparedStatement commandeStmt = conn.prepareStatement(commandeQuery);
            commandeStmt.setInt(1, numCom);
            commandeStmt.setDate(2, java.sql.Date.valueOf(dateCom));
            commandeStmt.setInt(3, numCl);
            int rowsInserted = commandeStmt.executeUpdate();
    
            // Vérifier si une ligne a été insérée dans la table commande
            if (rowsInserted != 1) {
                throw new SQLException("Erreur lors de l'ajout de la commande : aucune ligne insérée dans la table commande.");
            }
    
            // Insertion dans la table detailsCom
            String detailsQuery = "INSERT INTO detailsCom (numLigne, qteCom, numCom, numProd) VALUES (?, ?, ?, ?)";
            PreparedStatement detailsStmt = conn.prepareStatement(detailsQuery);
            detailsStmt.setInt(1, numLigne);
            detailsStmt.setInt(2, qteCom);
            detailsStmt.setInt(3, numCom);
            detailsStmt.setInt(4, numProd);
            detailsStmt.executeUpdate();
            
            conn.commit(); // Valider la transaction
            
            // Afficher un message de succès
            Alert alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Succès");
            alert.setHeaderText(null);
            alert.setContentText("Commande effectuée avec succès.Veuillez svp relancer l'application pour prendre en compte les modifications");
            alert.showAndWait();
        } catch (SQLException e) {
            // En cas d'erreur, annuler la transaction
            try {
                if (conn != null) {
                    conn.rollback();
                }
            } catch (SQLException rollbackException) {
                rollbackException.printStackTrace();
            }
            
            // Afficher une boîte de dialogue d'alerte avec le message d'erreur
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Erreur");
            alert.setHeaderText(null);
            alert.setContentText("Erreur lors de l'ajout de la commande : " + e.getMessage());
            alert.showAndWait();
        } finally {
            // Fermer les ressources
            try {
                if (conn != null) {
                    conn.setAutoCommit(true); // Rétablir le mode de commit automatique
                    conn.close();
                }
            } catch (SQLException closeException) {
                closeException.printStackTrace();
            }
        }
    }
    

    public static List<List<String>> afficher(String nomTable) {
        List<List<String>> donnees = new ArrayList<>();

        try {
            Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM " + nomTable);

            ResultSetMetaData metaData = rs.getMetaData();
            int numColumns = metaData.getColumnCount();

            // Ajouter les noms de colonnes à la première ligne
            List<String> nomColonnes = new ArrayList<>();
            for (int i = 1; i <= numColumns; i++) {
                nomColonnes.add(metaData.getColumnName(i));
            }
            donnees.add(nomColonnes);

            // Ajouter les données
            while (rs.next()) {
                List<String> ligne = new ArrayList<>();
                for (int i = 1; i <= numColumns; i++) {
                    ligne.add(rs.getString(i));
                }
                donnees.add(ligne);
            }

            rs.close();
            stmt.close();
            conn.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return donnees;
    }


    // fonction pour calculer le montant total de la commande pour un client donné
    public static double montantComCl(int numCl, int numCom) {
        double montantTotal = 0;
    
        // SQL pour calculer le montant total de la commande pour un client donné
        String sql = "SELECT SUM(d.qteCom * p.prixUnit) AS montantTotal " +
                        "FROM detailsCom d " +
                        "JOIN produit p ON d.numProd = p.numProd " +
                        "WHERE d.numCom = ? AND EXISTS (" +
                        "    SELECT 1 FROM commande c WHERE c.numCom = d.numCom AND c.numCl = ?" +
                        ")";
    
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, numCom);
            stmt.setInt(2, numCl);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    montantTotal = rs.getDouble("montantTotal");
                } else {
                    return -1; // Aucune donnée trouvée
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du montant total : " + e.getMessage());
            return -1; // En cas d'erreur
        }
    
        return montantTotal;
    }
    
    
    // fonction pour calculer le montant total de toutes les commandes pour un client donné
    public static double montantTComCl(int numCl) {
        double montantTotal = 0;
    
        // SQL pour calculer le montant total de toutes les commandes pour un client donné
        String sql = "SELECT SUM(d.qteCom * p.prixUnit) AS montantTotal " +
                        "FROM detailsCom d " +
                        "JOIN produit p ON d.numProd = p.numProd " +
                        "JOIN commande c ON c.numCom = d.numCom " +
                        "WHERE c.numCl = ?";
    
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, numCl);
    
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    montantTotal = rs.getDouble("montantTotal");
                    if (rs.wasNull()) {
                        montantTotal = 0; // Gère le cas où aucun montant n'est trouvé
                    }
                } else {
                    return -1; // Aucune donnée trouvée
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la récupération du montant total : " + e.getMessage());
            return -1; // En cas d'erreur
        }
    
        return montantTotal;
    }
    
    // affiche les numéros et dates des commandes d’un client.
    public static List<List<String>> listeCom(int numCl) {
        List<List<String>> commandesClient = new ArrayList<>();
    
        String sql = "SELECT numCom, dateCom FROM commande WHERE numCl = ? ORDER BY dateCom DESC";
    
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, numCl);
    
            try (ResultSet rs = stmt.executeQuery()) {
                boolean hasResults = false;
    
                while (rs.next()) {
                    int numCom = rs.getInt("numCom");
                    java.sql.Date dateCom = rs.getDate("dateCom");
    
                    List<String> commande = new ArrayList<>();
                    commande.add(String.valueOf(numCom));
                    commande.add(dateCom.toString());
                    commandesClient.add(commande);
    
                    hasResults = true;
                }
    
                if (!hasResults) {
                    // Afficher un message d'avertissement si le client n'est pas trouvé
                    Alert alert = new Alert(AlertType.INFORMATION);
                    alert.setTitle("Avertissement");
                    alert.setHeaderText(null);
                    alert.setContentText("Aucune commande trouvée pour le client " + numCl);
                    alert.showAndWait();
        
                }
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de l'affichage des commandes : " + e.getMessage());
        }
    
        return commandesClient;
    }
    
    
    // retourne le nom du client ayant passé une commande.
    public static String trouverNom(int numCom) {
        String sql = "SELECT c.nom FROM client c JOIN commande co ON c.numCl = co.numCl WHERE co.numCom = ?";
    
        try (Connection conn = DriverManager.getConnection(url, utilisateur, motDePasse);
                PreparedStatement stmt = conn.prepareStatement(sql)) {
    
            stmt.setInt(1, numCom);
            ResultSet rs = stmt.executeQuery();
    
            if (rs.next()) {
                return rs.getString("nom");
            } else {
                return "Aucun client trouvé pour la commande numéro " + numCom;
            }
        } catch (SQLException e) {
            System.err.println("Erreur lors de la recherche du nom du client : " + e.getMessage());
            return "Erreur de base de données";
        }
    }

    public static void EtatClient() {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
	String fichierJRXML = ".\\TClient.jrxml";
	String fichierPDF = ".\\TClient.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	// Paramètres à envoyer a l'etat
	Map parameters = new HashMap();
	
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }


     public static void EtatProduit() {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
	String fichierJRXML = ".\\TProduit.jrxml";
	String fichierPDF = ".\\TProduit.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
	// Paramètres à envoyer a l'etat
	Map parameters = new HashMap();
	
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }

     public static void EtatCommande() {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
	String fichierJRXML = ".\\TCommande.jrxml";
	String fichierPDF = ".\\TCommande.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    
	// Paramètres à envoyer a l'etat
	Map parameters = new HashMap();
	
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
    /*if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        File pdfFile = new File(fichierPDF);
        if (pdfFile.exists()) {
            desktop.open(pdfFile);
        }
    }*/
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }
    
        
     public static void EtatFacture(int numCl) {
       @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap

		Connection connection = null; 
		double totalHT = 0;
		double totalTTC = totalHT*1.20;
		String totalEnLettres = "null";
		
        // Votre requête SQL
        String query = "SELECT SUM(gestioncommandes.detailscom.`qteCom` * gestioncommandes.produit.`prixUnit`) AS TotalHT FROM gestioncommandes.detailscom INNER JOIN gestioncommandes.commande ON gestioncommandes.detailscom.`numCom` = gestioncommandes.commande.`numCom` INNER JOIN gestioncommandes.client ON gestioncommandes.commande.`numCl` = gestioncommandes.client.`numCl` INNER JOIN gestioncommandes.produit ON gestioncommandes.detailscom.`numProd` = gestioncommandes.produit.`numProd` WHERE gestioncommandes.client.`numCl` = ? GROUP BY gestioncommandes.client.`numCl`";

        try {

				Driver monDriver = new com.mysql.jdbc.Driver();
				DriverManager.registerDriver(monDriver);
				connection = DriverManager.getConnection(url, utilisateur, motDePasse);
				// Préparer et exécuter la requête SQL
				PreparedStatement pstmt = connection.prepareStatement(query);
				pstmt.setInt(1, numCl);  // Remplacez par le numéro du client dynamique "c'est le deuxiemme 1"
				ResultSet rs = pstmt.executeQuery();
				// Traiter le résultat
				if (rs.next()) {
					totalHT = rs.getDouble("TotalHT");
					totalTTC = totalHT*1.20;
					System.out.println("Total TTC: " + totalTTC);
	
					// Convertir en mots
					totalEnLettres = convert((long) totalTTC);
                    totalEnLettres = "Arretee la presente facture a la somme de: " + totalEnLettres +" FCFA toutes taxes comprises";
					System.out.println("Facture en cour de generation...");
				}
				String fichierJRXML = ".\\Facture.jrxml";
				String fichierPDF = ".\\Facture.pdf";
				// Connexion à la base

				JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
				JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
				// Paramètres à envoyer a l'etat
				Map<String, Object> parameters = new HashMap<>();
				parameters.put("NumeroClient", numCl); // Supposons que c'est l'ID du client
				parameters.put("PrixString", totalEnLettres); // 'totalEnLettres' est la valeur calculée
				parameters.put("totalHT", totalHT); // 'totalHT' est la valeur calculée
				parameters.put("totalTTC", totalTTC); // 'totalTTC' est la valeur calculée
				
				// Execution de l'etat
				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
				// Création de l'etat au format PDF
				JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
                Alert alert = new Alert(AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setHeaderText(null);
                alert.setContentText("generation terminee " + numCl);
                alert.showAndWait();
            // Fermer les connexions
            rs.close();
            pstmt.close();
            connection.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    
    }

    
    public static final String[] dizaineNames = {
        "", "", "vingt", "trente", "quarante", "cinquante", "soixante", "soixante", "quatre-vingt", "quatre-vingt"
    };

    public static final String[] uniteNames1 = {
        "", "un", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf",
        "dix", "onze", "douze", "treize", "quatorze", "quinze", "seize", "dix-sept", "dix-huit", "dix-neuf"
    };
 
    public static final String[] uniteNames2 = {
        "", "", "deux", "trois", "quatre", "cinq", "six", "sept", "huit", "neuf", "dix"
    };

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

    public static String convertLessThanOneThousand(int number) {
        int hundreds = number / 100;
        int rest = number % 100;
        String sRest = convertZeroToHundred(rest);

        if (hundreds == 0) return sRest;
        else if (hundreds == 1) return "cent " + (rest != 0 ? sRest : "");
        else return uniteNames2[hundreds] + " cent" + (rest != 0 ? " " + sRest : "s");
    }

    public static String convertZeroToHundred(int number) {
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


    public static void EtatmontantCommande(int NumeroCommande , int numcl) {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
            String fichierJRXML = ".\\montantCommande.jrxml";
            String fichierPDF = ".\\montantCommande.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    
	// Paramètres à envoyer a l'etat
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("NumeroCl", numcl); // Supposons que c'est l'ID du client
    parameters.put("NumeroCommande", NumeroCommande); // 'totalEnLettres' est la valeur calculée
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
    /*if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        File pdfFile = new File(fichierPDF);
        if (pdfFile.exists()) {
            desktop.open(pdfFile);
        }
    }*/
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }

     public static void EtatmontantTotal(int numcl) {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
            String fichierJRXML = ".\\MontantTotal.jrxml";
            String fichierPDF = ".\\MontantTotal.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    
   
	// Paramètres à envoyer a l'etat
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("NumeroClient", numcl); // Supposons que c'est l'ID du client
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
    /*if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        File pdfFile = new File(fichierPDF);
        if (pdfFile.exists()) {
            desktop.open(pdfFile);
        }
    }*/
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }

     public static void EtatListeCommande(int numcl) {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
            String fichierJRXML = ".\\ListeCom.jrxml";
            String fichierPDF = ".\\ListeCom.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    
	// Paramètres à envoyer a l'etat
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("NumeroClient", numcl); // Supposons que c'est l'ID du client
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
    /*if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        File pdfFile = new File(fichierPDF);
        if (pdfFile.exists()) {
            desktop.open(pdfFile);
        }
    }*/
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }

     public static void EtattrouverNom(int numcom) {
        @SuppressWarnings({ "unchecked", "rawtypes" }) //a cause de Map et HashMap
        // Paramètres de connexion à la base de données

        Connection connection = null; 
        try {
            String fichierJRXML = ".\\trouverNom.jrxml";
            String fichierPDF = ".\\trouverNom.pdf";
	// Connexion à la base
	Driver monDriver = new com.mysql.jdbc.Driver();
	DriverManager.registerDriver(monDriver);
	connection = DriverManager.getConnection(url, utilisateur, motDePasse);
	
	JasperDesign jasperDesign = JRXmlLoader.load(fichierJRXML);
	JasperReport jasperReport = JasperCompileManager.compileReport(jasperDesign);
    
	// Paramètres à envoyer a l'etat
    Map<String, Object> parameters = new HashMap<>();
    parameters.put("NumeroCommande", numcom); // Supposons que c'est l'ID du client
	// Execution de l'etat
	JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
	// Création de l'etat au format PDF
	JasperExportManager.exportReportToPdfFile(jasperPrint, fichierPDF);
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle("Information");
    alert.setHeaderText(null);
    alert.setContentText("generation terminee ");
    alert.showAndWait();
    /*if (Desktop.isDesktopSupported()) {
        Desktop desktop = Desktop.getDesktop();
        File pdfFile = new File(fichierPDF);
        if (pdfFile.exists()) {
            desktop.open(pdfFile);
        }
    }*/
	} catch (JRException | SQLException e) {
	      } finally {
	            try {
	                connection.close();
	            } catch (SQLException e) {
	              }
	        }
	 }
}