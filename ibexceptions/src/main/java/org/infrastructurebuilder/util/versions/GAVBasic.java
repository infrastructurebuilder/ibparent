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

import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.ofNullable;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

import org.infrastructurebuilder.exceptions.IBException;

public interface GAVBasic extends Comparable<GAVBasic> {
  String BASIC_PACKAGING = "jar";
  String SNAPSHOT_DESIGNATOR = "-SNAPSHOT";

  default Optional<String> asMavenDependencyGet() {

    try {
      final String theVersion = getVersion().map(v -> v.toString())
          .orElseThrow(() -> new IllegalArgumentException("No version available"));
      final String theClassifier = getClassifier().map(c -> ":" + c).orElse("");

      final String theType = ofNullable(getExtension()).map(t -> ":" + t)
          .orElse("".equals(theClassifier) ? "" : ":jar");
      return Optional
          .of(String.format("%s:%s:%s%s%s", getGroupId(), getArtifactId(), theVersion, theType, theClassifier));
    } catch (final IllegalArgumentException e) {
      return empty();
    }
  }

  default String getDefaultSignaturePath() {
    return String.format("%s:%s:%s%s:%s", getGroupId(), getArtifactId(),
        ofNullable(getExtension()).map(pp -> pp).orElse("jar"), getClassifier().map(c2 -> ":" + c2).orElse(""),
        getVersion().orElseThrow(() -> new IBException("No string version available")));
  }

  default Optional<String> asModelId() {

    try {
      final String theVersion = getVersion().map(v -> v.toString())
          .orElseThrow(() -> new IllegalArgumentException("No version available"));
      return Optional.of(String.format("%s:%s:%s", getGroupId(), getArtifactId(), theVersion));
    } catch (final IllegalArgumentException e) {
      return empty();
    }

  }

  static String asPaxUrl(final GAVBasic v) {
    final String cl = !v.getClassifier().isPresent() ? "" : "/" + v.getClassifier().orElse("");
    return String.format("mvn:%s/%s/%s/%s%s", v.getGroupId(), v.getArtifactId(), v.getVersion().orElse(""),
        v.getExtension(), cl);
  }

  default String asPaxUrl() {
    return GAVBasic.asPaxUrl(this);
  }

  default String asRange() {
    return "[" + getVersion().orElse("0.0.0,99999.99999.99999") + "]";
  }

  String getArtifactId();

  Optional<String> getClassifier();

  String getExtension();

  String getGroupId();

  Optional<String> getVersion();

  /**
   * Get the "API version" for semantic versions.
   *
   * Might blow up if you're not a semantic version
   *
   * @return String with Major.Minor verions
   */
  default Optional<String> getAPIVersion() {
    return getVersion().map(s -> {
      String[] splits = s.split("\\.");
      return splits[0] + "." + splits[1];
    });

  }

  default boolean isSnapshot() {
    return getVersion().map(v -> v.endsWith(SNAPSHOT_DESIGNATOR)).orElse(false);
  }

  default boolean equalsIgnoreClassifier(final GAVBasic other, boolean ignoreClassifier) {
    if (!this.getExtension().equals(requireNonNull(other).getExtension()))
      return false;
    if (!this.getGroupId().equals(other.getGroupId()))
      return false;
    if (!this.getArtifactId().equals(other.getArtifactId()))
      return false;
    if (!ignoreClassifier && !Objects.equals(getClassifier(), other.getClassifier()))
      return false;
    if (!Objects.equals(getVersion(), other.getVersion()))
      return false;
    return true;

  }

  @Override
  default int compareTo(final GAVBasic o) {
    if (o == null)
      throw new NullPointerException("compareTo in DefaultGAV was passed a null");
    if (equals(o))
      return 0;
    int cmp = getGroupId().compareTo(o.getGroupId());
    if (cmp == 0) {
      cmp = getArtifactId().compareTo(o.getArtifactId());
      if (cmp == 0) {
        cmp = IBException.cet.returns(() -> {
          return compareVersion(o);
        });
        if (cmp == 0) {
          cmp = getExtension().compareTo(o.getExtension());
        }

      }
    }
    return cmp;
  }

  default int compareVersion(final GAVBasic otherVersion) {
    final String v = otherVersion.getVersion().orElse(null);
    final String q = getVersion().orElse(null);
    if (q == null && v == null)
      return 0;
    if (q == null)
      return -1;
    return q.compareTo(v);

  }

}
