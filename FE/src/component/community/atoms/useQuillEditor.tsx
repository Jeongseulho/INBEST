import { useMemo } from "react";
import AWS from "aws-sdk";
import ReactQuill from "react-quill";
export const useQuillEdit = ({ quillRef }: { quillRef: React.LegacyRef<ReactQuill> }) => {
  const imageHandler = async () => {
    const input = document.createElement("input");
    input.setAttribute("type", "file");
    input.setAttribute("accept", "image/*");
    input.click();

    input.addEventListener("change", async () => {
      //이미지를 담아 전송할 formData를 만든다
      const file = input.files?.[0];

      try {
        //업로드할 파일의 이름으로 Date 사용
        const name = Date.now();
        //s3 관련 설정들
        AWS.config.update({
          region: import.meta.env.VITE_APP_AWS_S3_BUCKET_REGION,
          accessKeyId: import.meta.env.VITE_APP_AWS_S3_BUCKET_ACCESS_KEY_ID,
          secretAccessKey: import.meta.env.VITE_APP_AWS_S3_BUCKET_SECRET_ACCESS_KEY,
        });
        //앞서 생성한
        const upload = new AWS.S3.ManagedUpload({
          params: {
            ACL: "public-read",
            Bucket: "in-best",
            Key: `board/${name}`,
            Body: file,
          },
        });
        //이미지 업로드
        //업로드 된 이미지 url을 가져오기
        const url_key = await upload.promise().then((res) => res.Key);
        console.log(url_key);
        //useRef를 사용해 에디터에 접근한 후
        //에디터의 현재 커서 위치에 이미지 삽입
        const editor = (quillRef as React.RefObject<ReactQuill>).current?.getEditor();
        const range = editor!.getSelection();
        // 가져온 위치에 이미지를 삽입한다
        editor!.insertEmbed(range!.index, "image", url_key);
      } catch (error) {
        console.log(error);
      }
    });
  };

  const modules = useMemo(() => {
    return {
      toolbar: {
        // 툴바에 넣을 기능들을 순서대로 나열하면 된다.
        container: [
          ["bold", "italic", "underline", "strike", "blockquote"],
          [{ size: ["small", false, "large", "huge"] }, { color: [] }],
          [{ list: "ordered" }, { list: "bullet" }, { indent: "-1" }, { indent: "+1" }, { align: [] }],
          ["image"],
        ],
        handlers: {
          image: imageHandler,
        },
      },
    };
  }, []);
  return { modules };
};
