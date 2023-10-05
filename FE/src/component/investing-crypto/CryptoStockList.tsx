import IncreaseIcon from "../../component/common/IncreaseIcon";
import DecreaseIcon from "../../component/common/DecreaseIcon";
import { CoinStockInfoList } from "../../type/InvestingStockInfo";
import { fluctuationStringToNumber } from "../../util/formatStockInfo";
import { truncateContent } from "../../util/formatContent";
import Skeleton from "react-loading-skeleton";
import { CompanyInfo } from "../../type/InvestingCompanyDetail";

interface Props {
  title: string;
  isLoading: boolean;
  stockList: CoinStockInfoList | undefined;
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}

const CryptoStockList = ({ title, stockList, isLoading }: Props) => {
  return (
    <div className=" shadow-component flex flex-col p-4 gap-2 w-1/3">
      <h4>{title}</h4>
      <div className=" flex items-center justify-center gap-12 border-y-2 ">
        <p className=" text-center font-semiBold w-1/2">이름</p>
        <p className=" text-center font-semiBold w-1/2">가격</p>
        {/* <p className=" w-20 text-center font-semiBold">거래량</p> */}
      </div>
      <div className=" flex flex-col gap-2">
        {isLoading ? (
          <>
            {Array(10)
              .fill(null)
              .map((_, index) => (
                <Skeleton key={index} height={32} />
              ))}
          </>
        ) : (
          <>
            {stockList?.map((stock, index) => (
              <div
                className=" flex justify-center gap-12 cursor-pointer hover:bg-gray-400 hover:bg-opacity-20 items-center py-1 rounded-md transition-colors duration-300"
                key={index}
              >
                <div className=" flex items-center justify-center w-1/2 gap-1">
                  <img src={stock.image_url} width={20} />
                  <p className=" text-center ">{truncateContent(stock.Name, 8)}</p>
                </div>
                <div className=" flex items-center justify-center w-1/2">
                  <p className=" text-center">₩{stock.Price}</p>
                  {fluctuationStringToNumber(stock.Fluctuation) >= 0 ? (
                    <IncreaseIcon number={fluctuationStringToNumber(stock.Fluctuation)} />
                  ) : (
                    <DecreaseIcon number={fluctuationStringToNumber(stock.Fluctuation)} />
                  )}
                </div>
                {/* <p className=" w-24 text-center">{stock.거래량}</p> */}
              </div>
            ))}
          </>
        )}
      </div>
    </div>
  );
};
export default CryptoStockList;
