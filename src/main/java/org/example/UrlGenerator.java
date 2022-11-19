package org.example;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class UrlGenerator {

  public final static String ACCOUNTS_BOARD_FIRST_PAGE = "https://community.spotify.com/t5/Accounts/bd-p/spotifyaccountrelated";

  public final static String HOME_PAGE = "https://community.spotify.com";

  public final static String ACCOUNTS = "/t5/Accounts/";

  public final static String PAGE = "/page/";

  public static Set<String> collectUrls(HtmlPage htmlPage) {

    return htmlPage.getAnchors().stream()
        .map(htmlAnchor -> htmlAnchor.getAttribute("href"))
        .collect(Collectors.toSet());
  }

  public static List<String> generateThreadUrls(Set<String> anchorStringUrlSet,
      String boardPageSuffix) {

    // Filter URLs by board type
    List<String> accountPageThreadUrlFilteredList = anchorStringUrlSet.stream()
        .filter(e -> e.startsWith(boardPageSuffix))
        .toList();

    // Generate board specific thread Urls
    return accountPageThreadUrlFilteredList.stream()
        .map(str -> UrlGenerator.HOME_PAGE + str)
        .toList();


  }


  public static String pageNumberUrl(String accountsBoardFirstPage, int pageNumber) {

    return accountsBoardFirstPage + PAGE + pageNumber;

  }
}
