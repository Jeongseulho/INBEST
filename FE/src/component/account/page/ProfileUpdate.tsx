import Modal from "react-modal";
import "animate.css";
import { ProfileUpdateFormValue } from "../../../type/ProfileUpdateForm";
import { useForm } from "react-hook-form";
import Cropper from "react-cropper";
import defaltImg from "../../../asset/image/default_image.png";
import { useProfileUpdate } from "./useProfileUpdate";
import "cropperjs/dist/cropper.css";
import useStore from "../../../store/store";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { GetUserInfo } from "../../../type/Accounts";

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
  } = useProfileUpdate();
  const { userInfo } = useStore();
  const {
    handleSubmit,
    formState: { errors },
  } = useForm();

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
          <form onSubmit={handleSubmit((data) => console.log(data))}>
            <div className="text-center">
              <div className="flex justify-center">
                <img
                  src={isDefaultImg ? defaltImg : isCropped ? cropImg! : userInfo?.profileImgSearchName}
                  alt="유저이미지"
                  className="w-48 h-48 rounded-full shadow-lg"
                />
              </div>
              {!isChanged && (
                <button
                  className="jongRyul-gray  w-36 h-8
								my-3 me-4"
                  onClick={(e) => {
                    e.preventDefault();
                    setIsDefaultImg(true);
                    setImgInfo(null);
                    selectImg.current;
                  }}
                >
                  기본이미지로 변경
                </button>
              )}
              <div className="bg-gray-200 mt-3 px-3 flex items-center mx-auto font-regular rounded-md text-sm w-2/3 border-2 border-gray-400 h-10">
                <span className="align-middle whitespace-nowrap overflow-hidden overflow-ellipsis">
                  {imgInfo && imgInfo.length > 0 && imgInfo[0].name}
                </span>
              </div>
              <button
                className="jongRyul-primary  w-20 h-8
								my-3"
                onClick={(e) => {
                  e.preventDefault();
                  selectImg.current?.click();
                }}
              >
                사진 찾기
              </button>
              <input
                onChange={(e) => {
                  if (!e.target.files || e.target.files.length === 0) {
                    setImgInfo(null);
                    setIsChanged(false);
                    return;
                  }
                  setImgInfo(e.target.files);
                  setIsChanged(true);
                  setIsDefaultImg(false);
                }}
                ref={selectImg}
                type="file"
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
                      setIsDefaultImg(false);
                    }}
                  >
                    자르기
                  </button>
                  <button
                    className="jongRyul-gray w-16 h-8"
                    onClick={(e) => {
                      e.preventDefault();
                      onCancel();
                    }}
                  >
                    취소
                  </button>
                </div>
              </div>
            )}
            <div>
              <p className="text-xl font-bold">닉네임</p>
              <div className="flex my-2">
                <input type="text" className="signup-input w-full" defaultValue={myInfo?.nickname} />
                <button className="ms-3 w-24 jongRyul-primary">중복확인</button>
              </div>
            </div>
          </form>
        </div>
        <button onClick={() => setShowModal(false)}>닫기</button>
      </Modal>
    </>
  );
};
export default ProfileUpdate;
