import DecreaseGraphIcon from "../common/DecreaseGraphIcon";
import IncreaseGraphIcon from "../common/IncreaseGraphIcon";
import {
  getAmericaTradeVolumeStockList,
  getAmericaMarketCapStockList,
  getAmericaIncreaseStockList,
} from "../../api/investingStockInfo";
import { useQuery } from "react-query";
import AbroadStockList from "./AbroadStockList";
interface Props {
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}
const InvestingAbroad = ({ setCompanyCode }: Props) => {
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

  // TODO: desc 수정
  return (
    <div className=" flex flex-col items-center gap-4">
      <div className=" flex gap-4">
        <DecreaseGraphIcon title="나스닥" desc="코스피란?... 하강한다고 판단한 기준.." />
        <DecreaseGraphIcon title="다우존스" desc="급하락 주식에 대한 설명입니다." />
        <IncreaseGraphIcon title="S&P500" desc="급하락 주식에 대한 설명입니다." />
        <DecreaseGraphIcon title="나스닥100" desc="급하락 주식에 대한 설명입니다." />
        <IncreaseGraphIcon title="환율" desc="급하락 주식에 대한 설명입니다." />
      </div>
      <div className=" flex gap-4 w-full">
        <AbroadStockList
          stockList={tradeVolumeStockList}
          isLoading={isLoadingSearchStockList}
          title="많이 사고 파는 주식"
          setCompanyCode={setCompanyCode}
        />
        <AbroadStockList
          stockList={marketCapStockList}
          isLoading={isLoadingMarketCapStockList}
          title="시가총액 높은 주식"
          setCompanyCode={setCompanyCode}
        />
        <AbroadStockList
          stockList={increaseStockList}
          isLoading={isLoadingIncreaseStockList}
          title="급상승 주식"
          setCompanyCode={setCompanyCode}
        />
      </div>
    </div>
  );
};
export default InvestingAbroad;
