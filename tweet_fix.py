import pandas as pd
import time
import os
import sys
import csv

def read(file_path="tweets.csv"):
    result = []
    with open(file_path, 'rU', encoding='utf-8') as f:
        reader = csv.reader(f, delimiter=';')
        for row in reader:
            result.append(row)

    return result

def append(file_path, valuesArray):
    with open(file_path, 'a+', encoding='utf-8') as csvfile:
        spamwriter = csv.writer(csvfile, delimiter=';', quoting=csv.QUOTE_NONNUMERIC)
        spamwriter.writerow([s for s in valuesArray]);
    return

tweets = read()
for tweet in tweets:
    tweet[1] = tweet[1].replace('\n', '').replace('\r', '')
    append("tweets_oneline.csv", tweet)



