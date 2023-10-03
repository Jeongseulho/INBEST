import Compare from "../organisms/Compare";
import MyAssets from "../organisms/MyAssets";
import MyStock from "../organisms/MyStock";
import RecentDealStock from "../organisms/RecentDealStock";
import { getMyAsset } from "../../../api/investingMyInfo";
import { useQuery } from "react-query";
import { useParams } from "react-router-dom";

const InvestingMyInfo = () => {
  const { simulationSeq } = useParams();

  const { data, isLoading } = useQuery(["myAsset", simulationSeq], () => getMyAsset(simulationSeq));
  const myProfit =
    data && data.length >= 2 ? (data[data.length - 1].asset - data[0].asset) / data[data.length - 1].asset : 0;

  return (
    <div className=" grid grid-cols-12 grid-rows-none gap-4">
      <MyAssets data={data} isLoading={isLoading} />
      <Compare myProfit={myProfit} />
      <MyStock />
      <RecentDealStock />
    </div>
  );
};
export default InvestingMyInfo;
