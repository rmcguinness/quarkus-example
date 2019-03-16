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

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@XmlRootElement(name = "category")
@Entity
@Table(name="cat", uniqueConstraints = {
    @UniqueConstraint(name = "UQ_CATEGORY", columnNames = {"nm", "usr_id"})
})
@NamedQueries({
    @NamedQuery(name = Category.QRY_FIND_BY_USER, query = "select c from Category c where c.user = ?1")
})
public class Category extends Persistent {
  public static final String QRY_FIND_BY_USER = "Category.findByUser";

  @JsonbProperty("name")
  @Column(name = "nm", length = 128, nullable = false)
  private String name;

  @JsonbProperty("user")
  @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = false)
  @JoinColumn(name = "usr_id")
  private User user;

  /**
   * Note, we won't allow cascading here as we don't want
   * to allow accidental deletion.
   */
  @XmlElementWrapper
  @JsonbProperty("tasks")
  @OneToMany(targetEntity = Task.class,
      fetch = FetchType.EAGER,
      mappedBy = "category")
  private Set<Task> tasks = new HashSet<>();

  public Category() {
  }

  public Category(String name, User user) {
    this.name = name;
    this.user = user;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = Objects.nonNull(name) && !name.isBlank() ? name.trim() : null;
  }

  public User getUser() {
    return user;
  }

  public void setUser(User user) {
    this.user = user;
  }

  public Set<Task> getTasks() {
    return tasks;
  }

  public void setTasks(Set<Task> tasks) {
    this.tasks = tasks;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Category category = (Category) o;
    return Objects.equals(name, category.name) &&
        Objects.equals(user, category.user);
  }

  @Override
  public int hashCode() {
    return Objects.hash(name, user);
  }
}
