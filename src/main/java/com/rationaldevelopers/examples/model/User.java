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

package com.rationaldevelopers.examples.model;

import io.quarkus.runtime.annotations.RegisterForReflection;
import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;
import org.hibernate.annotations.Type;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.json.bind.annotation.JsonbProperty;
import javax.json.bind.annotation.JsonbTransient;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Objects;

@XmlRootElement(name = "user")
@RegisterForReflection
@Entity
@Table(name="usrs", uniqueConstraints = {
    @UniqueConstraint(name = "UQ_APP_USER_01", columnNames = {"nm"})
})
@NamedQueries({
    @NamedQuery(name = User.QRY_FIND_BY_USER_NAME, query = "select u from User u where u.name=?1"),
    @NamedQuery(name = User.QRY_FIND_BY_USER_NAME_AND_PASSWORD, query = "select u from User u where u.name=?1 and u.password=?2 and u.verified=true")
})
public class User extends Persistent {
  private static final Logger LOGGER = LoggerFactory.getLogger(User.class);
  public static final String QRY_FIND_BY_USER_NAME = "User.findByName";

  @SuppressWarnings("squid:S2068")
  public static final String QRY_FIND_BY_USER_NAME_AND_PASSWORD = "User.findByNameAndPassword";

  @JsonbProperty("id")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
  @SequenceGenerator(name="task_generator", allocationSize=50, sequenceName = "seq_user", initialValue = 100)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  /**
   * The name of the user
   */
  @JsonbProperty("name")
  @Column(name = "nm", length = 265, nullable = false)
  private String name;

  /**
   * The password of the user
   */
  @JsonbTransient
  @Column(name = "pwd", length = 265, nullable = false)
  private String password;

  /**
   * If the user has manually finished registration
   */
  @JsonbProperty("verified")
  @Column(name="verified")
  @Type(type = "yes_no")
  private boolean verified = Boolean.FALSE;

  public User() {
  }

  public User(String name, String password) {
    this.name = name;
    this.password = password;
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

  public boolean isVerified() {
    return verified;
  }

  public void setVerified(boolean verified) {
    this.verified = verified;
  }

  public boolean matchPassword(final String password) {
    if (Objects.nonNull(this.password)
        && Objects.nonNull(password)
        && !this.password.trim().isEmpty()
        && !password.trim().isEmpty()) {
        return this.password.equals(hashPassword(password));
    }
    return Boolean.FALSE;
  }

  /**
   * A simple utility method for creating a SHA-256 of the password, to ensure it's never in the clear.
   * @param password
   * @return
   */
  public static String hashPassword(final String password) {
    if (Objects.nonNull(password) && !password.trim().isEmpty()) {
      return Hex.encodeHexString(DigestUtils.getSha256Digest().digest(password.getBytes()));
    }
    return password;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    User user = (User) o;
    return Objects.equals(name, user.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name);
  }
}
