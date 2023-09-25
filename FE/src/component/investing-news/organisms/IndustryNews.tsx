// import { useIndustryNews } from "./useIndustryNews";
import news from "../../../asset/image/news.png";
import IndustryNewsItem from "../molecules/IndustryNewsItem";
import IndustryTag from "../molecules/IndustryTag";
import { useIndustryNews } from "./useIndustryNews";
import { INDUSTRY_NEWS_TAB } from "../../../constant/INDUSTRY_NEWS_TAB";

const IndustryNews = () => {
  // const { data, isLoading, isError } = useIndustryNews("heavy-chemistry");
  const { data, isLoading, isError } = {
    data: [{ title: "title", content: "content", image_url: "imgUrl", link_url: "linkUrl" }],
    isLoading: false,
    isError: false,
  };

  const { industryTab, setIndustryTab } = useIndustryNews();

  return (
    <div className=" shadow-component col-span-2 row-span-1 p-4">
      <div className=" flex items-center mb-4 gap-4">
        <img src={news} width={40} />
        <h3>산업별 뉴스</h3>
      </div>
      <div className=" flex gap-1">
        <IndustryTag
          text="전기/전자"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.ELECTRONICS}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="중화학"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.HEAVY_CHEMISTRY}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="자동차"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.AUTOMOBILE}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="건설"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.CONSTRUCTION}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="에너지/자원"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.ENERGY_RESOURCE}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="IT/과학"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.TECHNOLOGY_SCIENCE}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag text="게임" curTab={industryTab} myTab={INDUSTRY_NEWS_TAB.GAME} setIndustryTab={setIndustryTab} />
        <IndustryTag
          text="유통/서비스"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.DISTRIBUTION_SERVICE_INDUSTRY}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="중기/벤처"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.VENTURE_BUSINESS}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="바이오헬스"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.BIO_INDUSTRY_HEALTH}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="농림축산"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.AGRICULTURE}
          setIndustryTab={setIndustryTab}
        />
        <IndustryTag
          text="해양수산"
          curTab={industryTab}
          myTab={INDUSTRY_NEWS_TAB.OCEAN_FISHERY}
          setIndustryTab={setIndustryTab}
        />
      </div>
      {isLoading ? (
        <p>로딩중...</p>
      ) : isError ? (
        <p>에러가 발생했습니다.</p>
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
