import { create } from "zustand"; // create로 zustand를 불러옵니다.
import { devtools } from "zustand/middleware";
import { Client } from "@stomp/stompjs";

interface ModalManagerState {
  client: Client | null;
  setClient: (client: Client) => void;
}

const stompStore = create<ModalManagerState>()(
  devtools((set) => ({
    client: null,
    setClient: (client) => set({ client }),
  }))
);

export default stompStore;
