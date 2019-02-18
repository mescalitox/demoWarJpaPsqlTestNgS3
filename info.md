
##DTM to Spring Boot


>j'ai passé un peu de temps pour te présenter les possibilités de migration du socle de librairies de DTM "ASF" vers Spring Boot.

>L'intérêt étant de passer sur des librairies dont la cohérence a été validée pour chaque montée de version, plus matures mais aussi dont la communauté est grande et participe beaucoup à son enrichissement.
Elle permet aussi de rassembler l'ensemble du paramétrage dans un fichier de configuration.

>Bien sûr, pour des raisons de cout de migration du code mais aussi en terme de non régression, il est évident que le passage vers spring boot doit être le plus "discret" possible. Garder le code tel qu'il est, avec la gestion des couches actuelles (resource/service/dao) et les objets actuels DTO, entitées.

>Ce que l'on accepte de changer : les configurations, la localisation du paramétrage, les annotations. A de rare exception, quelques nouvelles classes mais toujours 1 pour 1 sans changer les structures d'objets existants.

dans cet optique pour certaines migrations de librairies dont les couts pouvaient être remis en question, j'ai préféré garder ces librairies ou choisir lorsque cela était possible sa version "spring".

* jboss weld 	-> 	supprimé utilisation de spring 
					-> impact : migration simple (annotations)

* jersey WS 	-> 	il existe une version "spring", mais la migration vers spring restful est très simple à mettre en place et à tester (car tous les WS sont testés de bout en bout).
					nouvelles annotations, 
					classe resources à ajuster pour les objets de retour
					simplification du mode multipart lors des uploads de fichiers.					
			
* BD : toujours Postgresql et H2 pour les tests

* Liquibase 	-> utilisation de liquibase uniquement pour le plugin et utilisation de sa version "spring" pour les tests. 
				migration très rapide : 
					deplacement de dossiers, 
					suppression de fichier de configuration pour les tests.

* JTA, Persistence, datasource
hibernate, javax.transaction, atomikos -> passage à spring JPA, spring orm et hibernate. possibilité de garder une version "spring" d'atomikos et de continuer sur une gestion bas niveau des transactions mais spring  permet de simplifier énormément cette partie.
						migration très rapide : 
							suppression de fichiers de configuration
							modification des annotations 
							
* centralisation des exceptions -> spring 	
									migration assez simple en gardant le même principe de centralisation des "throwable", 
									possibilité de transformer les exceptions applicatives en Error pour ne plus les voir dans les signatures : élégant mais pas forcément judicieux.

* Log :
log4j ou logback -> spring utilise par défaut logback et tous les autres types de logger des librairies redirigent vers slf4j 
						migration simple : 
							centralisation de la configuration dans le fichier de configuration
							
* S3 -> librairie externe, migration tres rapide 
		(pom.xml) 
		utilisation de aws-java-sdk-s3 au lieu aws-java-sdk et utilisation de sl4j pour les logs

* Dozer mapping -> trop ancienne utilisation d'une librairie externe ModelMapper compatible spring, 
				migration très simple, memes appels, juste un nouveau type de mapper. 

* json -> librairie jackson intégré à spring

* Tests :  
TestNg : spring utilise Junit mais pour éviter trop de régression et de garder les avantages de TestNg (inter-dépendances et ordre des tests), il vaut mieux garder la librairie externe.    
Client Jersey et grizzly server -> utilisation de spring et restTemplate 
										migration relativement simple : 
											modification des appels aux WS.
											simplification du paramétrage
											configuration centralisée
											
* mockito : supprimée
* s3-mock : dernière version et centralisation de la configuration									

	