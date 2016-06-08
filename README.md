# ApiLeBoncoin
Ceci est une api qui a été développé au cours de ma formation à M2I dans le cadre d'un projet décliné en trois partie :
 
 - JAVA EE
 - JAVA SE
 - Application ANDROID


ApiLeBonCoin est une api qui permettra à notre Application d'android de communiquer avec la base de données et de récupérer les informations qui lui est nécessaire .
L’échange de données se feront en XML et se feront sur le serveur http://ns320828.ip-5-39-86.eu/apiLeBonCoin/.


----------

La liste des ressources disponible : 

**Connexion en méthode GET**

    ConnexionApi?email=monemail@gmail.com&mdp=motdepasse

 	{   "Client": 	
	[[
       "LUPIN",
       "ARSENE",
       "0690995524",
       "jeanraynal.bebel@gmail.com",
       "jrbebel"
     ]]
	 }

 - Inscription en mode GET
 
 - Liste des régions en méthode GET
-  `apiLeBonCoin/RegionForAndroid`

> {   "REGION": [
> [
>   "2",
>   "75",
>   "Martinique",
>   "02"
> ],
> [
>   "3",
>   "75",
>   "Guyane",
>   "03"
> ]]}

 - Liste des sous catégorie et de sous catégories en méthode GET
	 - `apiLeBonCoin/CategorieSSCatForAndroid`
> {   "VEHICULES": [
>     [
>       "2",
>       "2",
>       "Voitures"
>     ],
>     [
>       "3",
>       "2",
>       "Motos"
>     ],   "MATERIEL PROFESSIONNEL": [
>     [
>       "34",
>       "7",
>       "Matériel Agricole"
>     ]]}

