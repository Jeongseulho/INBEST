export interface Board {
  seq: string;
  view: number;
  likes: number;
  userSeq: number;
  context: string;
  title: string;
  files: string[];
  imgList: string[];
  commentList: Comment[];
  writer: Writer;
  createdDate: string;
  lastModifiedDate: string;
}
export interface Writer {
  seq: number;
  email: string;
  name: string;
  nickname: string;
  birth: string;
  birthyear: null;
  birthday: null;
  gender: 1 | 0 | 2;
  profileImgSearchName: string;
  profileImgOriginalName: string;
  provider: string;
  role: string;
}

export interface GetBoardList {
  success: boolean;
  board: Board[];
}
export interface GetBoardDetail {
  success: boolean;
  board: Board;
  loginUserLike: boolean;
}

export interface Comment {
  boardId: null | string;
  cocommentList: Comment[];
  context: string;
  createdDate: string;
  lastModifiedDate: string;
  likes: number;
  seq: string;
  userSeq: number;
  writer: null;
}

export interface Cocoment extends Omit<Comment, "cocommentList"> {}
