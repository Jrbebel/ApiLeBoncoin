/*
 * Connexion.java
 */
package fr.jbb.dao;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;

/**
 *
 * @author pascal
 */
public class Connexion {

    /*
     Attributs
     */
    private static Connection icnx;

    /*
     Constructeurs
     */
    /**
     * Avec un fichier de properties
     *
     * @param psChemin
     * @return
     */
    public static Connection seConnecter(String psChemin) {
        try {

            // --- Creation d'un objet de proprietes
            Properties applicationProps = new Properties();

            // --- Lecture du fichier des proprietes
            FileInputStream in = new FileInputStream(psChemin);
            applicationProps.load(in);
            in.close();

            // --- Recuperation des proprietes une a une
            String lsPilote = applicationProps.get("pilote").toString();

            String lsProtocole = applicationProps.get("protocole").toString();
            String lsServeur = applicationProps.get("serveur").toString();
            String lsPort = applicationProps.get("port").toString();
            String lsBD = applicationProps.get("bd").toString();

            String lsUT = applicationProps.get("ut").toString();
            String lsMDP = applicationProps.get("mdp").toString();

            //String lsURL=jdbc:mysql://127.0.0.1:3306/cours
            String lsURL = lsProtocole + "://" + lsServeur + ":" + lsPort + "/" + lsBD;

            // --- Test sur une connexion et un SELECT
            Class.forName(lsPilote);
            icnx = DriverManager.getConnection(lsURL, lsUT, lsMDP);
            icnx.setAutoCommit(false);

        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        } catch (ClassNotFoundException e) {
            System.err.println(e.getMessage());
        } catch (SQLException exe) {
            System.err.println(exe.getMessage());
        }
        return icnx;
    } /// seConnecter

    public static Connection seConnecter(String asServeur, String asPort, String asBD, String asUser, String asPassword) throws ClassNotFoundException {
        try {
            // String lsURL="jdbc:mysql://172.17.0.2:3306/leboncoin";
            String lsURL = "jdbc:mysql://" + asServeur + ":" + asPort + "/" + asBD + "";

            //Le pilote
            Class.forName("com.mysql.jdbc.Driver");

            // --- Test sur une connexion et un SELECT
            icnx = DriverManager.getConnection(lsURL, asUser, asPassword);
            icnx.setAutoCommit(false);

        } catch (SQLException ex) {
         
            System.err.println(ex.getMessage());
        }
        return icnx;
    } /// seConnecter

    /*
     Methodes
     */
    /**
     *
     * @return
     */
//    public Connection getCnx() {
//        return icnx;
//    }
    /**
     *
     * @return
     */
    public static boolean seDeconnecter() {
        boolean lbOK = true;
        try {
            icnx.close();
        } catch (SQLException ex) {
            lbOK = false;
            System.err.println(ex.getMessage());
//            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lbOK;
    } /// seDeconnecter

    /**
     *
     * @return
     */
    public static boolean valider() {
        boolean lbOK = true;
        try {
            icnx.commit();
        } catch (SQLException ex) {
            lbOK = false;
            System.err.println(ex.getMessage());
//            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lbOK;
    } /// valider

    /**
     *
     * @return
     */
    public static boolean annuler() {
        boolean lbOK = true;
        try {
            icnx.rollback();
        } catch (SQLException ex) {
            lbOK = false;
            System.err.println(ex.getMessage());
//            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lbOK;
    } /// annuler

} /// class
