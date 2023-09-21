import Slider from "rc-slider";
import BoostModeTag from "../../common/BoostModeTag";

const Period = () => {
  return (
    <div className=" shadow-component col-span-4 row-span-1 p-4 flex flex-col gap-7">
      <div className=" flex items-center gap-10">
        <h5>남은 기간</h5>
        <BoostModeTag />
      </div>
      <div className=" flex flex-col gap-5">
        <div className=" flex items-center justify-between">
          <p className=" text-myGray">시작 : 2023.08.30</p>
          <p className=" font-medium">30일 남았습니다</p>
          <p className=" text-myGray">종료 : 2023.09.30</p>
        </div>
        <Slider min={0} max={100} defaultValue={0} value={50} />
        <p className=" text-myGray text-sm">가속 모드인 경우 실제 시간의 흐름과 다릅니다</p>
      </div>
    </div>
  );
};
export default Period;
