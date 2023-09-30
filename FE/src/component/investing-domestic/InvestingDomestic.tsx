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
          desc="코스피 설명"
          state={kospi?.fluctuation_state}
          isLoading={isLoadingKospi}
        />
        <GraphIconComponent
          title="코스닥"
          desc="코스닥 설명"
          state={kosdaq?.fluctuation_state}
          isLoading={isLoadingKosdaq}
        />
        <GraphIconComponent
          title="코스피200"
          desc="코스피200 설명"
          state={kospi200?.fluctuation_state}
          isLoading={isLoadingKospi200}
        />
        <GraphIconComponent title="KRX" desc="KRX 설명" state={krx?.fluctuation_state} isLoading={isLoadingKrx} />
        <GraphIconComponent
          title="환율"
          desc="환율 설명"
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
