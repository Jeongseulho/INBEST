import StepPagination from "../molecules/StepPagination";

interface StepSideProps {
  curStep: number;
}

const StepSide = ({ curStep }: StepSideProps) => {
  return (
    <div className="sticky top-20 z-50">
      <StepPagination curStep={curStep} />
    </div>
  );
};
export default StepSide;
