package mariadbPojo;

import jakarta.persistence.*;

@Entity
@Table(name = "lieu", schema = "uboeventflow_bdd")
public class LieuPojo {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private int id;
  @Basic
  @Column(name = "nom", nullable = false)
  private String nom;
  @Basic
  @Column(name = "adresse", nullable = false)
  private String adresse;
  @Basic
  @Column(name = "capaciteAccueil", nullable = false)
  private int capaciteAccueil;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getNom() {
    return nom;
  }

  public void setNom(String nom) {
    this.nom = nom;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public int getCapaciteAccueil() {
    return capaciteAccueil;
  }

  public void setCapaciteAccueil(int capaciteAccueil) {
    this.capaciteAccueil = capaciteAccueil;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    LieuPojo that = (LieuPojo) o;

    if (id != that.id) return false;
    if (capaciteAccueil != that.capaciteAccueil) return false;
    if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
    if (adresse != null ? !adresse.equals(that.adresse) : that.adresse != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (nom != null ? nom.hashCode() : 0);
    result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
    result = 31 * result + capaciteAccueil;
    return result;
  }
}
