package org.example;




import com.gargoylesoftware.htmlunit.html.HtmlPage;

        import java.io.IOException;

        import static org.example.HtmlUnitScraper.connect;

class Scratch {
    public static void main(String[] args) throws IOException {

// Connect to a URL and return an HTML page
        String url = "https://community.spotify.com/t5/Accounts/bd-p/spotifyaccountrelated";
        HtmlPage htmlPage = connect(url);

        System.out.println(htmlPage.getBaseURL());
    }

}
