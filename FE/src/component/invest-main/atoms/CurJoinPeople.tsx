interface Props {
  profileImageList: string[];
}
import people from "../../../asset/image/people.png";

const CurJoinPeople = ({ profileImageList }: Props) => {
  return (
    <div className=" flex flex-col gap-2">
      <div className=" flex items-end">
        <p className=" font-medium text-gray-600 me-2">현재 참여 인원</p>
        <img src={people} width={40} />
      </div>
      <div className=" flex items-center gap-2">
        {profileImageList.map((profileImage) => {
          return <img src={profileImage} width={40} />;
        })}
      </div>
    </div>
  );
};

export default CurJoinPeople;
