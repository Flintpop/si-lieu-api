package mariadbPojo;

import jakarta.persistence.*;

import java.sql.Timestamp;
import java.util.Objects;

@Entity
@Table(name = "evenement", schema = "uboeventflow_bdd")
public class EvenementPojo {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private int id;
  @Basic
  @Column(name = "nom", nullable = false)
  private String nom;
  @Basic
  @Column(name = "dateHeureDebut", nullable = false)
  private Timestamp dateHeureDebut;
  @Basic
  @Column(name = "dateHeureFin", nullable = false)
  private Timestamp dateHeureFin;
  @Basic
  @Column(name = "maxParticipant", nullable = false)
  private int maxParticipant;
  @Basic
  @Column(name = "lieuId", nullable = false)
  private int lieuId;
  @ManyToOne
  @JoinColumn(name = "lieuId", referencedColumnName = "id", nullable = false)
  private LieuPojo lieuByLieuId;

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

  public Timestamp getDateHeureDebut() {
    return dateHeureDebut;
  }

  public void setDateHeureDebut(Timestamp dateHeureDebut) {
    this.dateHeureDebut = dateHeureDebut;
  }

  public Timestamp getDateHeureFin() {
    return dateHeureFin;
  }

  public void setDateHeureFin(Timestamp dateHeureFin) {
    this.dateHeureFin = dateHeureFin;
  }

  public int getMaxParticipant() {
    return maxParticipant;
  }

  public void setMaxParticipant(int maxParticipant) {
    this.maxParticipant = maxParticipant;
  }

  public int getLieuId() {
    return lieuId;
  }

  public void setLieuId(int lieuId) {
    this.lieuId = lieuId;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EvenementPojo that = (EvenementPojo) o;

    if (id != that.id) return false;
    if (maxParticipant != that.maxParticipant) return false;
    if (lieuId != that.lieuId) return false;
    if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
    if (dateHeureDebut != null ? !dateHeureDebut.equals(that.dateHeureDebut) : that.dateHeureDebut != null)
      return false;
    return Objects.equals(dateHeureFin, that.dateHeureFin);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (nom != null ? nom.hashCode() : 0);
    result = 31 * result + (dateHeureDebut != null ? dateHeureDebut.hashCode() : 0);
    result = 31 * result + (dateHeureFin != null ? dateHeureFin.hashCode() : 0);
    result = 31 * result + maxParticipant;
    result = 31 * result + lieuId;
    return result;
  }

  public LieuPojo getLieuByLieuId() {
    return lieuByLieuId;
  }

  public void setLieuByLieuId(LieuPojo lieuByLieuId) {
    this.lieuByLieuId = lieuByLieuId;
  }
}
