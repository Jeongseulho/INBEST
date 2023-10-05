import img404 from "../../asset/image/404Img.png";
import { Link } from "react-router-dom";
const NotFound404 = () => {
  return (
    <div>
      <div className="text-center text-[5rem] mt-16">404 Not Found</div>
      <div className="flex justify-center mt-10">
        <img src={img404} className="w-56" />
      </div>
      <div className="flex justify-center mt-10">찾으시는 페이지가 존재하지 않습니다!</div>
      <div className="flex justify-center">
        <Link to={"/"}>
          <button className="w-28 h-10 bg-primary text-white rounded-full mt-10">홈으로 이동</button>
        </Link>
      </div>
    </div>
  );
};
export default NotFound404;
