/*
 * Connexion.java
 */
package fr.jbb.dao;

import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
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

    public static Connection seConnecter() throws ClassNotFoundException {
        try {

            String asServeur = "127.0.0.1";
            String asPort = "48775";
            String asBD = "leboncoin";
            String asUser = "root";
            String asPassword = "";
            // String lsURL="jdbc:mysql://172.17.0.2:3306/leboncoin";
            String lsURL = "jdbc:mysql://" + asServeur + ":" + asPort + "/" + asBD + "";

            //Le pilote
            Class.forName("com.mysql.jdbc.Driver");

            // --- Test sur une connexion et un SELECT
            icnx = DriverManager.getConnection(lsURL, asUser, asPassword);
            icnx.setAutoCommit(false);

        } catch (SQLException ex) {
            out.println("Erreur SQL"+ex.getMessage());
            System.err.println(ex.getMessage());
        }
        return icnx;
    } /// seConnecter

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
