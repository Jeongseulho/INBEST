export const removeHtmltag = (content: string) => {
  const contentText = content ? content.replace(/(<([^>]+)>)/gi, "") : null;

  return contentText;
};
