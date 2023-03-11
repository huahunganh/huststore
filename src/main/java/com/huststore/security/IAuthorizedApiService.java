

package com.huststore.security;

import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public interface IAuthorizedApiService {
  Collection<String> getAuthorizedApiRoutes(UserDetails userDetails);

  Collection<String> getAuthorizedApiRouteAccess(UserDetails userDetails, String apiRoute);
}
