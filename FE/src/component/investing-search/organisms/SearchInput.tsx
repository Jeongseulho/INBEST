const SearchInput = () => {
  return (
    <span className=" shadow-component p-4 h-32">
      <label htmlFor="nickname-search" className="text-left ">
        <p className="my-2 font-regular">국내 및 해외 주식, 가상자산 통합 검색</p>
        <input
          id="nickname-search"
          type="text"
          placeholder="예) 삼성전자"
          className="  border-gray-400 border p-2 rounded-md bg-main bg-opacity-10"
        />
      </label>
    </span>
  );
};
export default SearchInput;
