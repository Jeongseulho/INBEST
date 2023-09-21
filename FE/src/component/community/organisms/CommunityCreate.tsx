import BoardEditor from "../molecules/BoardEditor";

const CommunityCreate = () => {
  return (
    <>
      <input type="text" className="w-full mt-10 h-12 border-gray-300 px-3 border-b-2" placeholder="제목" />
      <BoardEditor />
    </>
  );
};

export default CommunityCreate;
