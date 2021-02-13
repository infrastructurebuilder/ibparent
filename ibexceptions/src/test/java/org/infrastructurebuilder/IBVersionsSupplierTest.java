/**
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
 */
package org.infrastructurebuilder;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.util.function.Supplier;

import org.infrastructurebuilder.exceptions.IBRepoException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IBVersionsSupplierTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  private IBVersionsSupplier t;

  @Before
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

  @After
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
