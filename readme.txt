#titre Packaging de l'application :  

Après avoir récupérer l'application web, l'API REST et le batch vous n'avez plus qu'à packager les projets.  
Pour cela ouvrer un invite de commandes à la racine d'un des projets et taper : `mvn package``.

#titre Exécution des projets :

Une fois cela fait pour les trois projets vous pouvez éxécuter la commande : `mvn spring-boot:run` à la racine de chaque projet
dans l'invite de commande.

#titre Paramétrage de la BDD :

Pour la base de données vous pouvez utiliser les fichiers .SQL dans le fichier nommé _sql_.  
L'un d'eux permet de créer la BDD et les différentes tables et l'autre permet d'insérer les variables.

#titre Les requêtes Postman :

Dans le dossier api-rest vous trouverez le fichier nommé _postman_, dans lequel vous aurez la collection des requêtes postman.

#titre Informations utiles :

* gestionnaire de projets - _Maven_ | version - _4.0.0_
* frameworks
	`* _Spring Boot_ | version - _2.3.1.RELEASE_
	`* _Hibernate_
	`* _Thymeleaf_ | version - _3.0.4.RELEASE_
	`* _Json_ | version - _20200518_
	`* _Spring Boot Mail_ | version - _2.1.9.RELEASE_
	`* _Spring Boot Batch_ | version - _2.1.9.RELEASE_
* java - version - _1.8_
