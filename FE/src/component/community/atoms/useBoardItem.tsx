import { Board } from "../../../type/Board";

export const useBoardItem = (board: Board) => {
  const contentText = board.context.replace(/(<([^>]+)>)/gi, "");
  return { contentText };
};
