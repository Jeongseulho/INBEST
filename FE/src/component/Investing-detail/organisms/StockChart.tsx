import { useQuery } from "react-query";
import { getKorStockChart } from "../../../api/investingStockChart";
import Chart from "react-apexcharts";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";
import { useState } from "react";

interface Props {
  companyCode: string;
}

const StockChart = ({ companyCode }: Props) => {
  const [chartPeriod, setChartPeriod] = useState(30);
  const { data } = useQuery(
    ["stockChart", companyCode, chartPeriod],
    () => getKorStockChart(companyCode, chartPeriod),
    {
      staleTime: 0,
      cacheTime: 0,
    }
  );
  return (
    <div>
      <div className=" flex gap-2">
        <button
          className={` ${
            chartPeriod === 30 && "bg-lightPrimary bg-opacity-40"
          } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
          onClick={() => {
            setChartPeriod(30);
          }}
        >
          <p>최근 1달</p>
        </button>
        <button
          className={` ${
            chartPeriod === 60 && "bg-lightPrimary bg-opacity-40"
          } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
          onClick={() => {
            setChartPeriod(60);
          }}
        >
          <p>최근 2달</p>
        </button>
        <button
          className={` ${
            chartPeriod === 90 && "bg-lightPrimary bg-opacity-40"
          } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
          onClick={() => {
            setChartPeriod(90);
          }}
        >
          <p>최근 3달</p>
        </button>
      </div>

      {data && (
        <div>
          <Chart
            type="candlestick"
            series={[
              {
                data: data.output2
                  .reverse()
                  .map(
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
                toolbar: {
                  show: false,
                },
              },
              stroke: {
                curve: "smooth",
              },
              yaxis: {
                show: true,
                labels: {
                  formatter: function (val: number) {
                    return formatNumberToKoreanWon(val);
                  },
                },
              },
              xaxis: {
                type: "category",
                labels: {
                  formatter: function (inputDateStr: string): string {
                    const inputDate = new Date(inputDateStr);
                    const day = inputDate.getDate();
                    const formattedDate = `${inputDate.getMonth() + 1}.${day}`;
                    return formattedDate;
                  },
                },
              },
              tooltip: {
                custom: function ({ seriesIndex, dataPointIndex, w }) {
                  console.log(seriesIndex, dataPointIndex, w);
                  const o = w.globals.seriesCandleO[seriesIndex][dataPointIndex];
                  const h = w.globals.seriesCandleH[seriesIndex][dataPointIndex];
                  const l = w.globals.seriesCandleL[seriesIndex][dataPointIndex];
                  const c = w.globals.seriesCandleC[seriesIndex][dataPointIndex];
                  return (
                    // eslint-disable-next-line quotes
                    '<div className="apexcharts-tooltip-candlestick">' +
                    // eslint-disable-next-line quotes
                    '<div>시가: <span class="value">' +
                    formatNumberToKoreanWon(o) +
                    "</span></div>" +
                    // eslint-disable-next-line quotes
                    '<div>최고가: <span class="value">' +
                    formatNumberToKoreanWon(h) +
                    "</span></div>" +
                    // eslint-disable-next-line quotes
                    '<div>최저가: <span class="value">' +
                    formatNumberToKoreanWon(l) +
                    "</span></div>" +
                    // eslint-disable-next-line quotes
                    '<div>종가: <span class="value">' +
                    formatNumberToKoreanWon(c) +
                    "</span></div>" +
                    "</div>"
                  );
                },
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
        </div>
      )}
    </div>
  );
};
export default StockChart;
