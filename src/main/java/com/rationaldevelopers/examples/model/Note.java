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
import javax.xml.bind.annotation.XmlRootElement;
import java.util.Calendar;
import java.util.Objects;

@XmlRootElement(name = "note")
@Entity
@Table(name = "nts")
public class Note extends Persistent {

  @JsonbProperty("id")
  @Id
  @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "task_generator")
  @SequenceGenerator(name="task_generator", allocationSize=50, sequenceName = "seq_note", initialValue = 100)
  @Column(name = "id", updatable = false, nullable = false)
  private Long id;

  @JsonbProperty("task")
  @ManyToOne(targetEntity = Task.class, optional = false)
  @JoinColumn(name = "task_id")
  private Task task;

  @JsonbProperty("note")
  @Basic(optional = false)
  @Column(name = "nt", nullable = false)
  @Lob
  private String note;

  public Note() {
    this.setCreated(Calendar.getInstance().getTime());
  }

  public Note(final String note) {
    this();
    this.note = note;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Task getTask() {
    return task;
  }

  public void setTask(final Task task) {
    this.task = task;
  }

  public String getNote() {
    return note;
  }

  public void setNote(final String note) {
    this.note = note;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;
    if (!super.equals(o)) return false;
    Note note1 = (Note) o;
    return task.equals(note1.task) &&
        note.equals(note1.note);
  }

  @Override
  public int hashCode() {
    return Objects.hash(task, note);
  }
}
