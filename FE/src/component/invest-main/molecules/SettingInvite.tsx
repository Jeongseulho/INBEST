import UserItem from "../atoms/UserItem";
import UserTag from "../atoms/UserTag";
import { SearchUser } from "../../../type/Group";
import modalStore from "../../../store/modalStore";
import { useSettingInvite } from "./useSettingInvite";
import { BsSearch } from "react-icons/bs";
import spinner from "../../../asset/image/spinner.svg";
import userStore from "../../../store/userStore";

type Action = { type: "ADD_INVITE"; payload: SearchUser } | { type: "DELETE_INVITE"; payload: number };
interface Props {
  onNextStep: () => void;
  dispatch: React.Dispatch<Action>;
  inviteUsers: SearchUser[] | [];
  resetStepAndGroupSetting: () => void;
}
const SettingInvite = ({ onNextStep, resetStepAndGroupSetting, dispatch, inviteUsers }: Props) => {
  const { closeModal } = modalStore();
  const { data, isLoading, searchUserNickname, onChangeSearchUserNickname } = useSettingInvite();
  const { userInfo } = userStore();

  return (
    <div className=" relative w-full h-full overflow-hidden my-2">
      <div className=" flex flex-col items-center h-full gap-4">
        <h3 className=" text-center text-dark">어떤 사람을 초대할까요?</h3>

        <div className=" flex flex-col w-full">
          <label htmlFor="nickname-search" className="text-left">
            <p className=" font-regular">유저 검색</p>
            <div className=" relative">
              <input
                type="text"
                className="px-3 border-gray-400 border bg-main bg-opacity-10 h-10 w-full rounded-md pe-8"
                placeholder="검색어를 입력하세요"
                value={searchUserNickname}
                onChange={onChangeSearchUserNickname}
              />
              <div>
                <BsSearch
                  style={{
                    position: "absolute",
                    top: "50%",
                    transform: "translate(-50%, -50%)",
                    right: "0",
                    cursor: "pointer",
                  }}
                />
              </div>
            </div>
          </label>
        </div>
        <div className=" w-full h-1/4 ">
          <p className=" my-1 font-regular text-left">검색 결과</p>
          {isLoading ? (
            <div className=" flex items-center justify-center">
              <img src={spinner} alt="Loading Spinner" width={80} />
            </div>
          ) : (
            <div className=" flex flex-wrap overflow-y-scroll h-full">
              {data?.map((user) => {
                if (user.seq === userInfo?.seq) return null;
                return (
                  <UserItem
                    key={user.seq}
                    nickname={user.nickname}
                    profileImg={user.profileImgSearchName}
                    dispatch={dispatch}
                    payload={user}
                  />
                );
              })}
            </div>
          )}
        </div>

        <div className=" w-full h-1/5 mt-6">
          <p className=" my-2 font-regular text-left">초대 목록</p>
          <div className=" flex flex-wrap overflow-y-scroll h-full">
            {inviteUsers.map((user) => (
              <UserTag
                key={user.seq}
                nickname={user.nickname}
                profileImg={user.profileImgSearchName}
                dispatch={dispatch}
                payload={user.seq}
              />
            ))}
          </div>
        </div>

        <p className=" text-myGray text-center relative top-8">
          아무도 초대하지 않아도 다음 단계로 넘어갈 수 있습니다.
        </p>
      </div>
      <div className=" flex justify-center absolute bottom-0 w-full">
        <button
          onClick={() => {
            closeModal();
            resetStepAndGroupSetting();
          }}
          className=" ms-10 me-5 gray-btn"
        >
          취소
        </button>
        <button onClick={onNextStep} className=" me-10 ms-5 main-dark-btn">
          다음 단계로
        </button>
      </div>
    </div>
  );
};
export default SettingInvite;
