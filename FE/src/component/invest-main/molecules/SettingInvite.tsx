import UserItem from "../atoms/UserItem";
import UserTag from "../atoms/UserTag";
interface Props {
  onNextStep: () => void;
  dispatch: React.Dispatch<{ type: string; payload: number }>;
  invitedUserSeq: number[];
  closeModal: () => void;
  resetStepAndGroupSetting: () => void;
}
const SettingInvite = ({ onNextStep, closeModal, resetStepAndGroupSetting }: Props) => {
  const data = [
    {
      userSeq: 1,
      nickname: "닉네임1",
      email: "email1",
      profileImg: "",
    },
    {
      userSeq: 2,
      nickname: "닉네임2",
      email: "email2",
      profileImg: "",
    },
    {
      userSeq: 3,
      nickname: "닉네임3",
      email: "email3",
      profileImg: "",
    },
    {
      userSeq: 4,
      nickname: "닉네임4",
      email: "email4",
      profileImg: "",
    },
  ];
  return (
    <div className=" relative w-full h-full">
      <div className=" flex flex-col items-center justify-around h-5/6">
        <h3 className=" text-center text-dark">어떤 사람을 초대할까요?</h3>

        <div className=" w-full">
          <p className=" font-regular text-left">초대 목록</p>
          <div className=" flex flex-wrap">
            {data.map((user) => (
              <UserTag nickname={user.nickname} profileImg={user.profileImg} />
            ))}
          </div>
        </div>

        <div className=" flex flex-col">
          <label htmlFor="nickname-search" className="text-left">
            <p className="my-2 font-regular">유저 검색</p>
            <input
              id="nickname-search"
              type="text"
              placeholder="닉네임을 입력해주세요."
              className=" border-gray-400 border p-2 rounded-md bg-main bg-opacity-10 w-full"
            />
          </label>
          <div className=" flex flex-wrap">
            {data.map((user) => (
              <UserItem nickname={user.nickname} email={user.email} profileImg={user.profileImg} />
            ))}
          </div>
        </div>

        <p className=" text-myGray text-center">아무도 초대하지 않아도 다음 단계로 넘어갈 수 있습니다.</p>
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
