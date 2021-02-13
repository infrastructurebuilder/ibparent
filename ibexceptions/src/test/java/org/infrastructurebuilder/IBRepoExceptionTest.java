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

import org.infrastructurebuilder.exceptions.IBException;
import org.infrastructurebuilder.exceptions.IBRepoException;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

public class IBRepoExceptionTest {

  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
  }

  @AfterClass
  public static void tearDownAfterClass() throws Exception {
  }

  @Before
  public void setUp() throws Exception {
  }

  @After
  public void tearDown() throws Exception {
  }

  @Test
  public void testIBMavenException() {
    assertNotNull("Empty maven exception", new IBRepoException());
  }

  @Test
  public void testIBMavenExceptionString() {
    assertNotNull("With string", new IBRepoException("ABC"));
  }

  @Test
  public void testIBMavenExceptionStringThrowable() {
    assertNotNull("With string", new IBRepoException("ABC", new IBException("DEF")));
  }

  @Test
  public void testIBMavenExceptionStringThrowableBooleanBoolean() {
    assertNotNull("With string", new IBRepoException("ABC", new IBException("DEF"), true, true));
  }

  @Test
  public void testIBMavenExceptionThrowable() {
    assertNotNull("With  string", new IBRepoException(new IBException()));
  }

}
