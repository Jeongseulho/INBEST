import CryptoStockList from "./CryptoStockList";
import DecreaseGraphIcon from "../common/DecreaseGraphIcon";
import IncreaseGraphIcon from "../common/IncreaseGraphIcon";
import { getCoinStockList } from "../../api/investingStockInfo";
import { useQuery } from "react-query";
import FearRadialBar from "./FearRadialBar";
interface Props {
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const InvestingCrypto = ({ setCompanyCode }: Props) => {
  const { data, isLoading } = useQuery(["coinStockList"], getCoinStockList);

  return (
    <div className=" flex flex-col items-center gap-4">
      <div className=" flex gap-4">
        <DecreaseGraphIcon title="코스피" desc="코스피란?... 하강한다고 판단한 기준.." />
        <IncreaseGraphIcon title="코스닥" desc="급하락 주식에 대한 설명입니다." />
        <DecreaseGraphIcon title="코스피200" desc="급하락 주식에 대한 설명입니다." />
        <FearRadialBar percentage={10} />
        <IncreaseGraphIcon title="환율" desc="급하락 주식에 대한 설명입니다." />
      </div>
      <div className=" flex gap-4 w-full">
        <CryptoStockList
          setCompanyCode={setCompanyCode}
          stockList={data?.slice(0, 10)}
          isLoading={isLoading}
          title="많이 검색되는 주식"
        />
        <CryptoStockList
          setCompanyCode={setCompanyCode}
          stockList={data?.slice(10, 20)}
          isLoading={isLoading}
          title="시가총액 높은 주식"
        />
        <CryptoStockList
          setCompanyCode={setCompanyCode}
          stockList={data?.slice(20, 30)}
          isLoading={isLoading}
          title="급상승 주식"
        />
      </div>
    </div>
  );
};
export default InvestingCrypto;
