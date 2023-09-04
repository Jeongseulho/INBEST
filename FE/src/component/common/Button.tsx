interface ButtonProps {
  width?: number;
  textContent: string;
  color: string;
}

const Button = ({ width = 10, textContent, color }: ButtonProps) => {
  console.log(width, textContent, color);
  return <div className="">{textContent}</div>;
};
export default Button;
