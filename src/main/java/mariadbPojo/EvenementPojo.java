package mariadbPojo;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;
import java.util.Objects;

@Getter
@Setter
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

}
