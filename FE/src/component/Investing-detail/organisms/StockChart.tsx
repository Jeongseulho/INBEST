interface Props {
  companyCode: string;
}

const StockChart = ({ companyCode }: Props) => {
  return <div>{companyCode}</div>;
};
export default StockChart;
