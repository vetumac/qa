package by.bsuir.qa

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.jsoup.Jsoup

object App {

  def main(args: Array[String]): Unit = {
    val httpClient = HttpClientBuilder.create().build()

    checkCaseOneOne()

    def checkCaseOneOne() = {
      val httpResponse = httpClient.execute(new HttpGet("http://svyatoslav.biz/testlab/wt/"))
      val entity = httpResponse.getEntity
      var content = ""
      if (entity != null) {
        val inputStream = entity.getContent
        content = io.Source.fromInputStream(inputStream).getLines.mkString
        inputStream.close()
      }
      httpResponse.close()

      val document = Jsoup.parse(content)
      document.getAllElements
    }

  }
}
