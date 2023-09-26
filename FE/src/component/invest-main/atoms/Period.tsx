import calendar from "../../../asset/image/calendar.png";
interface Props {
  period: number | undefined;
}

const Period = ({ period }: Props) => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-medium text-gray-600">모의 투자 진행 기간</p>
        <img src={calendar} width={40} />
      </div>
      <p className=" font-bold text-xl">{period} 일</p>
    </div>
  );
};
export default Period;
