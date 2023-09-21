import News from "../../investing-news/molecules/News";
import CompanyNews from "../../Investing-detail/molecules/CompanyNews";

const MainNews = () => {
  // const { data, isLoading } = useMainNews();
  return (
    <div className=" shadow-component col-span-1 row-span-2 p-4">
      <h3>주요 뉴스</h3>
      <News title="제목" content="내용......" imgUrl="" linkUrl="" />
      <CompanyNews title="제목1" imgUrl="" linkUrl="" time="5분전" sentiment_analysis={60} />
    </div>
  );
};
export default MainNews;
