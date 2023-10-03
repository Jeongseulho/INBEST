import compare from "../../../asset/image/compare.png";
import Chart from "react-apexcharts";
import { useQueries } from "react-query";
import { getFund, getSaving, getSaving2, getDeposit, getDeposit2 } from "../../../api/investingCompany";

const Compare = () => {
  const results = useQueries([
    {
      queryKey: ["fund"],
      queryFn: getFund,
    },
    {
      queryKey: ["saving"],
      queryFn: getSaving,
    },
    {
      queryKey: ["saving2"],
      queryFn: getSaving2,
    },
    {
      queryKey: ["deposit"],
      queryFn: getDeposit,
    },
    {
      queryKey: ["deposit2"],
      queryFn: getDeposit2,
    },
  ]);
  const isAllLoading = results.some((res) => res.isLoading);
  const data = results.map((res) => res.data);
  //TODO: skeleton loading
  return (
    <div className=" shadow-component col-span-7 row-span-1 p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <img src={compare} width={40} />
        <h5>금융 상품 비교</h5>
      </div>
      <div>
        {isAllLoading ? (
          <></>
        ) : (
          data && (
            <Chart
              type="bar"
              series={[
                {
                  name: "이자율",
                  data: results.map((res) => res.data!.intr_rate2),
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
                    return val + "%";
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
                  },
                },

                xaxis: {
                  categories: results.map((res) => res.data!.fin_prdt_nm),
                  position: "bottom",
                },
              }}
            />
          )
        )}
      </div>
    </div>
  );
};
export default Compare;
