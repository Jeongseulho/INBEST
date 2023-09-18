import PeriodFilterBar from "../atoms/PeriodFilterBar";
import PeriodFilterTag from "../atoms/PeriodFilterTag";

interface Props {
  period: number[];
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: number[] }>;
}

const FilterPeriodTab = ({ period, dispatch }: Props) => {
  return (
    <div className="border-b-2 border-opacity-30 h-3/5 mt-6">
      <p className=" text-xl mb-2">기간 필터링</p>
      <PeriodFilterBar period={period} dispatch={dispatch} />
      <div className="flex mt-6 gap-2">
        <PeriodFilterTag text="1주 이하" period={period} payload={[1, 7]} dispatch={dispatch} />
        <PeriodFilterTag text="1주 ~ 2주" period={period} payload={[7, 14]} dispatch={dispatch} />
        <PeriodFilterTag text="2주 ~ 3주" period={period} payload={[14, 21]} dispatch={dispatch} />
        <PeriodFilterTag text="3주 ~ 4주" period={period} payload={[21, 28]} dispatch={dispatch} />
      </div>
    </div>
  );
};
export default FilterPeriodTab;
