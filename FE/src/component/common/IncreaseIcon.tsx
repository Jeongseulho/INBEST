interface Props {
  number: number;
}

const IncreaseIcon = ({ number }: Props) => {
  return (
    <div className=" h-5 flex items-center rounded-full bg-mainMoreLight py-1 px-2 mx-3 gap-1">
      <div id="increase-triangle"></div>
      <p className=" text-mainMoreDark font-extraBold text-xs">{Number(number).toFixed(2)}%</p>
    </div>
  );
};
export default IncreaseIcon;
