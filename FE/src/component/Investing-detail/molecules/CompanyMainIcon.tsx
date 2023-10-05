import { getCompanySummary } from "../../../api/investingCompany";
import { useQuery } from "react-query";
import stability from "../../../asset/image/stability.png";
import company_size from "../../../asset/image/company_size.png";
import profit from "../../../asset/image/profit.png";
import up from "../../../asset/image/up.png";
import { AiFillQuestionCircle } from "react-icons/ai";
interface Props {
  companyInfo: { code: string; name: string };
}
const CompanyMainIcon = ({ companyInfo }: Props) => {
  const { data, isLoading } = useQuery(["companySummary", companyInfo.code], () => getCompanySummary(companyInfo.code));
  return (
    <>
      {isLoading ? (
        <></>
      ) : (
        data && (
          <>
            <div className=" flex items-center gap-8 justify-center">
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={stability} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">안정성 : </p>
                  <p className={`${data[0].stability >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].stability}
                  </p>
                  <div className="group relative cursor-pointer">
                    <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                    <div className=" bg-opacity-90 z-50 hidden group-hover:block text-sm bottom-4 w-80 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                      평가 요소 : 부채비율, 유동자산, 유동부채
                      <br />
                      평가 기준 : 부채비율이 100%기준으로 점수 측정
                      <br />
                      (유동자산 / 유동부채) 200% 기준으로 점수 측정
                    </div>
                  </div>
                </div>
              </div>
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={company_size} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">규모형태 : </p>
                  <p className={`${data[0].size >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].size}
                  </p>
                  <div className="group relative cursor-pointer">
                    <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                    <div className=" bg-opacity-90 z-50 hidden group-hover:block text-sm bottom-4 w-96 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                      평가 요소 : 매출액, 영업이익, 자본금
                      <br />
                      평가 기준 : 평가 요소들의 평균값을 기준으로 점수 측정
                    </div>
                  </div>
                </div>
              </div>
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={up} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">성장성 : </p>
                  <p className={`${data[0].growth >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].growth}
                  </p>
                  <div className="group relative cursor-pointer">
                    <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                    <div className=" bg-opacity-90 z-50 hidden group-hover:block text-sm bottom-4 w-80 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                      평가 요소 : 자산증가율, 영업이익증가율
                      <br />
                      평가 기준 : 2022 반기 ~ 2023 반기 사이에 평가 요소를 계산하여 점수 측정
                    </div>
                  </div>
                </div>
              </div>
              <div className=" col-span-1 flex flex-col items-center gap-1">
                <img src={profit} width={100} />
                <div className=" flex items-center gap-1">
                  <p className="  text-myGray font-bold">수익성 : </p>
                  <p className={`${data[0].profitability >= 50 ? "text-mainDark" : "text-myRed"} font-bold text-lg`}>
                    {data[0].profitability}
                  </p>
                  <div className="group relative cursor-pointer">
                    <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                    <div className=" bg-opacity-90 z-50 hidden group-hover:block text-sm bottom-4 w-80 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                      평가 요소 : 매출액순이익율, 자본금순이익률, 매출액영업이익율
                      <br />
                      평가 기준 : 2022 반기 ~ 2023 반기 사이에 평가 요소를 계산하여 점수 측정
                    </div>
                  </div>
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
                  <div className="group relative cursor-pointer">
                    <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                    <div className=" bg-opacity-90 z-50 hidden group-hover:block text-sm bottom-4 w-96 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                      2022 반기 ~ 2023 반기 사이에 평가 요소를 계산하여 점수 측정
                    </div>
                  </div>
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
                  <div className="group relative cursor-pointer">
                    <AiFillQuestionCircle className="text-gray-500 hover:text-gray-700" />
                    <div className=" bg-opacity-90 z-50 hidden group-hover:block text-sm bottom-4 w-96 text-white bg-gray-500 rounded px-2 py-1 absolute left-1/2 transform -translate-x-1/2 ">
                      2022 반기 ~ 2023 반기 사이에 해당 지표 증가율 계산하여 높을수록 점수 추가
                    </div>
                  </div>
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
