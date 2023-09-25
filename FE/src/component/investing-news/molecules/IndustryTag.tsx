interface Props {
  text: string;
  curTab: string;
  myTab: string;
  setIndustryTab: React.Dispatch<React.SetStateAction<string>>;
}

const IndustryTag = ({ text, curTab, myTab, setIndustryTab }: Props) => {
  return (
    <div
      className={`hover:text-black cursor-pointer px-4 min-w-[2rem] rounded-full border-2 border-gray-300 py-1 text-center hover:bg-mainLight hover:bg-opacity-40 duration-500 transition-colors text-gray-500 ${
        curTab === myTab && " bg-mainLight bg-opacity-40 text-black"
      } `}
      onClick={() => setIndustryTab(myTab)}
    >
      {text}
    </div>
  );
};
export default IndustryTag;
