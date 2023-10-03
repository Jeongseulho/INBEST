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
          width={460}
          top={-70}
          desc1="
          미국의 장외 주식거래시장에 상장된 종목들을 종합적으로 표시한 수치입니다."
          desc2="특히, 기술분야 및 IT 기업들이 많이 포함되어 있습니다."
          desc3={`
          현재 ${nasdaq?.fluctuation_rate.toFixed(2)}% 만큼 변화하였습니다.
            `}
          state={nasdaq?.fluctuation_state}
          isLoading={isLoadingNasdaq}
        />
        <GraphIconComponent
          title="다우존스"
          width={470}
          top={-50}
          desc1="미국 주식 시장에서 선별된 30개의 기업에 대해 종합적으로 표시한 수치입니다."
          desc2={`
          현재 ${dowJones?.fluctuation_rate.toFixed(2)}% 만큼 변화하였습니다.
            
          `}
          state={dowJones?.fluctuation_state}
          isLoading={isLoadingDowJones}
        />
        <GraphIconComponent
          title="S&P 500"
          width={480}
          top={-50}
          desc1="미국 주식 시장에서 선별된 500개의 기업에 대해 종합적으로 표시한 수치입니다."
          desc2={`
          
          현재 ${sp500?.fluctuation_rate.toFixed(2)}% 만큼 변화하였습니다.
            `}
          state={sp500?.fluctuation_state}
          isLoading={isLoadingSp500}
        />
        <GraphIconComponent
          width={420}
          top={-50}
          title="VIX"
          desc1="주식 시장의 예상 변동성과 불안정성을 나타내는 수치입니다."
          desc2="이 수치가 증가할수록 주식 시장의 변동성과 불안정성이 증가합니다."
          state={vix?.fluctuation_state}
          isLoading={isLoadingVix}
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
