interface ButtonProps {
  width?: string;
  height?: string;
  textContent: string;
}

const PrimaryBtn = ({ width, textContent, height }: ButtonProps) => {
  return <button className={`rounded-md w-${width} bg-primary text-white h-${height}`}>{textContent}</button>;
};
export default PrimaryBtn;
