import { SeedMoney } from "../../../type/GroupSetting";

interface Props {
  text: string;
  dispatch: React.Dispatch<{ type: string; payload: SeedMoney }>;
  activeTag: number;
  setActiveTag: React.Dispatch<React.SetStateAction<number>>;
  tagNum: number;
  payload: SeedMoney;
}
const SeedMoneySettingTag = ({ text, dispatch, activeTag, tagNum, setActiveTag, payload }: Props) => {
  return (
    <button
      className={` ${
        activeTag === tagNum && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        setActiveTag(tagNum);
        dispatch({ type: "SEED_MONEY", payload });
      }}
    >
      <p className={`${text === "계좌연동모드" ? "text-lightRed" : "text-black"}`}>{text}</p>
    </button>
  );
};
export default SeedMoneySettingTag;
