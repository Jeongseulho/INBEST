import { useEffect } from "react";
import BoardEditor from "../molecules/BoardEditor";
import { useCommunityCreate } from "./useCommunityCreate";
import { useSearchParams } from "react-router-dom";

const CommunityCreate = () => {
  const { title, setTitle } = useCommunityCreate();
  const [searchParams] = useSearchParams();
  const paramTitle = searchParams.get("title");
  useEffect(() => {
    if (paramTitle) {
      setTitle(paramTitle);
    }
  }, [paramTitle]);
  return (
    <>
      <input
        type="text"
        className="w-full mt-10 h-12 border-gray-300 px-3 border-b-2"
        placeholder="제목"
        value={title}
        onChange={(e) => setTitle(e.target.value)}
      />
      <BoardEditor title={title} />
    </>
  );
};

export default CommunityCreate;
