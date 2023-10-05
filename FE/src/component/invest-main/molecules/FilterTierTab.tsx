import TierFilterBar from "../atoms/TierFilterBar";
import TierFilterTag from "../atoms/TierFilterTag";

interface Props {
  meanTier: number[];
  dispatch: React.Dispatch<{ type: "MEAN_TIER"; payload: number[] }>;
}

const FilterTierTab = ({ meanTier, dispatch }: Props) => {
  return (
    <div className="border-b-2 border-opacity-30 h-3/5 mt-6">
      <p className=" text-xl mb-2">시드머니 필터링</p>
      <TierFilterBar meanTier={meanTier} dispatch={dispatch} />
      <div className="flex mt-6 mb-2 gap-2">
        <TierFilterTag text="100 포인트 이하" meanTier={meanTier} payload={[0, 100]} dispatch={dispatch} />
        <TierFilterTag text="100 ~ 200 포인트" meanTier={meanTier} payload={[100, 200]} dispatch={dispatch} />
        <TierFilterTag text="200 ~ 300 포인트" meanTier={meanTier} payload={[200, 300]} dispatch={dispatch} />
        <TierFilterTag text="300 포인트 이상" meanTier={meanTier} payload={[300, 400]} dispatch={dispatch} />
      </div>
    </div>
  );
};
export default FilterTierTab;
