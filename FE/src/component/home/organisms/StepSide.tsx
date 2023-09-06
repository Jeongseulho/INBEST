import StepPagination from "../molecules/StepPagination";
import StepPaginationDesc from "../molecules/StepPaginationDesc";

interface StepSideProps {
  curStep: number;
}

const StepSide = ({ curStep }: StepSideProps) => {
  const title = ["Title0", "Title1", "Title2", "Title3", "Title4"];

  return (
    <div className="fixed top-1/3 z-50 bg-transparent">
      <StepPagination curStep={curStep} />
      <StepPaginationDesc title={title[curStep]} />
    </div>
  );
};
export default StepSide;
