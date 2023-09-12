import { BsQuestionCircleFill } from "react-icons/bs";

const LinkingModeTag = () => {
  return (
    <div className=" w-40 flex items-center justify-around rounded-full border-2 border-gray-300 py-1 px-4">
      <p className=" text-lightRed ">계좌 연동 모드</p>
      <BsQuestionCircleFill />
    </div>
  );
};
export default LinkingModeTag;
