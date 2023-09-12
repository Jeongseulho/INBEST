import IncreaseIcon from "../../common/IncreaseIcon";
import crown from "../../../asset/image/crown.png";
const MyGroupRank = () => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-regular me-2">나의 순위</p>
        <img src={crown} width={40} />
      </div>
      <div className=" flex">
        <p className=" font-bold text-xl">3위</p>
        <IncreaseIcon number={100} />
      </div>
    </div>
  );
};
export default MyGroupRank;
