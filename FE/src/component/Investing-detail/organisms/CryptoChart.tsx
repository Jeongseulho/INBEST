import { useQuery } from "react-query";
import { getCryptoChart } from "../../../api/investingStockChart";
import Chart from "react-apexcharts";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";
import { useState } from "react";
import candle from "../../../asset/image/candle.png";
import spinner from "../../../asset/image/spinner.svg";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";
import help from "../../../asset/image/help.png";
import ChartTutorial from "../molecules/ChartTutorial";
import notResult from "../../../asset/image/notResult.png";

interface Props {
  companyInfo: CompanyInfo;
}

const CryptoChart = ({ companyInfo }: Props) => {
  const [timeType, setTimeType] = useState<"1m" | "1h" | "24h">("1m");
  const [tutorialStep, setTutorialStep] = useState(-1);
  const { data, isLoading } = useQuery(
    ["cryptoStockChart", companyInfo, timeType],
    () => getCryptoChart(companyInfo.code, timeType),
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
                timeType === "1m" && "bg-lightPrimary bg-opacity-40"
              } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
              onClick={() => {
                setTimeType("1m");
              }}
            >
              <p>최근 1일</p>
            </button>
            <button
              className={` ${
                timeType === "1h" && "bg-lightPrimary bg-opacity-40"
              } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
              onClick={() => {
                setTimeType("1h");
              }}
            >
              <p>최근 1달</p>
            </button>
            <button
              className={` ${
                timeType === "24h" && "bg-lightPrimary bg-opacity-40"
              } px-4 min-w-[5rem] rounded-full border-2 border-gray-300 py-1`}
              onClick={() => {
                setTimeType("24h");
              }}
            >
              <p>최근 3달</p>
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
            {data === undefined ? (
              <div className="flex justify-center items-center w-[450px] h-full">
                <div className="text-center">
                  <img src={notResult} alt="404" className="w-56 h-56" />
                  데이터 준비중입니다!
                </div>
              </div>
            ) : tutorialStep >= 0 ? (
              <ChartTutorial setTutorialStep={setTutorialStep} tutorialStep={tutorialStep} />
            ) : (
              <Chart
                type="candlestick"
                series={[
                  {
                    data: data.data
                      .slice(-100)
                      .sort(
                        (
                          a: [number, string, string, string, string, string],
                          b: [number, string, string, string, string, string]
                        ) => {
                          return a[0] - b[0];
                        }
                      )
                      .map((item: [number, string, string, string, string, string]) => {
                        return {
                          x: new Date(item[0]),
                          y: [Number(item[1]), Number(item[3]), Number(item[4]), Number(item[2])],
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
export default CryptoChart;
