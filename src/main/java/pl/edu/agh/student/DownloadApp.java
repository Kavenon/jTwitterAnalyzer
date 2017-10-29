package pl.edu.agh.student;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.edu.agh.student.downloader.ConfigProvider;
import pl.edu.agh.student.downloader.TweetRequest;
import pl.edu.agh.student.downloader.TwitterDownloader;
import twitter4j.Status;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class DownloadApp {

    public static final ObjectMapper OM = new ObjectMapper();

    public static void main( String[] args ){

        System.out.println("Twitter downloader started...");
        TwitterDownloader twitterDownloader = new TwitterDownloader(new ConfigProvider());

        List<Status> result = twitterDownloader
                .getTweets(new TweetRequest("#stałempodblokiem", "pl", 5));

        try(PrintWriter writer = new PrintWriter(new File(DownloadApp.class.getResource("/tweets.json").getPath()))){
            writer.write(OM.writeValueAsString(result));
        } catch (IOException e) {
            e.printStackTrace();
        } ;

        System.out.println("Twitter downloader finished...");

    }
}
