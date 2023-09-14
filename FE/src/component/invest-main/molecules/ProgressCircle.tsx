interface Props {
  step: number;
}

const ProgressCircle = ({ step }: Props) => {
  const baseRotation = 45;
  const rotations = [baseRotation, 135, 225, 315];

  const getRotation = (index: number) => {
    if (index === 0) return baseRotation; // 첫 번째 div는 항상 45도
    if (step >= 1 && index === 1) return rotations[1];
    if (step >= 2 && index === 2) return rotations[2];
    if (step === 3 && index === 3) return rotations[3];
    return baseRotation;
  };

  return (
    <div className="relative mt-6">
      <div className="w-24 h-24 border-4 border-lightPrimary border-opacity-25 rounded-[50%]"></div>

      {rotations.map((rotation, index) => (
        <div
          key={index}
          className=" transition-all duration-500 absolute top-0 left-0 w-24 h-24 border-4 border-transparent border-t-primary border-opacity-100 rounded-[50%]"
          style={{ transform: `rotate(${getRotation(index)}deg)` }}
        ></div>
      ))}

      <p className="absolute top-1/2 left-1/2 transform -translate-x-1/2 -translate-y-1/2 text-2xl">{step} / 4</p>
    </div>
  );
};

export default ProgressCircle;
