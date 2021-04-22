/*
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
package org.infrastructurebuilder.exceptions;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class IBRepoExceptionTest {

  @BeforeAll
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  public static void tearDownAfterClass() throws Exception {
  }

  @BeforeEach
  public void setUp() throws Exception {
  }

  @AfterEach
  public void tearDown() throws Exception {
  }

  @Test
  public void testIBMavenException() {
    assertNotNull( new IBRepoException(),"Empty maven exception");
  }

  @Test
  public void testIBMavenExceptionString() {
    assertNotNull( new IBRepoException("ABC"),"With string");
  }

  @Test
  public void testIBMavenExceptionStringThrowable() {
    assertNotNull( new IBRepoException("ABC", new IBException("DEF")));
  }

  @Test
  public void testIBMavenExceptionStringThrowableBooleanBoolean() {
    assertNotNull(new IBRepoException("ABC", new IBException("DEF"), true, true));
  }

  @Test
  public void testIBMavenExceptionThrowable() {
    assertNotNull(new IBRepoException(new IBException()));
  }

}
