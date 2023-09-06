import { useStepPagination } from "./useStepPagination";
import { motion } from "framer-motion";

interface StepPaginationProps {
  curStep: number;
}

const StepPagination = ({ curStep }: StepPaginationProps) => {
  const { ref, animation, variants } = useStepPagination(curStep);

  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants}>
      <div className="w-28 h-2.5 px-1 justify-start items-start gap-2 inline-flex">
        {Array.from({ length: 5 }, (_, index) => index).map((step) => (
          <div
            key={step}
            className={`${
              curStep === step ? "w-5 bg-light" : "w-2.5 bg-neutral-600"
            } h-2.5 transition-all duration-300 ease-in-out rounded-md 
          `}
          />
        ))}
      </div>
      <h1 className="text-white">인베스트</h1>
    </motion.div>
  );
};

export default StepPagination;
