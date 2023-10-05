import { Period } from "../../../type/GroupSetting";

interface Props {
  text: string;
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: Period }>;
  payload: Period;
  period: Period;
}
const PeriodSettingTag = ({ text, dispatch, period, payload }: Props) => {
  return (
    <button
      className={` ${
        period === payload && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        dispatch({ type: "PERIOD", payload });
      }}
    >
      <p className={`${text === "가속모드" ? "text-primary" : "text-black"}`}>{text}</p>
    </button>
  );
};
export default PeriodSettingTag;
