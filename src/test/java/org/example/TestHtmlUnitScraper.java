package org.example;

import com.gargoylesoftware.htmlunit.html.HtmlPage;
import junit.framework.TestCase;

import java.io.IOException;

import static org.example.HtmlUnitScraper.connect;

public class TestHtmlUnitScraper extends TestCase {

    // Connect to a URL and return an HTML page
    protected String url;

    @Override
    protected void setUp() throws IOException {
        url = "https://community.spotify.com/t5/Accounts/bd-p/spotifyaccountrelated";

    }


    public void testConnect() throws IOException {

        HtmlPage htmlPage = connect(url);

        assertEquals(url, htmlPage.getBaseURL().toString());
    }

}
