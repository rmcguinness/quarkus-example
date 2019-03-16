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

import jdk.jfr.Name;

import javax.inject.Inject;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.Optional;

@Name("dataService")
@Singleton
public class DataService {
  @Inject
  EntityManager entityManager;

  static final Query queryRange(final Query query, final int offset, final int count) {
    if (count >= 0)
      query.setMaxResults(count);
    if (offset >= 0)
      query.setFirstResult(offset);
    return query;
  }

  public void flush() {
    entityManager.flush();
  }

  public <E> Optional<E> save(final E e) {
    entityManager.persist(e);
    return Optional.ofNullable(e);
  }

  public <E> Optional<E> update(final E e) {
    entityManager.merge(e);
    return Optional.ofNullable(e);
  }

  public <E> Optional<E> findById(final Class<E> clazz, final String id) {
    return Optional.ofNullable(entityManager.find(clazz, id));
  }

  public <E> void delete(final Class<E> clazz, final String id) {
    entityManager.remove(entityManager.find(clazz, id));
  }
  @SuppressWarnings("unchecked")
  public <E> List<E> findByRange(final Class<E> clazz, final String query, final int offset, final int count) {
    return queryRange(entityManager.createQuery(query, clazz), offset, count)
        .getResultList();
  }

  @SuppressWarnings("unchecked")
  public <E> List<E> findByNamedQueryAndRange(final Class<E> clazz, final String query, final int offset, final int count) {
    return queryRange(entityManager.createNamedQuery(query, clazz), offset, count)
        .getResultList();
  }

  public <E> List<E> findByNamedQuery(final Class<E> clazz, final String queryName) {
    return entityManager.createNamedQuery(queryName, clazz).getResultList();
  }

  @SuppressWarnings("unchecked")
  public <E> List<E> findByNamedQueryWithParams(final Class<E> clazz, final String queryName, final Object... parameters) {
    Query q = entityManager.createNamedQuery(queryName, clazz);
    for (int i = 0; i < parameters.length; i++) {
      q.setParameter(i + 1, parameters[i]);
    }
    return q.getResultList();
  }

  @SuppressWarnings("unchecked")
  public <E> Optional<E> findUniqueByNamedQueryWithParams(final Class<E> clazz, final String queryName,
                                                   Object... parameters) {
    List<E> list = findByNamedQueryWithParams(clazz, queryName, parameters);
    return (list.size() == 1) ? Optional.ofNullable(list.get(0)) : Optional.empty();
  }
}
