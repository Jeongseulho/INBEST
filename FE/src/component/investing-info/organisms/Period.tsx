import Slider from "rc-slider";

const Period = () => {
  return (
    <div className=" shadow-component">
      <Slider min={0} max={100} defaultValue={0} value={50} />
    </div>
  );
};
export default Period;
