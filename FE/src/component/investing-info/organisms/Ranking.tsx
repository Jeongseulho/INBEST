import Ranker from "../molecules/Ranker";
import NormalRanking from "../molecules/NormalRanking";

const Ranking = () => {
  const rankers = [
    {
      ranking: 1,
      profileImg: "",
      nickname: "이름1",
      money: 1000000,
      percentage: 20,
      companyIcon: ["", "", ""],
    },
    {
      ranking: 2,
      profileImg: "",
      nickname: "이름2",
      money: 119357237,
      percentage: 100,
      companyIcon: ["", ""],
    },
    {
      ranking: 3,
      profileImg: "",
      nickname: "이름3",
      money: 4843434,
      percentage: -10,
      companyIcon: [],
    },
  ];

  const normalRankers = [
    {
      ranking: 4,
      profileImg: "",
      nickname: "이름4",
      money: 125789557,
      percentage: 100,
    },
    {
      ranking: 5,
      profileImg: "",
      nickname: "이름5",
      money: 119357237,
      percentage: -10,
    },
    {
      ranking: 6,
      profileImg: "",
      nickname: "이름6",
      money: 4843434,
      percentage: -10,
    },
    {
      ranking: 7,
      profileImg: "",
      nickname: "이름7",
      money: 125789557,
      percentage: 40,
    },
  ];
  return (
    <div className=" shadow-component col-span-3 row-span-full flex flex-col gap-4 p-4">
      {rankers.map((ranker) => (
        <Ranker
          key={ranker.ranking}
          ranking={ranker.ranking}
          profileImg={ranker.profileImg}
          nickname={ranker.nickname}
          money={ranker.money}
          percentage={ranker.percentage}
          companyIcon={ranker.companyIcon}
        />
      ))}
      {normalRankers.map((ranker) => (
        <NormalRanking
          key={ranker.ranking}
          ranking={ranker.ranking}
          profileImg={ranker.profileImg}
          nickname={ranker.nickname}
          money={ranker.money}
          percentage={ranker.percentage}
        />
      ))}
    </div>
  );
};
export default Ranking;
