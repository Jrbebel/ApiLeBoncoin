/**
 * DAOGeneriqueSimple.java
 *
 * Que des methodes statiques Et 4 methodes seulement :
 *
 * + les methodes privees getWhere, getOrderBy et getLimit
 *
 * int insert(Connection, Table, mapInsert)
 *
 * int update(Connection, Table, mapSet , mapWhere)
 *
 * int delete(Connection, Table, mapWhere)
 *
 * String[][] select(Connection, Table, tColonnes, mapWhere, mapOrderBy, String
 * debut, String nombre)
 */
package fr.jbb.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author pascal
 */
public class DAOGeneriqueSimple {

    /**
     *
     * @param pcnx
     * @param psTable
     * @param mapInsert
     * @return
     */
    public static int insert(Connection pcnx, String psTable, Map<String, String> mapInsert) {
        int liAffectes = -1;

        StringBuilder lsbSQL = new StringBuilder();
        StringBuilder lsbColonnes = new StringBuilder();
        StringBuilder lsbParametresValeurs = new StringBuilder();

        Set<String> nomsColonnes = mapInsert.keySet();
        Collection<String> valeursColonnes = mapInsert.values();

        for (String nomColonne : nomsColonnes) {
            lsbColonnes.append(nomColonne);
            lsbColonnes.append(",");

            lsbParametresValeurs.append("?,");
        }
        lsbColonnes.deleteCharAt(lsbColonnes.length() - 1);
        lsbParametresValeurs.deleteCharAt(lsbParametresValeurs.length() - 1);

        /*
         Exemple :
         INSERT INTO villes(cp,nom_ville,site,photo,id_pays) VALUES(?,?,?,?,?)
         */
        lsbSQL.append("INSERT INTO ");
        lsbSQL.append(psTable);
        lsbSQL.append("(");
        lsbSQL.append(lsbColonnes);
        lsbSQL.append(")");
        lsbSQL.append(" VALUES");
        lsbSQL.append("(");
        lsbSQL.append(lsbParametresValeurs);
        lsbSQL.append(")");

//        System.out.println(lsbSQL);
        PreparedStatement pst;
        try {
            pst = pcnx.prepareStatement(lsbSQL.toString());
            System.out.println("Requete insert : " + lsbSQL.toString());
            int i = 1;
            for (String valeurColonne : valeursColonnes) {
                pst.setString(i, valeurColonne);
                System.out.println("valeurColonne : " + valeurColonne);
                i++;
            }
            System.out.println("pst" + pst);
            liAffectes = pst.executeUpdate();
            System.out.println("Ligne affecter" + liAffectes);
            pst.close();

        } catch (SQLException e) {

            System.out.println("Erreur INSERT : " + e.getMessage());
        }

        return liAffectes;
    } /// insert

    /**
     *
     * @param pcnx
     * @param psTable
     * @param mapWhere
     * @return
     */
    public static int delete(Connection pcnx, String psTable, Map<String, String> mapWhere) {
        int liAffectes = -1;

        StringBuilder lsbSQL = new StringBuilder();

        Set<String> nomsColonnesWhere = mapWhere.keySet();
        Collection<String> valeursColonnesWhere = mapWhere.values();

        /*
         Exemple :
         DELETE FROM ville WHERE cp = ?
         ou
         ou DELETE FROM villes
         */
        lsbSQL.append("DELETE FROM ");
        lsbSQL.append(psTable);
        if (mapWhere != null) {
            lsbSQL.append(getWhere(nomsColonnesWhere));
        }

//        System.out.println(lsbSQL);
        PreparedStatement pst = null;
        try {
            pst = pcnx.prepareStatement(lsbSQL.toString());
            int i = 1;
            for (String valeurColonneWhere : valeursColonnesWhere) {
                pst.setString(i, valeurColonneWhere);
                i++;
            }

            liAffectes = pst.executeUpdate();
            pst.close();
        } catch (SQLException e) {
//            System.out.println("Erreur DELETE : " + e.getMessage());
        }

        return liAffectes;
    } /// delete

    /**
     *
     * @param pcnx
     * @param psTable
     * @param mapColonnes
     * @param mapWhere
     * @return
     */
    public static int update(Connection pcnx, String psTable, Map<String, String> mapColonnes, Map<String, String> mapWhere) {
        int liAffectes = -1;

        StringBuilder lsbSET = new StringBuilder();
        StringBuilder lsbSQL = new StringBuilder();
        String lsWHERE = "";

        Set<String> nomsColonnes = mapColonnes.keySet();
        Collection<String> valeursColonnes = mapColonnes.values();

        Set<String> nomsColonnesWhere = mapWhere.keySet();
        Collection<String> valeursColonnesWhere = mapWhere.values();

        lsbSET.append(" SET ");
        for (String colonneSET : nomsColonnes) {
            lsbSET.append(colonneSET);
            lsbSET.append("=?,");
        }
        lsbSET.deleteCharAt(lsbSET.length() - 1);
//        System.out.println("SET ... ! " + lsbSET);

        /*
         Exemple :
         UPDATE ville SET nom_ville='', site='' WHERE cp =? AND insee=?
         */
        lsbSQL.append("UPDATE ");
        lsbSQL.append(psTable);

        lsbSQL.append(lsbSET);

        if (mapWhere != null) {
            lsWHERE = getWhere(nomsColonnesWhere);
        }

        lsbSQL.append(lsWHERE);

//        System.out.println("where : " + lsWHERE);
//        System.out.println("update : " + lsbSQL);
        PreparedStatement pst;
        try {
            pst = pcnx.prepareStatement(lsbSQL.toString());
            int i = 1;
            // Pour le SET
            for (String valeurColonne : valeursColonnes) {
                pst.setString(i, valeurColonne);
//                System.out.println(valeurColonne);
                i++;
            }
            // Pour le WHERE
            for (String valeurWhere : valeursColonnesWhere) {
                pst.setString(i, valeurWhere);
//                System.out.println(valeurWhere);
                i++;
            }

            liAffectes = pst.executeUpdate();

            pst.close();

        } catch (SQLException e) {
//            System.out.println("Erreur UPDATE : " + e.getMessage());
        }

        return liAffectes;
    } /// update

    /**
     *
     * @param pcnx
     * @param psTable
     * @param tColonnes
     * @param mapWhere
     * @param mapJoin
     * @param mapOrderBy
     * @param debut
     * @param nombre
     * @return
     */
    public static String[][] select(Connection pcnx, String psTable, String[] tColonnes, Map<String, String> mapWhere, Map<String, String> mapJoin, Map<String, String> mapOrderBy, String debut, String nombre) {
        String[][] tData;
        PreparedStatement pst;
        ResultSet lrs;
        ResultSetMetaData lrsmd;
        int liCols;
        String[] tValeursColonnes;
        List<String[]> listeLignes;
        StringBuilder lsbSQL = new StringBuilder();
        StringBuilder lsbColonnes = new StringBuilder();
        String lsValeur;
        String lsColonnes = "*";
        String lsWHERE = "";
        String lsJOIN = "";
        String lsORDERBY = "";
        String lsLIMIT = "";
        Set<String> nomsColonnesWhere;
        Set<String> nomsColonnesJOIN;

        Collection<String> valeursColonnes;

        try {

            if (tColonnes != null) {
                for (String lsNomColonne : tColonnes) {
                    lsbColonnes.append(lsNomColonne);
                    lsbColonnes.append(",");
                }
                lsbColonnes.deleteCharAt(lsbColonnes.length() - 1);
                lsColonnes = lsbColonnes.toString();
            }

            if (mapWhere != null) {
                nomsColonnesWhere = mapWhere.keySet();
                lsWHERE = getWhere(nomsColonnesWhere);
            }
            if (mapJoin != null) {
                nomsColonnesJOIN = mapJoin.keySet();
                System.out.println("en premier lieu " + nomsColonnesJOIN);
                lsJOIN = getJOIN(nomsColonnesJOIN);
            }
            if (mapOrderBy != null) {
                lsORDERBY = getOrderBy(mapOrderBy);
            }
            if (debut != null) {
                lsLIMIT = getLimit(debut, nombre);
            }

            //lsSelect = "SELECT " + lsColonnes + " FROM " + psTable + lsWHERE;
            lsbSQL.append("SELECT ");
            lsbSQL.append(lsColonnes);
            lsbSQL.append(" FROM ");
            lsbSQL.append(psTable);
            lsbSQL.append(lsWHERE);
            lsbSQL.append(lsJOIN);
            lsbSQL.append(lsORDERBY);
            lsbSQL.append(lsLIMIT);

            pst = pcnx.prepareStatement(lsbSQL.toString());

            System.out.println(lsbSQL);
            if (mapWhere != null) {
                valeursColonnes = mapWhere.values();
                int i = 1;
                for (String lsValeurColonne : valeursColonnes) {
                    pst.setString(i, lsValeurColonne);
                    System.out.println("lsvaleur " + lsValeurColonne);
                    i++;
                }
            }

            if (mapOrderBy != null) {
                lsbSQL.append(getOrderBy(mapOrderBy));
            }

            if (debut != null) {
                lsbSQL.append(getLimit(debut, nombre));
            }

            lrs = pst.executeQuery();
            lrsmd = lrs.getMetaData();
            liCols = lrsmd.getColumnCount();

            // --- Affichage du contenu des lignes/colonnes
            listeLignes = new ArrayList();
            while (lrs.next()) {
                tValeursColonnes = new String[liCols];
                for (int i = 1; i <= liCols; i++) {
                    lsValeur = lrs.getString(i);
                    if (lsValeur == null) {
                        tValeursColonnes[i - 1] = "NUL";
                    } else {
                        tValeursColonnes[i - 1] = lsValeur.toString();
                    }
                }
//                System.out.println("Taille de tColonnes : " + tValeursColonnes.length);
                listeLignes.add(tValeursColonnes);
            }

            lrs.close();
            pst.close();

            //tData = listeLignes.toArray(new String[listeLignes.size()]);
            tData = new String[listeLignes.size()][liCols];
            for (int i = 0; i < tData.length; i++) {
                tData[i] = listeLignes.get(i);
            }
//            System.out.println("Taille de tData : " + tData.length);

        } catch (SQLException e) {

//            System.out.println("Erreur SELECT : " + e.getMessage());
            tData = new String[1][1];
            tData[0][0] = e.getMessage();
        }

        return tData;

    } /// select

    /**
     *
     * @param mapWhere
     * @return
     */
    private static String getWhere(Set<String> nomsColonnes) {
        /*
         du genre WHERE cp=? AND insee=?
         */
        StringBuilder lsbWhere = new StringBuilder(" WHERE ");

        for (String nomColonne : nomsColonnes) {
            lsbWhere.append(nomColonne);
            lsbWhere.append("=? AND ");
        }
        lsbWhere.delete(lsbWhere.length() - 4, lsbWhere.length());

        System.out.println("La requete lsbWhere " + lsbWhere);
        return lsbWhere.toString();
    } /// getWhere

    /**
     *
     * @param mapWhere
     * @return
     */
    private static String getJOIN(Set<String> nomsColonnes) {
        /*
         du genre WHERE cp=? AND insee=?
         */
        StringBuilder lsbJOIN = new StringBuilder(" AND ");

        for (String nomColonne : nomsColonnes) {
            System.out.println("nomColonne " + nomColonne);
            lsbJOIN.append(nomColonne);
            lsbJOIN.append("= ");
        }
        lsbJOIN.delete(lsbJOIN.length() - 2, lsbJOIN.length());

        System.out.println("La requete lsbjoin " + lsbJOIN);
        return lsbJOIN.toString();
    } /// getJOIN

    /**
     *
     * @param nomsColonnes
     * @return
     */
    private static String getOrderBy(Map<String, String> nomsColonnes) {
        /*
         Du genre ORDER BY  cp DESC ,insee ASC
         */
        StringBuilder lsbOrderBy = new StringBuilder(" ORDER BY ");

        for (Map.Entry<String, String> entry : nomsColonnes.entrySet()) {
            String nomColonne = entry.getKey();
            String ordre = entry.getValue();
            lsbOrderBy.append(nomColonne);
            lsbOrderBy.append(" ");
            lsbOrderBy.append(ordre);
            lsbOrderBy.append(",");
        }
        lsbOrderBy.deleteCharAt(lsbOrderBy.length() - 1);

//        System.out.println(lsbOrderBy);
        return lsbOrderBy.toString();
    } /// getOrderBy

    /**
     *
     * @param debut
     * @param nombre
     * @return
     */
    private static String getLimit(String debut, String nombre) {
        /*
         Du genre LIMIT 1, 3
         */
        StringBuilder lsbLimit = new StringBuilder(" LIMIT ");
        lsbLimit.append(debut);
        lsbLimit.append(",");
        lsbLimit.append(nombre);

//        System.out.println(lsbLimit);
        return lsbLimit.toString();
    } /// getLimit

} /// class

