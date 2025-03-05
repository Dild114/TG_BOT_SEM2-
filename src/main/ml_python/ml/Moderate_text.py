from transformers import pipeline

hate_speech_model = pipeline("text-classification", model="facebook/roberta-hate-speech-dynabench-r4-target")
toxicity_model = pipeline("text-classification", model="unitary/unbiased-toxic-roberta")

def split_text(text, max_length=450):
    return [text[i:i+max_length] for i in range(0, len(text), max_length)]


# если flag = true, то текст норм если false, то не проходит цензуру
def moderate_text(text):
    chunks = split_text(text)
    flag = True

    for chunk in chunks:
        hate_result = hate_speech_model(chunk)[0]
        toxic_result = toxicity_model(chunk)[0]
        if hate_result["score"] <= 0.3 or toxic_result["score"] >= 0.7:
            flag = False

        print(f"Chunk: {chunk[:50]}...")
        print(f"Hate score: {hate_result['score']}, label: {hate_result['label']}")
        print(f"Toxic score: {toxic_result['score']}, label: {toxic_result['label']}\n")
    return flag

