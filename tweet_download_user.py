import time
import os
import sys
import csv
from TwitterSearch import *

def append(file_path, author, text, date, retweet_count, favorite_count, followers_count, tags):
    print('writing', file_path)
    valuesArray = [author, text.replace('\n', '').replace('\r', ''), date, str(retweet_count), str(favorite_count), str(followers_count), ':'.join(tags)]
    with open(file_path, 'a+', encoding='utf-8') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=';', quoting=csv.QUOTE_NONNUMERIC)
        spamwriter.writerow([s for s in valuesArray]);
    return

try:
    file_path = "tweets_user.csv"
    if len(sys.argv) > 1:
        file_path = "tweets_" + sys.argv[1] + ".csv"

    tuo = TwitterUserOrder(sys.argv[1])
    tuo.set_exclude_replies(True)
    tuo.set_include_rts(False)

    querystr = tuo.create_search_url()


    ts = TwitterSearch(
         consumer_key = 'tjYkJwTKlpK40ZblE2XIqbI8a',
         consumer_secret = 'njaZM1pzG27wa9OXjd4DheeY3WARFNxy5UtHU1EzRdK0Tind6e',
         access_token = '907999680503713792-Yz1pnFxt1hWSMzNGnjMS7vFARUvFEWg',
         access_token_secret = 'x2m3oZEkkR2dflLGUmXFtxGwj8r2mQvd6kBUizetmuNbI'
    )

    for tweet in ts.search_tweets_iterable(tuo):

         tagsraw = tweet.get('entities', {'hashtags':[]}).get('hashtags', [])
         tags = []
         for tag in tagsraw:
             tags.append(tag.get('text'))
         append(file_path, tweet['user']['screen_name'], tweet['text'].replace('\n', '').replace('\r', ''), tweet['created_at'], tweet['retweet_count'], tweet['favorite_count'], tweet['user']['followers_count'], tags)

except TwitterSearchException as e:
    print(e)

