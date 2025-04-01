from transformers import pipeline

classifier = pipeline("text-classification", model="mrm8488/bert-tiny-finetuned-fake-news-detection")

def checkFakeNews(text):
    return True if classifier(text) > 0.5 else False
