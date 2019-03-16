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
import org.hibernate.annotations.GenericGenerator;

import javax.json.bind.annotation.JsonbProperty;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

@RegisterForReflection
@MappedSuperclass
public abstract class Persistent implements Serializable {
  private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

  @JsonbProperty("id")
  @Id
  @GeneratedValue(generator = "uuid")
  @GenericGenerator(name = "uuid", strategy = "org.hibernate.id.UUIDGenerator")
  @Column(name = "id", length = 36, nullable = false, columnDefinition = "CHAR(36)")
  private String id;

  @JsonbProperty("ver")
  @Version
  @Column(name = "ver", nullable = false)
  private int version;

  @JsonbProperty("cr_dt")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "cr_dt", nullable = false)
  private Date created;

  @JsonbProperty("up_dt")
  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "up_dt", nullable = false)
  private Date updated;

  public static String getDateFormat() {
    return DATE_FORMAT;
  }

  public String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public Date getUpdated() {
    return updated;
  }

  public void setUpdated(Date updated) {
    this.updated = updated;
  }

  @PrePersist
  public void prePersist() {
    created = Calendar.getInstance().getTime();
    updated = Calendar.getInstance().getTime();
  }

  @PreUpdate
  public void preUpdate() {
    updated = Calendar.getInstance().getTime();
  }
}
