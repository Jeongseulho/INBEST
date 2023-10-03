import { formatNumberToKoreanWon } from "../../../util/formatMoney";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";
import { useOrderTab } from "./useOrderTab";
import { tradeStock } from "../../../api/investingTrade";
import { useMutation } from "react-query";
import { toast } from "react-toastify";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import { useParams } from "react-router-dom";
import { getHeldStockNum } from "../../../api/investingMyInfo";
import { useQuery } from "react-query";

interface Props {
  expectedPrice: number;
  companyInfo: CompanyInfo;
  stockType: 0 | 1 | 2;
}
const SellOrderTab = ({ expectedPrice, companyInfo, stockType }: Props) => {
  const { simulationSeq } = useParams<{ simulationSeq: string }>();
  const { amount, price, onChangeAmount, onChangePrice } = useOrderTab(expectedPrice);
  const profitPercentage =
    expectedPrice === 0 ? 0 : Number((((price - expectedPrice) / expectedPrice) * 100).toFixed(2));
  const { data: heldStockNum } = useQuery(["myStock", simulationSeq], () =>
    getHeldStockNum(simulationSeq, companyInfo.code, stockType)
  );

  const { mutate } = useMutation(
    () => tradeStock(simulationSeq, companyInfo.code, companyInfo.name, amount, price, 0, stockType, 0),
    {
      onMutate: () => {
        if (heldStockNum === null || (heldStockNum && heldStockNum.amount < amount)) {
          toast.error(
            `보유하고 있는 주식 개수가 부족합니다, 현재 ${
              heldStockNum === null ? 0 : heldStockNum.amount
            }주 보유중입니다.`
          );
          throw new Error("보유하고 있는 주식이 부족합니다.");
        }
      },
      onSuccess: () => {
        toast.success("매도 주문이 완료되었습니다.");
      },
    }
  );

  return (
    <div className=" flex items-center justify-around">
      <div className="">
        <div className=" flex flex-col gap-2 mt-3">
          <div className=" flex items-center justify-between">
            <p className=" text-myGray text-center">매도 수량</p>
            <div className=" flex items-center gap-4">
              <input
                type="number"
                step={1}
                min={1}
                className="p-1 border-gray-400 border bg-main bg-opacity-10 rounded-md text-right w-2/3"
                placeholder="판매할 수량"
                onChange={onChangeAmount}
                value={amount}
              />
              <p>주</p>
            </div>
          </div>
          <div className=" flex gap-2 items-center justify-between">
            <p className=" text-myGray text-center">매도 가격</p>
            <div className=" flex items-center gap-4">
              <input
                type="number"
                min={1}
                className="p-1 border-gray-400 border bg-main bg-opacity-10 rounded-md text-right w-2/3"
                placeholder="1주당 판매할 가격"
                onChange={onChangePrice}
                value={price}
              />
              <p>원</p>
            </div>
          </div>
          <div className=" mx-auto flex text-black text-opacity-80 items-center gap-2">
            <p>현재 예상 체결가는</p>
            <h5 className=" text-black font-semiBold ">{formatNumberToKoreanWon(expectedPrice || 10000)}</h5>
            <p>입니다</p>
          </div>
        </div>
      </div>
      <div className="flex flex-col gap-1 w-1/2">
        <div className=" bg-gray-300 bg-opacity-30 rounded-md p-4 flex flex-col gap-4">
          <div className=" mx-auto flex text-black text-opacity-80 items-center">
            <p>총&nbsp;&nbsp;</p>
            <h5 className=" text-black font-semiBold ">{amount}주</h5>
            <p>를&nbsp;&nbsp;</p>
            <h5 className=" text-black font-semiBold ">{formatNumberToKoreanWon(amount * price)}</h5>
            <p>에 판매합니다.</p>
          </div>
          <div className=" mx-auto flex text-black text-opacity-80 items-center text-sm">
            {profitPercentage == 0 ? (
              <p>예상 체결가에 팔아요</p>
            ) : profitPercentage > 0 ? (
              <>
                <p>예상 체결가보다</p>
                <IncreaseIcon number={profitPercentage} />
                <p>더 비싸게 팔아요</p>
              </>
            ) : (
              <>
                <p>예상 체결가보다</p>
                <DecreaseIcon number={profitPercentage} />
                <p>더 싸게 팔아요</p>
              </>
            )}
          </div>
        </div>
        <p className=" text-sm">수수료(부가세 포함): 0.05%</p>
        <button className="primary-btn" onClick={() => mutate()}>
          매도 주문하기
        </button>
      </div>
    </div>
  );
};
export default SellOrderTab;
