import requests
from openai import OpenAI

def get_html(url):
    try:
        response = requests.get(url)
        response.raise_for_status()
        return response.text
    except requests.exceptions.RequestException as e:
        print(f"Ошибка при получении страницы: {e}")
        return None

def parser(url_request):


    client = OpenAI(
        # base_url="https://api.deepseek.com/v1",
        # gpt
        api_key="***REMOVED***"
        # deepseek
        # api_key="sk-10805575b3134e7ab24b5ed721604dd0"
    )
    prompt = """
    Ты — парсер новостей. Твоя задача — из переданного HTML-кода страницы извлечь **последние 10** актуальных статей или новостей и вернуть их в точном формате, без лишнего текста.
    1. Игнорируй всё, что не является основным контентом статьи: рекламные блоки, меню, футеры, боковые панели, виджеты и т.д.
    2. Оцени актуальность материалов и выбери **ТОЛЬКО 10** новостей/статей.
    3. Для каждой статьи получи:
    - **ТЕКСТ** (до 400 слов)
    - **Ссылку** на источник (полный URL)
    4. Формат вывода — **строго** повторять эту схему (разделитель — `q1w2e3r4t5y6u7i8o9@@#!@`):
    5. Если статей нет, верни ровно строку: -1
    Вот HTML-код страницы:
    """ + get_html(url_request)

    completion = client.chat.completions.create(
        model="gpt-4o-mini",
        # model="deepseek-chat",
        store=True,
        messages=[
            {"role": "user", "content": prompt}
        ]
    )

    return completion.choices[0].message.content

print(parser("https://habr.com/ru/articles/"))

