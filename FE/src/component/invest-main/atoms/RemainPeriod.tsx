import calendar from "../../../asset/image/calendar.png";
const RemainPeriod = () => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-regular me-2">모의 투자 진행도</p>
        <img src={calendar} width={40} />
      </div>
      <p className=" font-bold text-xl">12일 남았습니다.</p>
      <div className=" relative">
        <div className="h-5 bg-mainLight bg-opacity-50 rounded-3xl" />
        <div className=" w-[50%] h-5 z-10 absolute top-0 rounded-3xl bg-main"></div>
      </div>
      <div className=" flex justify-between font-regular">
        <p>2023.08.30</p>
        <p>진행도 : 25%</p>
        <p>2023.09.30</p>
      </div>
    </div>
  );
};
export default RemainPeriod;
