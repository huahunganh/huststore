

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.QUserRole;
import com.huststore.jpa.entities.UserRole;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UserRolesSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<UserRole> {

  @Override
  public QUserRole getBasePath() {
    return QUserRole.userRole;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name",  QUserRole.userRole.name.asc()
    );
  }
}
