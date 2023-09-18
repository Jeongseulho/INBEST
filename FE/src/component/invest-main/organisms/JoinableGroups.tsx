import JoinableGroup from "../molecules/JoinableGroup";

const JoinableGroups = () => {
  const data = [
    { index: 1, title: "title1", isBoostMode: true, groupMemberCnt: 1, groupLeaderProfileImg: "leader1" },
    { index: 2, title: "title2", isBoostMode: false, groupMemberCnt: 2, groupLeaderProfileImg: "leader2" },
    { index: 3, title: "title3", isBoostMode: true, groupMemberCnt: 3, groupLeaderProfileImg: "leader3" },
    { index: 4, title: "title4", isBoostMode: false, groupMemberCnt: 4, groupLeaderProfileImg: "leader4" },
  ];
  return (
    <div className=" flex flex-col w-1/3 gap-2 text-center">
      <div className=" flex justify-between w-full">
        <p className=" w-2">#</p>
        <p className=" w-24">그룹 이름</p>
        <p className=" w-24">모드</p>
        <p className=" w-16">현재 인원</p>
        <p className=" w-12">그룹장</p>
      </div>
      {data.map((group) => (
        <JoinableGroup
          index={group.index}
          title={group.title}
          isBoostMode={group.isBoostMode}
          groupMemberCnt={group.groupMemberCnt}
          groupLeaderProfileImg={group.groupLeaderProfileImg}
        />
      ))}
    </div>
  );
};
export default JoinableGroups;
