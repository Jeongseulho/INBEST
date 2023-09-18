import { Period } from "../../../type/GroupFilter";

interface Props {
  text: string;
  payload: Period;
  period: Period;
  dispatch: React.Dispatch<{ type: "PERIOD"; payload: Period }>;
}
const PeriodFilterTag = ({ text, payload, period, dispatch }: Props) => {
  return (
    <button
      className={` ${
        period[0] === payload[0] && period[1] === payload[1] && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        dispatch({ type: "PERIOD", payload });
      }}
    >
      <p className="text-black">{text}</p>
    </button>
  );
};
export default PeriodFilterTag;
