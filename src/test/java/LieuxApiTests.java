import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.junit.jupiter.api.Test;

public class LieuxApiTests {

  @Test
  public void testGetAllLocations() {
    given().when().get("http://localhost:8082/lieux")
            .then().statusCode(200)
            .body("", hasSize(greaterThan(0))); // Assure que la liste n'est pas vide
  }

  @Test
  public void testCreateLocation() {
    String newLocationJson = """
                {
                    "nom": "Nouveau Lieu",
                    "adresse": "123 Rue Exemple",
                    "capaciteAccueil": 105
                }
                """;

    given().contentType("application/json")
            .body(newLocationJson)
            .when().post("http://localhost:8082/lieux")
            .then().statusCode(201)
            .log().ifError();
  }

  @Test
  public void testGetLocationWithInvalidId() {
    int invalidId = 999999; // Un ID qui n'existe pas
    given().pathParam("lieuId", invalidId)
            .when().get("http://localhost:8082/lieux/{lieuId}")
            .then().statusCode(404) // Supposant que l'API renvoie 404 pour un ID non trouvé
            .log().ifError(); // Logge la réponse en cas d'erreur
  }

  @Test
  public void testCreateLocationWithBadBody() {
    String badLocationJson = """
                {
                    "nom": "",
                    "adresse": "123 Rue Exemple"
                    // Manque "capaciteAccueil"
                }
                """;

    given().contentType("application/json")
            .body(badLocationJson)
            .when().post("http://localhost:8082/lieux")
            .then().statusCode(400) // Supposant que l'API renvoie 400 pour un body incorrect
            .log().ifError(); // Logge la réponse en cas d'erreur
  }
}
