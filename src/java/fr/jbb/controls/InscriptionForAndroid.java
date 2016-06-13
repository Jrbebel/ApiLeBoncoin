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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {

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
            String idClient = request.getParameter("idclient");
            String nomClient = request.getParameter("nom");
            String prenomClient = request.getParameter("prenom");
            String telephoneClient = request.getParameter("telephone");
            String emailClient = request.getParameter("email");
            String adresseClient = request.getParameter("adresse");
            String pseudoClient = request.getParameter("pseudo");
            String mdpClient = request.getParameter("mdp");
            String dateString = request.getParameter("date");
            String idVille = request.getParameter("idVille");
            String idCategorieClient = request.getParameter("idCategorie");

            Map<String, String> mapColonne = new HashMap<String, String>(); //ma map pour inserer les données

            mapColonne.put("NOM_CLIENT", nomClient);
            mapColonne.put("PRENOM_CLIENT", prenomClient);
            mapColonne.put("TELEPHONE_CLIENT", telephoneClient);
            mapColonne.put("EMAIL_CLIENT", emailClient);
            mapColonne.put("ADRESSE_CLIENT", adresseClient);
            mapColonne.put("PSEUDO_CLIENT", pseudoClient);
            mapColonne.put("MDP_CLIENT", mdpClient);
            mapColonne.put("DATENAISS_CLIENT", dateString);
            mapColonne.put("ID_VILLE", idVille);
            mapColonne.put("ID_CATEGORIE_CLIENT", idCategorieClient);
            try {
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                Date date = formatter.parse(dateString);
                System.out.println(date);
                System.out.println(formatter.format(date));

            } catch (ParseException e) {

                e.printStackTrace();
            }

            if (idClient.equals(null)) { //si c'est null c'est que c'est une inscription sinon c une modification de profil

                resultat = DAOGeneriqueSimple.insert(Cnx, psTable, mapColonne);

            } else {
                 Map<String, String> mapUpdate= new HashMap<String, String>();
                mapUpdate.put("ID_CLIENT", idClient);
                resultat = DAOGeneriqueSimple.update(Cnx, psTable, mapColonne, mapUpdate);
            }
            if (resultat == 1) {

                Connexion.valider();
                Connexion.seDeconnecter();
                json.put("retour", "success");

            } else {

                Connexion.annuler();
                Connexion.seDeconnecter();
                json.put("retour", "Erreur Requete");

            }
            out.print(json.toString());

        } catch (IOException ex) {
            Logger.getLogger(InscriptionForAndroid.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
