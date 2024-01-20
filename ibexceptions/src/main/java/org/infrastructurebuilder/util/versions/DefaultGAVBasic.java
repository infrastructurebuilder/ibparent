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

import static java.util.Arrays.copyOf;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.ofNullable;

import java.util.Optional;
import java.util.StringJoiner;

/**
 * There are what <b>APPEAR TO BE SOME INCONSISTENCIES</b> in the constructors of {@link DefaultGAVBasic}. However,
 * there are some reasons for making the possibility of choosing the wrong one a thing.
 *
 * IF you use a dependency string, then the extension is NOT defaulted to 'jar'.
 *
 * IF you use the 3-argument constructor, the extension is NOT defaulted to 'jar'.
 *
 * IF you call {@link #setExtension(String)}, the extension IS defaulted to 'jar'.
 *
 */
public class DefaultGAVBasic implements GAVBasic {

  private String groupId, artifactId, version, extension, classifier;

  protected DefaultGAVBasic() {

  }

  public DefaultGAVBasic(final String from) {
    final String[] l = copyOf(from.split(":"), 5);

    for (int i = 0; i < 5; ++i) {
      if ("".equals(l[i]))
        l[i] = null;
      switch (i) {
      case 0:
        setGroupId(l[i]);
        break;
      case 1:
        setArtifactId(l[i]);
        break;
      case 2:
        setVersion(l[i]);
        break;
      case 3:
        setExtension(l[i]);
        break;
      case 4:
        setClassifier(l[i]);
        break;
      }
    }
  }

  @Override
  public String toString() {
    return getDefaultToString();
  }

  public DefaultGAVBasic(final String groupId, final String artifactId, final String version, final String extension) {
    this(groupId, artifactId, null, version, extension);
  }

  public DefaultGAVBasic(final String groupId, final String artifactId, final String version) {
    this(groupId, artifactId, version, null);
  }

  public DefaultGAVBasic(final String groupId, final String artifactId, final String classifier, final String version,
      final String extension)
  {
    this.groupId = requireNonNull(groupId);
    this.artifactId = requireNonNull(artifactId);
    this.version = version;
    this.extension = extension;
    this.classifier = classifier;
  }

  @Override
  public String getArtifactId() {
    return this.artifactId;
  }

  @Override
  public Optional<String> getClassifier() {
    return ofNullable(this.classifier);
  }

  @Override
  public Optional<String> getExtension() {
    return ofNullable(this.extension);
  }

  @Override
  public String getGroupId() {
    return this.groupId;
  }

  @Override
  public Optional<String> getVersion() {
    return ofNullable(this.version);
  }

  protected DefaultGAVBasic setGroupId(String groupId) {
    this.groupId = groupId;
    return this;
  }

  protected DefaultGAVBasic setArtifactId(String artifactId) {
    this.artifactId = requireNonNull(artifactId);
    return this;
  }

  protected DefaultGAVBasic setVersion(String version) {
    this.version = version;
    return this;
  }

  protected DefaultGAVBasic setExtension(String extension) {
    this.extension = ofNullable(extension).orElse(BASIC_PACKAGING);
    return this;
  }

  protected DefaultGAVBasic setClassifier(String classifier) {
    if (classifier != null && "".equals(classifier.trim())) {
      this.classifier = null;
    } else {
      this.classifier = ofNullable(classifier).orElse(null);
    }
    return this;
  }

}
