import StockComponent from "../../common/StockComponent";
interface Props {
  topProfitStock: {
    stockName: string;
    stockMarketPrice: string;
    totalStockPrice: number;
    stockImgSearchName: string;
  };
}
const TopProfitStock = ({ topProfitStock }: Props) => {
  return (
    <StockComponent
      name={topProfitStock.stockName}
      price={topProfitStock.stockMarketPrice}
      totalStockPrice={topProfitStock.totalStockPrice}
      stockImg={topProfitStock.stockImgSearchName}
    />
  );
};
export default TopProfitStock;
