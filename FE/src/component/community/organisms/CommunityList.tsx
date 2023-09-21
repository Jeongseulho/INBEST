import { Link } from "react-router-dom";

const CommunityList = () => {
  return (
    <>
      <div>
        <header>
          <div className="flex justify-start">
            <Link to={"create"}>
              <button>글 작성하기</button>
            </Link>
          </div>
          <div></div>
        </header>
        <main></main>
      </div>
    </>
  );
};
export default CommunityList;
