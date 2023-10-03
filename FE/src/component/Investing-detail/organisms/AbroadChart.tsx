import { useQuery } from "react-query";
import { getAbroadStockChart } from "../../../api/investingStockChart";
import Chart from "react-apexcharts";
import { formatNumberToDollar } from "../../../util/formatMoney";
import { useState } from "react";
import candle from "../../../asset/image/candle.png";
import spinner from "../../../asset/image/spinner.svg";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import help from "../../../asset/image/help.png";
import ChartTutorial from "../molecules/ChartTutorial";

interface Props {
  companyInfo: CompanyInfo;
}

const AbroadChart = ({ companyInfo }: Props) => {
  const [daysType, setDaysType] = useState(0);
  const [tutorialStep, setTutorialStep] = useState(-1);
  const { data, isLoading } = useQuery(
    ["abroadStockChart", companyInfo, daysType],
    () => getAbroadStockChart(companyInfo.code, daysType),
    {
      retry: 3,
    }
  );

  return (
    <div className=" flex flex-col gap-4">
      <div className=" flex items-center gap-2 ">
        <img src={candle} width={40} />
        <h5>주식 시세 캔들 차트</h5>
      </div>
      <div className=" shadow-component p-4 min-w-[800px] min-h-[600px]">
        <div className=" flex items-center justify-between">
          <div className=" flex gap-2">
            <button
              className={` ${
                daysType === 0 && "bg-lightPrimary bg-opacity-40"
              } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
              onClick={() => {
                setDaysType(0);
              }}
            >
              <p>최근 5달</p>
            </button>
            <button
              className={` ${
                daysType === 1 && "bg-lightPrimary bg-opacity-40"
              } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
              onClick={() => {
                setDaysType(1);
              }}
            >
              <p>최근 2년</p>
            </button>
            <button
              className={` ${
                daysType === 2 && "bg-lightPrimary bg-opacity-40"
              } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
              onClick={() => {
                setDaysType(2);
              }}
            >
              <p>최근 8년</p>
            </button>
          </div>
          <p
            className=" text-gray-600 hover:text-mainMoreDark transition-colors duration-300 me-10 cursor-pointer"
            onClick={() => setTutorialStep(0)}
          >
            <img src={help} width={40} className=" mx-auto" />
            캔들 차트는 어떻게 보나요?
          </p>
        </div>

        {isLoading ? (
          <img src={spinner} className="mt-20 mx-auto" />
        ) : (
          <>
            {data === undefined || Object.keys(data.output2[0]).length === 0 ? (
              <div>데이터가 없습니다.</div>
            ) : tutorialStep >= 0 ? (
              <ChartTutorial setTutorialStep={setTutorialStep} tutorialStep={tutorialStep} />
            ) : (
              <Chart
                type="candlestick"
                series={[
                  {
                    data: data.output2
                      .sort((a: { xymd: string }, b: { xymd: string }) => {
                        return Number(a.xymd) - Number(b.xymd);
                      })
                      .map((item: { xymd: string; open: string; high: string; low: string; clos: string }) => {
                        return {
                          x: new Date(
                            item.xymd.slice(0, 4) + "-" + item.xymd.slice(4, 6) + "-" + item.xymd.slice(6, 8)
                          ),
                          y: [Number(item.open), Number(item.high), Number(item.low), Number(item.clos)],
                        };
                      }),
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
                        return formatNumberToDollar(val);
                      },
                    },
                  },
                  xaxis: {
                    type: "category",
                    labels: {
                      formatter: function (inputDateStr: string): string {
                        const inputDate = new Date(inputDateStr);
                        const day = inputDate.getDate();
                        const formattedDate = `${inputDate.getFullYear()}년 ${inputDate.getMonth() + 1}월 ${day}일`;
                        return formattedDate;
                      },
                    },
                    tickAmount: 10,
                  },
                  tooltip: {
                    custom: function ({ seriesIndex, dataPointIndex, w }) {
                      const o = w.globals.seriesCandleO[seriesIndex][dataPointIndex];
                      const h = w.globals.seriesCandleH[seriesIndex][dataPointIndex];
                      const l = w.globals.seriesCandleL[seriesIndex][dataPointIndex];
                      const c = w.globals.seriesCandleC[seriesIndex][dataPointIndex];
                      return (
                        // eslint-disable-next-line quotes
                        '<div className="apexcharts-tooltip-candlestick">' +
                        // eslint-disable-next-line quotes
                        '<div>시가: <span class="value">' +
                        formatNumberToDollar(o) +
                        "</span></div>" +
                        // eslint-disable-next-line quotes
                        '<div>최고가: <span class="value">' +
                        formatNumberToDollar(h) +
                        "</span></div>" +
                        // eslint-disable-next-line quotes
                        '<div>최저가: <span class="value">' +
                        formatNumberToDollar(l) +
                        "</span></div>" +
                        // eslint-disable-next-line quotes
                        '<div>종가: <span class="value">' +
                        formatNumberToDollar(c) +
                        "</span></div>" +
                        "</div>"
                      );
                    },
                  },
                  plotOptions: {
                    candlestick: {
                      colors: {
                        downward: "#3C90EB",
                        upward: "#df4646",
                      },
                    },
                  },
                }}
              />
            )}
          </>
        )}
      </div>
    </div>
  );
};
export default AbroadChart;
