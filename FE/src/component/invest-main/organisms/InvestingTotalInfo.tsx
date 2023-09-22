import inprogress_member_cnt from "../../../asset/image/inprogress_member_cnt.png";
import TotalInfoItem from "../molecules/TotalInfoItem";
import total_member_cnt from "../../../asset/image/total_member_cnt.png";
import inprogress_group_cnt from "../../../asset/image/inprogress_group_cnt.png";

const InvestingTotalInfo = () => {
  return (
    <div className=" shadow-component h-full p-4 ">
      <h3 className=" mb-6">모의투자 진행 현황</h3>
      <div className=" flex flex-col gap-12">
        <TotalInfoItem img={total_member_cnt} title="총 회원 수" number={1000} change={10} />
        <TotalInfoItem img={inprogress_member_cnt} title="모의 투자 진행중 인원" number={1000} change={-10} />
        <TotalInfoItem img={inprogress_group_cnt} title="모의 투자 진행중 그룹" number={1000} change={50} />
      </div>
    </div>
  );
};
export default InvestingTotalInfo;
