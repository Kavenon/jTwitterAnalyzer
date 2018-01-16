import time
import os
import sys
import csv
from TwitterSearch import *

def append(file_path, author, text, date, retweet_count, favorite_count, followers_count, tags):
    print('writing', file_path)
    valuesArray = [author, text, date, str(retweet_count), str(favorite_count), str(followers_count), ':'.join(tags)]
    with open(file_path, 'a+', encoding='utf-8') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=';', quoting=csv.QUOTE_NONNUMERIC)
        spamwriter.writerow([s for s in valuesArray]);
    return

try:
    file_path = "tweets.csv"
    if len(sys.argv) > 1:
        file_path = sys.argv[1]

    tso = TwitterSearchOrder()
    tso.set_keywords(['sejm', 'pis'], or_operator=True)
    tso.set_language('pl')
    tso.set_include_entities(True)
    tso.set_count(100)

    querystr = tso.create_search_url()

    tso2 = TwitterSearchOrder()
    tso2.set_search_url(querystr + '&exclude=retweets')

    ts = TwitterSearch(
         consumer_key = 'tjYkJwTKlpK40ZblE2XIqbI8a',
         consumer_secret = 'njaZM1pzG27wa9OXjd4DheeY3WARFNxy5UtHU1EzRdK0Tind6e',
         access_token = '907999680503713792-Yz1pnFxt1hWSMzNGnjMS7vFARUvFEWg',
         access_token_secret = 'x2m3oZEkkR2dflLGUmXFtxGwj8r2mQvd6kBUizetmuNbI'
    )

    def my_callback_closure(current_ts_instance):
         queries, tweets_seen = current_ts_instance.get_statistics()
         if queries > 0 and (queries % 5) == 0:
             time.sleep(60)

    for tweet in ts.search_tweets_iterable(tso2, callback=my_callback_closure):

         tagsraw = tweet.get('entities', {'hashtags':[]}).get('hashtags', [])
         tags = []
         for tag in tagsraw:
             tags.append(tag.get('text'))
         append(file_path, tweet['user']['screen_name'], tweet['text'].replace('\n', '').replace('\r', ''), tweet['created_at'], tweet['retweet_count'], tweet['favorite_count'], tweet['user']['followers_count'], tags)

except TwitterSearchException as e:
    print(e)

