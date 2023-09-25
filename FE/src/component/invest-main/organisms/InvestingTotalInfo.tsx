import TotalInfoItem from "../molecules/TotalInfoItem";
import investing_status from "../../../asset/image/investing_status.png";

const InvestingTotalInfo = () => {
  return (
    <div className=" shadow-component h-full p-4 flex flex-col gap-10 w-11/12">
      <div className=" flex items-center gap-2">
        <img src={investing_status} width={70} />
        <h3 className=" mb-6">모의투자 진행 현황</h3>
      </div>
      <div className=" flex items-center gap-8 justify-around">
        <TotalInfoItem title="총 회원" number={1000} fluctuation={10} />
        <TotalInfoItem title="접속중 회원" number={380} fluctuation={-10} />
        <TotalInfoItem title="진행중 인원" number={21} fluctuation={50} />
        <TotalInfoItem title="진행중 그룹" number={245} fluctuation={10} />
        <TotalInfoItem title="종료된 그룹" number={125} fluctuation={-10} />
        <TotalInfoItem title="평균 수익률" number={250} fluctuation={50} />
      </div>
    </div>
  );
};
export default InvestingTotalInfo;
