/*
 * Copyright 2019 Ryan McGuinness
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.rationaldevelopers.examples.service;

import com.rationaldevelopers.examples.model.User;

import javax.inject.Inject;
import javax.inject.Named;
import javax.inject.Singleton;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Named("userService")
@Singleton
public class UserService {
  @Inject
  DataService dataService;

  @Transactional
  public Optional<User> authenticate(final String userName, final String password) {
    if (Objects.nonNull(userName) && Objects.nonNull(password) && !userName.trim().isEmpty() &&
        !password.trim().isEmpty()) {
      Optional<User> existing = dataService.findUniqueByNamedQueryWithParams(User.class, User.QRY_FIND_BY_USER_NAME, userName);
      if (existing.isPresent()) {
        User u = existing.get();
        if (u.matchPassword(password)) {
          return Optional.of(u);
        }
      }
    }
    return Optional.empty();
  }

  @Transactional
  public Optional<User> save(final User user) {
    Optional<User> existing = dataService.findUniqueByNamedQueryWithParams(User.class, User.QRY_FIND_BY_USER_NAME, user.getName());
    if (!existing.isPresent()) {
      user.setVerified(true);
      return dataService.save(user);
    }
    return Optional.empty();
  }

  @Transactional
  public List<User> list(final String name) {
    return dataService.findByNamedQueryWithParams(User.class, User.QRY_FIND_BY_USER_NAME, name);
  }
}
