from bs4 import BeautifulSoup

html_doc = """
<html>
  <head>
    <title>복잡한 HTML 예제</title>
  </head>
  <body>
    <div class="content">
      <h1>Main Heading</h1>
      <p class="paragraph">첫 번째 단락</p>
      <p class="paragraph">두 번째 단락</p>
    </div>
    <div class="footer">
      <p>Copyright © 2023</p>
    </div>
  </body>
</html>
"""

soup = BeautifulSoup(html_doc, 'html.parser')

paragraphs = soup.select('p')
print(paragraphs)
print('')

content_div = soup.select_one('.content')
print(content_div)
print('')

paragraphs_in_content = content_div.select('p')
print(paragraphs_in_content)
print('')

main_heading = soup.select_one('h1')
print(main_heading.text)  # 출력: Main Heading
print('')

copyright_div = soup.select_one('.footer')
print(copyright_div)
print('')

copyright_text = copyright_div.select_one('p').text
print(copyright_text)  # 출력: Copyright © 2023