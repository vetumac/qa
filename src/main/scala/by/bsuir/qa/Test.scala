package by.bsuir.qa

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.jsoup.Jsoup

trait Test {

  val httpClient = HttpClientBuilder.create().build()

  def getResult: Boolean

  def getTask: String

  def getFileName: String

  def getStringByGet: String = {
    val httpResponse = httpClient.execute(new HttpGet("http://svyatoslav.biz/testlab/wt/"))
    val entity = httpResponse.getEntity
    var content = ""
    if (entity != null) {
      val inputStream = entity.getContent
      content = io.Source.fromInputStream(inputStream).getLines.mkString
      inputStream.close()
    }
    httpResponse.close()
    content
  }
}

class OneOne extends Test {
  override def getResult: Boolean = {
    val document = Jsoup.parse(getStringByGet)

    document.getElementsContainingText("menu").size() > 0 && document.getElementsContainingText("banners").size() > 0
  }

  override def getTask: String = "Главная страница содержит слова «menu» и «banners»."

  override def getFileName: String = "1_01.html"
}

class OneTwo extends Test {
  override def getResult: Boolean = {
    val document = Jsoup.parse(getStringByGet)

    document.getElementsByTag("tr").last().getElementsContainingText("CoolSoft by Somebody").size() > 0
  }

  override def getTask: String = "В нижней ячейке таблицы присутствует текст «CoolSoft by Somebody»."

  override def getFileName: String = "1_02.html"
}
