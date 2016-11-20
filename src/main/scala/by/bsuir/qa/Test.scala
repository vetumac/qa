package by.bsuir.qa

import org.apache.http.client.methods.{HttpGet, HttpUriRequest, RequestBuilder}
import org.apache.http.impl.client.HttpClientBuilder
import org.jsoup.Jsoup

trait Test {

  val httpClient = HttpClientBuilder.create().build()

  def getResult: Boolean

  def getTask: String

  def getFileName: String

  def getStringByGet: String = getStringByRequest(new HttpGet("http://svyatoslav.biz/testlab/wt/"))

  def getStringByPostByHeightAndWeight(height: String, weight: String): String = {
    val httpPost = RequestBuilder.create("POST")
      .setUri("http://svyatoslav.biz/testlab/wt/index.php")
      .addParameter("name", "name")
      .addParameter("gender", "m")
      .addParameter("height", height)
      .addParameter("weight", weight)
      .build()

    getStringByRequest(httpPost)
  }

  private def getStringByRequest(request: HttpUriRequest): String = {
    val httpResponse = httpClient.execute(request)
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

class OneThree extends Test {
  override def getResult: Boolean = {
    val document = Jsoup.parse(getStringByGet)

    import collection.JavaConverters._

    document.getElementsByTag("input").asScala
      .filter(element => element.attr("type") == "text")
      .forall(element => element.attr("value").isEmpty) &&
      document.getElementsByTag("input").asScala
        .filter(element => element.attr("type") == "radio")
        .forall(element => element.getElementsByAttribute("checked").asScala.isEmpty)
  }

  override def getTask: String = "По умолчанию все текстовые поля формы пусты, а значение поля «Пол» не выбрано."

  override def getFileName: String = "1_03.html"
}

class OneFour extends Test {
  override def getResult: Boolean = {
    val document = Jsoup.parse(getStringByPostByHeightAndWeight("50", "3"))

    import collection.JavaConverters._

    document.getElementsByTag("input").asScala.isEmpty &&
      document.getElementsContainingText("Слишком большая масса тела").asScala.nonEmpty
  }

  override def getTask: String = "После заполнения поля «Рост» значением «50» и вес значением «3» и отправки формы, " +
    "форма исчезает, а вместо неё появляется надпись «Слишком большая масса тела»."

  override def getFileName: String = "1_04.html"
}

class TwoOne extends Test {
  override def getResult: Boolean = {
    val document = Jsoup.parse(getStringByGet)

    import collection.JavaConverters._

    document.getElementsByTag("input").asScala
      .filter(element => element.attr("type") == "text").length == 3




    last().getElementsContainingText("CoolSoft by Somebody").size() > 0
  }

  override def getTask: String = "Главная страница приложения сразу после открытия содержит форму с тремя текстовыми " +
    "полями, одной группой из двух радио-баттонов и одной кнопкой."

  override def getFileName: String = "2_01.html"
}