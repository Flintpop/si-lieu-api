package mariadbPojo;

import jakarta.persistence.*;

import java.sql.Date;

@Entity
@Table(name = "membre", schema = "uboeventflow_bdd")
public class MembrePojo {
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Id
  @Column(name = "id", nullable = false)
  private int id;
  @Basic
  @Column(name = "nom", nullable = false)
  private String nom;
  @Basic
  @Column(name = "prenom", nullable = false)
  private String prenom;
  @Basic
  @Column(name = "dateNaissance", nullable = false)
  private Date dateNaissance;
  @Basic
  @Column(name = "adresse", nullable = false)
  private String adresse;
  @Basic
  @Column(name = "email", nullable = false)
  private String email;
  @Basic
  @Column(name = "motDePasse", nullable = false)
  private String motDePasse;

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

  public String getPrenom() {
    return prenom;
  }

  public void setPrenom(String prenom) {
    this.prenom = prenom;
  }

  public Date getDateNaissance() {
    return dateNaissance;
  }

  public void setDateNaissance(Date dateNaissance) {
    this.dateNaissance = dateNaissance;
  }

  public String getAdresse() {
    return adresse;
  }

  public void setAdresse(String adresse) {
    this.adresse = adresse;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getMotDePasse() {
    return motDePasse;
  }

  public void setMotDePasse(String motDePasse) {
    this.motDePasse = motDePasse;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    MembrePojo that = (MembrePojo) o;

    if (id != that.id) return false;
    if (nom != null ? !nom.equals(that.nom) : that.nom != null) return false;
    if (prenom != null ? !prenom.equals(that.prenom) : that.prenom != null) return false;
    if (dateNaissance != null ? !dateNaissance.equals(that.dateNaissance) : that.dateNaissance != null) return false;
    if (adresse != null ? !adresse.equals(that.adresse) : that.adresse != null) return false;
    if (email != null ? !email.equals(that.email) : that.email != null) return false;
    if (motDePasse != null ? !motDePasse.equals(that.motDePasse) : that.motDePasse != null) return false;

    return true;
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (nom != null ? nom.hashCode() : 0);
    result = 31 * result + (prenom != null ? prenom.hashCode() : 0);
    result = 31 * result + (dateNaissance != null ? dateNaissance.hashCode() : 0);
    result = 31 * result + (adresse != null ? adresse.hashCode() : 0);
    result = 31 * result + (email != null ? email.hashCode() : 0);
    result = 31 * result + (motDePasse != null ? motDePasse.hashCode() : 0);
    return result;
  }
}
