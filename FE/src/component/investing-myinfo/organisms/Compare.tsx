import compare from "../../../asset/image/compare.png";
import Chart from "react-apexcharts";
import { useQueries } from "react-query";
import { getFund, getSaving, getSaving2, getDeposit, getDeposit2 } from "../../../api/investingCompany";
interface Props {
  myProfit: number;
}
const Compare = ({ myProfit }: Props) => {
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
  return (
    <div className=" shadow-component col-span-7 p-4 flex flex-col gap-4">
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
                  data: [Number(myProfit.toFixed(2))].concat(results.map((res) => res.data!.intr_rate2)),
                },
              ]}
              options={{
                plotOptions: {
                  bar: {
                    borderRadius: 10,
                    dataLabels: {
                      position: "top", // top, center, bottom
                    },
                    colors: {
                      ranges: [
                        {
                          from: -100,
                          to: 0,
                          color: "#627bc5",
                        },
                        {
                          from: 1,
                          to: 100,
                          color: "#ffb488",
                        },
                      ],
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
                  categories: ["내 모의투자 수익률"].concat(results.map((res) => res.data!.fin_prdt_nm)),
                  position: "bottom",
                  labels: {
                    style: {
                      fontSize: "10px",
                      fontWeight: 500,
                    },
                  },
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
