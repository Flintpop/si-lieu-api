package servlet;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import mariadbPojo.LieuPojo;

import java.io.BufferedReader;
import java.io.IOException;

public class ServletUtils {
  public static String lireCorpsRequete(HttpServletRequest request) throws IOException {
    StringBuilder jsonBody = new StringBuilder();
    try (BufferedReader reader = request.getReader()) {
      String line;
      while ((line = reader.readLine()) != null) {
        jsonBody.append(line);
      }
    }
    return jsonBody.toString();
  }

  /**
   * Extrait et valide l'ID de la requête HTTP.
   * **Modifie HttpServletResponse si erreur**. Si l'ID est invalide, une réponse -1, ou null
   * si c'est une erreur qui n'est pas un paramètre manquant.
   * @param pathInfo  Le chemin de la requête.
   * @param response La réponse HTTP.
   * @param expectedId Si true, une erreur sera envoyée si l'ID est manquant.
   * @return L'ID extrait, -1 ou null si erreur ou pas de paramètre envoyé.
   * @throws IOException Si une erreur d'entrée/sortie survient.
   */
  public static Integer extraireEtValiderId(String pathInfo, HttpServletResponse response, boolean expectedId) throws IOException {
    if (pathInfo == null || pathInfo.length() <= 1) {
      if (expectedId) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        response.getWriter().write("{\"error\":\"L'ID du commentaire est manquant ou invalide.\"}");
      }
      return -1;
    }

    try {
      return Integer.parseInt(pathInfo.substring(1));
    } catch (NumberFormatException e) {
      response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
      response.getWriter().write("{\"error\":\"Format de l'ID invalide, n'est pas un chiffre.\"}");
      return null;
    }
  }

  /**
   * Envoie une réponse JSON avec le code d'état HTTP spécifié.
   *
   * @param response   La réponse HTTP.
   * @param statusCode Le code d'état HTTP.
   * @param message    Le message à envoyer.
   * @throws IOException Si une erreur d'entrée/sortie survient.
   */
  public static void envoyerReponseJson(HttpServletResponse response, int statusCode, String message) throws IOException {
    response.setContentType("application/json");
    response.setCharacterEncoding("UTF-8");
    response.setStatus(statusCode);
    response.getWriter().write(message);
    response.getWriter().flush();
  }


  public static LieuPojo extraireEtValiderCommentaire(HttpServletRequest request, HttpServletResponse response) throws IOException {
    String pathInfo = request.getPathInfo();
    Integer id = extraireEtValiderId(pathInfo, response, true);
    if (id == null) {
      return null; // Une réponse a déjà été envoyée par extraireEtValiderId
    }

    LieuPojo lieuPojo = model.Lieu.getLieuById(id);
    if (lieuPojo == null) {
      envoyerReponseJson(response, HttpServletResponse.SC_NOT_FOUND, "{\"error\":\"Commentaire non trouvé.\"}");
      return null;
    }

    return lieuPojo;
  }
}
