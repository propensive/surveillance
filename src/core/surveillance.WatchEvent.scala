/*
    Surveillance, version [unreleased]. Copyright 2024 Jon Pretty, Propensive OÜ.

    The primary distribution site is: https://propensive.com/

    Licensed under the Apache License, Version 2.0 (the "License"); you may not use this
    file except in compliance with the License. You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software distributed under the
    License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
    either express or implied. See the License for the specific language governing permissions
    and limitations under the License.
*/

package surveillance

import java.nio.file as jnf, jnf.StandardWatchEventKinds.*

import anticipation.*
import contingency.*
import rudiments.*
import spectacular.*

enum WatchEvent:
  case NewFile(dir: Text, file: Text)
  case NewDirectory(dir: Text, directory: Text)
  case Modify(dir: Text, file: Text)
  case Delete(dir: Text, file: Text)

  def dir: Text

  def path[DirectoryType: SpecificPath]: DirectoryType = unsafely:
    val relPath = this match
      case NewFile(_, file)      => file
      case NewDirectory(_, path) => path
      case Modify(_, file)       => file
      case Delete(_, path)       => path

      SpecificPath(jnf.Paths.get(dir.s, relPath.show.s).nn.normalize.nn.toString.show)