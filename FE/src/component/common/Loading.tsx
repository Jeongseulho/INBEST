import loading from "../../asset/image/spinner.svg";
const Loading = () => {
  return (
    <div className="flex justify-center items-center w-full h-screen bg-white">
      <img src={loading} alt="로딩중입니다." />
    </div>
  );
};
export default Loading;
