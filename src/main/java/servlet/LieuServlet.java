package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.Lieu;
import mariadbPojo.LieuPojo;
import routes.Routes;
import validation.LieuValidateur;
import validation.ValidateurResultat;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@WebServlet(name = "LieuServlet", value = Routes.lieux)
public class LieuServlet extends HttpServlet {

  private final Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    String lieuIdParam = request.getParameter("lieuId");
    if (lieuIdParam != null) {
      try {
        UUID lieuId = UUID.fromString(lieuIdParam);
        LieuPojo lieuEntity = Lieu.getLieuById(lieuId);
        if (lieuEntity == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          response.getWriter().write("{\"error\":\"Lieu non trouvé.\"}");
        } else {
          response.getWriter().write(gson.toJson(lieuEntity));
        }
      } catch (IllegalArgumentException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"Format de l'ID invalide.\"}");
      }
      return;
    }

    List<LieuPojo> lieux = Lieu.getListeLieux();
    response.getWriter().write(gson.toJson(lieux));
    response.setStatus(HttpServletResponse.SC_OK);
  }

  @Override
  protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
    StringBuilder jsonBody = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBody.append(line);
      }
    }

    try {
      LieuPojo lieuEntity = gson.fromJson(jsonBody.toString(), LieuPojo.class);
      LieuValidateur validateur = new LieuValidateur();
      ValidateurResultat resultat = validateur.valider(lieuEntity);

      if (!resultat.isValid()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(gson.toJson(resultat.getErrorMessages()));
        return;
      }

      LieuPojo createdLieu = Lieu.creerLieu(lieuEntity);
      response.setStatus(HttpServletResponse.SC_CREATED);
      response.getWriter().write(gson.toJson(createdLieu));
    } catch (JsonSyntaxException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de données incorrect.\"}");
    }
  }

  @Override
  protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String lieuIdParam = request.getParameter("lieuId");
    if (lieuIdParam == null || lieuIdParam.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"ID du lieu requis.\"}");
      return;
    }

    StringBuilder jsonBody = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBody.append(line);
      }
    }

    try {
      UUID lieuId = UUID.fromString(lieuIdParam);
      LieuPojo lieuToUpdate = gson.fromJson(jsonBody.toString(), LieuPojo.class);

      LieuValidateur validateur = new LieuValidateur();
      ValidateurResultat resultat = validateur.valider(lieuToUpdate);

      if (!resultat.isValid()) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write(gson.toJson(resultat.getErrorMessages()));
        return;
      }

      LieuPojo updatedLieu = Lieu.mettreAJourLieu(lieuId, lieuToUpdate);
      if (updatedLieu == null) {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("{\"error\":\"Lieu non trouvé.\"}");
      } else {
        response.getWriter().write(gson.toJson(updatedLieu));
        response.setStatus(HttpServletResponse.SC_OK);
      }
    } catch (JsonSyntaxException | IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de données incorrect ou ID invalide.\"}");
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String lieuIdParam = request.getParameter("lieuId");
    if (lieuIdParam == null || lieuIdParam.isEmpty()) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"ID du lieu requis.\"}");
      return;
    }

    try {
      UUID lieuId = UUID.fromString(lieuIdParam);
      boolean deleted = Lieu.supprimerLieu(lieuId);
      if (deleted) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
      } else {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("{\"error\":\"Lieu non trouvé.\"}");
      }
    } catch (IllegalArgumentException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de l'ID invalide.\"}");
    }
  }

}
