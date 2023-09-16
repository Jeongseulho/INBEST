interface Props {
  nickname: string;
  profileImg: string;
}

const UserTag = ({ nickname, profileImg }: Props) => {
  return (
    <div className=" flex flex-wrap items-center rounded-full border-2 border-gray-300 px-2 mx-2">
      <img src={profileImg} width={20} />
      <p>{nickname}</p>
    </div>
  );
};
export default UserTag;
