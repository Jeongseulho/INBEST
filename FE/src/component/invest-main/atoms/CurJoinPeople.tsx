interface Props {
  profileImageList: string[];
}
import people from "../../../asset/image/people.png";

const CurJoinPeople = ({ profileImageList }: Props) => {
  return (
    <div className=" flex flex-col gap-2">
      <div className=" flex items-center gap-2">
        <p className=" font-medium text-gray-500 ">현재 참여 인원</p>
        <img src={people} width={40} />
      </div>
      <div className=" flex items-center gap-2">
        {profileImageList.map((profileImage, index) => {
          return <img src={profileImage} width={40} key={index} />;
        })}
      </div>
    </div>
  );
};

export default CurJoinPeople;
