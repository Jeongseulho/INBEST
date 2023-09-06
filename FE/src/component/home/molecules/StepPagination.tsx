interface StepPaginationProps {
  step: number;
}
const StepPagination = ({ step }: StepPaginationProps) => {
  return (
    <div className="w-28 h-2.5 px-1 justify-start items-start gap-2 inline-flex">
      <div className="w-5 h-2.5 bg-light rounded-md" />
      <div className="w-2.5 h-2.5 bg-neutral-600 rounded-md" />
      <div className="w-2.5 h-2.5 bg-neutral-600 rounded-md" />
      <div className="w-2.5 h-2.5 bg-neutral-600 rounded-md" />
      <div className="w-2.5 h-2.5 bg-neutral-600 rounded-md" />
      <p>{step}</p>
    </div>
  );
};
export default StepPagination;
