interface Props {
  text: string;
  payload: number[];
  meanTier: number[];
  dispatch: React.Dispatch<{ type: "MEAN_TIER"; payload: number[] }>;
}
const TierFilterTag = ({ text, payload, meanTier, dispatch }: Props) => {
  return (
    <button
      className={` ${
        meanTier[0] === payload[0] && meanTier[1] === payload[1] && "bg-lightPrimary bg-opacity-40"
      } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
      onClick={() => {
        dispatch({ type: "MEAN_TIER", payload });
      }}
    >
      <p className="text-black">{text}</p>
    </button>
  );
};
export default TierFilterTag;
