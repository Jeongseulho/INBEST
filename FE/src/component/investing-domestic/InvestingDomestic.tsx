import DomesticStockList from "./DomesticStockList";
import GraphIconComponent from "../../component/common/GraphIconComponent";
import {
  getKorSearchStockList,
  getKorMarketCapStockList,
  getKorIncreaseStockList,
  getKospi,
  getKosdaq,
  getKospi200,
  getExchangeRate,
  getKrx,
} from "../../api/investingStockInfo";
import { useQuery } from "react-query";
import { CompanyInfo } from "../../type/InvestingCompanyDetail";
interface Props {
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}

const InvestingDomestic = ({ setCompanyInfo }: Props) => {
  const { data: searchStockList, isLoading: isLoadingSearchStockList } = useQuery(
    ["korSearchStockList"],
    getKorSearchStockList
  );
  const { data: marketCapStockList, isLoading: isLoadingMarketCapStockList } = useQuery(
    ["korMarketCapStockList"],
    getKorMarketCapStockList
  );
  const { data: increaseStockList, isLoading: isLoadingIncreaseStockList } = useQuery(
    ["korIncreaseStockList"],
    getKorIncreaseStockList
  );
  const { data: kospi, isLoading: isLoadingKospi } = useQuery(["kospi"], getKospi);
  const { data: kosdaq, isLoading: isLoadingKosdaq } = useQuery(["kosdaq"], getKosdaq);
  const { data: kospi200, isLoading: isLoadingKospi200 } = useQuery(["kospi200"], getKospi200);
  const { data: krx, isLoading: isLoadingKrx } = useQuery(["krx"], getKrx);
  const { data: exchangeRate, isLoading: isLoadingExchangeRate } = useQuery(["exchangeRate"], getExchangeRate);

  return (
    <div className=" flex flex-col items-center gap-4">
      <div className=" flex gap-4">
        <GraphIconComponent
          title="코스피"
          width={380}
          top={-70}
          desc1="증권 거래소에 상장된 종목들을 종합적으로 표시한 수치입니다."
          desc2="주로 안정적인 대기업들이 이에 해당합니다."
          desc3={`
          현재 ${kospi?.fluctuation_rate.toFixed(2)}% 만큼 변화하였습니다.`}
          state={kospi?.fluctuation_state}
          isLoading={isLoadingKospi}
        />
        <GraphIconComponent
          title="코스닥"
          width={380}
          top={-70}
          desc1="장외 거래소에 상장된 종목들을 종합적으로 표시한 수치입니다."
          desc2="주로 중소, 벤처기업들이 이에 해당합니다."
          desc3={`
          현재 ${kosdaq?.fluctuation_rate.toFixed(2)}% 만큼 변화하였습니다.
          `}
          state={kosdaq?.fluctuation_state}
          isLoading={isLoadingKosdaq}
        />
        <GraphIconComponent
          title="코스피200"
          width={530}
          top={-50}
          desc1="코스피에 포함된 종목들 중에서 상위 200개의 기업에대해 종합적으로 표시한 수치입니다."
          desc2={`
          현재 ${kospi200?.fluctuation_rate.toFixed(2)}% 만큼변화하였습니다.`}
          state={kospi200?.fluctuation_state}
          isLoading={isLoadingKospi200}
        />
        <GraphIconComponent
          title="KRX 100"
          width={460}
          top={-50}
          desc1="한국 거래소에서 선별한 100개의 기업에 대해 종합적으로 표시한 수치입니다."
          desc2={`
          현재 ${krx?.fluctuation_rate.toFixed(2)}% 만큼 변화하였습니다.`}
          state={krx?.fluctuation_state}
          isLoading={isLoadingKrx}
        />
        <GraphIconComponent
          title="환율"
          width={220}
          top={-50}
          desc1="달러와 원의 환율을 나타냅니다."
          desc2={`
          현재 ${exchangeRate?.exchange_rate_change.toFixed(2)}% 만큼 변화하였습니다.
          `}
          state={exchangeRate?.exchange_rate_change_state}
          isLoading={isLoadingExchangeRate}
        />
      </div>
      <div className=" flex gap-4 w-full">
        <DomesticStockList
          setCompanyInfo={setCompanyInfo}
          stockList={searchStockList}
          isLoading={isLoadingSearchStockList}
          title="많이 검색되는 주식"
        />
        <DomesticStockList
          setCompanyInfo={setCompanyInfo}
          stockList={marketCapStockList}
          isLoading={isLoadingMarketCapStockList}
          title="시가총액 높은 주식"
        />
        <DomesticStockList
          setCompanyInfo={setCompanyInfo}
          stockList={increaseStockList}
          isLoading={isLoadingIncreaseStockList}
          title="급상승 주식"
        />
      </div>
    </div>
  );
};
export default InvestingDomestic;
