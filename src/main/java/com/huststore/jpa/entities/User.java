

package com.huststore.jpa.entities;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(
  name = "app_users",
  indexes = {
    @Index(columnList = "user_name")
  })
public class User
  implements Serializable {

  private static final long serialVersionUID = 19L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_id", nullable = false)
  private Long id;
  @Size(min = 1, max = 50)
  @Column(name = "user_name", nullable = false, unique = true)
  private String name;
  @Size(min = 1, max = 100)
  @Column(name = "user_password", nullable = false)
  private String password;
  @JoinColumn(name = "person_id", referencedColumnName = "person_id")
  @ManyToOne(fetch = FetchType.LAZY)
  private Person person;
  @JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id")
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UserRole userRole;

  public User() { }

  public User(User source) {
    this.id = source.id;
    this.name = source.name;
    this.password = source.password;
    this.person = source.person;
    this.userRole = source.userRole;
  }

  public User(Long id, String name, String password, Person person, UserRole userRole) {
    this.id = id;
    this.name = name;
    this.password = password;
    this.person = person;
    this.userRole = userRole;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getPassword() {
    return password;
  }

  public void setPassword(String password) {
    this.password = password;
  }

  public Person getPerson() {
    return person;
  }

  public void setPerson(Person person) {
    this.person = person;
  }

  public UserRole getUserRole() {
    return userRole;
  }

  public void setUserRole(UserRole userRole) {
    this.userRole = userRole;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(id, user.id) &&
        Objects.equals(name, user.name) &&
        Objects.equals(password, user.password) &&
        Objects.equals(person, user.person) &&
        Objects.equals(userRole, user.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, password, person, userRole);
  }

  @Override
  public String toString() {
    return "User{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", password='" + password + '\'' +
        ", person=" + person +
        ", userRole=" + userRole +
        '}';
  }
}
