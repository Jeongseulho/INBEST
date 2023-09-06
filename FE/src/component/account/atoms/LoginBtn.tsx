interface ButtonProps {
  text: string;
}

const LoginBtn = ({ text }: ButtonProps) => {
  return (
    <>
      <button>{text}</button>
    </>
  );
};
export default LoginBtn;
