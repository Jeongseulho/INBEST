const HorizonLine = ({ text }: { text: string }) => {
  return (
    <div className=" border-b-2 text-center h-3 w-full">
      <span className="bg-gray-50 px-3 font-regular text-gray-500">{text}</span>
    </div>
  );
};

export default HorizonLine;
