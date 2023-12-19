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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.fail;

import org.apiguardian.api.API;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class DefaultGAVBasicTest {

  private static final String APIVERSION = "1.0";
  private static final String VERSION = APIVERSION + ".13";
  private static final String ARTIFACTID = "sample";
  private static final String GROUPID = "org.sample";
  private static final String DEPGETSTRING = GROUPID + ":" + ARTIFACTID + ":" + VERSION;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  private GAVBasic gav;

  @BeforeEach
  void setUp() throws Exception {
    this.gav = new DefaultGAVBasic(DEPGETSTRING);
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  @Test
  void testDefaultGAVBasicStringStringString() {
    assertEquals(this.gav.getGroupId(), GROUPID);
    assertEquals(this.gav.getArtifactId(), ARTIFACTID);
    assertEquals(this.gav.getVersion().get(), VERSION);
    assertEquals(this.gav.getAPIVersion().get(), APIVERSION);
    assertEquals(this.gav, this.gav);
    assertNotEquals(this.gav, APIVERSION);
    assertEquals(DEPGETSTRING + ":" + GAVBasic.BASIC_PACKAGING, this.gav.asMavenDependencyGet().get());
  }

}
