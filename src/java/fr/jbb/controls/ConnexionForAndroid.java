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

        String[] tColonnes = {"*"};
        String psTable = "CLIENT";

        Map<String, String> mapWhere = new HashMap<String, String>();
        mapWhere.put("EMAIL_CLIENT", email);
        mapWhere.put("MDP_CLIENT", mdp);

        String[][] tData;
        tData = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, mapWhere, null, null, null);

        if (tData.length != 0) { //ON TRAITE SELON LES RESULTATS
            json.put("Client", tData);
        } else {
            json.put("Client", 0);
        }

        out.print(json.toString());
        out.close();

    }
}
