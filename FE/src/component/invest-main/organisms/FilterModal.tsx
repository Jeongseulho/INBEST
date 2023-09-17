import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { useFilterModal } from "./useFilterModal";
import { FILTER_MODAL_TAB } from "../../../constant/FILTER_MODAL_TAB";
import FilterPeriodTab from "../molecules/FilterPeriodTab";
import FilterSeedMoneyTab from "../molecules/FilterSeedMoneyTab";
import FilterTierTab from "../molecules/FilterTierTab";

interface Props {
  showFilterModal: boolean;
  closeFilterModal: () => void;
}

const FilterModal = ({ showFilterModal, closeFilterModal }: Props) => {
  const { activeTab, setActiveTab } = useFilterModal();
  return (
    <Modal
      isOpen={showFilterModal}
      ariaHideApp={false}
      onRequestClose={() => {
        closeFilterModal();
      }}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "500px",
          height: "500px",
          display: "flex",
          flexDirection: "column",
          transition: "all 0.3s ease",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      <div>
        <h3>필터</h3>
        <div className=" flex border-b-2 border-opacity-30 gap-10 mt-5">
          <div onClick={() => setActiveTab(FILTER_MODAL_TAB.PERIOD)} className=" cursor-pointer">
            기간
          </div>
          <div onClick={() => setActiveTab(FILTER_MODAL_TAB.SEED_MONEY)} className=" cursor-pointer">
            시드머니
          </div>
          <div onClick={() => setActiveTab(FILTER_MODAL_TAB.MEAN_TIER)} className=" cursor-pointer">
            평균티어
          </div>
        </div>
        {activeTab === FILTER_MODAL_TAB.PERIOD && <FilterPeriodTab />}
        {activeTab === FILTER_MODAL_TAB.SEED_MONEY && <FilterSeedMoneyTab />}
        {activeTab === FILTER_MODAL_TAB.MEAN_TIER && <FilterTierTab />}
      </div>
    </Modal>
  );
};
export default FilterModal;
