# signatureQR
C'est un projet Spring Boot qui crée une API pour la signature et la vérification électronique d’un document via QR Code avec un UUID.

# Objectif
Concevoir une API Spring Boot qui permet la signature  de documents à l’aide d’un QR code généré à partir d’un UUID unique, ainsi il y a une vérification à travers le uuid du document selon le nombre de vérifications déjà faits , et sa date de signature .


# Fonctionnalités implémentées
# # 1. Génération de signature et QR code:
L’utilisateur peut signer un document via api/signer en donnant son nom et prenom .
+ Un UUID unique est généré pour chaque nouveau document signé .
+ Un QR code est crée à partir de ce UUID (format PNG), renvoyé en Base64 pour affichage dans une page HTML ou un client mobile.
+ Les informations nom, prénom, date de signature, et UUID sont enregistrées dans une base de données.

# # 2. Vérification du document signé:
L’utilisateur peut soumettre un UUID via api/verify.
+ La vérification se fait selon :
   ### le nombre de vérifications déjà faites (>5 document expiré )
   ### la date de signature dépasse un an ( document expiré ) 
+ la vérification renvoie depuis la base de donnée le nom , prenom du signant , et le status du document .
+ chaque fois une vérification est faite , le compteur s'incrémente .


# Technologies utilisées : 
+ Java 21

+ Spring Boot 3.5.0

+ Spring Data JPA

+ Base de données MySQL 

+ ZXing (com.google.zxing) pour la génération de QR code

+ Postman pour le test

# endpoints 

➕  POST /api/signer : 

### Requete JSON :
{
  "nom": "Ayoub",
  "prenom": "Jarhni"
}
### Réponse JSON :
{
    "uuid": "4ff202e4-3d1f-4e7f-963f-ad776d8238d9",
    "qrCode": "iVBORw0KGgoAAAANSUhEUgAAAPoAAAD6AQAAAACgl2eQAAABeUlEQVR4Xu2YUW4DIQxEkTjAHomrcyQOgERnxt4oJW2+dySsaAv45WPWjCEt63v0sq9scYCIA0QcIOIAEc8BZmFgNkurWLs655cbwHGfV18DicZBLnoBlMaBZFbqLaaAFt+flsAajZNR/pP5eIBDVApmqVq+F60A2Z81uj+7/R2ADJUJ65GKMAIok9Lg/VVHYxMYHJsByr46QB2s1O6sxwNkCiuV3me9+rtMCyCMj3UdKNx7YswA/EWBolKEueXuhA8QoqI6CW/vwQOAccqSX1S1PpV1A3JG76Rx9vfwfICXE1534f3Fp6b5HR8g/aJWjCnEfrwHD6CqCUMdNYphwgzIJ8Qu1qtlTzYDIIoaow+gZEW/qryAiLsJpN6YGgHURXXwDq+LctDvYlkAVMTqVHZjGn/ep6QV0ClNV8QZW64o4QgotSRTJfME+B8GtuK4aP1ZrGcDHE4qhTrAUmoHaI9N+gXnY1ufx6ID8CUOEHGAiANEHCDCA/gBXjGXQH7QeXQAAAAASUVORK5CYII="
}


➕   GET /api/document/verify?uuid={uuid} : 

### Requete JSON :
http://localhost:8080/api/verify?uuid=0a08412b-2d20-42d6-9499-391998b51a95

### Réponse JSON :

{
    "nom": "Ayoub",
    "prenom": "Jarhni",
    "status": "valid"
}



# Règles métier implémentées
Un UUID est unique par document signé.

QR Code encode uniquement l'UUID.

5 vérifications maximum par document.

La signature expire après un an.

En cas d’UUID inexistant : une erreur 404 est retournée.
