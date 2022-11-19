package org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class HtmlUnitScraper {

  public static void main(String[] args) throws IOException {

    // Connect to a URL and return an HTML page (home page)
    HtmlPage htmlPage = loadHtmlPage(UrlManipulator.ACCOUNTS_BOARD_FIRST_PAGE);

    // Collect URLs
    Set<String> anchorStringUrlSet = UrlManipulator.collectUrls(htmlPage);
    
    List<String> threadUrlList = UrlManipulator.generateThreadUrls(anchorStringUrlSet, UrlManipulator.ACCOUNTS);



    HtmlPage threadPage = loadHtmlPage(threadUrlList.get(0));

    // get title text
    String title = getThreadTitle(threadPage);
    // get title post text
    String titlePostText = getTitlePostText(threadPage);
    // get next reply
    String firstReply = getFirstReply(threadPage);
    // get all replies
    List<String> threadRepliesList = getReplies(threadPage);

    threadRepliesList.forEach(reply -> System.out.println("REPLY: \n" + reply));
    System.out.println();

  }

  private static List<String> getReplies(HtmlPage threadPage) {

    List<String> repliesList = new ArrayList<>();
    try {
      for (int i = 1; i < 20; i++) {
        DomElement domElement = threadPage.getFirstByXPath(StringUtil.generatePostXpath(i));
        String currentReply = domElement.asNormalizedText();
        repliesList.add(currentReply);
      }

    } catch (NullPointerException e) {
      System.out.println("End of thread");
      return repliesList;
    }

    return repliesList;
  }

  private static String getFirstReply(HtmlPage threadPage) {
    DomElement domElement = threadPage.getFirstByXPath(
        "/html/body/div[2]/center/div[1]/div/div/div/div[1]/div[6]/div/div/div/div[2]/div[1]/div[1]/div/div/div[1]/div[2]/div/div[3]/div/div/div[1]/div");

    return domElement.asNormalizedText();
  }

  private static String getTitlePostText(HtmlPage threadPage) {

    DomElement domElement = threadPage.getFirstByXPath(
        "/html/body/div[2]/center/div[1]/div/div/div/div[1]/div[2]/div/div/div/div[1]/div/div/div/div/div[1]/div[2]/div/div[3]/div/div/div[1]/div");
    return domElement.asNormalizedText();
  }

  private static String getThreadTitle(HtmlPage threadPage) {

    DomElement domElement = threadPage.getFirstByXPath(
        "/html/body/div[2]/center/div[1]/div/div/div/div[1]/div[2]/div/div/div/div[1]/div/div/div/div/div[1]/div[2]/div/div[1]/div/div/div/div[1]/div/h2/span/div");
    return domElement.asNormalizedText();
  }








  public static HtmlPage loadHtmlPage(String url) throws IOException {
    WebClient webClient = new WebClient();

    webClient.getOptions().setUseInsecureSSL(true);
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);

    return webClient.getPage(url);
  }
}
