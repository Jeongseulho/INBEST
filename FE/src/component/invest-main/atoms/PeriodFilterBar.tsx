import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import { MIN_PERIOD, MAX_PERIOD } from "../../../constant/FILTER_MIN_MAX";
import { Period } from "../../../type/GroupFilter";

interface Props {
  period: Period;
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: Period }>;
}

const PeriodFilterBar = ({ period, dispatch }: Props) => {
  return (
    <div className=" border-2 rounded-lg py-4 px-10 w-full pb-10">
      <div className=" flex flex-col w-full items-center ">
        <p className=" mb-4 mr-4 text-xl">{`${period[0]}일 ~ ${period[1]}일`}</p>
        <Slider
          range
          min={MIN_PERIOD}
          max={MAX_PERIOD}
          defaultValue={[MIN_PERIOD, MAX_PERIOD]}
          step={1}
          allowCross={false}
          marks={{
            1: "1일 ",
            7: "7일 ",
            14: "14일 ",
            21: "21일",
            28: "28일",
          }}
          onChange={(payload: number[] | number) => {
            if (Array.isArray(payload)) {
              dispatch({ type: "PERIOD", payload });
            }
          }}
          value={period}
        />
      </div>
    </div>
  );
};

export default PeriodFilterBar;
