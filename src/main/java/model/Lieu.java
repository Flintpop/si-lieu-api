package model;

import database.MariaDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceException;
import jakarta.persistence.TypedQuery;
import mariadbPojo.LieuPojo;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.List;

public class Lieu {

  public static List<LieuPojo> getListeLieux() {
    EntityManager em = MariaDB.getEntityManager();
    try {
      TypedQuery<LieuPojo> query = em.createQuery("SELECT l FROM LieuPojo l", LieuPojo.class);
      return query.getResultList();
    } finally {
      em.close();
    }
  }

  public static LieuPojo getLieuById(int id) {
    EntityManager em = MariaDB.getEntityManager();
    try {
      return em.find(LieuPojo.class, id);
    } finally {
      em.close();
    }
  }

  public static LieuPojo creerLieu(LieuPojo lieu) {
    EntityManager em = MariaDB.getEntityManager();
    try {
      em.getTransaction().begin();
      em.persist(lieu);
      em.getTransaction().commit();
      return lieu;
    } finally {
      em.close();
    }
  }

  public static LieuPojo mettreAJourLieu(int id, LieuPojo lieuMisAJour) {
    EntityManager em = MariaDB.getEntityManager();
    try {
      LieuPojo lieu = em.find(LieuPojo.class, id);
      if (lieu != null) {
        System.out.println("Capacité actuelle: " + lieu.getCapaciteAccueil());
        em.getTransaction().begin();
        lieu.setNom(lieuMisAJour.getNom());
        lieu.setAdresse(lieuMisAJour.getAdresse());
        lieu.setCapaciteAccueil(lieuMisAJour.getCapaciteAccueil());
        System.out.println("Nouveau nom: " + lieu.getNom());
        System.out.println("Nouvelle adresse: " + lieu.getAdresse());
        System.out.println("Nouvelle capacité: " + lieu.getCapaciteAccueil());
        em.getTransaction().commit();
        return lieu;
      }
      return null;
    } finally {
      em.close();
    }
  }

  public static boolean supprimerLieu(int id) throws SQLIntegrityConstraintViolationException {
    EntityManager em = MariaDB.getEntityManager();
    try {
      LieuPojo lieu = em.find(LieuPojo.class, id);
      if (lieu != null) {
        em.getTransaction().begin();
        em.remove(lieu);
        em.getTransaction().commit();
        return true;
      }
    } catch (PersistenceException e) {
      if (e.getCause() instanceof SQLIntegrityConstraintViolationException) {
        System.out.println("Impossible de supprimer le lieu, il est utilisé par un événement.");
        throw new SQLIntegrityConstraintViolationException();
      }
    } finally {
      em.close();
    }
    return false;
  }
}
