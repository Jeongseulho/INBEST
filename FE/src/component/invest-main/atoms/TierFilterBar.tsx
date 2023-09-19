import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import { MIN_MEAN_TIER, MAX_MEAN_TIER } from "../../../constant/FILTER_MIN_MAX";
import { TIER_NUM_MAP } from "../../../constant/FILTER_MIN_MAX";

interface Props {
  meanTier: number[];
  dispatch: React.Dispatch<{ type: "MEAN_TIER"; payload: number[] }>;
}

const TierFilterBar = ({ meanTier, dispatch }: Props) => {
  return (
    <div className=" border-2 rounded-lg py-4 px-10 w-full pb-10">
      <div className=" flex flex-col w-full items-center ">
        <p className=" mb-4 mr-4 text-xl">{`${TIER_NUM_MAP[meanTier[0]]} ~ ${TIER_NUM_MAP[meanTier[1]]}`}</p>
        <Slider
          range
          min={MIN_MEAN_TIER}
          max={MAX_MEAN_TIER}
          defaultValue={[MIN_MEAN_TIER, MAX_MEAN_TIER]}
          step={1}
          allowCross={false}
          marks={{
            1: "브론즈",
            2: "실버",
            3: "골드",
            4: "플래티넘",
            5: "다이아",
          }}
          onChange={(payload: number[] | number) => {
            if (Array.isArray(payload)) {
              dispatch({ type: "MEAN_TIER", payload });
            }
          }}
          value={meanTier}
        />
      </div>
    </div>
  );
};

export default TierFilterBar;
