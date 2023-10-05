export const tierToString = (tier: number | undefined) => {
  if (tier === undefined) return "";
  if (tier >= 0 && tier < 100) return "브론즈";
  else if (tier >= 100 && tier < 200) return "실버";
  else if (tier >= 200 && tier < 300) return "골드";
  else return "다이아";
};
