import { getBoardDetail } from "../../../api/board";

export const useBoardItem = () => {
  const onDetailPage = async (seq: string) => {
    try {
      const res = await getBoardDetail(seq);
      console.log(res);
    } catch (err) {
      console.log(err);
    }
  };

  return { onDetailPage };
};
