import ReactQuill from "react-quill";
import "react-quill/dist/quill.snow.css";
import { useQuillEdit } from "./useQuillEditor";
import { memo } from "react";
interface BoardEditorProps {
  quillRef: React.LegacyRef<ReactQuill>;
  htmlContent: string;
  setHtmlContent: React.Dispatch<React.SetStateAction<string>>;
}

const QuillEditor = memo(({ quillRef, htmlContent, setHtmlContent }: BoardEditorProps) => {
  const { modules } = useQuillEdit({ quillRef });
  return (
    <>
      <ReactQuill ref={quillRef} value={htmlContent} onChange={setHtmlContent} modules={modules} />
    </>
  );
});
export default QuillEditor;
