import { useBoardEditor } from "./useBoardEditor";
import QuillEditor from "../atoms/QuillEditor";
const BoardEditor = ({ title }: { title: string }) => {
  const { quillRef, htmlContent, setHtmlContent, onSubmit } = useBoardEditor(title);
  return (
    <>
      <QuillEditor quillRef={quillRef} htmlContent={htmlContent} setHtmlContent={setHtmlContent} />
      <div className="justify-end flex my-5">
        <button onClick={onSubmit}>작성하기</button>
      </div>
    </>
  );
};
export default BoardEditor;
