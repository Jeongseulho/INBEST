import { BsQuestionCircleFill } from "react-icons/bs";

const BoostModeTag = () => {
  return (
    <div className=" w-32 flex items-center justify-around rounded-full border-2 border-gray-300 py-1 px-4">
      <p className=" text-primary ">가속 모드</p>
      <BsQuestionCircleFill />
    </div>
  );
};
export default BoostModeTag;
