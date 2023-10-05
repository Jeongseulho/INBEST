import crown from "../../../asset/image/crown.png";
interface Props {
  rankInGroup: number;
  rankInGroupFluctuation: number;
}

const MyGroupRank = ({ rankInGroup, rankInGroupFluctuation }: Props) => {
  return (
    <div>
      <div className=" flex items-center gap-2">
        <p className=" font-medium text-gray-500">나의 순위</p>
        <img src={crown} width={30} />
      </div>
      <div className=" flex items-center">
        <p className=" font-bold text-xl">{rankInGroup}위</p>
        <div className=" h-6 flex items-center rounded-full bg-mainMoreLight py-1 px-3 mx-3">
          <div id="increase-triangle" className=" me-2"></div>
          {rankInGroupFluctuation < 0 ? (
            <p className=" text-myRed font-extraBold">{rankInGroupFluctuation}</p>
          ) : (
            <p className=" text-mainMoreDark font-extraBold">+{rankInGroupFluctuation}</p>
          )}
        </div>
      </div>
    </div>
  );
};

export default MyGroupRank;
