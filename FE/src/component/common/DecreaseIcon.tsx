interface Props {
  number: number;
}

const DecreaseIcon = ({ number }: Props) => {
  return (
    <div className="h-6 flex items-center rounded-full bg-red-300 py-1 px-3 mx-3">
      <div id="decrease-triangle" className=" me-2"></div>
      <p className=" text-myRed font-extraBold">{number}%</p>
    </div>
  );
};
export default DecreaseIcon;
