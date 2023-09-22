import BoardEditor from "../molecules/BoardEditor";
import { useCommunityCreate } from "./useCommunityCreate";
const CommunityCreate = () => {
  const { title, setTitle } = useCommunityCreate();
  return (
    <>
      <input
        type="text"
        className="w-full mt-10 h-12 border-gray-300 px-3 border-b-2"
        placeholder="제목"
        onChange={(e) => setTitle(e.target.value)}
      />
      <BoardEditor title={title} />
    </>
  );
};

export default CommunityCreate;
