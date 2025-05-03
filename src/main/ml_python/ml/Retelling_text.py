from openai import OpenAI

def retelling(text):
    client = OpenAI(
        base_url="https://openrouter.ai/api/v1",
        api_key="YOUR_KEY",
    )

    completion = client.chat.completions.create(
        extra_headers={
        },
        extra_body={},
        model="deepseek/deepseek-chat-v3-0324:free",
        messages=[
            {
                "role": "user",
                "content":"""
                Пожалуйста, перескажи приведённый текст, **сохрани его основные идеи и ключевые факты**, убрав всё второстепенное и примеры. **Сократи или растяни результат до ровно 100 слов**, оформи одним связным абзацем без лишних вводных или заключений.
                вот текст: 
        """ + text
            }
        ]
    )
    return completion.choices[0].message.content

def short_retelling(text):
    client = OpenAI(
        base_url="https://openrouter.ai/api/v1",
        api_key="YOUR_KEY",
    )

    completion = client.chat.completions.create(
        extra_headers={
        },
        extra_body={},
        model="deepseek/deepseek-chat-v3-0324:free",
        messages=[
            {
                "role": "user",
                "content":"""
                Пожалуйста, напиши название приведённого текста, **сохрани его основные идеи и ключевые факты**, убрав всё второстепенное и примеры. **Сократи результат меньше чем до 10 слов**, оформи одним связным абзацем без лишних вводных или заключений.
                вот текст: 
        """ + text
            }
        ]
    )
    return completion.choices[0].message.content
