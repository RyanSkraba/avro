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

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class TestSchemaNames {

  @Test
  public void testGoodRecordName() {
    Schema s = SchemaBuilder.record("MyRecord").fields().endRecord();
    assertThat(s.getName(), is("MyRecord"));
    assertThat(s.getNamespace(), nullValue());
    assertThat(s.getFullName(), is("MyRecord"));

    Schema s2 = SchemaBuilder.record("org.avro.MyRecord").fields().endRecord();
    assertThat(s2.getName(), is("MyRecord"));
    assertThat(s2.getNamespace(), is("org.avro"));
    assertThat(s2.getFullName(), is("org.avro.MyRecord"));
  }

  @Test(expected = SchemaParseException.class)
  public void testBadRecordName() {
    SchemaBuilder.record("MyRecord$").fields().endRecord();
  }

  @Test(expected = SchemaParseException.class)
  public void testBadRecordNamespace() {
    Schema s = SchemaBuilder.record("org.avro$.MyRecord").fields().endRecord();
    System.out.println(s);
  }

}
