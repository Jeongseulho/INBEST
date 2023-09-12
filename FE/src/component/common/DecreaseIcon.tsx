interface Props {
  number: number;
}

const DecreaseIcon = ({ number }: Props) => {
  return (
    <div className=" flex items-center rounded-full bg-red bg-opacity-10 py-1 px-3 mx-3">
      <div id="decrease-triangle" className=" me-2"></div>
      <p className=" text-red font-extraBold">{number}%</p>
    </div>
  );
};
export default DecreaseIcon;
