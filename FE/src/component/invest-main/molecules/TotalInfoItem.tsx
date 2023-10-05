import { AiOutlineArrowUp, AiOutlineArrowDown } from "react-icons/ai";
import { formatComma } from "../../../util/formatComma";
interface Props {
  title: string;
  number: number | undefined;
  fluctuation: number | undefined;
  scale: string;
}

const TotalInfoItem = ({ title, number, fluctuation, scale }: Props) => {
  return (
    <div className=" flex flex-col">
      <p className=" text-myGray">{title}</p>
      <div className=" flex items-center gap-2">
        <p className=" font-bold text-3xl">{number && formatComma(number) + scale}</p>
        {fluctuation !== undefined && fluctuation >= 0 ? (
          <div className=" flex items-center">
            <AiOutlineArrowUp
              style={{
                color: "#00C851",
              }}
            />
            <p className=" font-semiBold text-green-500">{fluctuation}</p>
          </div>
        ) : (
          <div className=" flex items-center">
            <AiOutlineArrowDown
              style={{
                color: "#ff4444",
              }}
            />
            <p className=" font-semiBold text-red-500">{fluctuation}</p>
          </div>
        )}
      </div>
    </div>
  );
};
export default TotalInfoItem;
