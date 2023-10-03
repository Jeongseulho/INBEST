import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import { MIN_MEAN_TIER, MAX_MEAN_TIER } from "../../../constant/FILTER_MIN_MAX";

interface Props {
  meanTier: number[];
  dispatch: React.Dispatch<{ type: "MEAN_TIER"; payload: number[] }>;
}

const TierFilterBar = ({ meanTier, dispatch }: Props) => {
  const numToTier = (num: number) => {
    switch (num) {
      case 100:
        return "브론즈";
      case 200:
        return "실버";
      case 300:
        return "골드";
      case 400:
        return "다이아";
      default:
        return "";
    }
  };
  return (
    <div className=" border-2 rounded-lg py-4 px-10 w-full pb-10">
      <div className=" flex flex-col w-full items-center ">
        <p className=" mb-4 mr-4 text-xl">
          {numToTier(meanTier[0])} ~ {numToTier(meanTier[1])}
        </p>
        <Slider
          range
          min={MIN_MEAN_TIER}
          max={MAX_MEAN_TIER}
          defaultValue={[MIN_MEAN_TIER, MAX_MEAN_TIER]}
          step={100}
          allowCross={false}
          marks={{
            100: "브론즈",
            200: "실버",
            300: "골드",
            400: "다이아",
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
