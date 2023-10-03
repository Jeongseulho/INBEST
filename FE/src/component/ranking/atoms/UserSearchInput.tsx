import { useState } from "react";
import { BsSearch } from "react-icons/bs";
import { useNavigate } from "react-router-dom";

const UserSearchInput = () => {
  const [inputText, setInputText] = useState("");
  const navigate = useNavigate();
  return (
    <>
      <input
        value={inputText}
        type="text"
        className="rounded-md px-2 h-10 w-3/4"
        placeholder="유저 검색"
        onChange={(e) => setInputText(e.target.value)}
        onKeyUp={(e) => {
          if (e.key === "Enter") {
            e.preventDefault();
            navigate(`/ranking/search/${inputText}`);
          }
        }}
      />
      <div onClick={() => navigate(`/ranking/search/${inputText}`)}>
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
    </>
  );
};
export default UserSearchInput;
