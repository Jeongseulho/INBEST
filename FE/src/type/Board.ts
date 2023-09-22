export interface Board {
  seq: string;
  view: number;
  likes: number;
  userSeq: number;
  context: string;
  title: string;
  files: string[];
  imgList: string[];
  commentList: string[];
  writer: Writer;
  createdDate: Date;
  lastModifiedDate: Date;
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
