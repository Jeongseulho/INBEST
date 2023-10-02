import assets from "../../../asset/image/assets.png";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";
import { getMyAsset } from "../../../api/investingMyInfo";
import { useQuery } from "react-query";
import { formatNumberToWon } from "../../../util/formatMoney";
import spinner from "../../../asset/image/spinner.svg";
import { useParams } from "react-router-dom";

const MyAssets = () => {
  const { simulationSeq } = useParams();
  const { data: myAsset, isLoading: isLoadingMyAsset } = useQuery(["myAsset", simulationSeq], () =>
    getMyAsset(simulationSeq)
  );
  return (
    <div className=" shadow-component col-span-2 row-span-2 p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <img src={assets} width={40} />
        <h5>내 자산 변화</h5>
      </div>
      {isLoadingMyAsset ? (
        <img src={spinner} width={120} className=" mx-auto" />
      ) : (
        <>
          {/* <div className=" flex items-center">
        <h6>{formatNumberToWon(myAsset.)}</h6>
        {percentage >= 0 ? <IncreaseIcon number={percentage} /> : <DecreaseIcon number={percentage} />}
      </div>  */}
        </>
      )}
    </div>
  );
};
export default MyAssets;
