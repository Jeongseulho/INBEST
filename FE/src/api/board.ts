import { temp } from "./tempapi";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { GetBoardList } from "../type/Board";
const apiWithAuth = temp("boards");

export const createBoard = async (userSeq: number, context: string, title: string): Promise<ApiSuccessMessage> => {
  console.log(userSeq, context, title);
  const { data } = await apiWithAuth.post("", null, {
    params: {
      userSeq,
      context,
      title,
    },
  });
  return data;
};
export const getBoardList = async (pageNo: number): Promise<GetBoardList> => {
  const { data } = await apiWithAuth.get("", { params: { pageNo, pageSize: 10 } });
  return data;
};
