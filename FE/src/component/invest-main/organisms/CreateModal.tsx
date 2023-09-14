import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import InitGroup from "../molecules/InitGroup";
import SettingPeriod from "../molecules/SettingPeriod";
import { useCreateModal } from "./useCreateModal";

interface Props {
  showModal: boolean;
  closeModal: () => void;
  step: number;
  setStep: React.Dispatch<React.SetStateAction<number>>;
}

const CreateModal = ({ showModal, closeModal, step, setStep }: Props) => {
  const { onNextStep, groupSetting, dispatch } = useCreateModal(setStep);

  const CreateModalStepComponent = [
    <InitGroup onNextStep={onNextStep} closeModal={closeModal} />,
    <SettingPeriod onNextStep={onNextStep} period={groupSetting.period} dispatch={dispatch} />,
  ];

  return (
    <Modal
      isOpen={showModal}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: "500px",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      {CreateModalStepComponent[step]}
    </Modal>
  );
};

export default CreateModal;
