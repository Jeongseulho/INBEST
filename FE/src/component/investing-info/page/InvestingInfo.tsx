interface Props {
  groupCode: string | undefined;
}

const InvestingInfo = ({ groupCode }: Props) => {
  return <div>{groupCode}</div>;
};
export default InvestingInfo;
