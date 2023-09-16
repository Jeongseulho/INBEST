import { Period } from "../../../type/GroupSetting";

interface Props {
  text: string;
  dispatch: React.Dispatch<{ type: string; payload: Period }>;
  activeTag: number;
  setActiveTag: React.Dispatch<React.SetStateAction<number>>;
  tagNum: number;
  payload: Period;
}
const PeriodSettingTag = ({ text, dispatch, activeTag, tagNum, setActiveTag, payload }: Props) => {
  return (
    <button
      className={` ${
        activeTag === tagNum && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        setActiveTag(tagNum);
        dispatch({ type: "PERIOD", payload });
      }}
    >
      <p className={`${text === "가속모드" ? "text-primary" : "text-black"}`}>{text}</p>
    </button>
  );
};
export default PeriodSettingTag;
