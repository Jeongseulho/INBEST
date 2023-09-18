import PeriodFilterBar from "../atoms/PeriodFilterBar";
import { GroupFilter } from "../../../type/GroupFilter";
import PeriodFilterTag from "../atoms/PeriodFilterTag";
import { useActiveTag } from "../molecules/useActiveTag";

interface Props {
  groupFilter: GroupFilter;
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: number[] }>;
}

const FilterPeriodTab = ({ groupFilter, dispatch }: Props) => {
  const { activeTag, setActiveTag } = useActiveTag();
  return (
    <div className="border-b-2 border-opacity-30 h-3/5 mt-6">
      <p className=" text-xl mb-2">기간 필터링</p>
      <PeriodFilterBar period={groupFilter.period} dispatch={dispatch} />
      <div className="flex">
        <PeriodFilterTag text="1주" />
        <PeriodFilterTag text="2주" />
        <PeriodFilterTag text="3주" />
        <PeriodFilterTag text="4주" />
      </div>
    </div>
  );
};
export default FilterPeriodTab;
