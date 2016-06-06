<%-- 
    Document   : index
    Created on : 29 mai 2016, 17:54:48
    Author     : jrbebel
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<h1 id="apileboncoin">ApiLeBoncoin</h1>

<p>Ceci est une api qui a été développé au cours de ma formation à M2I dans le cadre d’un projet décliné en trois partie :</p>

<ul>
    <li>JAVA EE</li>
    <li>JAVA SE</li>
    <li>Application ANDROID</li>
</ul>

<p>ApiLeBonCoin est une api qui permettra à notre Application d’android de communiquer avec la base de données et de récupérer les informations qui lui est nécessaire . <br>
    L’échange de données se feront en XML et se feront sur le serveur <a href="http://ns320828.ip-5-39-86.eu/apiLeBonCoin/">http://ns320828.ip-5-39-86.eu/apiLeBonCoin/</a>.</p>

<hr>

<p>La liste des ressources disponible : </p>

<ul>
    <li>Connexion en méthode GET <br>
        <ul><li><code>ConnexionApi?email=monemail@gmail.com&amp;mdp=motdepasse</code></li></ul></li>
</ul>

<blockquote>
    <p>{   “Client”: [ <br>
        [ <br>
        “LUPIN”, <br>
        “ARSENE”, <br>
        “0690995524”, <br>
        “jeanraynal.bebel@gmail.com”, <br>
        “jrbebel” <br>
        ]   ] }</p>
</blockquote>

<ul>
    <li><p>Inscription en mode GET</p></li>
    <li><p>Liste des régions en méthode GET</p>

        <ul><li><code>apiLeBonCoin/RegionForAndroid</code></li></ul></li>
</ul>

<blockquote>
    <p>{   “REGION”: [ <br>
        [ <br>
        “2”, <br>
        “75”, <br>
        “Martinique”, <br>
        “02” <br>
        ], <br>
        [ <br>
        “3”, <br>
        “75”, <br>
        “Guyane”, <br>
        “03” <br>
        ]]}</p>
</blockquote>

<p></p><ul> <br>
    <li>Liste des sous catégorie et de sous catégories en méthode GET <br>
        <ul><li><code>apiLeBonCoin/CategorieSSCatForAndroid</code> <br></li></ul></li></ul><p></p>

<blockquote>
    <p>{   “VEHICULES”: [ <br>
        [ <br>
        “2”, <br>
        “2”, <br>
        “Voitures” <br>
        ], <br>
        [ <br>
        “3”, <br>
        “2”, <br>
        “Motos” <br>
        ],   “MATERIEL PROFESSIONNEL”: [ <br>
        [ <br>
        “34”, <br>
        “7”, <br>
        “Matériel Agricole” <br>
        ]]}
    </p>
</blockquote>
