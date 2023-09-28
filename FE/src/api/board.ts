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

export const getBoardList = async (pageNo: number, order: number): Promise<GetBoardList> => {
  let responseData;
  if (order === 0) {
    const { data } = await apiWithAuth.get("", { params: { pageNo, pageSize: 10 } });
    responseData = data;
  } else if (order === 1) {
    const { data } = await apiWithAuth.get("most-likes", { params: { pageNo, pageSize: 10, period: 100 } });
    responseData = data;
  } else {
    const { data } = await apiWithAuth.get("most-views", { params: { pageNo, pageSize: 10, period: 100 } });
    responseData = data;
  }
  return responseData;
};
export const getBoardTop10 = async (): Promise<GetBoardList> => {
  const { data } = await apiWithAuth.get("most-likes", { params: { pageNo: 1, pageSize: 10, period: 3 } });
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

export const likeBoard = async (boardSeq: string): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.put(`/${boardSeq}/likes`);
  return data;
};

export const deleteBoard = async (boardSeq: string): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.delete(`${boardSeq}`);
  return data;
};
export const deleteComment = async (boardSeq: string, commentSeq: string): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.delete(`${boardSeq}/comments/${commentSeq}`);
  return data;
};
export const deleteCocomment = async (
  boardSeq: string,
  commentSeq: string,
  cocomentSeq: string
): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.delete(`${boardSeq}/comments/${commentSeq}/cocomments/${cocomentSeq}`);
  return data;
};

export const putBoard = async (
  boardSeq: string,
  userSeq: number,
  context: string,
  title: string
): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.put(`${boardSeq}`, {
    userSeq,
    context,
    title,
  });
  console.log(data);

  return data;
};
export const putComment = async (boardSeq: string, commentSeq: string, context: string): Promise<Comment> => {
  const { data } = await apiWithAuth.put(`/${boardSeq}/comments/${commentSeq}`, { context });
  return data;
};
export const putCocomment = async (
  boardSeq: string,
  commentSeq: string,
  context: string,
  cocomentSeq: string
): Promise<Comment> => {
  const { data } = await apiWithAuth.put(`/${boardSeq}/comments/${commentSeq}/cocoments/${cocomentSeq}`, { context });
  return data;
};

export const likeComment = async (boardSeq: string, commentSeq: string): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.put(`/${boardSeq}/comments/${commentSeq}/likes`);
  return data;
};
export const likeCocomment = async (
  boardSeq: string,
  commentSeq: string,
  cocomentSeq: string
): Promise<ApiSuccessMessage> => {
  const { data } = await apiWithAuth.put(`/${boardSeq}/comments/${commentSeq}/cocomments/${cocomentSeq}/likes`);
  return data;
};

export const getBoardCount = async (keyword: string | null) => {
  const params: Record<string, string> = {};
  if (keyword) {
    params.keyword = keyword;
  }
  const { data } = await apiWithAuth.get("count", { params: params });

  return data;
};
