import { PiSmileySadLight, PiSmileyLight } from "react-icons/pi";

interface Props {
  title: string;
  sentiment_analysis: number;
  imgUrl: string;
  linkUrl: string;
  time: string;
}

const CompanyNews = ({ title, sentiment_analysis, imgUrl, linkUrl, time }: Props) => {
  return (
    <a className=" flex items-center" href={linkUrl} target="_black">
      <img src={imgUrl} />
      <div className=" flex flex-col">
        <div className=" flex">
          <p>{title}</p>
          {sentiment_analysis >= 50 ? (
            <div className=" bg-main px-1 rounded-full flex items-center bg-opacity-30 gap-1">
              <PiSmileyLight
                style={{
                  color: "#1B4265",
                }}
              />
              <p className=" text-mainMoreDark font-semiBold">{sentiment_analysis}</p>
            </div>
          ) : (
            <div className=" bg-lightRed px-1 rounded-full flex items-center bg-opacity-30 gap-1">
              <PiSmileySadLight
                style={{
                  color: "#1B4265",
                }}
              />
              <p className=" font-semiBold text-red-600">{sentiment_analysis}</p>
            </div>
          )}
        </div>
        <p className=" text-gray-400 text-sm">{time}</p>
      </div>
    </a>
  );
};
export default CompanyNews;
