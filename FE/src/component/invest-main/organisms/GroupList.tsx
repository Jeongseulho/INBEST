import Group from "../molecules/Group";

const GroupList = () => {
  const data = [
    { index: 1, title: "title1", isBoostMode: true, groupMemberCnt: 1, groupLeaderProfileImg: "leader1" },
    { index: 2, title: "title2", isBoostMode: false, groupMemberCnt: 2, groupLeaderProfileImg: "leader2" },
    { index: 3, title: "title3", isBoostMode: true, groupMemberCnt: 3, groupLeaderProfileImg: "leader3" },
    { index: 4, title: "title4", isBoostMode: false, groupMemberCnt: 4, groupLeaderProfileImg: "leader4" },
  ];
  return (
    <div className=" flex flex-col w-1/3 text-center p-4 shadow-component">
      <h3 className=" text-left mb-8">전체 그룹</h3>
      <div className=" flex justify-between w-full border-t-2 border-b-2 px-4">
        <p className=" w-2">#</p>
        <p className=" w-24">그룹 이름</p>
        <p className=" w-24">모드</p>
        <p className=" w-16">현재 인원</p>
        <p className=" w-12">그룹장</p>
      </div>
      {data.map((group) => (
        <Group
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
export default GroupList;
