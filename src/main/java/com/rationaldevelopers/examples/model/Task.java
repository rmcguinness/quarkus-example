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

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import javax.xml.bind.annotation.XmlElementWrapper;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.*;

@RegisterForReflection
@XmlRootElement(name = "task")
@Entity
@Table(name = "tsks", uniqueConstraints = {
    @UniqueConstraint(name = "UQ_TASK_O1", columnNames = {"cat_id", "nm"})
})
@NamedQueries({
    @NamedQuery(name = Task.QRY_FIND_BY_CATEGORY, query = "select t from Task t where t.category = ?1")
})
public class Task extends Persistent {
  public static final String QRY_FIND_BY_CATEGORY = "Task.findByCategory";

  @JsonbProperty("category")
  @ManyToOne(targetEntity = Category.class, optional = false, fetch = FetchType.EAGER)
  @JoinColumn(name = "cat_id")
  private Category category;

  @JsonbProperty("name")
  @Column(name = "nm", length = 256, nullable = false)
  private String name;

  @JsonbProperty("st_dt")
  @Temporal(TemporalType.DATE)
  @Column(name="st_dt")
  private Date startDate = Calendar.getInstance().getTime();

  @JsonbProperty("pdd")
  @Temporal(TemporalType.DATE)
  @Column(name = "pdd")
  private Date plannedDoneDate = Calendar.getInstance().getTime();

  @JsonbProperty("cp_dt")
  @Temporal(TemporalType.DATE)
  @Column(name = "cp_dt")
  private Date completedDate;

  @JsonbProperty("description")
  @Lob
  @Column(name="descr")
  private String description;

  @XmlElementWrapper
  @JsonbProperty("notes")
  @OneToMany(targetEntity = Note.class,
      mappedBy = "task",
      cascade = CascadeType.ALL,
      fetch = FetchType.EAGER)
  private Set<Note> notes = new HashSet<>();

  public Task() {

  }

  public Task(String name, String description) {
    this.name = name;
    this.description = description;
  }

  public Category getCategory() {
    return category;
  }

  public void setCategory(Category category) {
    this.category = category;
  }

  public Date getStartDate() {
    return startDate;
  }

  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }

  public Date getPlannedDoneDate() {
    return plannedDoneDate;
  }

  public void setPlannedDoneDate(Date plannedDoneDate) {
    this.plannedDoneDate = plannedDoneDate;
  }

  public Date getCompletedDate() {
    return completedDate;
  }

  public void setCompletedDate(Date completedDate) {
    this.completedDate = completedDate;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public Set<Note> getNotes() {
    return notes;
  }

  public void setNotes(Set<Note> notes) {
    this.notes = notes;
  }

  public void addNote(final Note note) {
    if (Objects.nonNull(note)) {
      note.setTask(this);
      this.notes.add(note);
    }
  }

  public void removeNote(final Note note) {
    if (Objects.nonNull(note)) {
      this.notes.remove(note);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    Task task = (Task) o;
    return Objects.equals(category, task.category) &&
        Objects.equals(name, task.name);
  }

  @Override
  public int hashCode() {
    return Objects.hash(super.hashCode(), category, name);
  }
}
