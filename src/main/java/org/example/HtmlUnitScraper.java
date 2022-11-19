package org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.DomElement;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class HtmlUnitScraper {

  public static void main(String[] args) throws IOException {

    // Connect to a URL and return an HTML page (home page)
    HtmlPage htmlPage = loadHtmlPage(UrlString.ACCOUNTS_BOARD_FIRST_PAGE);

    // Collect URLs
    Set<String> anchorStringUrlSet = collectUrls(htmlPage);

    // Filter URLs by board type (Accounts)
    List<String> filteredThreadUrlAnchorStringList = filterThreadUrlStrings(anchorStringUrlSet,
        UrlString.ACCOUNT_PAGE_PREFIX);

    // Create a list of thread URLs (first page) by appending the anchor bits to the home page suffix
    List<String> threadUrlList = generateThreadUrls(UrlString.HOME_PAGE_SUFFIX,
        filteredThreadUrlAnchorStringList);

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

  private static List<String> generateThreadUrls(String homePageUrlSuffix,
      List<String> filteredThreadUrlAnchorStringList) {
    return filteredThreadUrlAnchorStringList.stream()
        .map(str -> homePageUrlSuffix + str)
        .toList();
  }


  private static List<String> filterThreadUrlStrings(Set<String> anchorStringUrlSet,
      String boardPrefix) {

    return anchorStringUrlSet.stream()
        .filter(e -> e.startsWith(boardPrefix))
        .toList();
  }

  private static Set<String> collectUrls(HtmlPage htmlPage) {

    return htmlPage.getAnchors().stream()
        .map(htmlAnchor -> htmlAnchor.getAttribute("href"))
        .collect(Collectors.toSet());
  }

  public static HtmlPage loadHtmlPage(String url) throws IOException {
    WebClient webClient = new WebClient();

    webClient.getOptions().setUseInsecureSSL(true);
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);

    return webClient.getPage(url);
  }
}
