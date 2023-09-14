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
  const { onNextStep, groupSetting, dispatch, step, resetStepAndGroupSetting } = useCreateModal();

  const CreateModalStepComponent = [
    <InitGroup onNextStep={onNextStep} closeModal={closeModal} resetStepAndGroupSetting={resetStepAndGroupSetting} />,
    <SettingPeriod
      onNextStep={onNextStep}
      period={groupSetting.period}
      dispatch={dispatch}
      closeModal={closeModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
    />,
    <SettingPeriod
      onNextStep={onNextStep}
      period={groupSetting.period}
      dispatch={dispatch}
      closeModal={closeModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
    />,
    <SettingPeriod
      onNextStep={onNextStep}
      period={groupSetting.period}
      dispatch={dispatch}
      closeModal={closeModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
    />,
  ];

  return (
    <Modal
      isOpen={showModal}
      ariaHideApp={false}
      onRequestClose={() => {
        closeModal();
        resetStepAndGroupSetting();
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
