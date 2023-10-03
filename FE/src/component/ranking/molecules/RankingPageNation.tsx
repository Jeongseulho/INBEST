import { useRankingPageNation } from "./useRankingPageNation";
import { useTierChart } from "./useTierChart";
import ReactPaginate from "react-paginate";

const RankingPageNation = () => {
  const { pages } = useTierChart();
  const { handlePageClick } = useRankingPageNation();
  return (
    <div className="flex justify-center ">
      <ReactPaginate
        breakLabel={null}
        nextLabel=">"
        onPageChange={handlePageClick}
        pageRangeDisplayed={pages >= 5 ? 5 : pages}
        marginPagesDisplayed={0}
        pageCount={pages}
        previousLabel="<"
        renderOnZeroPageCount={null}
        pageClassName="border border-black w-full h-full flex justify-center items-center border-opacity-30"
        previousClassName="border border-black w-full h-full flex justify-center items-center  rounded-s-lg border-opacity-30 px-2"
        nextClassName="border border-black w-full h-full flex justify-center items-center  rounded-e-lg border-opacity-30 px-2"
        className="mb-10 flex justify-between items-center rounded-xl h-10 min-w-[16rem] bg-white mt-10 "
        pageLinkClassName="w-full h-full flex justify-center items-center"
        previousLinkClassName="w-full h-full flex justify-center items-center"
        nextLinkClassName="w-full h-full flex justify-center items-center"
        activeClassName="bg-primary text-white border-0"
      />
    </div>
  );
};
export default RankingPageNation;
