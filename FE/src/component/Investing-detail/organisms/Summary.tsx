interface Props {
  companyCode: string;
}

const Summary = ({ companyCode }: Props) => {
  return <div className=" shadow-component">{companyCode}</div>;
};
export default Summary;
