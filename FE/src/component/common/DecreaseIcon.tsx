interface Props {
  number: number;
}

const DecreaseIcon = ({ number }: Props) => {
  return (
    <div className="h-5 flex items-center rounded-full bg-red-300 py-1 px-2 mx-3 gap-1">
      <div id="decrease-triangle"></div>
      <p className=" text-myRed font-extraBold text-xs">{number.toFixed(2)}%</p>
    </div>
  );
};
export default DecreaseIcon;
