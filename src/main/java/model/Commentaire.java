package model;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.DeleteResult;
import database.MongoDB;
import mariadbPojo.LieuPojo;
import org.bson.conversions.Bson;
import validation.CommentaireValidateur;
import validation.ValidateurResultat;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;
import static database.DatabaseUtils.convertToList;

/**
 * Classe qui
 */
public class Commentaire {
  public static List<LieuPojo> getListeCommentaires() {
    MongoCollection<LieuPojo> collection = MongoDB.getCollection();

    return convertToList(collection);
  }

  public static LieuPojo getCommentaireById(int id) {
    MongoCollection<LieuPojo> collection = MongoDB.getCollection();
    Bson filter = eq("_id", id);
    return collection.find(filter).first();
  }

  /**
   * Méthode pour ajouter un commentaire dans la bdd.
   *
   * @param jsonBody le jsonBody de la requête POST, qui contient le commentaire à ajouter.
   * @return un objet ValidateurResultat qui contient le résultat de la validation du commentaire.
   * @throws JsonSyntaxException si le jsonBody n'est pas valide en termes de syntaxe (c.-à-d. int au lieu de string)
   */
  public static ValidateurResultat ajouterCommentaire(String jsonBody) throws JsonSyntaxException {
    Gson gson = new Gson();
    LieuPojo lieuPojo = gson.fromJson(jsonBody, LieuPojo.class);
    lieuPojo.setId(getNextCommentaireId());
    lieuPojo.setDateMessage(Instant.now().toString());
    CommentaireValidateur commentaireValidateur = new CommentaireValidateur();
    ValidateurResultat validationResult = commentaireValidateur.valider(lieuPojo);

    if (validationResult.isValid()) {
      MongoDB.getCollection().insertOne(lieuPojo);
    }

    return validationResult;
  }

  // Méthode pour récupérer le prochain id de commentaire à ajouter dans la bdd
  private static int getNextCommentaireId() {
    List<LieuPojo> commentaires = getListeCommentaires();
    int maxId = 0;
    for (LieuPojo commentaire : commentaires) {
      if (commentaire.getId() > maxId) {
        maxId = commentaire.getId();
      }
    }
    return maxId + 1;
  }

  // TODO: À tester
  public static void deleteCommentaire(int id) {
    MongoCollection<LieuPojo> collection = MongoDB.getCollection();
    Bson filter = eq("_id", id);
    DeleteResult deleteResult = collection.deleteOne(filter);

    if (deleteResult.getDeletedCount() == 0) {
      throw new IllegalArgumentException("Commentaire non trouvé.");
    }
  }

  public static void updateCommentaire(int id, String jsonBody) throws JsonSyntaxException {
    LieuPojo lieuPojo = getCommentaireById(id);
    if (lieuPojo != null) {
      Gson gson = new Gson();
      TexteWrapper texte = gson.fromJson(jsonBody, TexteWrapper.class);
      lieuPojo.setTexte(texte.getTexte());

      MongoCollection<LieuPojo> collection = MongoDB.getCollection();
      Bson filter = eq("_id", id);
      collection.replaceOne(filter, lieuPojo);
    }
  }

  public static ArrayList<LieuPojo> getCommentairesByEvenementId(int evenementId) {
    MongoCollection<LieuPojo> collection = MongoDB.getCollection();
    Bson filter = eq("evenementId", evenementId);
    return convertToList(collection.find(filter));
  }
}
