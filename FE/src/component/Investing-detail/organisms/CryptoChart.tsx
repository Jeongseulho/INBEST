import { useQuery } from "react-query";
import { getCryptoChart } from "../../../api/investingStockChart";
import Chart from "react-apexcharts";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
interface Props {
  companyInfo: CompanyInfo;
}

const CryptoChart = ({ companyInfo }: Props) => {
  const { data } = useQuery("cryptoChart", getCryptoChart);
  console.log(data);
  console.log(companyInfo);
  return <div>CryptoChart</div>;
};
export default CryptoChart;
