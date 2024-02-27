
package database;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import mariadbPojo.EvenementPojo;
import mariadbPojo.LieuPojo;
import mariadbPojo.MembrePojo;

public class MariaDB {
  private static final String PERSISTENCE_UNIT_NAME = "lieuPU";
  private static volatile EntityManagerFactory entityManagerFactory = null;

  private MariaDB() {
    // Constructeur privé pour éviter l'instanciation
  }

  public static EntityManager getEntityManager() {
    if (entityManagerFactory == null) {
      synchronized (MariaDB.class) {
        if (entityManagerFactory == null) {
          entityManagerFactory = Persistence.createEntityManagerFactory(PERSISTENCE_UNIT_NAME);
          System.out.println("Connexion à MariaDB établie.");
        }
      }
    }
    return entityManagerFactory.createEntityManager();
  }

  // Exemples d'utilisation
  public static EvenementPojo findEvenementById(int id) {
    EntityManager entityManager = getEntityManager();
    return entityManager.find(EvenementPojo.class, id);
  }

  public static LieuPojo findLieuById(int id) {
    EntityManager entityManager = getEntityManager();
    return entityManager.find(LieuPojo.class, id);
  }

  public static MembrePojo findMembreById(int id) {
    EntityManager entityManager = getEntityManager();
    return entityManager.find(MembrePojo.class, id);
  }

  // Ajoutez d'autres méthodes de gestion de la base de données ici
}
