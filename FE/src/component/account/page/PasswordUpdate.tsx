import Modal from "react-modal";
import "animate.css";
import { useForm } from "react-hook-form";
import "cropperjs/dist/cropper.css";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { usePasswordUpdate } from "./usePasswordUpdate";
import lock from "../../../asset/image/passwordChange.png";
import { AiOutlineEyeInvisible, AiOutlineEye } from "react-icons/ai";

const ProfileUpdate = ({
  showModal,
  setShowModal,
}: {
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const { showPassWord1, setShowPassWord1, showPassWord2, setShowPassWord2, onPasswordUpdate } =
    usePasswordUpdate(setShowModal);
  const {
    handleSubmit,
    register,
    getValues,
    reset,
    formState: { errors },
  } = useForm<{ password1: string; password2: string }>();

  return (
    <>
      <Modal
        isOpen={showModal}
        style={{
          content: {
            ...CONTENT_MODAL_STYLE,
            width: "500px",
            height: "510px",
            margin: "auto",
          },
          overlay: OVERLAY_MODAL_STYLE,
        }}
      >
        <form
          onSubmit={handleSubmit((data) => {
            onPasswordUpdate(data.password1);
            reset();
          })}
          className=" px-5"
        >
          <div className="flex justify-center">
            <img src={lock} alt="비밀번호 변경" width={150} />
          </div>
          <div className="w-full mt-4 text-center">
            <p className="text-2xl my-3">비밀번호 변경</p>
            <label htmlFor="password" className="mt-5 text-left">
              <p className="my-2 font-regular ">비밀번호</p>
            </label>
            <div className="relative">
              <input
                id="password"
                type={showPassWord1 ? "text" : "password"}
                placeholder="비밀번호를 입력해 주세요"
                className=" border-gray-400 border w-full p-3 rounded-md"
                {...register("password1", {
                  required: "비밀번호를 입력해 주세요",
                  maxLength: {
                    value: 16,
                    message: "16자 이하로 입력해 주세요",
                  },
                  minLength: {
                    value: 8,
                    message: "8자 이상 입력해 주세요",
                  },
                  pattern: {
                    value: /^(?=.*[A-Za-z])(?=.*\d)(?=.*[@#$%^&+=!]).*$/,
                    message: "숫자, 영어, 특수문자를 하나 이상 사용해 주세요",
                  },
                })}
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    e.preventDefault();
                    // Enter 키 누르면 사진사라지는 현상제거
                  }
                }}
              />
              <div
                className="absolute cursor-pointer top-1/2 transform -translate-y-1/2 right-2"
                onClick={() => setShowPassWord1((prev) => !prev)}
              >
                {showPassWord1 ? <AiOutlineEye /> : <AiOutlineEyeInvisible />}
              </div>
            </div>
            <div className="errorMessage text-left">{errors.password1?.message}</div>
          </div>
          <div className="w-full mt-4">
            <label htmlFor="password" className="mt-5 text-left">
              <p className="my-2 font-regular ">비밀번호 확인</p>
            </label>
            <div className="relative">
              <input
                id="password"
                type={showPassWord2 ? "text" : "password"}
                placeholder="비밀번호를 다시 입력해 주세요"
                className=" border-gray-400 border w-full p-3 rounded-md"
                {...register("password2", {
                  required: "비밀번호가 일치하지 않습니다",
                  validate: (value) => value === getValues("password1") || "비밀번호가 일치하지 않습니다",
                })}
                onKeyDown={(e) => {
                  if (e.key === "Enter") {
                    e.preventDefault();
                    // Enter 키 누르면 사진사라지는 현상제거
                  }
                }}
                onKeyUp={(e) => {
                  if (e.key === "Enter") {
                    e.preventDefault();
                    handleSubmit((data) => {
                      onPasswordUpdate(data.password1);
                      reset();
                    });
                  }
                }}
              />
              <div
                className="absolute cursor-pointer top-1/2 transform -translate-y-1/2 right-2"
                onClick={() => setShowPassWord2((prev) => !prev)}
              >
                {showPassWord2 ? <AiOutlineEye /> : <AiOutlineEyeInvisible />}
              </div>
            </div>
            <div className="errorMessage">{errors.password2?.message}</div>
          </div>
          <div className="flex justify-end  mt-5">
            <button
              onClick={() => {
                setShowModal(false);
                reset();
              }}
              className="me-2 jongRyul-gray w-16"
            >
              닫기
            </button>
            <button type="submit" className="ms-2 jongRyul-primary p-2">
              변경하기
            </button>
          </div>
        </form>
      </Modal>
    </>
  );
};
export default ProfileUpdate;
