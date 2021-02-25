Après avoir récupérer l'application web, l'API REST et le batch vous n'avez plus qu'à packager les projets.
Pour cela ouvrer un invite de commandes à la racine d'un des projets et taper : "mvn package". 
Une fois cela fait pour les trois projets vous pouvez récupérer dans chaque dossier target de chacun d'entre eux le .jar.
Pour le lancer ouvrez un invite de commande à l'emplacement du fichier jar que vous voulez démarrer.
	-exemple : pour l'API REST lorsque vôtre invite de commande est lancé vous taper : "java -jar apirest-0.0.1-SNAPSHOT.jar"
	(sachant que "apirest-0.0.1-SNAPSHOT.jar" est le nom par défault du projet.
pour la BDD vous pouvez utiliser les fichier .SQL dans le fichier sql l'un permet de créer la BDD une fois cela fait et après avoir
lancé l'API REST une fois les tables seront générée et vous pourrez y insérer les variables.

Dans le dossier api-rest vous trouverer le fichier postman, dans lequel vous aurez la collection des requêtes postman.