interface Props {
  step: number;
}

const ProgressCircle = ({ step }: Props) => {
  return (
    <div className=" relative mt-6">
      <div className=" w-24 h-24 border-4 border-lightPrimary border-opacity-25 rounded-[50%]"></div>
      <div
        className=" absolute top-0 left-0 w-24 h-24 border-4 border-transparent border-t-primary border-opacity-100 rounded-[50%]"
        style={{ transform: `rotate(${(step + 1) * 25}deg)` }}
      ></div>
      <p className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2">{step + 1} / 4</p>
    </div>
  );
};
export default ProgressCircle;
