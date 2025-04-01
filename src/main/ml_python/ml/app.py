from typing import List
from transformers import pipeline
from fastapi import FastAPI
from pydantic import BaseModel
import Moderate_text
import Retelling_text
import parsing_sites

app = FastAPI()

summarizer = pipeline("summarization", model="facebook/bart-large-cnn")
hate_speech_model = pipeline("text-classification", model="facebook/roberta-hate-speech-dynabench-r4-target")
toxicity_model = pipeline("text-classification", model="unitary/unbiased-toxic-roberta")
model = pipeline("zero-shot-classification", model="facebook/bart-large-mnli")
classifierFake = pipeline("text-classification", model="mrm8488/bert-tiny-finetuned-fake-news-detection")

class RequestModel(BaseModel):
    article: str
    categories : List[str]

class RequestParsingModel(BaseModel):
    url: str

class RequestModelOnlyArticle(BaseModel):
    article: str

@app.post("/articles")
async def receive_json(data: RequestModel):
    result = model(data.article, data.categories)
    best_label = result["labels"][0]
    best_score = result["scores"][0]
    best_index = data.categories.index(best_label)
    if best_score > 0.8:
        return best_index
    else:
        return -1

# здесь -1 значит что статья не подходит, -2 что не проходит цензуру
@app.post("/articleswithmoderate")
async def receive_json(data: RequestModel):
    check_moderate = Moderate_text.moderate_text(data.article)
    print(check_moderate)
    print(check_moderate["result"])
    if check_moderate["result"]:
        result = model(data.article, data.categories)
        best_label = result["labels"][0]
        best_score = result["scores"][0]
        best_index = data.categories.index(best_label)
        if best_score > 0.8:
            return best_index
        else:
            return -1
    else:
        return -2

@app.post("/check_moderate")
async def receive_json(data: RequestModel):
    check_moderate = Moderate_text.moderate_text(data.article)
    return {"Moderate" : check_moderate}

@app.post("/retelling")
async def receive_json(data: RequestModelOnlyArticle):
    print(data)
    result = Retelling_text.retelling(data.article)
    return result

@app.post("/parsing")
async def receive_json(data: RequestParsingModel):
    result = parsing_sites.parsing(data.url)
    return result

# true - fake, false - real news
@app.post("/check_fake")
async def receive_json(data: RequestModelOnlyArticle):
    result = True if classifierFake(data.article)[0]["score"] > 0.77 else False
    return result
