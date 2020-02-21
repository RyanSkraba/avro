/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.apache.avro;

import org.junit.Rule;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameter;
import org.junit.runners.Parameterized.Parameters;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Validates names for Avro named types in
 */
@RunWith(Enclosed.class)
public class TestNamedSchemaTypes {

  @RunWith(Parameterized.class)
  public static class TestNameValidationGood {

    @Parameters(name = "name-validation-good.txt:{0}:{1}")
    public static List<Object[]> cases() {
      return new BufferedReader(new InputStreamReader(System.class.getResourceAsStream("/name-validation-good.txt")))
          .lines().map(n -> n.split(":", 2)).collect(Collectors.toList());
    }

    @Parameter
    public String name;

    @Parameter(1)
    public String avroSchemaJson;

    @Test
    public void testParseable() throws Exception {
      new Schema.Parser().parse(avroSchemaJson);
    }
  }

  @RunWith(Parameterized.class)
  public static class TestNameValidationBad {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Parameters(name = "name-validation-bad.txt:{0}:{1}")
    public static List<Object[]> cases() {
      return new BufferedReader(new InputStreamReader(System.class.getResourceAsStream("/name-validation-bad.txt")))
          .lines().map(n -> n.split(":", 2)).collect(Collectors.toList());
    }

    @Parameter
    public String name;

    @Parameter(1)
    public String avroSchemaJson;

    @Test
    public void testUnparseable() throws Exception {
      thrown.expect(SchemaParseException.class);
      new Schema.Parser().parse(avroSchemaJson);
    }
  }

}
