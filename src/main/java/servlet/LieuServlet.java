package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.persistence.PersistenceException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lieu;
import mariadbPojo.LieuPojo;
import validation.LieuValidateur;
import validation.ValidateurResultat;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LieuServlet", urlPatterns = "/lieux/*")
public class LieuServlet extends HttpServlet {

  private final Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    Integer lieuId = ServletUtils.extraireEtValiderId(request.getPathInfo(), response, false);
    if (lieuId == null) {
      return; // La validation de l'ID a échoué.
    }

    if (lieuId == -1) {
      List<LieuPojo> lieux = Lieu.getListeLieux();
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_OK, gson.toJson(lieux));
      return;
    }

    LieuPojo lieuEntity = Lieu.getLieuById(lieuId);
    if (lieuEntity == null) {
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_NOT_FOUND, "{\"error\":\"Lieu non trouvé.\"}");
    } else {
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_OK, gson.toJson(lieuEntity));
    }
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String jsonBody = ServletUtils.lireCorpsRequete(request);
    try {
      LieuPojo lieuEntity = gson.fromJson(jsonBody, LieuPojo.class);
      LieuValidateur lieuValidateur = new LieuValidateur();
      ValidateurResultat validateurResultat = lieuValidateur.valider(lieuEntity);
      LieuPojo createdLieu = Lieu.creerLieu(lieuEntity);

      if (!validateurResultat.isValid()) {
        Map<String, Object> errors = new HashMap<>();
        errors.put("errors", validateurResultat.getErrorMessages());
        ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_BAD_REQUEST, gson.toJson(errors));        return;
      }

      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_CREATED, gson.toJson(createdLieu));
    } catch (JsonSyntaxException e) {
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_BAD_REQUEST, "{\"error\":\"Format de données incorrect.\"}");
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Integer lieuId = ServletUtils.extraireEtValiderId(request.getPathInfo(), response, true);
    if (lieuId == null || lieuId < 0) {
      return; // La validation de l'ID a échoué
    }

    String jsonBody = ServletUtils.lireCorpsRequete(request);
    try {
      LieuPojo lieuToUpdate = gson.fromJson(jsonBody, LieuPojo.class);

      LieuPojo updatedLieu = Lieu.mettreAJourLieu(lieuId, lieuToUpdate);
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_OK, gson.toJson(updatedLieu));
    } catch (JsonSyntaxException | PersistenceException e) {
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_BAD_REQUEST, "{\"error\":\"Format de données incorrect.\"}");
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    Integer lieuId = ServletUtils.extraireEtValiderId(request.getPathInfo(), response, true);
    if (lieuId == null || lieuId < 0) {
      return; // La validation de l'ID a échoué.
    }

    try {
      boolean deleted = Lieu.supprimerLieu(lieuId);
      if (deleted) {
        ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_NO_CONTENT, "{" + "\"message\":\"Lieu supprimé avec succès.\"}");
      } else {
        ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_NOT_FOUND, "{\"error\":\"Lieu non trouvé.\"}");
      }
    } catch (Exception e) {
      ServletUtils.envoyerReponseJson(response, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "{\"error\":\"Une erreur s'est produite.\"}");
    }
  }
}
