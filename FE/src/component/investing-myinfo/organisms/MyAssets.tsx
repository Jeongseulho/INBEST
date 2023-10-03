import assets from "../../../asset/image/assets.png";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";
import { getMyAsset } from "../../../api/investingMyInfo";
import { useQuery } from "react-query";
import { formatNumberToWon } from "../../../util/formatMoney";
import spinner from "../../../asset/image/spinner.svg";
import { useParams } from "react-router-dom";
import Chart from "react-apexcharts";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";

const MyAssets = () => {
  const { simulationSeq } = useParams();
  const { data, isLoading } = useQuery(["myAsset", simulationSeq], () => getMyAsset(simulationSeq));
  return (
    <div className=" shadow-component col-span-5 row-span-2 p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <img src={assets} width={40} />
        <h5>내 자산 변화</h5>
      </div>
      {isLoading ? (
        <div
          style={{
            height: 285,
          }}
        >
          <img src={spinner} width={120} className=" mx-auto" />
        </div>
      ) : (
        <>
          {data && (
            <div className=" flex items-center justify-center">
              <h5 className=" font-bold">{formatNumberToWon(data[0].asset)}</h5>
              {data.length >= 2 && data[0].asset > data[1].asset ? (
                <IncreaseIcon number={((data[0].asset - data[1].asset) / data[1].asset) * 100} />
              ) : (
                <DecreaseIcon number={((data[1].asset - data[0].asset) / data[1].asset) * 100} />
              )}
            </div>
          )}
          <div>
            {data && (
              <Chart
                type="area"
                series={[
                  {
                    name: "내 자산",
                    data: data.map((item) => item.asset),
                  },
                ]}
                options={{
                  dataLabels: {
                    enabled: false,
                  },
                  chart: {
                    toolbar: {
                      show: false,
                    },
                  },
                  yaxis: {
                    show: true,
                    tickAmount: 2,
                    labels: {
                      formatter: function (val: number) {
                        return formatNumberToKoreanWon(val);
                      },
                    },
                  },
                  labels: data.map((item) => item.createdTime),
                  xaxis: {
                    type: "category",
                    labels: {
                      formatter: function (inputDateStr: string): string {
                        const inputDate = new Date(inputDateStr);
                        const day = inputDate.getDate();
                        const formattedDate = `${inputDate.getMonth() + 1}월 ${day}일`;
                        return formattedDate;
                      },
                    },
                    tickAmount: 10,
                  },
                  fill: {
                    type: "gradient",
                    gradient: {
                      shadeIntensity: 1,
                      opacityFrom: 0.7,
                      opacityTo: 0.9,
                      stops: [0, 90, 100],
                    },
                  },
                }}
              />
            )}
          </div>
        </>
      )}
    </div>
  );
};
export default MyAssets;
