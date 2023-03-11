

package com.huststore.jpa.services.datatransport;

import com.huststore.dto.PersonPojo;
import com.huststore.dto.UserPojo;
import com.huststore.exceptions.BadInputException;
import com.huststore.jpa.entities.Person;
import com.huststore.jpa.entities.User;
import com.huststore.jpa.entities.UserRole;
import com.huststore.jpa.repositories.IPeopleJpaRepository;
import com.huststore.jpa.repositories.IUserRolesJpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Transactional
@Service
public class UsersDataTransportJpaServiceImpl
  implements IUsersDataTransportJpaService {
  private final IUserRolesJpaRepository rolesRepository;
  private final IPeopleJpaRepository peopleRepository;
  private final PasswordEncoder passwordEncoder;

  public UsersDataTransportJpaServiceImpl(
    IUserRolesJpaRepository rolesRepository,
    IPeopleJpaRepository peopleRepository,
    PasswordEncoder passwordEncoder
  ) {
    this.rolesRepository = rolesRepository;
    this.peopleRepository = peopleRepository;
    this.passwordEncoder = passwordEncoder;
  }

  @Override
  public User applyChangesToExistingEntity(UserPojo source, User existing) throws BadInputException {
    User target = new User(existing);

    String name = source.getName();
    if (name != null && !name.isBlank() && !target.getName().equals(name)) {
      target.setName(name);
    }

    String roleName = source.getRole();
    if (roleName != null && !roleName.isBlank() && !target.getUserRole().getName().equals(roleName)) {
      Optional<UserRole> roleNameMatch = rolesRepository.findByName(roleName);
      roleNameMatch.ifPresent(target::setUserRole);
    }

    String password = source.getPassword();
    if (password != null && !password.isBlank() && !passwordEncoder.matches(password, target.getPassword())) {
      String encodedPassword = passwordEncoder.encode(password);
      target.setPassword(encodedPassword);
    }

    PersonPojo person = source.getPerson();
    if (person != null) {
      String idNumber = person.getIdNumber();
      if (idNumber != null && !idNumber.isBlank() && !target.getPerson().getIdNumber().equals(idNumber)) {
        Optional<Person> idNumberMatch = peopleRepository.findByIdNumber(idNumber);
        idNumberMatch.ifPresent(target::setPerson);
      }
    }

    return target;
  }
}
