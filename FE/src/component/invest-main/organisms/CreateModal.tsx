import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import InitGroup from "../molecules/InitGroup";
import SettingPeriod from "../molecules/SettingPeriod";
import { useCreateModal } from "./useCreateModal";
import ProgressCircle from "../molecules/ProgressCircle";
import group from "../../../asset/image/group.png";
import SettingSeedMoney from "../molecules/SettingSeedMoney";
import SettingInvite from "../molecules/SettingInvite";
import { GROUP_CREATE_STEP_MAP } from "../../../constant/GROUP_CREATE_STEP_MAP";
import SettingTitle from "../molecules/SettingTitle";

interface Props {
  showCreateModal: boolean;
  closeCreateModal: () => void;
}

const CreateModal = ({ showCreateModal, closeCreateModal }: Props) => {
  const { onNextStep, groupSetting, dispatch, step, resetStepAndGroupSetting } = useCreateModal();

  const CreateModalStepComponent = [
    <InitGroup
      onNextStep={onNextStep}
      closeCreateModal={closeCreateModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
    />,
    <SettingPeriod
      onNextStep={onNextStep}
      period={groupSetting.period}
      dispatch={dispatch}
      closeCreateModal={closeCreateModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
    />,
    <SettingSeedMoney
      onNextStep={onNextStep}
      seedMoney={groupSetting.seedMoney}
      dispatch={dispatch}
      closeCreateModal={closeCreateModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
    />,
    <SettingInvite
      onNextStep={onNextStep}
      closeCreateModal={closeCreateModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
      inviteUsers={groupSetting.inviteUsers}
      unInviteUsers={groupSetting.unInviteUsers}
      dispatch={dispatch}
    />,
    <SettingTitle
      closeCreateModal={closeCreateModal}
      resetStepAndGroupSetting={resetStepAndGroupSetting}
      dispatch={dispatch}
      title={groupSetting.title}
    />,
  ];

  return (
    <Modal
      isOpen={showCreateModal}
      ariaHideApp={false}
      onRequestClose={() => {
        closeCreateModal();
        resetStepAndGroupSetting();
      }}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: step === GROUP_CREATE_STEP_MAP.INVITE_USER ? "700px" : "500px",
          display: "flex",
          flexDirection: "column",
          alignItems: "center",
          transition: "all 0.3s ease",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      {step === GROUP_CREATE_STEP_MAP.INIT_GROUP ? <img src={group} width={120} /> : <ProgressCircle step={step} />}
      {CreateModalStepComponent[step]}
    </Modal>
  );
};

export default CreateModal;
