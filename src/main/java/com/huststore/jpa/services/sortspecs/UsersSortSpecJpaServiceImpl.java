

package com.huststore.jpa.services.sortspecs;

import com.huststore.jpa.entities.QUser;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.services.GenericSortSpecJpaService;
import com.querydsl.core.types.OrderSpecifier;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class UsersSortSpecJpaServiceImpl
  extends GenericSortSpecJpaService<User> {

  @Override
  public QUser getBasePath() {
    return QUser.user;
  }

  @Override
  protected Map<String, OrderSpecifier<?>> createOrderSpecMap() {
    return Map.of(
            "name", QUser.user.name.asc(),
            "role", QUser.user.userRole.name.asc()
    );
  }
}
