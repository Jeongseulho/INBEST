import { getCompanySummary } from "../../../api/investingCompany";
import { useQuery } from "react-query";
import stability from "../../../asset/image/stability.png";
import company_size from "../../../asset/image/company_size.png";
import profit from "../../../asset/image/profit.png";
import up from "../../../asset/image/up.png";

interface Props {
  companyInfo: { code: string; name: string };
}
const CompanyMainIcon = ({ companyInfo }: Props) => {
  const { data, isLoading } = useQuery(["companySummary", companyInfo.code], () => getCompanySummary(companyInfo.code));
  // TODO: 도움말 및 스켈레톤 로딩
  return (
    <>
      {isLoading ? (
        <></>
      ) : (
        data && (
          <>
            <div className=" flex items-center gap-4 justify-center">
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={stability} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">안정성 : </p>
                  <p className={`${data[0].stability >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].stability}
                  </p>
                </div>
              </div>
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={company_size} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">규모형태 : </p>
                  <p className={`${data[0].size >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].size}
                  </p>
                </div>
              </div>
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={up} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">성장성 : </p>
                  <p className={`${data[0].growth >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].growth}
                  </p>
                </div>
              </div>
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={profit} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">수익성 : </p>
                  <p className={`${data[0].profitability >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].profitability}
                  </p>
                </div>
              </div>
            </div>
            <div className=" flex items-center justify-center gap-10">
              <div className=" col-start-2 flex justify-center items-center gap-1">
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">매출액 자산 증가율 : </p>
                  <p className={`${data[0].revenue_growth >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].revenue_growth}%
                  </p>
                </div>
              </div>
              <div className=" col-span-1 flex justify-center items-center gap-1">
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">순이익 증가율 : </p>
                  <p
                    className={`${
                      data[0].operating_profit_growth >= 50 ? "text-mainDark" : "text-myRed"
                    } font-bold text-lg`}
                  >
                    {data[0].operating_profit_growth}%
                  </p>
                </div>
              </div>
            </div>
          </>
        )
      )}
    </>
  );
};

export default CompanyMainIcon;
