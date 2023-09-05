import { Link } from "react-router-dom";
import temp_logo from "/image/temp_logo.png";

const Header = () => {
  return (
    <header className="w-screen h-20 flex justify-between items-center bg-gray-50 bg-opacity-90">
      <Link to="/">
        <img className="w-32 h-20 mx-24 " src={temp_logo} />
      </Link>
      <a className=" text-center text-black text-xl font-normal">모의투자 게임</a>
      <a className=" text-center text-black text-xl font-normal">랭킹</a>
      <a className=" text-center text-black text-xl font-normal">게시판</a>
      <a className=" text-center text-black text-xl font-normal">금융 상품 추천</a>
      <a className=" text-center text-black text-xl font-normal">금융 사전</a>
      <a className=" text-center text-black text-xl font-normal">관리자 페이지</a>
      <Link to="/login" className=" text-center me-10 text-black text-xl font-normal">
        로그인/ 프로필 사진
      </Link>
    </header>
  );
};
export default Header;
