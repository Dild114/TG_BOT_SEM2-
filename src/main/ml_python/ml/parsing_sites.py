import trafilatura

def parsing(url):
    downloaded = trafilatura.fetch_url(url)
    text = trafilatura.extract(downloaded)
    return text
