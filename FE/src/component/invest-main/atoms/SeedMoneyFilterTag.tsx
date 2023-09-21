import { SeedMoney } from "../../../type/GroupFilter";

interface Props {
  text: string;
  payload: SeedMoney;
  seedMoney: SeedMoney;
  dispatch: React.Dispatch<{ type: "SEED_MONEY"; payload: SeedMoney }>;
}
const SeedMoneyFilterTag = ({ text, payload, seedMoney, dispatch }: Props) => {
  return (
    <button
      className={` ${
        seedMoney[0] === payload[0] && seedMoney[1] === payload[1] && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        dispatch({ type: "SEED_MONEY", payload });
      }}
    >
      <p className="text-black">{text}</p>
    </button>
  );
};
export default SeedMoneyFilterTag;
