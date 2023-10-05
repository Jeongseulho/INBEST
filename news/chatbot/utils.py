import openai

openai.api_key = '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'

def generate_response(prompt):
    response = openai.Completion.create(
        engine="text-davinci-002",
        prompt=prompt,
        temperature=0.5,
        max_tokens=100
    )
    return response.choices[0].text.strip()


'''
def extract_title_and_content(url):
    response = requests.get(url)
    soup = BeautifulSoup(response.content, 'html.parser')

    title_element = soup.find("dd", class_="fc1").find("strong")
    content_element = soup.find("div", class_="scrollY", attrs={"tabindex": "0"})

    if title_element:
        title = title_element.get_text(strip=True)
        forbidden_chars = r'\\/:*?"<>|'

        title = ''.join(char for char in title if char not in forbidden_chars)
    else:
        title = ""

    if content_element:
        content = content_element.get_text(strip=True)
    else:
        content = "Content not found"

    return title, content

def load_single_document(file_path):
    loader = TextLoader(file_path, encoding="utf-8")
    return loader.load()[0]

def load_documents(source_dir):
    all_files = os.listdir(source_dir)
    return [load_single_document(f"{source_dir}/{file_name}") for file_name in all_files]
'''