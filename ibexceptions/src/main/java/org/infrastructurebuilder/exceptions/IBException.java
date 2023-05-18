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
package org.infrastructurebuilder.exceptions;

import com.mscharhag.et.ET;
import com.mscharhag.et.ExceptionTranslator;

public class IBException extends RuntimeException {

  /**
   *
   */
  private static final long serialVersionUID = -3615731463803876276L;
  public static ExceptionTranslator cet = ET.newConfiguration().translate(Exception.class).to(IBException.class).done();

  public IBException() {
  }

  public IBException(final String message) {
    super(message);
  }

  public IBException(final String message, final Throwable cause) {
    super(message, cause);
  }

  public IBException(final String message, final Throwable cause, final boolean enableSuppression,
      final boolean writableStackTrace)
  {
    super(message, cause, enableSuppression, writableStackTrace);
  }

  public IBException(final Throwable cause) {
    super(cause);
  }

}
