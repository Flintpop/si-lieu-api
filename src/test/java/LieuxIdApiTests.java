import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

public class LieuxIdApiTests {

  @Test
  public void testGetLocationById() {
    int testId = 1; // Assurez-vous que cet ID existe dans votre base de données ou mock
    given().pathParam("lieuId", testId)
            .when().get("http://localhost:8082/lieux/{lieuId}")
            .then().statusCode(200)
            .body("id", equalTo(testId));
  }

  @Test
  public void testUpdateLocation() {
    int testId = 1;
    String updatedLocationJson = """
                {
                    "id": 1,
                    "nom": "Lieu Mis à Jour",
                    "adresse": "321 Rue Exemple",
                    "capaciteAccueil": 200
                }
                """;

    given().contentType("application/json")
            .pathParam("lieuId", testId)
            .body(updatedLocationJson)
            .when().put("http://localhost:8082/lieux/{lieuId}")
            .then().statusCode(200);
  }

  @Test
  public void testDeleteLocation() {
    int testId = 1; // Assurez-vous que cet ID existe et est supprimable
    given().pathParam("lieuId", testId)
            .when().delete("http://localhost:8082/lieux/{lieuId}")
            .then().statusCode(204);
  }
}
