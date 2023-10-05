export const getImgCount = (content: string) => {
  const count = content ? content.split("<img src=").length - 1 : null;
  return count;
};
