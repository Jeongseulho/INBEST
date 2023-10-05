import { IoMdRemoveCircleOutline } from "react-icons/io";

interface Props {
  nickname: string;
  profileImg: string;
  dispatch: React.Dispatch<{ type: "DELETE_INVITE"; payload: number }>;
  payload: number;
}

const UserTag = ({ nickname, profileImg, dispatch, payload }: Props) => {
  return (
    <div className=" flex flex-wrap items-center rounded-full border-2 border-gray-300 px-2 mr-2 my-1 h-10">
      <img src={profileImg} width={20} />
      <p className=" mx-1">{nickname}</p>
      <span
        onClick={() =>
          dispatch({
            type: "DELETE_INVITE",
            payload,
          })
        }
      >
        <IoMdRemoveCircleOutline
          color="#FF4D4D"
          size="20"
          style={{
            cursor: "pointer",
          }}
        />
      </span>
    </div>
  );
};
export default UserTag;
