import table from "../../../asset/image/table.png";
interface Props {
  companyCode: string;
}

const FinancialAndNews = ({ companyCode }: Props) => {
  return (
    <div className=" flex flex-col gap-4">
      <div className=" flex items-center gap-2">
        <img src={table} width={40} />
        <h5>재무제표 / 뉴스</h5>
      </div>
      <div className=" flex h-[80vh] gap-4 justify-center">
        <div className=" shadow-component ">{companyCode}재무 제표</div>
        <div className=" shadow-component ">뉴스</div>
      </div>
    </div>
  );
};
export default FinancialAndNews;
