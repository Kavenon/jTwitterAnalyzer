package pl.edu.agh.student.downloader;

import pl.edu.agh.student.Config;
import twitter4j.conf.Configuration;
import twitter4j.conf.ConfigurationBuilder;

public class ConfigProvider {

    public Configuration getConfiguration(){
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setOAuthConsumerKey(Config.CONSUMER_KEY);
        cb.setOAuthConsumerSecret(Config.CONSUMER_SECRET);
        cb.setOAuthAccessToken(Config.ACCESS_TOKEN);
        cb.setOAuthAccessTokenSecret(Config.ACCESS_TOKEN_SECRET);
        return cb.build();
    }

}
