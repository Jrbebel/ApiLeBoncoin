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
@WebServlet(name = "CategorieSSCatForAndroid", urlPatterns = {"/CategorieSSCatForAndroid"})
public class CategorieSSCatForAndroid extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PrintWriter out = null;

        try {

            response.setContentType("application/json;charset=UTF-8"); // on specifie que notre response sera de type json
            out = response.getWriter();
            JSONObject json = new JSONObject(); //on crée un objet json
            Connection Cnx = Connexion.seConnecter();
            if (Cnx == null) {
                json.put("Erreur", "Probleme de connexion");
                out.print(json.toString());
            }
            String[] tColonnes = {"*"};
            String psTable = "region";
            String[][] tData;
            tData = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, null, null, "1", "30");
            json.put("REGION", tData);
            out.print(json.toString());
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(RegionForAndroid.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            out.close();
        }

    }

}
