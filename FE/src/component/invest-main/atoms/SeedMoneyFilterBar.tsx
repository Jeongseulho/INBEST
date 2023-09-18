import Slider from "rc-slider";
import "rc-slider/assets/index.css";
import { MIN_SEED_MONEY, MAX_SEED_MONEY } from "../../../constant/FILTER_MIN_MAX";
import { SeedMoney } from "../../../type/GroupFilter";
import { formatNumberToWon } from "../../../util/formatMoney";

interface Props {
  seedMoney: SeedMoney;
  dispatch: React.Dispatch<{ type: "SEED_MONEY"; payload: SeedMoney }>;
  prevSeedMoney: number[];
}

const SeedMoneyFilterBar = ({ seedMoney, dispatch, prevSeedMoney }: Props) => {
  return (
    <div className=" border-2 rounded-lg py-4 px-10 w-full pb-10">
      <div className=" flex flex-col w-full items-center ">
        <p className=" mb-4 mr-4">
          {seedMoney === "linkingMode"
            ? `${formatNumberToWon(prevSeedMoney[0])}원 ~ ${formatNumberToWon(prevSeedMoney[1])}원`
            : `${formatNumberToWon(seedMoney[0])}원 ~ ${formatNumberToWon(seedMoney[1])}원`}
        </p>
        <Slider
          range
          min={MIN_SEED_MONEY}
          max={MAX_SEED_MONEY}
          defaultValue={[MIN_SEED_MONEY, MAX_SEED_MONEY]}
          step={1000000}
          allowCross={false}
          marks={{
            1000000: "백만원",
            20000000: "이천만원",
            40000000: "사천만원",
            60000000: "육천만원",
            80000000: "팔천만원",
            100000000: "일억원",
          }}
          onChange={(payload: number[] | number) => {
            if (Array.isArray(payload)) {
              dispatch({ type: "SEED_MONEY", payload });
            }
          }}
          value={seedMoney === "linkingMode" ? prevSeedMoney : seedMoney}
          disabled={seedMoney === "linkingMode"}
        />
      </div>
    </div>
  );
};

export default SeedMoneyFilterBar;
