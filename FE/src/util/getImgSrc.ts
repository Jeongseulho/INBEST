export const getImgSrc = (content: string) => {
  const parser = new DOMParser();
  const doc = parser.parseFromString(content, "text/html");

  // 첫 번째 이미지 요소 검색
  const firstImage = doc.querySelector("img");

  if (firstImage) {
    // 이미지의 속성 가져오기
    const src = firstImage.getAttribute("src"); // 이미지 소스 URL
    return src;
  }
};
