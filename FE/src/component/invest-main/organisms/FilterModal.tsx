import Modal from "react-modal";
import { CONTENT_MODAL_STYLE, OVERLAY_MODAL_STYLE } from "../../../constant/MODAL_STYLE";
import { FILTER_MODAL_TAB } from "../../../constant/FILTER_MODAL_TAB";
import FilterPeriodTab from "../molecules/FilterPeriodTab";
import FilterSeedMoneyTab from "../molecules/FilterSeedMoneyTab";
import FilterTierTab from "../molecules/FilterTierTab";
import modalStore from "../../../store/modalStore";
import { GrPowerReset } from "react-icons/gr";
import { GroupFilter } from "../../../type/GroupFilter";

interface Props {
  activeTab: number;
  setActiveTab: React.Dispatch<React.SetStateAction<number>>;
  groupFilter: {
    period: number[];
    seedMoney: number[];
    meanTier: number[];
  };
  dispatch: React.Dispatch<
    | { type: "PERIOD" | "SEED_MONEY" | "MEAN_TIER"; payload: number[] }
    | {
        type: "RESET";
      }
  >;
  setFilter: React.Dispatch<React.SetStateAction<GroupFilter>>;
}
const FilterModal = ({ activeTab, setActiveTab, groupFilter, dispatch, setFilter }: Props) => {
  const { modalType, closeModal } = modalStore();
  return (
    <Modal
      isOpen={modalType === "filter"}
      onRequestClose={closeModal}
      ariaHideApp={false}
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
        <div className=" absolute bottom-1 right-2 flex items-center gap-4">
          <div
            className=" bg-gray-200 border-2 rounded-lg p-4 flex items-center gap-2 cursor-pointer"
            onClick={() => {
              dispatch({ type: "RESET" });
            }}
          >
            <GrPowerReset />
            <p>초기화</p>
          </div>
          <button
            onClick={() => {
              setFilter(groupFilter);
              closeModal();
            }}
            className=" rounded-xl text-white bg-mainDark py-4 px-24 transition-colors duration-500 hover:text-mainDark border-2 border-mainDark hover:bg-opacity-10"
          >
            결과보기
          </button>
        </div>
      </div>
    </Modal>
  );
};
export default FilterModal;
