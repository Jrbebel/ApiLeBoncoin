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
import org.json.JSONObject;

/**
 *
 * @author jrbebel
 */
@WebServlet(name = "ConnexionForAndroid", urlPatterns = {"/ConnexionApi"})
public class ConnexionForAndroid extends HttpServlet {

    PrintWriter out;
    JSONObject json;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        response.setContentType("application/json;charset=UTF-8");
        out = response.getWriter();
        json = new JSONObject(); //on crée un objet json

        Connection Cnx = Connexion.seConnecter();
        String email = request.getParameter("email");
        String mdp = request.getParameter("mdp");

        String[] tColonnes = {"NOM_CLIENT,PRENOM_CLIENT,TELEPHONE_CLIENT,EMAIL_CLIENT,PSEUDO_CLIENT,ADRESSE_CLIENT,MDP_CLIENT,NOM_VILLE"};
        String psTable = "leboncoin.client,leboncoin.ville ";

        Map<String, String> mapWhere = new HashMap<String, String>();
        mapWhere.put("EMAIL_CLIENT", "jeanraynal.bebel@gmail.com");
        mapWhere.put("MDP_CLIENT", "a");
        // mapWhere.put("leboncoin.client.ID_VILLE","leboncoin.ville.ID_VILLE");
        Map<String, String> mapJoin = new HashMap<String, String>();
        mapJoin.put("client.ID_VILLE","null");
         mapJoin.put("ville.ID_VILLE","null");

        String[][] tData;
        tData = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, mapWhere, mapJoin, null, null, null);

        if (tData.length != 0) { //ON TRAITE SELON LES RESULTATS
            json.put("Client", tData);
        } else {
            json.put("Client", 0);
        }

        out.print(json.toString());
        out.close();

    }
}
