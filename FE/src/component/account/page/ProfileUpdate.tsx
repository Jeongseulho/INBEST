import Modal from "react-modal";
import "animate.css";
const ProfileUpdate = ({ showModal }: { showModal: boolean }) => {
  return (
    <>
      <div className="animate__animated animate__fadeInRight">
        <Modal
          isOpen={showModal}
          style={{
            content: {
              width: "500px",
              margin: "auto",
            },
          }}
        >
          This is Modal content
        </Modal>
      </div>
    </>
  );
};
export default ProfileUpdate;
