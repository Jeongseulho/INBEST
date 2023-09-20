import StockList from "../../common/StockList";
import DecreaseGraphIcon from "../../common/DecreaseGraphIcon";

interface Props {
  groupCode: string | undefined;
}

const InvestingDomestic = ({ groupCode }: Props) => {
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
  return (
    <div>
      <StockList stockList={stockList} title="급상승 주식" />
      <DecreaseGraphIcon title="코스피" desc="급하락 주식에 대한 설명입니다." />
    </div>
  );
};
export default InvestingDomestic;
