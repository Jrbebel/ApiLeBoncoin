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
import java.util.logging.Level;
import java.util.logging.Logger;
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

    public static Connection seConnecter() {

        try {

            String asPort = "3306";
//            String asBD = "leboncoin";
//            String asUser = "root";
            //  String asServeur = "172.17.0.2";
            String asServeur = "localhost";
            //  String asPort = "3306";
            String asBD = "leboncoin";
            String asUser = "root";
             String asPassword = "hellsing003";
             //String asPassword = "";
            // String lsURL="jdbc:mysql://172.17.0.2:3306/leboncoin";
            String lsURL = "jdbc:mysql://" + asServeur + ":" + asPort + "/" + asBD + "";

            //Le pilote
            Class.forName("com.mysql.jdbc.Driver");

            // --- Test sur une connexion et un SELECT
            icnx = DriverManager.getConnection(lsURL, asUser, asPassword);
            icnx.setAutoCommit(false);

        } catch (SQLException ex) {
            icnx = null;
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Connexion.class.getName()).log(Level.SEVERE, null, ex);
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
        }
        return lbOK;
    } /// annuler

} /// class
