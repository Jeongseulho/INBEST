import { BsSearch } from "react-icons/bs";

interface Props {
  onChangeSearchCompanyKeyword: (e: React.ChangeEvent<HTMLInputElement>) => void;
  searchCompanyKeyword: string;
}
const SearchInput = ({ onChangeSearchCompanyKeyword, searchCompanyKeyword }: Props) => {
  return (
    <span className=" shadow-component p-4 h-32">
      <p className="my-2 font-regular">국내 및 해외 주식, 가상자산 통합 검색</p>
      <div className=" relative">
        <input
          type="text"
          className="px-3 border-gray-400 border bg-main bg-opacity-10 h-10 w-full rounded-md pe-8"
          placeholder="검색어를 입력하세요"
          onChange={onChangeSearchCompanyKeyword}
          value={searchCompanyKeyword}
        />
        <div>
          <BsSearch
            style={{
              position: "absolute",
              top: "50%",
              transform: "translate(-50%, -50%)",
              right: "0",
              cursor: "pointer",
            }}
          />
        </div>
      </div>
    </span>
  );
};
export default SearchInput;
