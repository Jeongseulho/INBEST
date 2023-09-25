import coin from "../../../asset/image/coin.png";

const SeedMoneyTag = () => {
  return (
    <div>
      <div className=" flex items-end">
        <p className=" font-medium text-gray-600">시드머니</p>
        <img src={coin} width={40} />
      </div>
      <p className=" font-bold text-xl">1,000,000 원</p>
    </div>
  );
};
export default SeedMoneyTag;
