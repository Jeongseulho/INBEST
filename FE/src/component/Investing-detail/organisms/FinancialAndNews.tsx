import table from "../../../asset/image/table.png";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import { useQuery } from "react-query";
import { getCompanyNews } from "../../../api/investingNews";
import CompanyNews from "../atoms/CompanyNews";
import { getFinancialStatements } from "../../../api/investingCompany";
import { FinancialStatements } from "../../../type/CompanyInfo";
import { numberFormat } from "../../../util/formatMoney";
import Skeleton from "react-loading-skeleton";
import { AiFillQuestionCircle } from "react-icons/ai";
interface Props {
  companyInfo: CompanyInfo;
}

const FinancialAndNews = ({ companyInfo }: Props) => {
  const { data: companyNews, isLoading: isLoadingCompanyNews } = useQuery(["news", companyInfo.code], () =>
    getCompanyNews(companyInfo.code)
  );
  const { data: financialStatements, isLoading: isLoadingFinancialStatements } = useQuery(
    ["financialStatements", companyInfo.code],
    () => getFinancialStatements(companyInfo.code)
  );
  const keyToKoreanMap: {
    [key: string]: string;
  } = {
    current_assets: "유동자산",
    non_current_assets: "비유동자산",
    total_assets: "자산총계",
    current_liabilities: "유동부채",
    non_current_liabilities: "비유동부채",
    total_liabilities: "부채총계",
    capital: "자본금",
    total_equity: "자본총계",
    revenue: "매출액",
    gross_profit: "매출총이익",
    operating_profit: "영업이익",
    income_before_tax: "법인세차감전이익",
    income_tax_expense: "법인세비용",
    net_income: "총당기순이익",
  };
  const keyToDescMap: { [key: string]: string } = {
    current_assets: "유동자산이란 1년 이내에 현금화가 가능한 자산을 의미합니다.",
    non_current_assets: "비유동자산이란 1년 이상의 기간이 소요되는 자산을 의미합니다.",
    total_assets: "자산총계란 회사의 총 자산을 의미합니다.",
    current_liabilities: "유동부채란 1년 이내에 상환해야 하는 부채를 의미합니다.",
    non_current_liabilities: "비유동부채란 1년 이상의 기간이 소요되는 부채를 의미합니다.",
    total_liabilities: "부채총계란 회사의 총 부채를 의미합니다.",
    capital: "자본금이란 회사의 자본금을 의미합니다.",
    total_equity: "자본총계란 회사의 총 자본을 의미합니다.",
    revenue: "매출액이란 회사의 총 매출액을 의미합니다.",
    gross_profit: "매출총이익이란 회사의 총 매출총이익을 의미합니다.",
    operating_profit: "영업이익이란 회사의 총 영업이익을 의미합니다.",
    income_before_tax: "법인세차감전이익이란 법인세를 제외한 총 이익을 의미합니다.",
    income_tax_expense: "법인세비용이란 법인세를 의미합니다.",
    net_income: "총당기순이익이란 총 당기순이익을 의미합니다.",
  };
  //TODO: 도움말, skeleton loading
  return (
    <div className=" flex flex-col gap-4">
      <div className=" flex items-center gap-2">
        <img src={table} width={40} />
        <h5>재무제표 / 뉴스</h5>
      </div>
      <div className=" flex gap-4 justify-center">
        <div className=" shadow-component flex flex-col gap-4 p-4 w-1/2">
          <h6>재무 제표</h6>
          <div className=" flex flex-col w-full">
            {isLoadingFinancialStatements ? (
              <></>
            ) : (
              financialStatements &&
              Object.keys(financialStatements[0]).map((status, index) => {
                if (
                  status === "seq" ||
                  status === "company_seq_id" ||
                  status === "total_asset_growth_rate" ||
                  status === "revenue_asset_growth_rate" ||
                  status === "net_income_growth_rate" ||
                  status === "operating_profit_margin" ||
                  status === "roe" ||
                  status === "roic" ||
                  status === "debt_to_equity_ratio"
                )
                  return;
                return (
                  <div className=" flex border-2 justify-between w-full" key={index}>
                    <div className=" flex gap-1 border-r-2 w-2/5 p-2 items-center">
                      <p className=" ">{keyToKoreanMap[status]}</p>
                      <div className="group relative cursor-pointer">
                        <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                        <div className=" bg-opacity-80 z-50 hidden group-hover:block text-sm bottom-4 w-56 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                          {keyToDescMap[status]}
                        </div>
                      </div>
                    </div>

                    <p className=" p-2">
                      {numberFormat(financialStatements[0][status as keyof FinancialStatements[0]])}원
                    </p>
                  </div>
                );
              })
            )}
          </div>
        </div>
        <div className=" shadow-component flex flex-col gap-4 p-4">
          <h6>기업 관련 뉴스</h6>
          <div className=" flex flex-col gap-8 ">
            {isLoadingCompanyNews ? (
              <>
                <Skeleton height={60} width={360} />
                <Skeleton height={60} width={360} />
                <Skeleton height={60} width={360} />
                <Skeleton height={60} width={360} />
                <Skeleton height={60} width={360} />
                <Skeleton height={60} width={360} />
                <Skeleton height={60} width={360} />
              </>
            ) : (
              companyNews?.map((news, index) => (
                <CompanyNews
                  key={index}
                  title={news.title}
                  sentiment_analysis={news.sentiment_analysis}
                  imgUrl={news.image_url}
                  linkUrl={news.link_url}
                  time={news.time}
                />
              ))
            )}
          </div>
        </div>
      </div>
    </div>
  );
};
export default FinancialAndNews;
