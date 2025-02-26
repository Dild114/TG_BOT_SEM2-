from deep_translator import GoogleTranslator

def translate_from_russian(text_ru):
    text_en = GoogleTranslator(source="ru", target="en").translate(text_ru)
    return text_en

def translate_from_english(text_eng):
    text_ru_back = GoogleTranslator(source="en", target="ru").translate(text_eng)
    return text_ru_back
