import Slider from "rc-slider";
import { useLocation } from "react-router-dom";
import { calEndDateAndProceed } from "../../../util/formatDateSign";
import calendar from "../../../asset/image/calendar.png";

const Period = () => {
  const location = useLocation();
  const { formattedStartDate, formattedEndDate, proceed, remainDays } = calEndDateAndProceed(
    location.state.startDate,
    location.state.period
  );
  return (
    <div className=" shadow-component col-span-4 row-span-1 p-4 flex flex-col gap-7">
      <div className=" flex items-center gap-2">
        <img src={calendar} width={40} />
        <h5>남은 기간</h5>
      </div>
      <div className=" flex flex-col gap-5">
        <div className=" flex items-center justify-between">
          <p className=" text-myGray">시작 : {formattedStartDate}</p>
          <p className=" font-medium">{remainDays}일 남았습니다</p>
          <p className=" text-myGray">종료 : {formattedEndDate}</p>
        </div>
        <Slider min={0} max={100} defaultValue={0} value={proceed} />
        <p className=" text-myGray text-sm text-center">{proceed}% 진행되었습니다.</p>
      </div>
    </div>
  );
};
export default Period;
