import { useStepPagination } from "./useStepPagination";
import { motion } from "framer-motion";
import only_logo from "../../../asset/image/only_logo.png";

interface StepPaginationProps {
  curStep: number;
}

const StepPagination = ({ curStep }: StepPaginationProps) => {
  const { ref, animation, variants } = useStepPagination(curStep);
  return (
    <motion.div ref={ref} initial="hidden" animate={animation} variants={variants}>
      <div className="w-28 h-2.5 px-1 justify-start items-start gap-2 inline-flex">
        {Array.from({ length: 6 }, (_, index) => index).map((step) =>
          step === 0 || step === 6 ? null : (
            <div
              key={step}
              className={`${
                curStep === step ? "w-5 bg-light" : "w-2.5 bg-neutral-600"
              } h-2.5 transition-all duration-300 ease-in-out rounded-md 
            `}
            />
          )
        )}
      </div>
      <div className="flex mt-4">
        <img src={only_logo} alt="" width="60px" />
        <h1 className="text-light ml-4">INBEST</h1>
      </div>
    </motion.div>
  );
};

export default StepPagination;
