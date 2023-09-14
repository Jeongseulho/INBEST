import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import InitGroup from "../molecules/InitGroup";
import SettingPeriod from "../molecules/SettingPeriod";
import { useCreateModal } from "./useCreateModal";
import ProgressCircle from "../molecules/ProgressCircle";
import group from "../../../asset/image/group.png";

interface Props {
  showModal: boolean;
  closeModal: () => void;
}

const CreateModal = ({ showModal, closeModal }: Props) => {
  const { onNextStep, groupSetting, dispatch, step, setStep } = useCreateModal();

  const CreateModalStepComponent = [
    <InitGroup onNextStep={onNextStep} closeModal={closeModal} />,
    <SettingPeriod onNextStep={onNextStep} period={groupSetting.period} dispatch={dispatch} closeModal={closeModal} />,
  ];

  return (
    <Modal
      isOpen={showModal}
      ariaHideApp={false}
      onRequestClose={() => {
        closeModal();
        setTimeout(() => {
          setStep(0);
          dispatch({ type: "RESET" });
        }, 300);
      }}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: "500px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      {step === 0 ? <img src={group} width={120} /> : <ProgressCircle step={step} />}
      {CreateModalStepComponent[step]}
    </Modal>
  );
};

export default CreateModal;
