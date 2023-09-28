import { useQuery } from "react-query";
import { getKorStockChart } from "../../../api/investingStockChart";
import Chart from "react-apexcharts";

interface Props {
  companyCode: string;
}

const StockChart = ({ companyCode }: Props) => {
  const { data, isLoading } = useQuery(["stockChart", companyCode], () => getKorStockChart(companyCode, 14), {
    staleTime: 0,
    cacheTime: 0,
  });
  console.log(data);
  return (
    <div>
      {isLoading ? (
        <>
          <div>loading</div>
        </>
      ) : (
        <>
          <Chart
            type="candlestick"
            series={[
              {
                data: data?.output2.map(
                  (item: {
                    stck_bsop_date: string;
                    stck_oprc: string;
                    stck_hgpr: string;
                    stck_lwpr: string;
                    stck_clpr: string;
                  }) => {
                    return {
                      x: new Date(
                        item.stck_bsop_date.slice(0, 4) +
                          "-" +
                          item.stck_bsop_date.slice(4, 6) +
                          "-" +
                          item.stck_bsop_date.slice(6, 8)
                      ),
                      y: [
                        Number(item.stck_oprc),
                        Number(item.stck_hgpr),
                        Number(item.stck_lwpr),
                        Number(item.stck_clpr),
                      ],
                    };
                  }
                ),
              },
            ]}
            options={{
              theme: {
                mode: "light",
              },
              chart: {
                type: "candlestick",
              },
              stroke: {
                curve: "smooth",
              },
              yaxis: {
                show: true,
              },
              xaxis: {
                type: "datetime",
                tickPlacement: "on",
              },
              plotOptions: {
                candlestick: {
                  colors: {
                    upward: "#3C90EB",
                    downward: "#df4646",
                  },
                },
              },
            }}
          />
        </>
      )}
    </div>
  );
};
export default StockChart;
