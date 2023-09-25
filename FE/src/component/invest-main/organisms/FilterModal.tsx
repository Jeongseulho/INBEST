import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { useFilterModal } from "./useFilterModal";
import { FILTER_MODAL_TAB } from "../../../constant/FILTER_MODAL_TAB";
import FilterPeriodTab from "../molecules/FilterPeriodTab";
import FilterSeedMoneyTab from "../molecules/FilterSeedMoneyTab";
import FilterTierTab from "../molecules/FilterTierTab";
import modalStore from "../../../store/modalStore";

const FilterModal = () => {
  const { activeTab, setActiveTab, groupFilter, dispatch } = useFilterModal();
  const { modalType, closeModal } = modalStore();
  return (
    <Modal
      isOpen={modalType === "filter"}
      ariaHideApp={false}
      onRequestClose={closeModal}
      closeTimeoutMS={300}
      style={{
        content: {
          ...CONTENT_MODAL_STYLE,
          width: "700px",
          height: "500px",
          display: "flex",
          flexDirection: "column",
          transition: "all 0.3s ease",
        },
        overlay: OVERLAY_MODAL_STYLE,
      }}
    >
      <div className=" relative h-full">
        <h3>필터</h3>
        <div className=" flex border-b-2 border-opacity-30 gap-10 mt-5">
          <div
            onClick={() => setActiveTab(FILTER_MODAL_TAB.PERIOD)}
            className={` cursor-pointer border-b-2  ${
              activeTab === FILTER_MODAL_TAB.PERIOD ? " border-black text-black" : "border-transparent text-gray-400"
            }`}
          >
            기간
          </div>
          <div
            onClick={() => setActiveTab(FILTER_MODAL_TAB.SEED_MONEY)}
            className={` cursor-pointer border-b-2  ${
              activeTab === FILTER_MODAL_TAB.SEED_MONEY
                ? " border-black text-black"
                : "border-transparent text-gray-400"
            }`}
          >
            시드머니
          </div>
          <div
            onClick={() => setActiveTab(FILTER_MODAL_TAB.MEAN_TIER)}
            className={` cursor-pointer border-b-2  ${
              activeTab === FILTER_MODAL_TAB.MEAN_TIER ? " border-black text-black" : "border-transparent text-gray-400"
            }`}
          >
            평균티어
          </div>
        </div>
        {activeTab === FILTER_MODAL_TAB.PERIOD && <FilterPeriodTab period={groupFilter.period} dispatch={dispatch} />}
        {activeTab === FILTER_MODAL_TAB.SEED_MONEY && (
          <FilterSeedMoneyTab seedMoney={groupFilter.seedMoney} dispatch={dispatch} />
        )}
        {activeTab === FILTER_MODAL_TAB.MEAN_TIER && (
          <FilterTierTab meanTier={groupFilter.meanTier} dispatch={dispatch} />
        )}

        <button className=" absolute bottom-1 right-2 rounded-xl text-white bg-mainDark py-4 px-24 transition-colors duration-500 hover:text-mainDark border-2 border-mainDark hover:bg-opacity-10">
          결과보기
        </button>
      </div>
    </Modal>
  );
};
export default FilterModal;
