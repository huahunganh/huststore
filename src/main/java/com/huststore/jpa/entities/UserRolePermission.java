

package com.huststore.jpa.entities;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "app_user_role_permissions")
public class UserRolePermission
  implements Serializable {

  private static final long serialVersionUID = 21L;

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "user_role_permission_id", nullable = false)
  private Long id;
  @JoinColumn(name = "permission_id", referencedColumnName = "permission_id", updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private Permission permission;
  @JoinColumn(name = "user_role_id", referencedColumnName = "user_role_id", updatable = false)
  @ManyToOne(optional = false, fetch = FetchType.LAZY)
  private UserRole userRole;

  public UserRolePermission() { }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Permission getPermission() {
    return permission;
  }

  public void setPermission(Permission permission) {
    this.permission = permission;
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
    UserRolePermission that = (UserRolePermission) o;
    return Objects.equals(id, that.id) &&
        Objects.equals(permission, that.permission) &&
        Objects.equals(userRole, that.userRole);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, permission, userRole);
  }

  @Override
  public String toString() {
    return "UserRolePermission{" +
        "id=" + id +
        ", permission=" + permission +
        ", userRole=" + userRole +
        '}';
  }
}
