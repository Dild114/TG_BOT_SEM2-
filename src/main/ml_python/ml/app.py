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
class RequestModel(BaseModel):
    article: str
    categories : List[str]

class RequestParsingModel(BaseModel):
    url: str
@app.post("/articles")
async def receive_json(data: RequestModel):
    result = model(data.article, data.categories)
    best_label = result["labels"][0]
    best_score = result["scores"][0]
    best_index = data.categories.index(best_label)
    return {"best_label": best_label, "best_score": best_score, "best_index": best_index}


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
        return {"best_label": best_label, "best_score": best_score, "best_index": best_index, "Moderate" : check_moderate}
    else:
        return {"best_label" : None, "best_score" : None, "best_index" : None, "Moderate" : check_moderate}

@app.post("/check_moderate")
async def receive_json(data: RequestModel):
    check_moderate = Moderate_text.moderate_text(data.article)
    return {"Moderate" : check_moderate}

@app.post("/retelling")
async def receive_json(data: RequestModel):
    result = Retelling_text.retelling(data.article)
    return result

@app.post("/parsing")
async def receive_json(data: RequestParsingModel):
    result = parsing_sites.parsing(data.url)
    return result