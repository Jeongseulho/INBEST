import { PiSmileySadLight, PiSmileyLight } from "react-icons/pi";
import { truncateContent } from "../../../util/formatContent";

interface Props {
  title: string;
  sentiment_analysis: number;
  imgUrl: string;
  linkUrl: string;
  time: string;
}

const CompanyNews = ({ title, sentiment_analysis, imgUrl, linkUrl, time }: Props) => {
  return (
    <a className=" flex items-center gap-2" href={linkUrl} target="_black">
      <img src={imgUrl} width={60} />
      <div className=" flex flex-col">
        <div className=" flex">
          <p className=" text-sm font-semiBold">{truncateContent(title, 20)}</p>
          {sentiment_analysis >= 50 ? (
            <div className=" bg-main px-1 rounded-full flex items-center bg-opacity-30 gap-1">
              <PiSmileyLight
                style={{
                  color: "#1B4265",
                }}
              />
              <p className=" text-mainMoreDark font-semiBold">{sentiment_analysis.toFixed(2)}</p>
            </div>
          ) : (
            <div className=" bg-lightRed px-1 rounded-full flex items-center bg-opacity-30 gap-1">
              <PiSmileySadLight
                style={{
                  color: "#1B4265",
                }}
              />
              <p className=" font-semiBold text-red-600">{sentiment_analysis.toFixed(2)}</p>
            </div>
          )}
        </div>
        <p className=" text-gray-400 text-sm">{time}</p>
      </div>
    </a>
  );
};
export default CompanyNews;
