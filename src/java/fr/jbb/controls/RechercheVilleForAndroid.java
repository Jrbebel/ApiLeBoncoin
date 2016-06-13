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
@WebServlet(name = "RechercheVilleForAndroid", urlPatterns = {"/RechercheVilleForAndroid"})
public class RechercheVilleForAndroid extends HttpServlet {

    PrintWriter out;
    JSONObject json;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        out = response.getWriter();
        json = new JSONObject(); //on crée un objet json

        Connection Cnx = Connexion.seConnecter();
        String ville = request.getParameter("villeRech");
        int[] tDataNoresult = {0};
        String[] tColonnes = {"ville.ID_VILLE,NOM_VILLE,CP_VILLE,NOM_REGION"};
        String psTable = "leboncoin.ville,leboncoin.departement,leboncoin.region ";

        Map<String, String> mapWhere = new HashMap<String, String>();
        mapWhere.put(" NOM_VILLE", ville + "%");

        // mapWhere.put("leboncoin.client.ID_VILLE","leboncoin.ville.ID_VILLE");
        Map<String, String> mapJoin = new HashMap<String, String>();
        mapJoin.put("ville.ID_DEPARTEMENT", "departement.ID_DEPARTEMENT");
        mapJoin.put("departement.ID_REGION", "region.ID_REGION");
 
        String[][] tData;
        tData = DAOGeneriqueSimple.selectLike(Cnx, psTable, tColonnes, mapWhere, mapJoin, null, "1", "10");

        if (tData.length != 0) { //ON TRAITE SELON LES RESULTATS
            json.put("Ville", tData);
        } else {
            json.put("Ville", tDataNoresult);
        }

        out.print(json.toString());
        out.close();

    }

}
