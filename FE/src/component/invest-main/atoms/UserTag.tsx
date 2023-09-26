import { IoMdRemoveCircleOutline } from "react-icons/io";
import { SearchUser } from "../../../type/Group";

interface Props {
  nickname: string;
  profileImg: string;
  dispatch: React.Dispatch<{ type: "DELETE_INVITE"; payload: SearchUser }>;
  payload: SearchUser;
}

const UserTag = ({ nickname, profileImg, dispatch, payload }: Props) => {
  return (
    <div className=" flex flex-wrap items-center rounded-full border-2 border-gray-300 px-2 mx-2 my-1">
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
        <IoMdRemoveCircleOutline color="#FF4D4D" size="20" />
      </span>
    </div>
  );
};
export default UserTag;
