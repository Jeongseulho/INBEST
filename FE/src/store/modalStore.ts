import { create } from "zustand"; // create로 zustand를 불러옵니다.
import { devtools } from "zustand/middleware";

interface ModalManagerState {
  modalType: string;
  simulationSeq: number;
  openModal: (modalType: string, simulationSeq?: number) => void;
  closeModal: () => void;
}

const modalStore = create<ModalManagerState>()(
  devtools((set) => ({
    modalType: "",
    simulationSeq: 0,
    openModal: (modalType, simulationSeq) => set({ modalType, simulationSeq }),
    closeModal: () => set({ modalType: "", simulationSeq: 0 }),
  }))
);

export default modalStore;
