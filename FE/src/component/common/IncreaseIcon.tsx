interface Props {
  number: number;
}

const IncreaseIcon = ({ number }: Props) => {
  return (
    <div className=" flex items-center rounded-full bg-mainMoreDark bg-opacity-10 py-1 px-3 mx-3">
      <div id="increase-trianlge" className=" me-2"></div>
      <p className=" text-mainMoreDark font-extraBold">{number}%</p>
    </div>
  );
};
export default IncreaseIcon;
