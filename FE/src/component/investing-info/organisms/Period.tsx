import Slider from "rc-slider";

const Period = () => {
  return (
    <div className=" shadow-component col-span-4 row-span-1">
      <Slider min={0} max={100} defaultValue={0} value={50} />
    </div>
  );
};
export default Period;
