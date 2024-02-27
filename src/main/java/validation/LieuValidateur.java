package validation;

import mariadbPojo.LieuPojo;
import java.util.ArrayList;
import java.util.List;

public class LieuValidateur implements Validateur<LieuPojo> {

  @Override
  public ValidateurResultat valider(LieuPojo input) {
    List<String> errorMessages = new ArrayList<>();
    boolean isValid = true;

    // Validation du nom
    if (input.getNom() == null || input.getNom().isEmpty()) {
      errorMessages.add("Le nom du lieu ne peut pas être vide.");
      isValid = false;
    } else if (input.getNom().length() > 255) {
      errorMessages.add("Le nom du lieu ne peut pas dépasser 255 caractères.");
      isValid = false;
    }

    // Validation de l'adresse
    if (input.getAdresse() == null || input.getAdresse().isEmpty()) {
      errorMessages.add("L'adresse du lieu ne peut pas être vide.");
      isValid = false;
    } else if (input.getAdresse().length() > 500) {
      errorMessages.add("L'adresse du lieu ne peut pas dépasser 500 caractères.");
      isValid = false;
    }

    // Validation de la latitude
//    if (input.getLatitude() == null) {
//      errorMessages.add("La latitude ne peut pas être vide.");
//      isValid = false;
//    } else if (input.getLatitude() < -90 || input.getLatitude() > 90) {
//      errorMessages.add("La latitude doit être comprise entre -90 et 90.");
//      isValid = false;
//    }
//
//    // Validation de la longitude
//    if (input.getLongitude() == null) {
//      errorMessages.add("La longitude ne peut pas être vide.");
//      isValid = false;
//    } else if (input.getLongitude() < -180 || input.getLongitude() > 180) {
//      errorMessages.add("La longitude doit être comprise entre -180 et 180.");
//      isValid = false;
//    }

    return new ValidateurResultat(isValid, errorMessages);
  }
}
