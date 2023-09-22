import { AiOutlineArrowUp, AiOutlineArrowDown } from "react-icons/ai";
import { formatComma } from "../../../util/formatComma";
interface Props {
  img: string;
  title: string;
  number: number;
  change: number;
}

const TotalInfoItem = ({ img, title, number, change }: Props) => {
  return (
    <div className=" flex gap-6">
      <img src={img} />
      <div className=" flex flex-col">
        <p className=" text-myGray">{title}</p>
        <p className=" font-bold text-3xl">{formatComma(number)}</p>
        {change >= 0 ? (
          <div className=" flex items-center">
            <AiOutlineArrowUp
              style={{
                color: "#00C851",
              }}
            />
            <p className=" font-semiBold text-green-500">{change}</p>
          </div>
        ) : (
          <div className=" flex items-center">
            <AiOutlineArrowDown
              style={{
                color: "#ff4444",
              }}
            />
            <p className=" font-semiBold text-red-500">{change}</p>
          </div>
        )}
      </div>
    </div>
  );
};
export default TotalInfoItem;
