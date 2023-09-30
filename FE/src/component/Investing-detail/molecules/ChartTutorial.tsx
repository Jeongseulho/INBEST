import Chart from "react-apexcharts";
import { CHART_TUTORIAL } from "../../../constant/CHART_TUTORIAL";
import { AiOutlineArrowLeft, AiOutlineArrowRight, AiOutlineClose } from "react-icons/ai";
import green_stick from "../../../asset/image/green_stick.png";
import red_stick from "../../../asset/image/red_stick.png";

interface Props {
  setTutorialStep: React.Dispatch<React.SetStateAction<number>>;
  tutorialStep: number;
}
const ChartTutorial = ({ setTutorialStep, tutorialStep }: Props) => {
  const description = [
    "빨간색으로 표시되는 양봉은 그날 시세가 올랐음을 의미합니다. 즉, 그날의 시작가격보다 높은 가격으로 마감했다는 것입니다.",
    "양봉의 네모의 아랫변은 시작가격을, 윗변은 마감가격을 나타냅니다. 네모가 길수록 그날 시세가 크게 올랐음을 의미합니다.",
    "양봉의 아랫꼬리는 그날의 최저가격을 나타냅니다.",
    "양봉의 윗꼬리는 그날의 최고가격을 나타냅니다.",
    "파란색으로 표시되는 음봉은 그날 시세가 내렸음을 의미합니다. 즉, 그날의 시작가격보다 낮은 가격으로 마감했다는 것입니다.",
    "음봉의 네모는 양봉과 반대입니다, 윗변이 시작가격을, 아랫변이 마감가격을 나타냅니다. 네모가 길수록 그날 시세가 크게 내렸음을 의미합니다.",
    "아랫꼬리는 양봉과 동일하게 그날의 최저가격을 나타냅니다.",
    "윗꼬리도 양보과 동일하게 그날의 최고가격을 나타냅니다.",
  ];
  return (
    <div className=" relative">
      <Chart
        type="candlestick"
        series={[
          {
            data: [
              [1, 100, 200, 50, 150],
              [2, 150, 200, 50, 100],
            ],
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
          grid: {
            show: false,
          },
          yaxis: {
            show: false,
          },
          tooltip: {
            enabled: false,
          },
          xaxis: {},
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
      <div
        className={`${
          tutorialStep === CHART_TUTORIAL.RED_CANDLE && "border-t-[35vh] border-b-[30vh] border-r-[30vw] border-l-[4vw]"
        } 
        ${
          tutorialStep === CHART_TUTORIAL.RED_CANDLE_BODY &&
          "border-t-[48vh] border-b-[40vh] border-r-[30vw] border-l-[4vw]"
        }
        ${
          tutorialStep === CHART_TUTORIAL.RED_CANDLE_DOWN &&
          "border-t-[80vh] border-b-[30vh] border-r-[41vw] border-l-[14vw]"
        }
        ${
          tutorialStep === CHART_TUTORIAL.RED_CANDLE_UP &&
          "border-t-[37vh] border-b-[72vh] border-r-[41vw] border-l-[14vw]"
        }
        ${
          tutorialStep === CHART_TUTORIAL.BLUE_CANDLE &&
          "border-t-[35vh] border-b-[30vh] border-r-[3vw] border-l-[30vw]"
        }
        ${
          tutorialStep === CHART_TUTORIAL.BLUE_CANDLE_BODY &&
          "border-t-[48vh] border-b-[40vh] border-r-[3vw] border-l-[30vw]"
        }
        ${
          tutorialStep === CHART_TUTORIAL.BLUE_CANDLE_DOWN &&
          "border-t-[80vh] border-b-[30vh] border-r-[14vw] border-l-[41vw]"
        }
        ${
          tutorialStep === CHART_TUTORIAL.BLUE_CANDLE_UP &&
          "border-t-[37vh] border-b-[72vh] border-r-[14vw] border-l-[41vw]"
        }
          absolute w-[60vw] h-[121vh]  border-black border-opacity-40 -top-52 -left-12 p-4 transition-all duration-300 ease-in-out`}
      >
        <div className=" absolute -top-[200px] right-6">
          <div className=" bg-white z-20 rounded-md text-black  p-4 min-w-[400px] flex flex-col gap-3 ">
            <div className=" flex items-center justify-between">
              {tutorialStep >= 0 && tutorialStep <= 3 ? (
                <div className=" flex items-center gap-1">
                  <h5>양봉</h5>
                  <img src={red_stick} width={35} />
                </div>
              ) : (
                <div className=" flex items-center gap-1">
                  <img src={green_stick} width={35} />
                  <h5>음봉</h5>
                </div>
              )}
              <AiOutlineClose
                style={{
                  color: "gray",
                  cursor: "pointer",
                  fontSize: "1.2rem",
                }}
                onClick={() => setTutorialStep(-1)}
              />
            </div>
            <p className=" text-sm text-gray-700">{description[tutorialStep]}</p>
            <div className=" flex items-center justify-between">
              <p className="text-sm text-myGray">{tutorialStep + 1} / 8</p>
              <div className=" flex items-center justify-end">
                <div
                  className={`${
                    tutorialStep === 0
                      ? "bg-mainDark bg-opacity-70"
                      : "bg-mainDark hover:bg-mainMoreDark cursor-pointer"
                  } flex items-center  transition-colors duration-300 text-white rounded-l-md p-1 gap-1`}
                  onClick={() => {
                    setTutorialStep((prev) => {
                      if (prev === 0) return 0;
                      else return prev - 1;
                    });
                  }}
                >
                  <AiOutlineArrowLeft
                    style={{
                      color: "white",
                    }}
                  />
                  <div className=" text-sm">이전</div>
                </div>
                <div
                  className={`${
                    tutorialStep === 7
                      ? "bg-mainDark bg-opacity-70"
                      : "bg-mainDark hover:bg-mainMoreDark cursor-pointer"
                  } flex items-center  transition-colors duration-300 text-white rounded-r-md p-1 gap-1`}
                  onClick={() => {
                    setTutorialStep((prev) => {
                      if (prev === 7) return 7;
                      else return prev + 1;
                    });
                  }}
                >
                  <AiOutlineArrowRight
                    style={{
                      color: "white",
                    }}
                  />
                  <div className=" text-sm">다음</div>
                </div>
              </div>
            </div>
          </div>
          <div id="triangle" className=" ms-[370px] w-4 h-4 bg-white "></div>
        </div>
      </div>
    </div>
  );
};
export default ChartTutorial;
