import { formatNumberToWon } from "../../../util/formatMoney";

interface Props {
  logo: string;
  name: string;
  code: string;
  price: number;
  index: number;
}

const SearchItem = ({ logo, name, code, price, index }: Props) => {
  return (
    <div className=" flex justify-around border-t-2 items-center py-2 ">
      <p className=" w-2 text-center">{index}</p>
      <div className=" flex w-28 justify-center">
        <img src={logo} />
        <p>{name}</p>
      </div>
      <p className=" w-28 text-center">{code}</p>
      <p className=" w-28 text-center">{formatNumberToWon(price)}</p>
    </div>
  );
};
export default SearchItem;
