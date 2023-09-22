import Group from "../molecules/Group";
import group from "../../../asset/image/group.png";

const GroupList = () => {
  const data = [
    {
      seq: 1,
      title: "title1",
      groupMemberCnt: 1,
      groupLeaderProfileImg: "leader1",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      seq: 2,
      title: "title2",
      groupMemberCnt: 2,
      groupLeaderProfileImg: "leader2",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      seq: 3,
      title: "title3",
      groupMemberCnt: 3,
      groupLeaderProfileImg: "leader3",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      seq: 4,
      title: "title4",
      groupMemberCnt: 4,
      groupLeaderProfileImg: "leader4",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
    {
      seq: 5,
      title: "title4",
      groupMemberCnt: 4,
      groupLeaderProfileImg: "leader4",
      seedMoney: 10000,
      meanTierImg: "",
      period: 1,
    },
  ];
  return (
    <div className="w-4/5 flex flex-col text-center px-4 shadow-component">
      <div className=" flex items-center gap-4">
        <img src={group} width={70} />
        <h3 className=" text-left">참여 가능 그룹</h3>
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
          key={group.seq}
          index={index}
          title={group.title}
          groupMemberCnt={group.groupMemberCnt}
          seedMoney={group.seedMoney}
          meanTierImg={group.meanTierImg}
          period={group.period}
        />
      ))}
    </div>
  );
};
export default GroupList;
