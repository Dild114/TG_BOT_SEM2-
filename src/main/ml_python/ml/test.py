from transformers import AutoTokenizer, AutoModelForSeq2SeqLM, pipeline

# Задаем название модели
model_name = "sberbank-ai/ruT5-base"

# Загружаем токенизатор и модель
tokenizer = AutoTokenizer.from_pretrained(model_name, legacy=False)

model = AutoModelForSeq2SeqLM.from_pretrained(model_name)

# Для модели T5 желательно добавить префикс, указывающий на задачу (например, "summarize: ")
prompt_prefix = "summarize: "

# Пример русского текста для пересказа
text = (
    "В последнее время всё больше внимания уделяется развитию технологий обработки естественного языка. "
    "Исследователи работают над созданием моделей, способных понимать и генерировать тексты на разных языках, "
    "а также адаптировать их под конкретные задачи, такие как суммаризация, перевод и генерация диалогов."
)

# Создаем конвейер для суммаризации
summarizer = pipeline("summarization", model=model, tokenizer=tokenizer)

# Получаем пересказ текста; параметры max_length и min_length можно корректировать
summary = summarizer(prompt_prefix + text, max_length=150, min_length=30, do_sample=False)
print(summary[0]['summary_text'])
