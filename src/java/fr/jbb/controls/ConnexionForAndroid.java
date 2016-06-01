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
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author jrbebel
 */
@WebServlet(name = "ConnexionForAndroid", urlPatterns = {"/ConnexionApi"})
public class ConnexionForAndroid extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("text/xml;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            /**
             * On prepare la connexion a partir de la classe Connexion*
             */
            String asServeur = "172.17.0.2";
            String asPort = "3306";
            String asBD = "leboncoin";
            String asUser = "root";
            String asPassword = "root";
            Connection Cnx = Connexion.seConnecter(asServeur, asPort, asBD, asUser, asPassword);

            /**
             * *On prepare notre requete**
             */
            String user = request.getParameter("pseudo"); //RECUPERE DANS L'URL LE PSEUDO
            String mdp = request.getParameter("mdp"); // RECUPERE DANS LE MDP LE MOT DE PASSE
            String[] tColonnes = {"*"};
            String psTable = "CLIENT"; //NOM DE LA TABLE

            Map<String, String> mapWhere = new HashMap<String, String>(); //MAP QUI VA CONTENIR NOS ELEMENT DU WHERE ( EX PSEUDO_CLIENT= JRBEBEL)

            mapWhere.put("PSEUDO_CLIENT", user);
            mapWhere.put("MDP_CLIENT", mdp);

            String[][] tData; // RESULTAT

            tData = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, mapWhere, null, null, null); //SELECT

            if (tData.length != 0) { //ON TRAITE SELON LES RESULTATS
                out.print("<xml>"
                        + "<reponse>"
                        + "OK"
                        + "</reponse>"
                        + "</xml>");
            } else {
                out.print("<xml>"
                        + "<reponse>"
                        + "KO"
                        + "</reponse>"
                        + "</xml>");
            }

        } catch (ClassNotFoundException ex) {
            out.print("Connection" + ex.getMessage());

        } finally {
            out.close();
        }

    }

}
