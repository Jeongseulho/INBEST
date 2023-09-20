interface Props {
  number: number;
}

const IncreaseIcon = ({ number }: Props) => {
  return (
    <div className=" h-6 flex items-center rounded-full bg-mainMoreLight py-1 px-3 mx-3">
      <div id="increase-triangle" className=" me-2"></div>
      <p className=" text-mainMoreDark font-extraBold">{number}%</p>
    </div>
  );
};
export default IncreaseIcon;
