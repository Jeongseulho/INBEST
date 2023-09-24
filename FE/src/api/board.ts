import { temp } from "./tempapi";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { Board, Comment, GetBoardDetail, GetBoardList } from "../type/Board";
const apiWithAuth = temp("boards");

export const createBoard = async (userSeq: number, context: string, title: string): Promise<ApiSuccessMessage> => {
  console.log(userSeq, context, title);
  const { data } = await apiWithAuth.post("", {
    userSeq,
    context,
    title,
  });
  console.log(data);
  return data;
};
// export const createBoard = async (userSeq: number, context: string, title: string): Promise<ApiSuccessMessage> => {
//   console.log(userSeq, context, title);
//   const { data } = await apiWithAuth.post("", null, {
//     params: {
//       userSeq,
//       context,
//       title,
//     },
//   });
//   return data;
// };
export const getBoardList = async (pageNo: number): Promise<GetBoardList> => {
  const { data } = await apiWithAuth.get("", { params: { pageNo, pageSize: 10 } });
  return data;
};
export const getBoardDetail = async (seq: string): Promise<GetBoardDetail> => {
  const { data } = await apiWithAuth.get(`/${seq}`);
  return data;
};
export const postComment = async (boardSeq: string, userSeq: number, context: string): Promise<Comment> => {
  const { data } = await apiWithAuth.post(`/${boardSeq}/comments`, null, { params: { userSeq, context } });
  return data;
};
export const postCocomment = async (
  boardSeq: string,
  userSeq: number,
  context: string,
  commentSeq: string
): Promise<Comment> => {
  const { data } = await apiWithAuth.post(`/${boardSeq}/comments/${commentSeq}/cocoments`, null, {
    params: { userSeq, context },
  });
  return data;
};
