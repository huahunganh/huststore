

package com.huststore.jpa.services.conversion;

import com.huststore.dto.UserPojo;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.services.ITwoWayConverterJpaService;

public interface IUsersConverterJpaService
  extends ITwoWayConverterJpaService<UserPojo, User> {
}
