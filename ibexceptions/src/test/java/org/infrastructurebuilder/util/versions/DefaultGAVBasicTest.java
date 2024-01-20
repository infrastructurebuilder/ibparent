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
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

import org.apiguardian.api.API;
import org.infrastructurebuilder.exceptions.IBException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.infrastructurebuilder.util.versions.GAVBasic.*;

class DefaultGAVBasicTest {

  private static final String CLASSIFIER = "classified";
  private static final String APIVERSION = "1.0";
  private static final String VERSION = APIVERSION + ".13";
  private static final String VERSION2 = APIVERSION + ".14";
  private static final String ARTIFACTID = "sample";
  private static final String GROUPID = "org.sample";
  private static final String DEPGETSTRING = GROUPID + DELIMITER + ARTIFACTID + DELIMITER + VERSION;
  private static final String DEPGETSTRING2 = DEPGETSTRING + DELIMITER + BASIC_PACKAGING + DELIMITER + CLASSIFIER;
  private static final String SIGPATH = GROUPID + DELIMITER + ARTIFACTID + DELIMITER + BASIC_PACKAGING + DELIMITER
      + VERSION;
  private static final String MODELID = DEPGETSTRING;

  @BeforeAll
  static void setUpBeforeClass() throws Exception {
  }

  @AfterAll
  static void tearDownAfterClass() throws Exception {
  }

  private GAVBasic gav, gv2, gv3, gv4, gv5, gv6, gv7, gv8;

  @BeforeEach
  void setUp() throws Exception {
    this.gav = new DefaultGAVBasic(DEPGETSTRING);
    this.gv2 = new DefaultGAVBasic(DEPGETSTRING2);
    this.gv3 = new DefaultGAVBasic(DEPGETSTRING2 + ":::");
    this.gv4 = new DefaultGAVBasic(GROUPID, ARTIFACTID, CLASSIFIER, VERSION, BASIC_PACKAGING);
    this.gv5 = new DefaultGAVBasic(GROUPID, ARTIFACTID, VERSION);
    this.gv6 = new DefaultGAVBasic(GROUPID, ARTIFACTID, CLASSIFIER, VERSION, null);
    this.gv7 = new DefaultGAVBasic(GROUPID, ARTIFACTID, null, VERSION, null);
    this.gv8 = new DefaultGAVBasic(GROUPID, ARTIFACTID, CLASSIFIER, VERSION2, BASIC_PACKAGING);
  }

  @AfterEach
  void tearDown() throws Exception {
  }

  @Test
  void testDefaultToString() {
    assertEquals(DEPGETSTRING + DELIMITER + BASIC_PACKAGING, this.gav.toString());
    assertEquals(DEPGETSTRING2, this.gv2.toString());
    assertEquals(DEPGETSTRING2, this.gv3.toString());
    assertEquals(DEPGETSTRING2, this.gv4.toString());
    assertEquals(DEPGETSTRING, this.gv5.toString());
  }

  @Test
  void testPaxURL() {
    assertEquals("mvn:" + GROUPID + "/" + ARTIFACTID + "/" + VERSION + "/" + BASIC_PACKAGING, this.gav.asPaxUrl());
  }

  @Test
  void testEqualsIgnoreClassifier() {
    assertNotEquals(this.gv6, this.gv7);
    assertFalse(this.gv6.equalsIgnoreClassifier(this.gv7, false));
    assertTrue(this.gv6.equalsIgnoreClassifier(this.gv7, true));
  }

  @Test
  void testCompares() {
    assertTrue(this.gv8.compareTo(gv4) > 0);
    assertTrue(this.gv4.compareTo(gv8) < 0);
    assertTrue(this.gv2.compareTo(gv3) == 0);

  }

  @Test
  void testAsRange() {
    assertEquals("[" + VERSION + "]", this.gav.asRange());
  }

  @Test
  void testDefaultSigPath() {
    assertEquals(SIGPATH, this.gav.getDefaultSignaturePath());
  }

  @Test
  void testGetModelId() {
    assertEquals(MODELID, this.gav.asModelId().get());
  }

  @Test
  void testDefaultGAVBasicStringStringString() {
    assertEquals(this.gav.getGroupId(), GROUPID);
    assertEquals(this.gav.getArtifactId(), ARTIFACTID);
    assertEquals(this.gav.getVersion().get(), VERSION);
    assertEquals(this.gav.getAPIVersion().get(), APIVERSION);
    assertEquals(this.gav, this.gav);
    assertNotEquals(this.gav, APIVERSION);
    assertEquals(DEPGETSTRING + DELIMITER + GAVBasic.BASIC_PACKAGING, this.gav.asMavenDependencyGet().get());
  }

}
