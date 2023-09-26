import Modal from "react-modal";
import "animate.css";
import { SignupFormValue } from "../../../type/Accounts";
import { useForm } from "react-hook-form";
import Cropper from "react-cropper";
import defaultImg from "../../../asset/image/default_image.png";
import { useProfileUpdate } from "./useProfileUpdate";
import "cropperjs/dist/cropper.css";
import userStore from "../../../store/userStore";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { GetUserInfo } from "../../../type/Accounts";
import InputDatePicker from "../atoms/InputDatePicker";
import { useEffect } from "react";
import { toast } from "react-toastify";
const ProfileUpdate = ({
  showModal,
  setShowModal,
  myInfo,
}: {
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
  myInfo: GetUserInfo | null;
}) => {
  const {
    beforeCropUrl,
    setImgInfo,
    imgInfo,
    isChanged,
    setIsChanged,
    selectImg,
    onCrop,
    cropperRef,
    cropImg,
    isCropped,
    onCancel,
    setIsDefaultImg,
    isDefaultImg,
    onCheckNickname,
    setIsChangedNickname,
    isChangedNickname,
    isCheckedNickname,
    onUpdate,
    onReset,
  } = useProfileUpdate();
  const { userInfo } = userStore();
  const {
    handleSubmit,
    getValues,
    register,
    control,
    setError,
    reset,
    formState: { errors },
  } = useForm<SignupFormValue>();
  useEffect(() => {
    console.log(imgInfo);
  }, [imgInfo]);
  return (
    <>
      <Modal
        isOpen={showModal}
        style={{
          content: {
            ...CONTENT_MODAL_STYLE,
            width: "500px",
            margin: "auto",
          },
          overlay: OVERLAY_MODAL_STYLE,
        }}
      >
        <div>
          <h3 className="text-center my-10">회원정보 변경</h3>
          <form
            onSubmit={handleSubmit((data) => {
              if (isChangedNickname && !isCheckedNickname) {
                toast.error("닉네임 중복검사를 완료해 주세요.");
                return;
              }
              onUpdate(data);
              onReset();
              setShowModal(false);
              setError("nickname", {
                type: "nicknameerror",
                message: "",
              });
              reset();
            })}
          >
            <div className="text-center">
              <div className="flex justify-center">
                <img
                  src={isDefaultImg ? defaultImg : isCropped ? cropImg! : userInfo?.profileImgSearchName}
                  alt="유저이미지"
                  className="w-48 h-48 rounded-full shadow-lg"
                />
              </div>
              {!isChanged && (
                <button
                  className="jongRyul-gray w-36 h-8
								my-3 me-4"
                  onClick={(e) => {
                    e.preventDefault();
                    setIsDefaultImg(true);
                    setImgInfo(null);
                  }}
                >
                  기본이미지로 변경
                </button>
              )}
              <div className="flex">
                <div className="bg-gray-200 mt-3 px-3 flex items-center mx-auto font-regular rounded-md text-sm w-4/5 border-2 border-gray-400 h-10">
                  <span className="align-middle whitespace-nowrap overflow-hidden overflow-ellipsis">
                    {imgInfo && imgInfo.name}
                  </span>
                </div>
                <button
                  className="jongRyul-primary  w-24 h-10 ms-3
								my-3"
                  onClick={(e) => {
                    e.preventDefault();
                    selectImg.current?.click();
                  }}
                >
                  사진 찾기
                </button>
              </div>
              <input
                onChange={(e) => {
                  console.log(e.target.files);
                  if (!e.target.files || e.target.files.length === 0 || !e.target) {
                    setIsChanged(false);
                    return;
                  }
                  setImgInfo(e.target.files[0]);
                  setIsChanged(true);
                  setIsDefaultImg(false);
                }}
                ref={selectImg}
                type="file"
                accept="image/*"
                id="file"
                style={{ display: "none" }}
              />
            </div>
            {isChanged && (
              <div className="">
                <Cropper
                  src={beforeCropUrl!}
                  // Cropper.js options
                  style={{ height: 400, width: "100%" }}
                  guides={false}
                  aspectRatio={1 / 1}
                  viewMode={1}
                  ref={cropperRef}
                  minCropBoxHeight={10}
                  minCropBoxWidth={10}
                  background={true}
                  dragMode="move"
                />
                <div className="mt-3 text-right">
                  <button
                    className="jongRyul-primary w-16 h-8 me-2"
                    onClick={(e) => {
                      e.preventDefault();
                      onCrop();
                      if (selectImg.current) selectImg.current.value = "";
                    }}
                  >
                    자르기
                  </button>
                  <button
                    className="jongRyul-gray w-16 h-8"
                    onClick={(e) => {
                      e.preventDefault();
                      setIsDefaultImg(false);
                      onCancel();
                    }}
                  >
                    취소
                  </button>
                </div>
              </div>
            )}
            <div>
              <p>닉네임</p>
              <div className="flex my-2">
                <input
                  type="text"
                  disabled={isCheckedNickname}
                  className="signup-input w-full"
                  placeholder="닉네임을 입력해 주세요"
                  defaultValue={myInfo?.nickname}
                  {...register("nickname", {
                    required: "닉네임은 필수 입력 사항입니다.",
                    pattern: {
                      value: /^[a-zA-Z0-9가-힣ぁ-んァ-ンー]{1,10}$/,
                      message: "닉네임은 특수문자를 제외한 1~10자만 입력가능합니다.",
                    },
                  })}
                  onChange={(e) => {
                    if (e.target.value === myInfo?.nickname) {
                      setIsChangedNickname(false);
                    } else {
                      setIsChangedNickname(true);
                    }
                  }}
                  onKeyDown={(e) => {
                    if (e.key === "Enter") {
                      e.preventDefault();
                      // Enter 키 누르면 사진사라지는 현상제거
                    }
                  }}
                  onKeyUp={(e) => {
                    if (e.key === "Enter") {
                      e.preventDefault();
                      onCheckNickname(e.currentTarget.value);
                    }
                  }}
                />
                <button
                  className={`ms-3 w-24 ${isCheckedNickname ? "jongRyul-gray" : "jongRyul-primary"}`}
                  disabled={isCheckedNickname}
                  onClick={(e) => {
                    e.preventDefault();
                    onCheckNickname(getValues("nickname"));
                  }}
                >
                  중복확인
                </button>
              </div>
              <div className="errorMessage">{errors.nickname?.message}</div>

              <div className="grid grid-cols-2">
                <div>
                  <label htmlFor="birth" className="mt-5 text-left">
                    <p className="my-2 font-regular">생년월일</p>
                  </label>
                  <div className="flex justify-between items-center">
                    <InputDatePicker control={control} originValue={myInfo ? myInfo.birth : null} />
                  </div>
                </div>
                <div>
                  <p className="my-2 font-regular ms-3">성별</p>

                  <div className="flex justify-between items-center signup-input w-full mx-3">
                    <div className="flex">
                      <input
                        defaultChecked={myInfo?.gender === 1}
                        {...register("gender")}
                        id="male"
                        type="radio"
                        value={1}
                        className="me-2"
                      />
                      <label htmlFor="male">남</label>
                    </div>
                    <div className="flex">
                      <input
                        defaultChecked={myInfo?.gender === 2}
                        {...register("gender")}
                        id="female"
                        type="radio"
                        value={2}
                        className="me-2"
                      />
                      <label htmlFor="female">여</label>
                    </div>
                    <div className="flex">
                      <input
                        defaultChecked={myInfo?.gender === 0}
                        {...register("gender")}
                        id="none"
                        type="radio"
                        value={0}
                        className="me-2"
                      />
                      <label htmlFor="none">선택안함</label>
                    </div>
                  </div>
                </div>
              </div>
            </div>
            <div className="text-center mt-20">
              <button
                className="jongRyul-gray w-24 h-12 me-3"
                onClick={() => {
                  onReset();
                  setShowModal(false);
                  setError("nickname", {
                    type: "nicknameerror",
                    message: "",
                  });
                  reset();
                }}
              >
                닫기
              </button>
              <button
                className="jongRyul-primary w-24 h-12 ms-3"
                type="submit"
                // onClick={() => setShowModal(false)}
              >
                변경하기
              </button>
            </div>
          </form>
        </div>
      </Modal>
    </>
  );
};
export default ProfileUpdate;
