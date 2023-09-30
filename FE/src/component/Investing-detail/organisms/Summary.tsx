import summary from "../../../asset/image/summary.png";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";

interface Props {
  companyInfo: CompanyInfo;
}

const Summary = ({ companyInfo }: Props) => {
  return (
    <div className=" flex flex-col gap-4">
      <div className=" flex items-center gap-2">
        <img src={summary} width={40} />
        <h5>기업 분석 한눈에 보기</h5>
      </div>
      <div className=" grid grid-cols-12 h-[80vh] grid-rows-2 gap-4">
        <div className=" shadow-component col-start-3 col-end-11 row-span-1">{companyInfo.name}요약 아이콘</div>
        <div className=" shadow-component col-start-2 col-end-7">영업이익</div>
        <div className=" shadow-component col-start-7 col-end-12">매출액</div>
      </div>
    </div>
  );
};
export default Summary;
