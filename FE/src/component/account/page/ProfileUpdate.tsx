import Modal from "react-modal";
import "animate.css";
import { ProfileUpdateFormValue } from "../../../type/ProfileUpdateForm";
import { useState, useRef } from "react";
import { useForm } from "react-hook-form";
import Cropper, { ReactCropperElement } from "react-cropper";
import "cropperjs/dist/cropper.css";
const ProfileUpdate = ({
  showModal,
  setShowModal,
}: {
  showModal: boolean;
  setShowModal: React.Dispatch<React.SetStateAction<boolean>>;
}) => {
  const [showPassWord, setShowPassWord] = useState(false);
  const [showPassWord2, setShowPassWord2] = useState(false);

  const onToggleShowPassWord = (order: number) => {
    if (order === 1) {
      return setShowPassWord((prev) => !prev);
    }
    return setShowPassWord2((prev) => !prev);
  };
  const cropperRef = useRef<ReactCropperElement>(null);
  const onCrop = () => {
    const cropper = cropperRef.current?.cropper;
  };
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
          <h3 className="text-center mb-5">회원정보 변경</h3>
          <form action="">
            <Cropper
              src="https://raw.githubusercontent.com/roadmanfong/react-cropper/master/example/img/child.jpg"
              // Cropper.js options
              style={{ height: 400, width: "100%" }}
              aspectRatio={1 / 1}
              crop={onCrop}
              ref={cropperRef}
              zoomTo={0.5}
            />
          </form>
        </div>
        <button onClick={() => setShowModal(false)}>닫기</button>
      </Modal>
    </>
  );
};
export default ProfileUpdate;
