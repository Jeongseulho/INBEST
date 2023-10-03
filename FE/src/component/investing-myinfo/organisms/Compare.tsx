import compare from "../../../asset/image/compare.png";

const Compare = () => {
  return (
    <div className=" shadow-component col-span-7 row-span-1 p-4 flex flex-col gap-4">
      <div className="  flex items-center gap-2">
        <img src={compare} width={40} />
        <h5>금융 상품 비교</h5>
      </div>
    </div>
  );
};
export default Compare;
