import moneyHand from "../../../asset/image/moneyHand.png";
interface Props {
  tier: number;
  startDate: string;
  finishedDate: string;
}
const SimulationIsWin = ({ tier, startDate, finishedDate }: Props) => {
  return (
    <div className="w-2/12 pt-5 border-e-2 border-gray-400 border-opacity-20">
      <div className="flex items-center justify-center">
        <img src={moneyHand} className="w-12" />
        <p className="text-4xl ms-2">
          {tier > 0 && "+"}
          {tier}P
        </p>
      </div>
      <div className="flex justify-center mt-10 text-md">
        <div>
          <p className="line-clamp-1">{startDate}~</p>
          <p className="ps-3 line-clamp-1">{finishedDate}</p>
        </div>
      </div>
    </div>
  );
};
export default SimulationIsWin;
