import Group from "../molecules/Group";
import group from "../../../asset/image/group.png";
import { LuSettings2 } from "react-icons/lu";
import modalStore from "../../../store/modalStore";

const GroupList = () => {
  const data = [
    {
      title: "title1",
      memberCnt: 1,
      seedMoney: 10000,
      avgTier: 100,
      period: 1,
      groupCode: "groupCode1",
    },
    {
      title: "title2",
      memberCnt: 2,
      seedMoney: 20000,
      avgTier: 200,
      period: 2,
      groupCode: "groupCode2",
    },
    {
      title: "title3",
      memberCnt: 3,
      seedMoney: 30000,
      avgTier: 300,
      period: 3,
      groupCode: "groupCode3",
    },
    {
      title: "title4",
      memberCnt: 4,
      seedMoney: 40000,
      avgTier: 400,
      period: 4,
      groupCode: "groupCode4",
    },
    {
      title: "title5",
      memberCnt: 5,
      seedMoney: 50000,
      avgTier: 90,
      period: 5,
      groupCode: "groupCode5",
    },
  ];
  const { openModal } = modalStore();
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
            id="nickname-search"
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

      {data.map((group, index) => (
        <Group
          key={group.groupCode}
          index={index}
          title={group.title}
          memberCnt={group.memberCnt}
          seedMoney={group.seedMoney}
          avgTier={group.avgTier}
          period={group.period}
          groupCode={group.groupCode}
        />
      ))}
    </div>
  );
};
export default GroupList;
