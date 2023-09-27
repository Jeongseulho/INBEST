import { useRef, useState } from "react";
import { createBoard } from "../../../api/board";
import userStore from "../../../store/userStore";
import ReactQuill from "react-quill";
import { toast } from "react-toastify";
import { useNavigate } from "react-router-dom";
import { useQueryClient } from "react-query";

export const useBoardEditor = (title: string) => {
  const queryClient = useQueryClient();

  const { userInfo } = userStore();
  const quillRef = useRef<ReactQuill | null>(null);
  const [htmlContent, setHtmlContent] = useState("");
  const navigate = useNavigate();

  const onSubmit = async () => {
    if (title.trim() === "") {
      return toast.error("제목을 입력해 주세요.");
    }
    const description = quillRef.current?.getEditor().root.innerHTML ?? "";
    if (description.trim() === "") {
      return toast.error("내용을 입력해 주세요.");
    }
    try {
      await createBoard(userInfo!.seq, description, title);
      alert("게시물이 등록되었습니다.");
      queryClient.invalidateQueries({
        queryKey: ["getBoardList"],
      });

      navigate("/community");
    } catch (err) {
      console.log(err);
    }
  };
  return { quillRef, htmlContent, setHtmlContent, onSubmit };
};
