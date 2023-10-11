import summary from "../../../asset/image/summary.png";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import CompanyMainIcon from "../molecules/CompanyMainIcon";
import IncomeThreeYearsChart from "../molecules/IncomeThreeYearsChart";
import RevenueThreeYearsChart from "../molecules/RevenueThreeYearsChart";
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
      <div className=" grid grid-cols-12 h-[80vh] grid-rows-2 gap-8">
        <div className=" shadow-component col-start-3 col-end-11 row-span-1 p-4 flex flex-col gap-10 items-center justify-center">
          <h6>기업 주요 지표</h6>
          <CompanyMainIcon companyInfo={companyInfo} />
        </div>
        <div className=" shadow-component col-start-2 col-end-7 p-4 flex flex-col gap-10 items-center justify-center">
          <h6>영업 이익 변화</h6>
          <IncomeThreeYearsChart companyInfo={companyInfo} />
        </div>
        <div className=" shadow-component col-start-7 col-end-12 p-4 flex flex-col gap-10 items-center justify-center">
          <h6>매출액 변화</h6>
          <RevenueThreeYearsChart companyInfo={companyInfo} />
        </div>
      </div>
    </div>
  );
};
export default Summary;
