import { create } from "zustand"; // create로 zustand를 불러옵니다.
import { devtools } from "zustand/middleware";

interface ModalManagerState {
  modalType: string;
  detailGroupCode: string | null;
  openModal: (modalType: string, detailGroupCode?: string | null) => void;
  closeModal: () => void;
}

const modalStore = create<ModalManagerState>()(
  devtools((set) => ({
    modalType: "",
    detailGroupCode: null,
    openModal: (modalType, detailGroupCode = null) => set({ modalType, detailGroupCode }),
    closeModal: () => set({ modalType: "", detailGroupCode: null }),
  }))
);

export default modalStore;
