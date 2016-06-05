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
import static java.lang.System.out;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author jrbebel
 */
@WebServlet(name = "ListeAnnonceOffre", urlPatterns = {"/ListeAnnonceOffre"})
public class ListeAnnonceOffreForAndroid extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;
        JSONObject json;
        try {

            response.setContentType("application/json;charset=UTF-8"); // on specifie que notre response sera de type json

            out = response.getWriter();
            json = new JSONObject(); //on crée un objet json

            Connection Cnx = Connexion.seConnecter();
            if (Cnx == null) {
                json.put("Erreur", "Probleme de connexion");
                out.print(json.toString());
            }

            String[] tColonnes = {"*"};
            String psTable = "produit,type_produit";
            String[][] TresultatListeOffre;

            TresultatListeOffre = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, null, null, null, null); // on recupere tout les categories de produit
            Map<String, String> mapWhere = new HashMap<String, String>();
            mapWhere.put("Produit_TypeProduit", "idType");
            mapWhere.put("idType", "2");

            for (String[] strings : TresultatListeOffre) { //on parcours toute la liste des produit de type offre

                json.put("listeAnnonceOffre", TresultatListeOffre); // on put la liste des annonces

            }

            out.print(json.toString());
            out.close();

        } catch (IOException ex) {

            // out.print(json.toString());
        } finally {

            out.close();

        }

    }

}
