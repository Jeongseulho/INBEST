import StepPagination from "../molecules/StepPagination";
import StepDesc from "../molecules/StepDesc";

interface StepSideProps {
  curStep: number;
}

const StepSide = ({ curStep }: StepSideProps) => {
  const title = ["Title0", "Title1", "Title2", "Title3", "Title4"];
  return (
    <div className="sticky top-20 z-50 bg-transparent">
      <StepPagination curStep={curStep} />
      <StepDesc title={title[curStep]} />
    </div>
  );
};
export default StepSide;
