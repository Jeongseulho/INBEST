import { SeedMoney } from "../../../type/GroupSetting";

interface Props {
  text: string;
  dispatch: React.Dispatch<{ type: "SEED_MONEY"; payload: SeedMoney }>;
  payload: SeedMoney;
  seedMoney: SeedMoney;
}
const SeedMoneySettingTag = ({ text, dispatch, seedMoney, payload }: Props) => {
  return (
    <button
      className={` ${
        seedMoney === payload && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        dispatch({ type: "SEED_MONEY", payload });
      }}
    >
      <p className={`${text === "계좌연동모드" ? "text-lightRed" : "text-black"}`}>{text}</p>
    </button>
  );
};
export default SeedMoneySettingTag;
