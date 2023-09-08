interface ButtonProps {
  width?: number;
  textContent: string;
  color: string;
}

const Button = ({ width = 10, textContent, color }: ButtonProps) => {
  console.log(width, textContent, color);
  return <button className="">{textContent}</button>;
};
export default Button;
