import Modal from "react-modal";
import "animate.css";
import { ProfileUpdateFormValue } from "../../../type/ProfileUpdateForm";
import { useRef } from "react";
import { useForm } from "react-hook-form";
import Cropper, { ReactCropperElement } from "react-cropper";
import { useProfileUpdate } from "./useProfileUpdate";
import "cropperjs/dist/cropper.css";
import tempimg from "../../../asset/image/default_image.png";
const ProfileUpdate = ({
  showModal,
  setShowModal,
}: {
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const { beforeCropUrl, setImgInfo, imgInfo, isChanged, setIsChanged } = useProfileUpdate();
  const cropperRef = useRef<ReactCropperElement>(null);
  const selectImg = useRef<HTMLInputElement | null>(null);
  const onCrop = () => {
    const cropper = cropperRef.current?.cropper;
    cropper?.getCroppedCanvas().toBlob((blob) => {
      const formData = new FormData();
      formData.append("file", blob!);
      console.log(blob);
      console.log(formData);
      for (const [value] of formData.entries()) {
        console.log(value);
      }
    });
  };
  const {
    control,
    register,
    handleSubmit,
    formState: { errors },
  } = useForm();

  return (
    <>
      <Modal
        isOpen={showModal}
        style={{
          content: {
            width: "500px",
            margin: "auto",
          },
        }}
      >
        <div>
          <h3 className="text-center my-10">회원정보 변경</h3>
          <form onSubmit={handleSubmit((data) => console.log(data))}>
            <div className="text-center">
              <div className="flex justify-center">
                <img src={isChanged ? beforeCropUrl! : tempimg} alt="유저이미지" className="w-48 h-48 rounded-full" />
              </div>
              <div className="bg-gray-200 mt-3 px-3 flex items-center mx-auto font-regular rounded-md text-sm w-2/3 border-2 border-gray-400 h-10">
                <span className="align-middle whitespace-nowrap overflow-hidden overflow-ellipsis">
                  {imgInfo && imgInfo.length > 0 && imgInfo[0].name}
                </span>
              </div>
              <button
                className="primary-btn w-20 h-8
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
                    className="primary-btn w-16 h-8 me-2"
                    onClick={(e) => {
                      e.preventDefault();
                      onCrop();
                    }}
                  >
                    자르기
                  </button>
                  <button className="gray-btn w-16 h-8">취소</button>
                </div>
              </div>
            )}
          </form>
        </div>
        <button onClick={() => setShowModal(false)}>닫기</button>
      </Modal>
    </>
  );
};
export default ProfileUpdate;
