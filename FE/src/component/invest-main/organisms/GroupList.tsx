import Group from "../molecules/Group";
import group from "../../../asset/image/group.png";
import { LuSettings2 } from "react-icons/lu";
import modalStore from "../../../store/modalStore";
import GroupSkeleton from "../molecules/GroupSkeleton";
import { JoinableGroupList } from "../../../type/Group";
import { GroupFilter } from "../../../type/GroupFilter";

interface Props {
  data: JoinableGroupList | undefined;
  isLoading: boolean;
  filter: GroupFilter;
}

const GroupList = ({ data, isLoading, filter }: Props) => {
  const { openModal } = modalStore();

  const filteredData = data?.filter((group) => {
    const { period, seedMoney, meanTier } = filter;
    const { period: groupPeriod, seedMoney: groupSeedMoney, averageTier } = group;

    const isPeriodValid = groupPeriod >= period[0] && groupPeriod <= period[1];
    const isSeedMoneyValid = groupSeedMoney >= seedMoney[0] && groupSeedMoney <= seedMoney[1];
    const isMeanTierValid = averageTier >= meanTier[0] * 100 && averageTier <= meanTier[1] * 100;

    return isPeriodValid && isSeedMoneyValid && isMeanTierValid;
  });

  return (
    <div className="w-4/5 flex flex-col text-center px-4 shadow-component">
      <div className=" flex items-center gap-4">
        <img src={group} width={70} />
        <h3 className=" text-left">참여 가능 그룹</h3>
      </div>

      <div className="text-left mb-4">
        <p className="my-2 font-regular">그룹 검색</p>
        <div className=" flex items-center gap-4">
          <input
            id="group-search"
            type="text"
            placeholder="그룹 이름으로 검색할 수 있습니다."
            className=" border-gray-400 border p-2 rounded-md bg-main bg-opacity-10"
          />
          <div
            onClick={() => openModal("filter")}
            className=" cursor-pointer hover:text-gray-700  h-8 flex items-center rounded-full border-2 border-gray-300 py-3 px-2 gap-1 hover:bg-primary hover:bg-opacity-20 transition-colors duration-300"
          >
            <LuSettings2 className="text-gray-500 " />
            <p className=" text-lightPrimary text-sm  ">필터 적용</p>
          </div>
        </div>
      </div>

      <div className="flex justify-between w-full border-t-2 border-b-2 px-4">
        <p className="w-2">#</p>
        <p className="w-24">그룹 이름</p>
        <p className="w-16">현재 인원</p>
        <p className="w-32">시드 머니</p>
        <p className="w-16">평균 티어</p>
        <p className="w-16">진행 기간</p>
      </div>

      {isLoading ? (
        <div className=" flex flex-col gap-4 pb-8">
          <GroupSkeleton />
          <GroupSkeleton />
          <GroupSkeleton />
          <GroupSkeleton />
          <GroupSkeleton />
          <GroupSkeleton />
        </div>
      ) : (
        <div className=" flex flex-col gap-4 pb-8">
          {filteredData?.map((group, index) => (
            <Group
              key={group.simulationSeq}
              index={index}
              title={group.title}
              currentMemberNum={group.currentMemberNum}
              seedMoney={group.seedMoney}
              averageTier={group.averageTier}
              period={group.period}
              simulationSeq={group.simulationSeq}
            />
          ))}
        </div>
      )}
    </div>
  );
};
export default GroupList;
