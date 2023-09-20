import SearchItem from "../molecules/SearchItem";
import stock_price from "../../../asset/image/stock_price.png";
import company from "../../../asset/image/company.png";
import barcode from "../../../asset/image/barcode.png";

interface SearchItem {
  logo: string;
  name: string;
  code: string;
  price: number;
}

interface Props {
  searchList: SearchItem[];
}

const SearchList = ({ searchList }: Props) => {
  return (
    <div className=" shadow-component flex flex-col p-6 gap-4 w-[50%]">
      <h3>검색 결과</h3>
      <div className=" flex justify-around border-t-4 pt-2 items-center">
        <p className=" w-2">#</p>
        <div className=" flex items-center gap-1 w-28 justify-center">
          <img src={company} width={25} />
          <p>종목 명</p>
        </div>
        <div className=" flex items-center gap-1 w-28 justify-center">
          <img src={barcode} width={35} />
          <p>종목 코드</p>
        </div>
        <div className=" flex items-center gap-1 w-28 justify-center">
          <img src={stock_price} width={35} />
          <p>가격</p>
        </div>
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
