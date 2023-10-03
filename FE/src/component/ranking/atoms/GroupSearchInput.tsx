import { BsSearch } from "react-icons/bs";
import GroupSearchModal from "./GroupSearchModal";
import { useGroupSearchInput } from "./useGroupSearchInput";
const GroupSearchInput = ({ setSearchSeq }: { setSearchSeq: React.Dispatch<React.SetStateAction<number>> }) => {
  const { inputText, setInputText, showModal, setShowModal } = useGroupSearchInput();
  return (
    <>
      <input
        value={inputText}
        type="text"
        className="rounded-md px-2 h-10 w-3/4"
        placeholder="그룹 검색"
        onChange={(e) => setInputText(e.target.value)}
        onKeyUp={(e) => {
          if (e.key === "Enter") {
            e.preventDefault();
            setShowModal(true);
          }
        }}
      />
      <div onClick={() => setShowModal(true)}>
        <BsSearch
          style={{
            position: "absolute",
            top: "50%",
            transform: "translate(-50%, -50%)",
            right: "0",
            cursor: "pointer",
          }}
        />
      </div>
      <GroupSearchModal
        showModal={showModal}
        setSearchSeq={setSearchSeq}
        setShowModal={setShowModal}
        inputText={inputText}
      />
    </>
  );
};
export default GroupSearchInput;
