

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.UserPojo;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.services.IDataTransportJpaService;

public interface IUsersDataTransportJpaService
  extends IDataTransportJpaService<UserPojo, User> {
}
