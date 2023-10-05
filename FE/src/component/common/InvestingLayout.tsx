interface Props {
  children: React.ReactNode;
}

const InvestingLayout = ({ children }: Props) => {
  return <div className=" mt-5 w-[90%] px-10 py-5">{children}</div>;
};
export default InvestingLayout;
