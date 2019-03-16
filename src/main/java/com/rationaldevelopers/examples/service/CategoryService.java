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



import com.rationaldevelopers.examples.concurrent.ManagedThreadLocal;
import com.rationaldevelopers.examples.model.Category;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicReference;

@Named("categoryService")
@ApplicationScoped
public class CategoryService {

  @Inject
  DataService dataService;

  @Transactional
  public Optional<Category> create(final String categoryName) {
    AtomicReference<Optional<Category>> cat = new AtomicReference<>(Optional.empty());
    ManagedThreadLocal.getCurrentUser().ifPresent(u ->
      cat.set(dataService.save(new Category(categoryName, u))));
    return cat.get();
  }

  @Transactional
  public List<Category> find() {
    List<Category> out = new ArrayList<Category>();
    ManagedThreadLocal.getCurrentUser().ifPresent(u ->
      out.addAll(dataService.findByNamedQueryWithParams(Category.class, Category.QRY_FIND_BY_USER, u)));
    return out;
  }
}
