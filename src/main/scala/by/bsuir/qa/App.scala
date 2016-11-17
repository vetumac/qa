package by.bsuir.qa

object App {

  def main(args: Array[String]): Unit = {

    executeTest(new OneOne)
    executeTest(new OneTwo)

    def executeTest(test: Test) = {
      val content = "Task: " + test.getTask + "\n" + "Result: " + test.getResult
      IOService.write(test.getFileName, content)
    }
  }
}
