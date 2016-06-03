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
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
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

//            TreeMap<String, String> map = new TreeMap<String, String>();
//            CategorieProduit catPro = new CategorieProduit();
//            DAOGeneric<CategorieProduit> daocatpro = new DAOGeneric<CategorieProduit>(catPro);
//
//            SousCategorieProduit sscat = new SousCategorieProduit();
//            DAOGeneric<SousCategorieProduit> daosscat = new DAOGeneric<SousCategorieProduit>(sscat);
//
//            List<CategorieProduit> categoriepro = daocatpro.findAll();
//
//            for (CategorieProduit CategorieProduit : categoriepro) {
//
//                System.out.println("------->" + CategorieProduit.getCategorieProduit() + "<--------");
//                map.put("ID_CATEGORIE_PRODUIT", String.valueOf(CategorieProduit.getIdCategorieProduit()));
//                List<SousCategorieProduit> sscategorie = daosscat.findby(map);
//
//                for (SousCategorieProduit sousCategorieProduit : sscategorie) {
//
//                    System.out.println(sousCategorieProduit.getSousCategorieProduit());
//
//                }
//
//            }
            String[] tColonnes = {"*"};
            String psTable = "categorie_produit";

            String psTableSsCat = "sous_categorie_produit";

            String[][] TresultatCategorie;
            String[][] TresultatSsCategorie;

            TresultatCategorie = DAOGeneriqueSimple.select(Cnx, psTable, tColonnes, null, null, null, null); // on recupere tout les categories de produit
            TresultatSsCategorie = DAOGeneriqueSimple.select(Cnx, psTableSsCat, tColonnes, null, null, null, null);
            Map<String, String> mapWhere = new HashMap<String, String>();

            for (String[] strings : TresultatCategorie) { //on parcours tout les categories de produit

                json.putOpt(strings[1], TresultatSsCategorie);

                mapWhere.put("ID_CATEGORIE_PRODUIT", strings[1]);
            }

            out.print(json.toString());
            out.close();

        } catch (IOException ex) {
            Logger.getLogger(RegionForAndroid.class.getName()).log(Level.SEVERE, null, ex);

        } finally {
            out.close();
        }

    }

}
