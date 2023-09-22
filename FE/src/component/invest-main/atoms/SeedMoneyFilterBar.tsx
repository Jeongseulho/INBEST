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
        <p className=" mb-4 mr-4 text-xl">
          {seedMoney === "linkingMode"
            ? `${formatNumberToWon(prevSeedMoney[0])} ~ ${formatNumberToWon(prevSeedMoney[1])}`
            : `${formatNumberToWon(seedMoney[0])} ~ ${formatNumberToWon(seedMoney[1])}`}
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
            2000000: "이백만원",
            3000000: "삼백만원",
            4000000: "사백만원",
            5000000: "오백만원",
            6000000: "육백만원",
            7000000: "칠백만원",
            8000000: "팔백만원",
            9000000: "구백만원",
            10000000: "천만원",
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
