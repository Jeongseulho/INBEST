interface Props {
  logo: string;
  name: string;
  code: string;
  type: string;
  index: number;
  setCompanyCode: React.Dispatch<React.SetStateAction<string>>;
}

const SearchItem = ({ logo, name, code, type, index, setCompanyCode }: Props) => {
  return (
    <div
      onClick={() => setCompanyCode(code)}
      className=" flex justify-around border-b-2 items-center py-2 cursor-pointer hover:bg-gray-400 hover:bg-opacity-20 transition-colors duration-300"
    >
      <p className=" w-2 text-center">{index}</p>
      <div className=" flex w-28 justify-center">
        <img src={logo} />
        <p>{name}</p>
      </div>
      <p className=" w-28 text-center">{code}</p>
      <p className=" w-28 text-center">{type}</p>
    </div>
  );
};
export default SearchItem;
