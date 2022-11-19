package org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;
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

    threadUrlList.forEach(System.out::println);

//    HtmlPage threadPage = loadHtmlPage(firstThreadUrl);

    System.out.println();

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
