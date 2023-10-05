interface ShowProps {
  showLogin: boolean;
  setShowLogin: React.Dispatch<React.SetStateAction<boolean>>;
}

const SlideLogin = ({ showLogin, setShowLogin }: ShowProps) => {
  return (
    <>
      <div className={`text-white absolute text-center top-72 ${showLogin ? "left-1/3" : "right-1/3"}`}>
        <h2>{showLogin ? "아직 가입하지 않으셨나요?" : "이미 가입하셨나요?"}</h2>
        <p className="font-regular my-8">
          {showLogin ? "지금 가입하고 INBEST를 경험해 보세요!" : "로그인 하고 시작해 보세요!"}
        </p>
        <button className="border-white border px-4 py-3 rounded-md mt-5" onClick={() => setShowLogin((pre) => !pre)}>
          {showLogin ? "회원가입" : "로그인"}
        </button>
      </div>
    </>
  );
};
export default SlideLogin;
