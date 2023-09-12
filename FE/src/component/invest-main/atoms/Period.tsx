import calendar from "../../../asset/image/calendar.png";

const Period = () => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-regular me-2">모의 투자 진행 기간</p>
        <img src={calendar} width={40} />
      </div>
      <p className=" font-bold text-xl">30 일</p>
    </div>
  );
};
export default Period;
