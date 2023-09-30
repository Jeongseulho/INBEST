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
  const { data: nasdaq } = useQuery(["nasdaq"], getNasdaq);
  const { data: dowJones } = useQuery(["dowJones"], getDowJones);
  const { data: sp500 } = useQuery(["sp500"], getSP500);
  const { data: vix } = useQuery(["vix"], getVix);
  const { data: exchangeRate } = useQuery(["exchangeRate"], getExchangeRate);

  return (
    <div className=" flex flex-col items-center gap-4">
      <div className=" flex gap-4">
        <GraphIconComponent title="나스닥" desc="나스닥 설명" state={nasdaq?.fluctuation_state} />
        <GraphIconComponent title="다우존스" desc="다우존스 설명" state={dowJones?.fluctuation_state} />
        <GraphIconComponent title="S&P500" desc="S&P500 설명" state={sp500?.fluctuation_state} />
        <GraphIconComponent title="VIX" desc="VIX 설명" state={vix?.fluctuation_state} />
        <GraphIconComponent title="환율" desc="환율 설명" state={exchangeRate?.exchange_rate_change_state} />
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
