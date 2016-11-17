package by.bsuir.qa

import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Paths, StandardOpenOption}

object IOService {

  def write(filePath: String, contents: String) = {
    Files.write(Paths.get(filePath), contents.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE)
  }

}
