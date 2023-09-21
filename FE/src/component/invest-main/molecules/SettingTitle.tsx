interface Props {
  closeCreateModal: () => void;
  resetStepAndGroupSetting: () => void;
  dispatch: React.Dispatch<{ type: "TITLE"; payload: string }>;
  title: string;
}

const SettingTitle = ({ closeCreateModal, resetStepAndGroupSetting, dispatch, title }: Props) => {
  return (
    <div className=" relative w-full h-full">
      <div className=" flex flex-col items-center justify-around h-5/6">
        <h3 className=" text-center text-dark">
          마지막 단계에요, <br />방 제목을 정하면 그룹이 생성돼요.
        </h3>
        <label htmlFor="title" className="text-left w-5/6 mb-10">
          <p className="mb-2 font-regular">방 제목</p>
          <input
            id="title"
            type="text"
            placeholder="방 제목을 입력해주세요."
            className=" border-gray-400 border p-2 rounded-md bg-main bg-opacity-10 w-full"
            value={title}
            onChange={(e) => dispatch({ type: "TITLE", payload: e.target.value })}
          />
        </label>
      </div>

      <div className=" flex justify-center absolute bottom-0 w-full">
        <button
          onClick={() => {
            closeCreateModal();
            resetStepAndGroupSetting();
          }}
          className=" ms-10 me-5 gray-btn"
        >
          취소
        </button>
        <button className=" me-10 ms-5 main-dark-btn">모의 투자 그룹 생성</button>
      </div>
    </div>
  );
};
export default SettingTitle;
