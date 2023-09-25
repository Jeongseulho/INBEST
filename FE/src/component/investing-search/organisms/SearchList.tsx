import SearchItem from "../molecules/SearchItem";
import search from "../../../asset/image/search.png";

interface SearchItem {
  logo: string;
  name: string;
  code: string;
  price: number;
}

interface Props {
  searchList: SearchItem[];
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const SearchList = ({ searchList, setCompanyCode }: Props) => {
  console.log(setCompanyCode);
  return (
    <div className=" shadow-component flex flex-col p-6 w-[50%]">
      <div className=" mb-4 flex items-center gap-2">
        <img src={search} width={30} />
        <h3>검색 결과</h3>
      </div>
      <div className=" flex justify-around border-y-2 items-center">
        <p className=" w-2 font-medium text-center">#</p>
        <p className=" w-28 font-medium text-center">종목 명</p>
        <p className=" w-28 font-medium text-center">종목 코드</p>
        <p className=" w-28 font-medium text-center">가격</p>
      </div>
      <div>
        {searchList.map((item, index) => (
          <SearchItem index={index} logo={item.logo} name={item.name} code={item.code} price={item.price} />
        ))}
      </div>
    </div>
  );
};
export default SearchList;
