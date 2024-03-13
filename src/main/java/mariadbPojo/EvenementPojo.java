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
  @Column(name = "titre", nullable = false)
  private String titre;
  @Basic
  @Column(name = "dateHeureDebut", nullable = false)
  private Timestamp dateHeureDebut;
  @Basic
  @Column(name = "dateHeureFin", nullable = false)
  private Timestamp dateHeureFin;

  @ManyToOne
  @JoinColumn(name = "lieuId", referencedColumnName = "id", nullable = false)
  private LieuPojo lieu;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    EvenementPojo that = (EvenementPojo) o;

    if (id != that.id) return false;
    if (dateHeureDebut != null ? !dateHeureDebut.equals(that.dateHeureDebut) : that.dateHeureDebut != null)
      return false;
    return Objects.equals(dateHeureFin, that.dateHeureFin);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (dateHeureDebut != null ? dateHeureDebut.hashCode() : 0);
    result = 31 * result + (dateHeureFin != null ? dateHeureFin.hashCode() : 0);
    return result;
  }

  public LieuPojo getLieu() {
    return lieu;
  }

  public void setLieu(LieuPojo lieuByLieuId) {
    this.lieu = lieuByLieuId;
  }
}
