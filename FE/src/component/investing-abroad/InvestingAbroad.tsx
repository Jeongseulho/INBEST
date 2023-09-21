import StockList from "../common/StockList";
import DecreaseGraphIcon from "../common/DecreaseGraphIcon";
import IncreaseGraphIcon from "../common/IncreaseGraphIcon";

interface Props {
  groupCode: string | undefined;
}

const InvestingAbroad = ({ groupCode }: Props) => {
  console.log(groupCode);
  const stockList = [
    {
      name: "삼성전자",
      price: 100000,
      percentage: 10,
      favorite: true,
    },
    {
      name: "삼성전자",
      price: 10000,
      percentage: -10,
      favorite: true,
    },
    {
      name: "삼성전자",
      price: 288900,
      percentage: 100,
      favorite: false,
    },
  ];
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
        <StockList stockList={stockList} title="많이 사고 파는 주식" />
        <StockList stockList={stockList} title="급상승 주식" />
        <StockList stockList={stockList} title="시가총액 높은 주식" />
      </div>
    </div>
  );
};
export default InvestingAbroad;
