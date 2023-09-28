import { useRef, useState } from "react";
import { createBoard, putBoard } from "../../../api/board";
import userStore from "../../../store/userStore";
import ReactQuill from "react-quill";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";
import { useSearchParams } from "react-router-dom";

export const useBoardEditor = (title: string) => {
  const queryClient = useQueryClient();

  const { userInfo } = userStore();
  const quillRef = useRef<ReactQuill | null>(null);
  const [htmlContent, setHtmlContent] = useState("");
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const onSubmit = async (postType: string) => {
    if (title.trim() === "") {
      return toast.error("제목을 입력해 주세요.");
    }
    const description = quillRef.current?.getEditor().root.innerHTML ?? "";
    if (description.trim() === "") {
      return toast.error("내용을 입력해 주세요.");
    }
    if (postType === "create") {
      try {
        await createBoard(userInfo!.seq, description, title);
        alert("게시물이 등록되었습니다.");
        queryClient.invalidateQueries("getBoardList");

        navigate("/community");
      } catch (err) {
        console.log(err);
      }
    } else {
      const boardSeq = searchParams.get("boardSeq");
      try {
        await putBoard(boardSeq!, userInfo!.seq, description, title);
        alert("게시물이 수정되었습니다.");
        queryClient.invalidateQueries("getBoardList");
        navigate("/community");
      } catch (err) {
        console.log(err);
      }
    }
  };
  return { quillRef, htmlContent, setHtmlContent, onSubmit };
};
