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

    // Validation de la capacité d'accueil
    if (input.getCapaciteAccueil() <= 0) {
      errorMessages.add("La capacité d'accueil ne peut pas être négative ou nulle.");
      isValid = false;
    }

    return new ValidateurResultat(isValid, errorMessages);
  }
}
