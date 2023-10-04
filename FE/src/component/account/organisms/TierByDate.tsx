import ReactApexChart from "react-apexcharts";
import { TierByDates } from "../../../type/MemberInfo";

const TierByDate = ({ tierByDates }: { tierByDates: TierByDates[] }) => {
  const dateList = tierByDates.map((item) => item.date);
  const tierList = tierByDates.map((item) => item.tier);
  return (
    <div className="bg-white rounded-lg shadow-md w-3/5 py-5 ms-5">
      <div>
        <ReactApexChart
          options={{
            chart: {
              type: "line",
              toolbar: false,
            },
            legend: {
              position: "bottom",
            },
            responsive: [
              {
                breakpoint: 480,
              },
            ],
            xaxis: {
              categories: dateList, // x축 데이터 설정
            },
            title: {
              text: "티어 변화 그래프",
              align: "center",
            },
          }}
          series={[
            {
              name: "Tier",
              data: tierList, // y축 데이터 설정
            },
          ]}
          type="line"
          width="80%"
        />
      </div>
    </div>
  );
};
export default TierByDate;
