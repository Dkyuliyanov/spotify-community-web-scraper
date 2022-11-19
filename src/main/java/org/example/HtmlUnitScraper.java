package org.example;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlPage;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


public class HtmlUnitScraper {
    public static void main(String[] args) throws IOException {

        // Connect to a URL and return an HTML page
        String url = "https://community.spotify.com/t5/Accounts/bd-p/spotifyaccountrelated";
        HtmlPage htmlPage = connect(url);

        // Collect unique URLs
        Set<String> anchorUrlSet = htmlPage.getAnchors().stream()
                .map(htmlAnchor -> htmlAnchor.getAttribute("href"))
                .collect(Collectors.toSet());


        // Filter URLs by board type
        List<String> filteredThreadUrlList = anchorUrlSet.stream()
                .filter(e -> e.startsWith("/t5/Accounts/"))
                .toList();




        System.out.println();


    }

    private static HtmlPage connect(String url) throws IOException {
        WebClient webClient = new WebClient();

        webClient.getOptions().setUseInsecureSSL(true);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setJavaScriptEnabled(false);

        return webClient.getPage(url);
    }

}
