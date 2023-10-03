import StockComponent from "../../common/StockComponent";
interface Props {
  topLossStock: {
    stockName: string;
    stockMarketPrice: string;
    totalStockPrice: number;
    stockImgSearchName: string;
  };
}
const TopLossStock = ({ topLossStock }: Props) => {
  return (
    <StockComponent
      name={topLossStock.stockName}
      price={topLossStock.stockMarketPrice}
      totalStockPrice={topLossStock.totalStockPrice}
      stockImg={topLossStock.stockImgSearchName}
    />
  );
};
export default TopLossStock;
