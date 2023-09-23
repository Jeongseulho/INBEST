import NumberToTierImage from "../../common/NumberToTierImage";

interface Props {
  tier: number;
}

const MeanTier = ({ tier }: Props) => {
  return (
    <div className=" flex flex-col">
      <p className=" font-regular ">이 그룹의 평균 티어</p>
      <div className=" flex w-2/3 items-center gap-2">
        <p className=" font-bold text-xl">실버</p>
        <div className=" w-1/5">
          <NumberToTierImage tier={tier} />
        </div>
      </div>
    </div>
  );
};
export default MeanTier;
