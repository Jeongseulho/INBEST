import Chart from "react-apexcharts";
import { formatKoreanNumber, formatNumberToKoreanWon } from "../../../util/formatMoney";
import { useQuery } from "react-query";
import { getIncome } from "../../../api/investingCompany";

interface Props {
  companyInfo: { code: string; name: string };
}

const ThreeYearsChart = ({ companyInfo }: Props) => {
  const { data, isLoading } = useQuery(["income", companyInfo.code], () => getIncome(companyInfo.code));
  return (
    <div className=" w-full">
      {isLoading ? (
        <></>
      ) : (
        data && (
          <Chart
            type="bar"
            series={[
              {
                name: "영업이익",
                data: [data.net_income_2021, data.net_income_2022, data.net_income_2023],
              },
            ]}
            options={{
              plotOptions: {
                bar: {
                  borderRadius: 10,
                  dataLabels: {
                    position: "top", // top, center, bottom
                  },
                },
              },
              dataLabels: {
                enabled: true,
                formatter: function (val) {
                  return formatKoreanNumber(val as number);
                },
                offsetY: -20,
                style: {
                  fontSize: "12px",
                  colors: ["#304758"],
                },
              },
              chart: {
                toolbar: {
                  show: false,
                },
              },
              yaxis: {
                show: false,
                axisBorder: {
                  show: false,
                },
                axisTicks: {
                  show: false,
                },
                labels: {
                  show: false,
                  formatter: function (val) {
                    if (!val) return "0";
                    return formatNumberToKoreanWon(val as number);
                  },
                },
              },

              xaxis: {
                categories: [2021, 2022, 2023],
                position: "bottom",
              },
            }}
          />
        )
      )}
    </div>
  );
};

export default ThreeYearsChart;
