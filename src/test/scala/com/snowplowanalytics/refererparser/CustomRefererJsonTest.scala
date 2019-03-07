/**
 * Copyright 2012-2018 Snowplow Analytics Ltd
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.snowplowanalytics.refererparser

import cats.effect.IO
import org.specs2.mutable.Specification

class CustomRefererJsonTest extends Specification {

  val resource   = getClass.getResource("/custom-referers.json").getPath
  val ioParser   = Parser.create[IO](resource).unsafeRunSync().fold(throw _, identity)
  val evalParser = Parser.unsafeCreate(resource).value.fold(throw _, identity)

  "Custom referer list" should {
    "give correct referer" in {
      val refererUri = "https://www.example.org/?query=hello+world"
      val expected   = Some(SearchReferer("Example", Some("hello world")))
      expected shouldEqual ioParser.parse(refererUri)
      expected shouldEqual evalParser.parse(refererUri)
    }
  }
}
