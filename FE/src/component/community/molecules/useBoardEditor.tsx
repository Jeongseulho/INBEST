import { useRef, useState } from "react";
import ReactQuill from "react-quill";
export const useBoardEditor = () => {
  const quillRef = useRef<ReactQuill | null>(null);
  const [htmlContent, setHtmlContent] = useState("");
  const onSubmit = () => {
    const description = quillRef.current?.getEditor().root.innerHTML;
    console.log(description);
  };
  return { quillRef, htmlContent, setHtmlContent, onSubmit };
};
