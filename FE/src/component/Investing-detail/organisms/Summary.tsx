interface Props {
  companyCode: string;
}

const Summary = ({ companyCode }: Props) => {
  return <div>{companyCode}</div>;
};
export default Summary;
