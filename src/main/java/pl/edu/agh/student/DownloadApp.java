package pl.edu.agh.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.student.downloader.ConfigProvider;
import pl.edu.agh.student.downloader.TweetRequest;
import pl.edu.agh.student.downloader.TwitterDownloader;
import pl.edu.agh.student.model.Tweet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DownloadApp {

    private static final ObjectMapper OM = new ObjectMapper();

    public static void main( String[] args ){

        System.out.println("Twitter downloader started...");
        TwitterDownloader twitterDownloader = new TwitterDownloader(new ConfigProvider());

        List<Tweet> result = twitterDownloader
                .getTweets(new TweetRequest("#stałempodblokiem", "pl", 5));

        try(PrintWriter writer = new PrintWriter(new File("filewithtweets.json"))){
            writer.write(OM.writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } ;

        System.out.println("Twitter downloader finished...");

    }
}
