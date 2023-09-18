import Group from "../molecules/Group";

const GroupList = () => {
  const data = [
    {
      index: 1,
      title: "title1",
      isBoostMode: true,
      groupMemberCnt: 1,
      groupLeaderProfileImg: "leader1",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      index: 2,
      title: "title2",
      isBoostMode: false,
      groupMemberCnt: 2,
      groupLeaderProfileImg: "leader2",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      index: 3,
      title: "title3",
      isBoostMode: true,
      groupMemberCnt: 3,
      groupLeaderProfileImg: "leader3",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      index: 4,
      title: "title4",
      isBoostMode: false,
      groupMemberCnt: 4,
      groupLeaderProfileImg: "leader4",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
  ];
  return (
    <div className=" flex flex-col w-3/4 text-center p-4 shadow-component">
      <h3 className=" text-left mb-8">전체 그룹</h3>
      <div className="flex justify-between w-full border-t-2 border-b-2 px-4">
        <p className="w-2">#</p>
        <p className="w-24">그룹 이름</p>
        <p className="w-24">모드</p>
        <p className="w-16">현재 인원</p>
        <p className="w-16">그룹장</p>
        <p className="w-16">시드 머니</p>
        <p className="w-16">평균 티어</p>
        <p className="w-16">진행 기간</p>
      </div>

      {data.map((group) => (
        <Group
          index={group.index}
          title={group.title}
          isBoostMode={group.isBoostMode}
          groupMemberCnt={group.groupMemberCnt}
          groupLeaderProfileImg={group.groupLeaderProfileImg}
          seedMoney={group.seedMoney}
          meanTierImg={group.meanTierImg}
          period={group.period}
        />
      ))}
    </div>
  );
};
export default GroupList;
