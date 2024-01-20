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

import static java.lang.String.format;
import static java.util.Objects.requireNonNull;
import static java.util.Optional.empty;
import static java.util.Optional.of;
import static java.util.Optional.ofNullable;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.StringJoiner;

import org.infrastructurebuilder.exceptions.IBException;

public interface GAVBasic extends Comparable<GAVBasic> {
  public static final String DELIMITER = ":";
  public static final String BASIC_PACKAGING = "jar";
  String SNAPSHOT_DESIGNATOR = "-SNAPSHOT";

  default Optional<String> asMavenDependencyGet() {

    try {
      final String theVersion = getVersion().map(v -> v.toString())
          .orElseThrow(() -> new IllegalArgumentException("No version available"));
      final String theClassifier = getClassifier().map(c -> DELIMITER + c).orElse("");

      final String theType = getExtension().map(t -> DELIMITER + t)
          .orElse("".equals(theClassifier) ? "" : DELIMITER + BASIC_PACKAGING);
      return of(format("%s:%s:%s%s%s", getGroupId(), getArtifactId(), theVersion, theType, theClassifier));
    } catch (final IllegalArgumentException e) {
      return empty();
    }
  }

  default public String getDefaultToString() {
    String[] s = new String[5];
    StringJoiner j = new StringJoiner(DELIMITER);
    s[0] = getGroupId();
    s[1] = getArtifactId();
    s[2] = getVersion().orElse(null);
    s[3] = getExtension().orElse(null);
    s[4] = getClassifier().orElse(null);
    for (String ss : s)
      if (ss != null)
        j.add(ss);
      else
        break;
    return j.toString();
  }

  default String getDefaultSignaturePath() {
    return format("%s:%s:%s%s:%s", getGroupId(), getArtifactId(), getExtension().orElse(BASIC_PACKAGING),
        getClassifier().map(c2 -> DELIMITER + c2).orElse(""),
        getVersion().orElseThrow(() -> new IBException("No string version available")));
  }

  default Optional<String> asModelId() {
    try {
      final String theVersion = getVersion().orElseThrow(() -> new IllegalArgumentException("No version available"));
      return of(format("%s:%s:%s", getGroupId(), getArtifactId(), theVersion));
    } catch (final IllegalArgumentException e) {
      return empty();
    }

  }

  /**
   * Note that this sets a default extension to 'jar'!
   * 
   * @param v
   * @return
   */
  static String asPaxUrl(final GAVBasic v) {
    final String cl = !v.getClassifier().isPresent() ? "" : "/" + v.getClassifier().orElse("");
    return format("mvn:%s/%s/%s/%s%s", v.getGroupId(), v.getArtifactId(), v.getVersion().orElse(""),
        v.getExtension().orElse(BASIC_PACKAGING), cl);
  }

  default String asPaxUrl() {
    return GAVBasic.asPaxUrl(this);
  }

  default String asRange() {
    return "[" + getVersion().orElse("0.0.0,99999.99999.99999") + "]";
  }

  String getArtifactId();

  Optional<String> getClassifier();

  Optional<String> getExtension();

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
        var e = getExtension();
        var o1 = o.getExtension();
        if (e.isPresent()) {
          if (o1.isPresent())
            cmp = e.get().compareTo(o1.get());
        } else {
          if (o1.isPresent())
            cmp = 1;
        }
      }

    }
    if (cmp == 0) {
      cmp = IBException.cet.returns(() -> {
        // FIXME Bad comparison
        return compareVersion(o);
      });
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
