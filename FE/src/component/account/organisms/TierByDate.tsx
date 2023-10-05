import ReactApexChart from "react-apexcharts";
import { TierByDates } from "../../../type/MemberInfo";
import { tierToString } from "../../../util/tierToString";
const TierByDate = ({ tierByDates }: { tierByDates: TierByDates[] }) => {
  const dateList = tierByDates.map((item) => item.date);
  const tierList = tierByDates.map((item) => item.tier);

  return (
    <div className="bg-white rounded-lg shadow-md w-3/5 py-5 ms-5 justify-center flex">
      <div className="w-[90%]">
        <ReactApexChart
          options={{
            chart: {
              type: "line",
              toolbar: { show: false },
            },
            legend: {
              position: "bottom",
            },
            responsive: [
              {
                breakpoint: 480,
              },
            ],
            markers: {
              size: 5,
            },
            xaxis: {
              categories: dateList, // x축 데이터 설정
              tickAmount: 10,
            },
            yaxis: {
              tickAmount: 4,
              labels: {
                formatter: function (value) {
                  return `${tierToString(value)} ${value % 100}P`;
                },
                style: {
                  fontSize: "13px",
                },
              },
            },
            title: {
              text: "티어 변화 그래프",
              align: "center",
              style: {
                fontSize: "16px",
              },
            },
          }}
          series={[
            {
              name: "티어 변화",
              data: tierList, // y축 데이터 설정
            },
          ]}
          type="line"
          width="100%"
          height={330}
        />
      </div>
    </div>
  );
};
export default TierByDate;
