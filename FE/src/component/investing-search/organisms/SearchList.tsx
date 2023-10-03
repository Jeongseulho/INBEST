import SearchItem from "../molecules/SearchItem";
import search from "../../../asset/image/search.png";
import { CompanySearchList } from "../../../type/InvestingStockInfo";
import spinner from "../../../asset/image/spinner.svg";
import { CompanyInfo } from "../../../type/InvestingCompanyDetail";

interface Props {
  searchList: CompanySearchList;
  isLoading: boolean;
  setCompanyInfo: React.Dispatch<React.SetStateAction<CompanyInfo>>;
}

const SearchList = ({ searchList, setCompanyInfo, isLoading }: Props) => {
  return (
    <div className=" shadow-component flex flex-col p-6 w-[60%]">
      <div className=" mb-4 flex items-center gap-2">
        <img src={search} width={30} />
        <h3>검색 결과</h3>
      </div>
      <div className=" flex justify-center gap-20 border-y-2 items-center">
        <p className=" w-2 font-medium text-center">#</p>
        <p className=" w-28 font-medium text-center">종목 명</p>
        <p className=" w-28 font-medium text-center">종목 코드</p>
        <p className=" w-16 font-medium text-center">분류</p>
      </div>
      <div>
        {isLoading ? (
          <img src={spinner} className=" mx-auto" />
        ) : (
          <>
            {searchList.map((item, index) => (
              <SearchItem
                key={index}
                index={index}
                name={item.company_name}
                code={item.company_stock_code}
                type={item.company_stock_type}
                setCompanyInfo={setCompanyInfo}
                companyImg={item.company_image_url}
              />
            ))}
          </>
        )}
      </div>
    </div>
  );
};
export default SearchList;
