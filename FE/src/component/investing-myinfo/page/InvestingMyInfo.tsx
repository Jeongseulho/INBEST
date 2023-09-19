interface Props {
  groupCode: string | undefined;
}

const InvestingMyInfo = ({ groupCode }: Props) => {
  return <div>{groupCode}</div>;
};
export default InvestingMyInfo;
