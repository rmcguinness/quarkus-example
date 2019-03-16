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

package com.rationaldevelopers.examples;

import com.rationaldevelopers.examples.concurrent.ManagedThreadLocal;
import com.rationaldevelopers.examples.model.Category;
import com.rationaldevelopers.examples.service.CategoryService;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class CategoryServiceTest extends AbstractTest {

  @Inject
  CategoryService categoryService;

  @Test
  public void testCategory() {
    System.out.println(ManagedThreadLocal.getCurrentUser());
    List<Category> all = categoryService.find();
    if (all == null || all.size() == 0) {
      categoryService.create("Test");
      assertEquals(1, categoryService.find().size());
    }
  }

}
