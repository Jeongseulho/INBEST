import calendar from "../../../asset/image/calendar.png";
import { calEndDateAndProceed } from "../../../util/formatDateSign";

interface Props {
  startDate: string;
  period: number;
}

const RemainPeriod = ({ startDate, period }: Props) => {
  const { formattedStartDate, formattedEndDate, proceed, remainDays } = calEndDateAndProceed(startDate, period);
  return (
    <div>
      <div className=" flex items-center gap-2">
        <p className=" font-medium text-gray-500 ">모의 투자 진행도</p>
        <img src={calendar} width={30} />
      </div>
      <p className=" font-bold text-xl">{remainDays}일 남았습니다.</p>
      <div className=" relative">
        <div className="h-5 bg-mainLight bg-opacity-50 rounded-3xl" />
        <div className={`w-[${proceed}%] h-5 z-10 absolute top-0 rounded-3xl bg-main`}></div>
      </div>
      <div className=" flex justify-between font-regular">
        <p>{formattedStartDate}</p>
        <p>진행도 : {proceed}%</p>
        <p>{formattedEndDate}</p>
      </div>
    </div>
  );
};
export default RemainPeriod;
