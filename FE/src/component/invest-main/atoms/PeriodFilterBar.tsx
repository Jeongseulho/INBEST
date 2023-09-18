import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import { MIN_PERIOD, MAX_PERIOD } from "../../../constant/FILTER_MIN_MAX";

interface Props {
  period: number[];
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: number[] }>;
}

const PeriodFilterBar = ({ period, dispatch }: Props) => {
  const onChange = (payload: number[] | number) => {
    if (Array.isArray(payload)) {
      dispatch({ type: "PERIOD", payload });
    }
  };

  const marks = {
    1: "1일 ",
    7: "7일 ",
    14: "14일 ",
    21: "21일",
    28: "28일",
  };

  return (
    <div className=" border-2 rounded-lg absolute py-4 px-10 w-full pb-10">
      <div className=" flex flex-col w-full items-center ">
        <p className=" mb-4">{`${period[0]}일 ~ ${period[1]}일`}</p>
        <Slider
          range
          min={MIN_PERIOD}
          max={MAX_PERIOD}
          defaultValue={[MIN_PERIOD, MAX_PERIOD]}
          step={1}
          allowCross={false}
          marks={marks}
          onChange={onChange}
        />
      </div>
    </div>
  );
};

export default PeriodFilterBar;
