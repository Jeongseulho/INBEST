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
  return (
    <div className=" grid grid-cols-12 grid-rows-none gap-4">
      <MyAssets data={data} isLoading={isLoading} />
      <Compare />
      <MyStock />
      <RecentDealStock />
    </div>
  );
};
export default InvestingMyInfo;
