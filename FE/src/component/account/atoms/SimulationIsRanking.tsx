interface Props {
  rank: number;
  rate: number;
  industries: string[];
}
const SimulationIsRanking = ({ rank, rate, industries }: Props) => {
  return (
    <div className="text-lg flex justify-start ms-3 items-center border-e-2 border-gray-400 border-opacity-20 w-6/12 line-clamp-1">
      <div>
        <div>등수 : {rank}</div>
        <div className="mt-3 line-clamp-1">
          수익률 :{" "}
          <span className={`${rate >= 0 ? "text-blue-600" : "text-red-600"}`}>
            {rate >= 0 && "+"}
            {rate}%
          </span>
        </div>
        <div className="flex mt-3 line-clamp-1 w-full">
          주요 투자 종목 :
          {industries.map((Industry, idx) => (
            <p className="ms-1 px-3 bg-gray-200 shadow-sm rounded-xl me-1  line-clamp-1" key={idx}>
              {Industry}
            </p>
          ))}
        </div>
      </div>
    </div>
  );
};
export default SimulationIsRanking;
