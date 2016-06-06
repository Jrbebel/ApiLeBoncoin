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
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.JSONObject;

/**
 *
 * @author formation
 */
@WebServlet(name = "InscriptionForAndroid", urlPatterns = {"/InscriptionForAndroid"})

public class InscriptionForAndroid extends HttpServlet {

    PrintWriter out = null;
    JSONObject json;
    int resultat;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {

        response.setContentType("application/json;charset=UTF-8"); // on specifie que notre response sera de type json
        try {
            out = response.getWriter();

            json = new JSONObject(); //on crée un objet json

            Connection Cnx = Connexion.seConnecter();
            if (Cnx == null) {
                json.put("Erreur", "Probleme de connexion");
                out.print(json.toString());
            }
            String psTable = "client"; //ma table d'insertion

            String nomClient = "BEBEL";
            String prenomClient = "Jean-raynal";
            String telephoneClient = "06995524";
            String emailClient = "jea@k.com";
            String adresseClient = "bananier";
            String pseudoClient = "jrbebel";
            String mdpClient = "jrbebel";
            String idVille = "1";
            String idCategorieClient = "1";

            Map<String, String> mapWhere = new HashMap<String, String>(); //ma map pour inserer les données

            mapWhere.put("NOM_CLIENT", nomClient);
            mapWhere.put("PRENOM_CLIENT", prenomClient);
            mapWhere.put("TELEPHONE_CLIENT", telephoneClient);
            mapWhere.put("EMAIL_CLIENT", emailClient);
            mapWhere.put("ADRESSE_CLIENT", adresseClient);
            mapWhere.put("PSEUDO_CLIENT", pseudoClient);
            mapWhere.put("MDP_CLIENT", mdpClient);
            mapWhere.put("ID_VILLE", idVille);
            mapWhere.put("ID_CATEGORIE_CLIENT", idCategorieClient);

            resultat = DAOGeneriqueSimple.insert(Cnx, psTable, mapWhere);

            if (resultat == 1) {

                Connexion.valider();
                Connexion.seDeconnecter();
                json.put("retour", "success");

            } else {

                Connexion.annuler();
                Connexion.seDeconnecter();
                json.put("retour", "erreur d'insertion");

            }
            out.print(json.toString());

        } catch (IOException ex) {
            Logger.getLogger(InscriptionForAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
