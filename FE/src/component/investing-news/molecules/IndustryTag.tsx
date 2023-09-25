interface Props {
  curIndustryTab: string;
  myTab: string;
  setCurIndustryTab: React.Dispatch<React.SetStateAction<string>>;
}

const IndustryTag = ({ curIndustryTab, myTab, setCurIndustryTab }: Props) => {
  return (
    <div
      className={`hover:text-black cursor-pointer px-4 min-w-[2rem] rounded-full border-2 border-gray-300 py-1 text-center hover:bg-mainLight hover:bg-opacity-40 duration-500 transition-colors text-gray-500 ${
        curIndustryTab === myTab && " bg-mainLight bg-opacity-40 text-black"
      } `}
      onClick={() => setCurIndustryTab(myTab)}
    >
      {myTab}
    </div>
  );
};
export default IndustryTag;
