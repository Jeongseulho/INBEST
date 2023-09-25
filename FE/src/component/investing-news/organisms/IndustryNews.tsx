import news from "../../../asset/image/news.png";
import IndustryNewsItem from "../molecules/IndustryNewsItem";
import IndustryTag from "../molecules/IndustryTag";
import { useIndustryNews } from "./useIndustryNews";
import { INDUSTRY_NEWS_TAB } from "../../../constant/INDUSTRY_NEWS_TAB";
import Skeleton from "react-loading-skeleton";

const IndustryNews = () => {
  const { data, isLoading, curIndustryTab, setCurIndustryTab } = useIndustryNews();

  return (
    <div className=" shadow-component col-span-2 row-span-1 p-4">
      <div className=" flex items-center mb-4 gap-4">
        <img src={news} width={40} />
        <h3>산업별 뉴스</h3>
      </div>
      <div className=" flex gap-1 mb-4">
        {Object.keys(INDUSTRY_NEWS_TAB).map((myTab) => (
          <IndustryTag
            key={myTab}
            curIndustryTab={curIndustryTab}
            myTab={myTab}
            setCurIndustryTab={setCurIndustryTab}
          />
        ))}
      </div>
      {isLoading ? (
        <Skeleton count={5} />
      ) : (
        <div className=" grid grid-cols-2 ">
          {data?.map((news, idx) => (
            <IndustryNewsItem
              key={idx}
              title={news.title}
              content={news.content}
              imgUrl={news.image_url}
              linkUrl={news.link_url}
            />
          ))}
        </div>
      )}
    </div>
  );
};
export default IndustryNews;
