export type ChatType = "enter" | "exit" | "message";
export interface Chat {
  type: ChatType;
  simulationSeq: number;
  userSeq: number;
  message: string;
  dateTime: string;
  profileImgSearchName: string;
  nickname: string;
}
