import crown from "../../../asset/image/crown.png";
const MyGroupRank = () => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-regular me-2">나의 순위</p>
        <img src={crown} width={40} />
      </div>
      <div className=" flex items-center">
        <p className=" font-bold text-xl">3위</p>
        <div className=" h-6 flex items-center rounded-full bg-mainMoreLight py-1 px-3 mx-3">
          <div id="increase-triangle" className=" me-2"></div>
          <p className=" text-mainMoreDark font-extraBold">{10}</p>
        </div>
      </div>
    </div>
  );
};

export default MyGroupRank;
