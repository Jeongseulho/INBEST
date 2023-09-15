import { IoIosAddCircleOutline } from "react-icons/io";

interface Props {
  nickname: string;
  email: string;
  profileImg: string;
}

const UserItem = ({ nickname, email, profileImg }: Props) => {
  return (
    <div className=" flex items-center m-2 mt-4">
      <img src={profileImg} width={40} />
      <div className="flex flex-col">
        <p>{nickname}</p>
        <p>{email}</p>
      </div>

      <IoIosAddCircleOutline color="#6CE061" size="32" />
    </div>
  );
};
export default UserItem;
