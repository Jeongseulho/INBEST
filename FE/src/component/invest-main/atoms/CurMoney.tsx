import coin from "../../../asset/image/coin.png";
import DecreaseIcon from "../../common/DecreaseIcon";
const CurMoney = () => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-regular me-2">현재머니</p>
        <img src={coin} width={40} />
      </div>
      <div className=" flex">
        <p className=" font-bold text-xl">1,000,000 원</p>
        <DecreaseIcon number={100} />
      </div>
    </div>
  );
};
export default CurMoney;
