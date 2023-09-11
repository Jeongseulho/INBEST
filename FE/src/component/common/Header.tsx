import { Link } from "react-router-dom";
import temp_logo from "../../asset/image/temp_logo.png";

const Header = () => {
  return (
    <header className="w-screen h-[8vh] flex justify-between items-center bg-gray-50  bg-opacity-90">
      <Link to="/">
        <img className="h-[9vh] mx-24 " src={temp_logo} />
      </Link>
      <Link to="/invest" className=" text-center text-xl">
        모의 투자
      </Link>
      <a className=" text-center text-xl">랭킹</a>
      <a className=" text-center text-xl">게시판</a>
      <a className=" text-center text-xl">금융 상품 추천</a>
      <a className=" text-center text-xl">금융 사전</a>
      <a className=" text-center text-xl">관리자 페이지</a>
      <Link to="/login" className=" text-center me-56 text-xl">
        로그인/ 프로필 사진
      </Link>
    </header>
  );
};
export default Header;
