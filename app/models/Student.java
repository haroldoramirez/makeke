package models;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import play.data.format.Formatters;
import play.data.validation.Constraints.Email;
import play.data.validation.Constraints.Required;
import play.db.ebean.Model;

@Entity
public class Student extends Model {
  private static final long serialVersionUID = 3881518174354436477L;

  @Id
  private Long primaryKey;
  
  @Required
  private String studentId;
  
  @Required
  private String firstName;
  
  @Required
  private String lastName;
  
  @Email
  private String email;
  
  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Offer> offers = new ArrayList<>();
  
  @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
  private List<Request> requests = new ArrayList<>();
  
  public Student(String studentId, String firstName, String lastName, String email) {
    this.studentId = studentId;
    this.firstName = firstName;
    this.lastName = lastName;
    this.email = email;
    
    Formatters.register(models.Student.class, new Formatters.SimpleFormatter<models.Student>() {
      @Override
      public models.Student parse(String text, Locale locale) throws ParseException {
        models.Student student = models.Student.find().where().eq("studentId", text).findUnique();
        if (student == null) {
          throw new ParseException("Could not find matching Student", 0);
        }
        return student;
      }

      @Override
      public String print(models.Student t, Locale locale) {
        return t.getStudentId();
      }
    });
  }
  
  public static Finder<Long, Student> find() {
    return new Finder<>(Long.class, Student.class);
  }
  
  @Override
  public String toString() {
    return String.format("[Student %s %s %s %s]", this.studentId, this.firstName, this.lastName,
        this.email);
  }

  public Long getPrimaryKey() {
    return primaryKey;
  }

  public void setPrimaryKey(Long primaryKey) {
    this.primaryKey = primaryKey;
  }

  public String getStudentId() {
    return studentId;
  }

  public void setStudentId(String studentId) {
    this.studentId = studentId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public List<Offer> getOffers() {
    return offers;
  }

  public void setOffers(List<Offer> offers) {
    this.offers = offers;
  }

  public List<Request> getRequests() {
    return requests;
  }

  public void setRequests(List<Request> requests) {
    this.requests = requests;
  }
}
