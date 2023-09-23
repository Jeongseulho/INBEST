import { create } from "zustand"; // create로 zustand를 불러옵니다.
import { devtools } from "zustand/middleware";

interface ModalManagerState {
  modalType: string;
  groupCode?: string;

  openModal: (modalType: string, groupCode?: string) => void;
  closeModal: () => void;
}

const modalStore = create<ModalManagerState>()(
  devtools((set) => ({
    modalType: "",
    openModal: (modalType, groupCode) => set({ modalType, groupCode }),
    closeModal: () => set({ modalType: "", groupCode: undefined }),
  }))
);

export default modalStore;
