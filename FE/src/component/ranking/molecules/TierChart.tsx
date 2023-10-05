import ReactApexChart from "react-apexcharts";
import { useTierChart } from "./useTierChart";

const TierChart = () => {
  const { perList, isError } = useTierChart();
  return (
    <div className="mt-5 border rounded-lg bg-white shadow-sm py-2">
      {isError && <div>에러가 발생했습니다!</div>}
      {perList && (
        <ReactApexChart
          height={300}
          type="bar"
          series={[
            {
              name: "tier",
              data: perList,
            },
          ]}
          options={{
            chart: {
              type: "bar",
              background: "transparent",
            },

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
              offsetY: -20,
              formatter: function (val) {
                return val + "%";
              },
              style: {
                fontSize: "12px",
                colors: ["#304758"],
              },
            },
            xaxis: {
              categories: ["브론즈", "실버", "골드", "다이아몬드"],

              axisBorder: {
                show: false,
              },
              axisTicks: {
                show: false,
              },
            },
            yaxis: {
              max: 100,
              axisBorder: {
                show: false,
              },
              axisTicks: {
                show: false,
              },
              tickAmount: 5,
            },
            title: {
              text: "티어별 분포도",
              floating: true,
              margin: 20,
              align: "center",

              style: {
                color: "#444",
                fontSize: "20px",
              },
            },
          }}
        />
      )}
    </div>
  );
};
export default TierChart;
