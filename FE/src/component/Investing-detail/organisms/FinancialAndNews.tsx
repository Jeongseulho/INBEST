import table from "../../../asset/image/table.png";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import { useQuery } from "react-query";
import { getCompanyNews } from "../../../api/investingNews";
import CompanyNews from "../atoms/CompanyNews";
import { getFinancialStatements } from "../../../api/investingCompany";
import { FinancialStatements } from "../../../type/CompanyInfo";
import { numberFormat } from "../../../util/formatMoney";
import Skeleton from "react-loading-skeleton";
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
              Object.keys(financialStatements[0]).map((status) => {
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
                  return <></>;
                return (
                  <div className=" flex border-2 justify-between w-full">
                    <p className=" border-r-2 w-2/5 p-2">{keyToKoreanMap[status]}</p>
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
                <Skeleton height={44} />
                <Skeleton height={44} />
                <Skeleton height={44} />
                <Skeleton height={44} />
                <Skeleton height={44} />
                <Skeleton height={44} />
                <Skeleton height={44} />
              </>
            ) : (
              companyNews?.map((news) => (
                <CompanyNews
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
