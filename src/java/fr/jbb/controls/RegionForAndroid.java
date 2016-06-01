/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fr.jbb.controls;

import fr.jbb.dao.Connexion;
import fr.jbb.dao.DAOGeneriqueSimple;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import static java.util.Collections.list;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject; // a importer dans les librairies http://json-simple.googlecode.com/files/json-simple-1.1.1.jar
//https://code.google.com/archive/p/json-simple/wikis/EncodingExamples.wiki pour les exemples

/**
 *
 * @author formation
 */
@WebServlet(name = "RegionForAndroid", urlPatterns = {"/RegionForAndroid"})
public class RegionForAndroid extends HttpServlet {

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8"); // on specifie que notre response sera de type json

        PrintWriter out = response.getWriter();
        JSONObject json = new JSONObject(); //on crée un objet json

        try {
            /**
             * On prepare la connexion a partir de la classe Connexion*
             */

            Connection Cnx = Connexion.seConnecter(); // classe Connexion
         
            String[] tColonnes = {"*"};
            String psTable = "VILLE"; //NOM DE LA TABLE

            String[][] tData; // RESULTAT

            tData = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, null, null, "1", "30"); //SELECT sur le DAOGenerique

            json.put("REGION", tData); // on specifie dans le premier parametre le nom et dans le deuxieme le tableau de notre requete
            out.print(json.toString()); //on convertie le tout en string

        } catch (ClassNotFoundException ex) {
            out.print("message Error" + ex.getMessage());

        } finally {
            out.close();
        }

    }

}
