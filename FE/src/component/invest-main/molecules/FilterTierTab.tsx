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
        <TierFilterTag text="브론즈 ~ 실버" meanTier={meanTier} payload={[1, 2]} dispatch={dispatch} />
        <TierFilterTag text="실버 ~ 골드" meanTier={meanTier} payload={[2, 3]} dispatch={dispatch} />
        <TierFilterTag text="골드 ~ 플래티넘" meanTier={meanTier} payload={[3, 4]} dispatch={dispatch} />
        <TierFilterTag text="플래티넘 ~ 다이아" meanTier={meanTier} payload={[4, 5]} dispatch={dispatch} />
      </div>
    </div>
  );
};
export default FilterTierTab;
