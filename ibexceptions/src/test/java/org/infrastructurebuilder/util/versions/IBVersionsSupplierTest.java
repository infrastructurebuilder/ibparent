/*
 * @formatter:off
 * Copyright © 2019 admin (admin@infrastructurebuilder.org)
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
 * @formatter:on
 */
package org.infrastructurebuilder.util.versions;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.function.Supplier;

import org.infrastructurebuilder.exceptions.IBRepoException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IBVersionsSupplierTest {

  @BeforeAll
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  public static void tearDownAfterClass() throws Exception {
  }

  private IBVersionsSupplier t;

  @BeforeEach
  public void setUp() throws Exception {
    t = new IBVersionsSupplier() {

      @Override
      public Supplier<String> getVersion() {
        return () -> "1.2.3";
      }

      @Override
      public Supplier<String> getGroupId() {
        return () -> "g";
      }

      @Override
      public Supplier<String> getExtension() {
        return () -> "jar";
      }

      @Override
      public Supplier<String> getArtifactId() {
        return () -> "a";
      }

      @Override
      public Supplier<String> getAPIVersion() {
        return () -> "1.2";
      }
    };
  }

  @AfterEach
  public void tearDown() throws Exception {
  }

  @Test
  public void testIBMavenException() {
    assertNotNull("Empty maven exception", new IBRepoException());
  }

  @Test
  public void testNotNull() {
    assertTrue(t.getArtifactDependency().get().contains("g:a:1.2.3"));
  }

}
