import create from "zustand"; // create로 zustand를 불러옵니다.

const useStore = create((set) => ({
  bears: 0,
  increasePopulation: () => set(() => ({})),
  removeAllBears: () => set({ bears: 0 }),
}));

export default useStore;
