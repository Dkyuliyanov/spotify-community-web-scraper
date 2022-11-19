package org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.IOException;

public class PageLoader {
  public static HtmlPage loadHtmlPage(String url) throws IOException {
    WebClient webClient = new WebClient();

    webClient.getOptions().setUseInsecureSSL(true);
    webClient.getOptions().setCssEnabled(false);
    webClient.getOptions().setJavaScriptEnabled(false);

    return webClient.getPage(url);
  }

}
