import { AiFillQuestionCircle } from "react-icons/ai";

const LinkingModeTag = () => {
  return (
    <div className="h-8 flex items-center justify-between rounded-full border-2 border-gray-300 py-3 px-2 gap-1">
      <p className=" text-lightRed text-sm  ">계좌 연동 모드</p>
      <div className="group relative">
        <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
        <span className=" bg-opacity-80 z-50 hidden group-hover:block text-sm text-white bg-gray-500 rounded px-2 py-1 absolute -top-12 left-1/2 transform -translate-x-1/2 w-40">
          계좌 연동 모드에 대한 설명입니다.
        </span>
      </div>
    </div>
  );
};
export default LinkingModeTag;
