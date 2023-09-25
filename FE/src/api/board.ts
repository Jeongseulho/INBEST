import { instanceWithAuth } from "./interceptors";
import { ApiSuccessMessage } from "../type/ApiSuccessMessage";
import { Comment, GetBoardDetail, GetBoardList } from "../type/Board";
const apiWithAuth = instanceWithAuth("board-service/boards");

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

export const getBoardList = async (pageNo: number): Promise<GetBoardList> => {
  const { data } = await apiWithAuth.get("", { params: { pageNo, pageSize: 10 } });
  return data;
};
export const getBoardDetail = async (seq: string): Promise<GetBoardDetail> => {
  const { data } = await apiWithAuth.get(`/${seq}`);
  return data;
};
export const postComment = async (boardSeq: string, userSeq: number, context: string): Promise<Comment> => {
  const { data } = await apiWithAuth.post(`/${boardSeq}/comments`, { userSeq, context });
  return data;
};
export const postCocomment = async (
  boardSeq: string,
  userSeq: number,
  context: string,
  commentSeq: string
): Promise<Comment> => {
  const { data } = await apiWithAuth.post(`/${boardSeq}/comments/${commentSeq}/cocomments`, { userSeq, context });
  return data;
};

export const likeBoard = async (boardSeq: string, userSeq: number): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.put(`/${boardSeq}/likes/${userSeq}`);
  return data;
};
