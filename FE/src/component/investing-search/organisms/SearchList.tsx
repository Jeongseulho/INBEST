import SearchItem from "../molecules/SearchItem";
import search from "../../../asset/image/search.png";

interface SearchItem {
  logo: string;
  name: string;
  code: string;
  type: string;
}

interface Props {
  searchList: SearchItem[];
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const SearchList = ({ searchList, setCompanyCode }: Props) => {
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
        <p className=" w-28 font-medium text-center">분류</p>
      </div>
      <div>
        {searchList.map((item, index) => (
          <SearchItem
            index={index}
            logo={item.logo}
            name={item.name}
            code={item.code}
            type={item.type}
            setCompanyCode={setCompanyCode}
          />
        ))}
      </div>
    </div>
  );
};
export default SearchList;
