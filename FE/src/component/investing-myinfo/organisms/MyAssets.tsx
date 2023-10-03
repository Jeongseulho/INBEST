import assets from "../../../asset/image/assets.png";
import IncreaseIcon from "../../common/IncreaseIcon";
import DecreaseIcon from "../../common/DecreaseIcon";
import { formatNumberToWon } from "../../../util/formatMoney";
import spinner from "../../../asset/image/spinner.svg";
import Chart from "react-apexcharts";
import { formatNumberToKoreanWon } from "../../../util/formatMoney";
import { MyAsset } from "../../../type/InvestingMyInfo";
interface Props {
  data: MyAsset | undefined;
  isLoading: boolean;
}
const MyAssets = ({ data, isLoading }: Props) => {
  return (
    <div className=" shadow-component col-span-5 p-4 flex flex-col gap-10">
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
          <img src={spinner} width={120} className=" mx-auto mt-32" />
        </div>
      ) : (
        <>
          {data && (
            <div className=" flex items-center justify-center">
              <h5 className=" font-bold">{formatNumberToWon(data[data.length - 1].asset)}</h5>
              {data.length >= 2 &&
                (data[data.length - 1].asset > data[data.length - 2].asset ? (
                  <IncreaseIcon
                    number={
                      ((data[data.length - 1].asset - data[data.length - 2].asset) / data[data.length - 2].asset) * 100
                    }
                  />
                ) : (
                  <DecreaseIcon
                    number={
                      ((data[data.length - 1].asset - data[data.length - 2].asset) / data[data.length - 2].asset) * 100
                    }
                  />
                ))}
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
