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
        base_url="https://openrouter.ai/api/v1",
        api_key="YOUR_KEY",
    )

    completion = client.chat.completions.create(
        extra_headers={
        },
        extra_body={},
        model="deepseek/deepseek-chat-v3-0324:free",
        # лучше отдельно разбить название статьи и ее содержимое
        messages=[
            {
                "role": "user",
                "content":"""
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
            }
        ]
    )
    return completion.choices[0].message.content
