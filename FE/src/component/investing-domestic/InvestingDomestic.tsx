import StockList from "../common/StockList";
import DecreaseGraphIcon from "../common/DecreaseGraphIcon";
import IncreaseGraphIcon from "../common/IncreaseGraphIcon";
import { getKorSearchStockList, getKorMarketCapStockList, getKorIncreaseStockList } from "../../api/investingStockInfo";
import { useQuery } from "react-query";

const InvestingDomestic = () => {
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

  return (
    <div className=" flex flex-col items-center gap-4">
      <div className=" flex gap-4">
        <DecreaseGraphIcon title="코스피" desc="코스피란?... 하강한다고 판단한 기준.." />
        <IncreaseGraphIcon title="코스닥" desc="급하락 주식에 대한 설명입니다." />
        <DecreaseGraphIcon title="코스피200" desc="급하락 주식에 대한 설명입니다." />
        <DecreaseGraphIcon title="코스닥200" desc="급하락 주식에 대한 설명입니다." />
        <IncreaseGraphIcon title="환율" desc="급하락 주식에 대한 설명입니다." />
      </div>
      <div className=" flex gap-4 w-full">
        <StockList stockList={searchStockList} isLoading={isLoadingSearchStockList} title="많이 검색되는 주식" />
        <StockList stockList={marketCapStockList} isLoading={isLoadingMarketCapStockList} title="시가총액 높은 주식" />
        <StockList stockList={increaseStockList} isLoading={isLoadingIncreaseStockList} title="급상승 주식" />
      </div>
    </div>
  );
};
export default InvestingDomestic;
