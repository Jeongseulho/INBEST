import {
  getAmericaTradeVolumeStockList,
  getAmericaMarketCapStockList,
  getAmericaIncreaseStockList,
  getNasdaq,
  getDowJones,
  getSP500,
  getVix,
  getExchangeRate,
} from "../../api/investingStockInfo";
import { useQuery } from "react-query";
import AbroadStockList from "./AbroadStockList";
import GraphIconComponent from "../../component/common/GraphIconComponent";
import { CompanyInfo } from "../../type/InvestingCompanyDetail";
interface Props {
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}
const InvestingAbroad = ({ setCompanyInfo }: Props) => {
  const { data: tradeVolumeStockList, isLoading: isLoadingSearchStockList } = useQuery(
    ["americaTradeVolumeStockList"],
    getAmericaTradeVolumeStockList
  );
  const { data: marketCapStockList, isLoading: isLoadingMarketCapStockList } = useQuery(
    ["americaMarketCapStockList"],
    getAmericaMarketCapStockList
  );
  const { data: increaseStockList, isLoading: isLoadingIncreaseStockList } = useQuery(
    ["americaIncreaseStockList"],
    getAmericaIncreaseStockList
  );
  const { data: nasdaq, isLoading: isLoadingNasdaq } = useQuery(["nasdaq"], getNasdaq);
  const { data: dowJones, isLoading: isLoadingDowJones } = useQuery(["dowJones"], getDowJones);
  const { data: sp500, isLoading: isLoadingSp500 } = useQuery(["sp500"], getSP500);
  const { data: vix, isLoading: isLoadingVix } = useQuery(["vix"], getVix);
  const { data: exchangeRate, isLoading: isLoadingExchangeRate } = useQuery(["exchangeRate"], getExchangeRate);

  return (
    <div className=" flex flex-col items-center gap-4">
      <div className=" flex gap-4">
        <GraphIconComponent
          title="나스닥"
          desc="나스닥 설명"
          state={nasdaq?.fluctuation_state}
          isLoading={isLoadingNasdaq}
        />
        <GraphIconComponent
          title="다우존스"
          desc="다우존스 설명"
          state={dowJones?.fluctuation_state}
          isLoading={isLoadingDowJones}
        />
        <GraphIconComponent
          title="S&P 500"
          desc="S&P 500 설명"
          state={sp500?.fluctuation_state}
          isLoading={isLoadingSp500}
        />
        <GraphIconComponent title="VIX" desc="VIX 설명" state={vix?.fluctuation_state} isLoading={isLoadingVix} />
        <GraphIconComponent
          title="환율"
          desc="환율 설명"
          state={exchangeRate?.exchange_rate_change_state}
          isLoading={isLoadingExchangeRate}
        />
      </div>
      <div className=" flex gap-4 w-full">
        <AbroadStockList
          stockList={tradeVolumeStockList}
          isLoading={isLoadingSearchStockList}
          title="많이 사고 파는 주식"
          setCompanyInfo={setCompanyInfo}
        />
        <AbroadStockList
          stockList={marketCapStockList}
          isLoading={isLoadingMarketCapStockList}
          title="시가총액 높은 주식"
          setCompanyInfo={setCompanyInfo}
        />
        <AbroadStockList
          stockList={increaseStockList}
          isLoading={isLoadingIncreaseStockList}
          title="급상승 주식"
          setCompanyInfo={setCompanyInfo}
        />
      </div>
    </div>
  );
};
export default InvestingAbroad;
