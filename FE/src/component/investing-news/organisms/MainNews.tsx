import News from "../../investing-news/molecules/News";
import { useMainNews } from "./useMainNews";

const MainNews = () => {
  const { data, isLoading, isError } = useMainNews();
  return (
    <div className=" shadow-component col-span-1 row-span-2 p-4">
      <h3 className=" mb-6">주요 뉴스</h3>
      {isLoading ? (
        <p>로딩중...</p>
      ) : isError ? (
        <p>에러가 발생했습니다.</p>
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
