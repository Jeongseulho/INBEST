import SearchItem from "../molecules/SearchItem";
import search from "../../../asset/image/search.png";
import { CompanySearchList } from "../../../type/InvestingStockInfo";
// import Skeleton from "react-loading-skeleton";

interface Props {
  searchList: CompanySearchList;
  isLoading: boolean;
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const SearchList = ({ searchList, setCompanyCode, isLoading }: Props) => {
  return (
    <div className=" shadow-component flex flex-col p-6 w-[50%]">
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
          <>
            {/* {Array(30)
              .fill(null)
              .map((_, index) => (
                <Skeleton key={index} height={32} />
              ))} */}
          </>
        ) : (
          <>
            {searchList.map((item, index) => (
              <SearchItem
                index={index}
                name={item.company_name}
                code={item.company_stock_code}
                type={item.company_stock_type}
                setCompanyCode={setCompanyCode}
              />
            ))}
          </>
        )}
      </div>
    </div>
  );
};
export default SearchList;
