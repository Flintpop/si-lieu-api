package model;

import database.MariaDB;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import mariadbPojo.LieuPojo;

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
        em.getTransaction().begin();
        lieu.setNom(lieuMisAJour.getNom());
        lieu.setAdresse(lieuMisAJour.getAdresse());
        em.getTransaction().commit();
        return lieu;
      }
      return null;
    } finally {
      em.close();
    }
  }

  public static boolean supprimerLieu(int id) {
    EntityManager em = MariaDB.getEntityManager();
    try {
      LieuPojo lieu = em.find(LieuPojo.class, id);
      if (lieu != null) {
        em.getTransaction().begin();
        em.remove(lieu);
        em.getTransaction().commit();
        return true;
      }
      return false;
    } finally {
      em.close();
    }
  }
}