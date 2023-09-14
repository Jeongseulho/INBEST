import create from "zustand"; // create로 zustand를 불러옵니다.

const useStore = create((set) => ({
  accessToken: null,
  setAccessToken: (Token: string) => set({ accesToken: Token }),
  delAccessToken: () => set({ accesToken: null }),
}));

export default useStore;
