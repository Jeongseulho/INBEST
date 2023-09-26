import News from "../../investing-news/molecules/News";
import { useQuery } from "react-query";
import { getMainNews } from "../../../api/investingNews";
import news from "../../../asset/image/news.png";
import NewsSkeleton from "../molecules/NewsSkeleton";

const MainNews = () => {
  const { data, isLoading } = useQuery(["mainNews"], getMainNews);
  return (
    <div className=" shadow-component col-span-1 row-span-2 p-4">
      <div className=" flex items-center mb-4 gap-4">
        <img src={news} width={40} />
        <h3>주요 뉴스</h3>
      </div>
      {isLoading ? (
        <div className=" flex flex-col gap-4">
          <NewsSkeleton />
          <NewsSkeleton />
          <NewsSkeleton />
          <NewsSkeleton />
          <NewsSkeleton />
        </div>
      ) : (
        <div className=" flex flex-col gap-4 ">
          {data?.map((news, idx) => (
            <News key={idx} title={news.title} content={news.content} imgUrl={news.image_url} linkUrl={news.link_url} />
          ))}
        </div>
      )}
    </div>
  );
};
export default MainNews;
