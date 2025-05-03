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
        # ключ максима
        api_key="YOUR_KEY"
    )
    prompt = """
    Ты — парсер новостей. Твоя задача — из переданного HTML-кода страницы извлечь **последние 10** актуальных статей или новостей и вернуть их в точном формате, никакого лишнего текста.
    1. Игнорируй всё, что не является основным контентом статьи: рекламные блоки, меню, футеры, боковые панели, виджеты и т.д.
    2. Оцени актуальность материалов и выбери **ТОЛЬКО 10** новостей/статей. НЕ НУЖНО писать никаких слов и символов кроме ссылок названий, никакой нумерации, никакого приветствия, строго по структуре.
    3. Для каждой статьи получи:
       - **ТЕКСТ** (до 400 слов)
       - **Ссылку** на источник (полный URL)
    4. Верни результат в формате:
      <url статьи>==<текст этой статьи>==
    5. Если статей нет, верни ровно строку: -1        
    Вот HTML-код страницы:
    """ + get_html(url_request)

    completion = client.chat.completions.create(
        model="deepseek/deepseek-chat-v3-0324",
        store=True,
        messages=[
            {"role": "user", "content": prompt}
        ]
    )


    return completion.choices[0].message.content

