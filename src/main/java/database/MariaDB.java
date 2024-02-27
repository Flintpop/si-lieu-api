
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

}
