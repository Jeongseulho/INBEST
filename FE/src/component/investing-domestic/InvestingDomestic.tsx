import StockList from "../common/StockList";
import DecreaseGraphIcon from "../common/DecreaseGraphIcon";
import IncreaseGraphIcon from "../common/IncreaseGraphIcon";
interface Props {
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const InvestingDomestic = ({ setCompanyCode }: Props) => {
  const stockList = [
    {
      name: "삼성전자",
      code: "005930",
      price: 100000,
      percentage: 10,
      favorite: true,
    },
    {
      name: "삼성전자",
      code: "005930",
      price: 10000,
      percentage: -10,
      favorite: true,
    },
    {
      name: "삼성전자",
      code: "005930",
      price: 288900,
      percentage: 100,
      favorite: false,
    },
  ];
  // TODO: desc 수정
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
        <StockList stockList={stockList} title="많이 사고 파는 주식" setCompanyCode={setCompanyCode} />
        <StockList stockList={stockList} title="급상승 주식" />
        <StockList stockList={stockList} title="시가총액 높은 주식" />
      </div>
    </div>
  );
};
export default InvestingDomestic;
