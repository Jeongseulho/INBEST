interface Props {
  text: string;
  payload: number[];
}
const PeriodFilterTag = ({ text, payload }: Props) => {
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
      <p className="text-black">{text}</p>
    </button>
  );
};
export default PeriodFilterTag;
