package servlet;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.ConstraintViolationException;
import model.Lieu;
import mariadbPojo.LieuPojo;
import routes.Routes;
import validation.IdValidateur;
import validation.LieuValidateur;
import validation.ValidateurResultat;

import java.io.BufferedReader;
import java.io.IOException;
import java.sql.SQLIntegrityConstraintViolationException;
import java.util.Arrays;
import java.util.List;

@WebServlet(name = "LieuServlet", urlPatterns = "/lieux/*")
public class LieuServlet extends HttpServlet {

  private final Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String pathInfo = request.getPathInfo();
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");

    if (pathInfo != null && pathInfo.length() > 1) {
      try {
        int id = Integer.parseInt(pathInfo.substring(1));
        LieuPojo lieuEntity = Lieu.getLieuById(id);

        if (lieuEntity == null) {
          response.setStatus(HttpServletResponse.SC_NOT_FOUND);
          response.getWriter().write("{\"error\":\"Lieu non trouvé.\"}");
        } else {
          response.getWriter().write(gson.toJson(lieuEntity));
        }
      } catch (NumberFormatException e) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"Format de l'ID invalide.\"}");
      }
    } else {
      List<LieuPojo> lieux = Lieu.getListeLieux();
      response.getWriter().write(gson.toJson(lieux));
      response.setStatus(HttpServletResponse.SC_OK);
    }
    response.getWriter().flush();
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
    String pathInfo = request.getPathInfo();
    if (pathInfo == null || pathInfo.length() <= 1) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"ID du lieu requis.\"}");
      return;
    }

    int lieuId;
    try {
      lieuId = Integer.parseInt(pathInfo.substring(1));
    } catch (NumberFormatException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de l'ID invalide.\"}");
      return;
    }

    StringBuilder jsonBody = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      reader.lines().forEach(jsonBody::append);
    }

    try {
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
    } catch (JsonSyntaxException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de données incorrect.\"}");
    }
  }

  @Override
  protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String pathInfo = request.getPathInfo();
    if (pathInfo == null || pathInfo.length() <= 1) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"ID du lieu requis.\"}");
      return;
    }

    int lieuId;
    try {
      lieuId = Integer.parseInt(pathInfo.substring(1));
    } catch (NumberFormatException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de l'ID invalide.\"}");
      return;
    }

    try {
      boolean deleted = Lieu.supprimerLieu(lieuId);
      if (deleted) {
        response.setStatus(HttpServletResponse.SC_NO_CONTENT);
      } else {
        response.setStatus(HttpServletResponse.SC_NOT_FOUND);
        response.getWriter().write("{\"error\":\"Lieu non trouvé.\"}");
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
      // Log the exception
      response.getWriter().write("{\"error\":\"Une erreur s'est produite.\"}");
    }
  }
}
